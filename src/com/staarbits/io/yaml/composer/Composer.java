/*
 * Copyright (c) 2019. StaarBits Network & Development says that this file is under the StaarBits Global Copyright (SGC).
 * Every file which contains this annotation as one of the first things written is under the SGC protocol.
 * The SGC (StaarBits Global Copyright) demonstrates that the file which has it cannot be copied and pasted as
 * an annotation file by anyone else who has not gotten the Owner rank at StaarBits. So... The most powerful rank
 * at the executive can spread this file. If someone uses this file without the permission given by the executive
 * administration, this same person will be able to be sued by the SEA (StaarBits Executive Administration); if
 * someone who works at StaarBits spreads this file, this person will as sooner as possible be removed from our
 * team and (s)he will also be able to response a lawsuit as well.
 */
package com.staarbits.io.yaml.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.staarbits.io.yaml.resolver.Resolver;
import com.staarbits.io.yaml.events.AliasEvent;
import com.staarbits.io.yaml.events.Event;
import com.staarbits.io.yaml.events.MappingStartEvent;
import com.staarbits.io.yaml.events.NodeEvent;
import com.staarbits.io.yaml.events.ScalarEvent;
import com.staarbits.io.yaml.events.SequenceStartEvent;
import com.staarbits.io.yaml.nodes.MappingNode;
import com.staarbits.io.yaml.nodes.Node;
import com.staarbits.io.yaml.nodes.NodeId;
import com.staarbits.io.yaml.nodes.NodeTuple;
import com.staarbits.io.yaml.nodes.ScalarNode;
import com.staarbits.io.yaml.nodes.SequenceNode;
import com.staarbits.io.yaml.nodes.Tag;
import com.staarbits.io.yaml.parser.Parser;

/**
 * Creates a node graph from parser events.
 * <p>
 * Corresponds to the 'Compose' step as described in chapter 3.1 of the <a
 * href="http://yaml.org/spec/1.1/">YAML Specification</a>.
 * </p>
 */
public class Composer {
    private final Parser parser;
    private final Resolver resolver;
    private final Map<String, Node> anchors;
    private final Set<Node> recursiveNodes;

    public Composer(Parser parser, Resolver resolver) {
        this.parser = parser;
        this.resolver = resolver;
        this.anchors = new HashMap<String, Node>();
        this.recursiveNodes = new HashSet<Node>();
    }

    /**
     * Checks if further documents are available.
     * 
     * @return <code>true</code> if there is at least one more document.
     */
    public boolean checkNode() {
        // Drop the STREAM-START event.
        if (parser.checkEvent(Event.ID.StreamStart)) {
            parser.getEvent();
        }
        // If there are more documents available?
        return !parser.checkEvent(Event.ID.StreamEnd);
    }

    /**
     * Reads and composes the next document.
     * 
     * @return The root node of the document or <code>null</code> if no more
     *         documents are available.
     */
    public Node getNode() {
        // Get the root node of the next document.
        if (!parser.checkEvent(Event.ID.StreamEnd)) {
            return composeDocument();
        } else {
            return null;
        }
    }

    /**
     * Reads a document from a source that contains only one document.
     * <p>
     * If the stream contains more than one document an exception is thrown.
     * </p>
     * 
     * @return The root node of the document or <code>null</code> if no document
     *         is available.
     */
    public Node getSingleNode() {
        // Drop the STREAM-START event.
        parser.getEvent();
        // Compose a document if the stream is not empty.
        Node document = null;
        if (!parser.checkEvent(Event.ID.StreamEnd)) {
            document = composeDocument();
        }
        // Ensure that the stream contains no more documents.
        if (!parser.checkEvent(Event.ID.StreamEnd)) {
            Event event = parser.getEvent();
            throw new ComposerException("expected a single document in the stream",
                    document.getStartMark(), "but found another document", event.getStartMark());
        }
        // Drop the STREAM-END event.
        parser.getEvent();
        return document;
    }

    private Node composeDocument() {
        // Drop the DOCUMENT-START event.
        parser.getEvent();
        // Compose the root node.
        Node node = composeNode(null);
        // Drop the DOCUMENT-END event.
        parser.getEvent();
        this.anchors.clear();
        recursiveNodes.clear();
        return node;
    }

    private Node composeNode(Node parent) {
        recursiveNodes.add(parent);
        if (parser.checkEvent(Event.ID.Alias)) {
            AliasEvent event = (AliasEvent) parser.getEvent();
            String anchor = event.getAnchor();
            if (!anchors.containsKey(anchor)) {
                throw new ComposerException(null, null, "found undefined alias " + anchor,
                        event.getStartMark());
            }
            Node result = anchors.get(anchor);
            if (recursiveNodes.remove(result)) {
                result.setTwoStepsConstruction(true);
            }
            return result;
        }
        NodeEvent event = (NodeEvent) parser.peekEvent();
        String anchor = null;
        anchor = event.getAnchor();
        // the check for duplicate anchors has been removed (issue 174)
        Node node = null;
        if (parser.checkEvent(Event.ID.Scalar)) {
            node = composeScalarNode(anchor);
        } else if (parser.checkEvent(Event.ID.SequenceStart)) {
            node = composeSequenceNode(anchor);
        } else {
            node = composeMappingNode(anchor);
        }
        recursiveNodes.remove(parent);
        return node;
    }

    private Node composeScalarNode(String anchor) {
        ScalarEvent ev = (ScalarEvent) parser.getEvent();
        String tag = ev.getTag();
        boolean resolved = false;
        Tag nodeTag;
        if (tag == null || tag.equals("!")) {
            nodeTag = resolver.resolve(NodeId.scalar, ev.getValue(), ev.getImplicit()
                    .canOmitTagInPlainScalar());
            resolved = true;
        } else {
            nodeTag = new Tag(tag);
        }
        Node node = new ScalarNode(nodeTag, resolved, ev.getValue(), ev.getStartMark(),
                ev.getEndMark(), ev.getStyle());
        if (anchor != null) {
            anchors.put(anchor, node);
        }
        return node;
    }

    private Node composeSequenceNode(String anchor) {
        SequenceStartEvent startEvent = (SequenceStartEvent) parser.getEvent();
        String tag = startEvent.getTag();
        Tag nodeTag;
        boolean resolved = false;
        if (tag == null || tag.equals("!")) {
            nodeTag = resolver.resolve(NodeId.sequence, null, startEvent.getImplicit());
            resolved = true;
        } else {
            nodeTag = new Tag(tag);
        }
        final ArrayList<Node> children = new ArrayList<Node>();
        SequenceNode node = new SequenceNode(nodeTag, resolved, children,
                startEvent.getStartMark(), null, startEvent.getFlowStyle());
        if (anchor != null) {
            anchors.put(anchor, node);
        }
        while (!parser.checkEvent(Event.ID.SequenceEnd)) {
            children.add(composeNode(node));
        }
        Event endEvent = parser.getEvent();
        node.setEndMark(endEvent.getEndMark());
        return node;
    }

    private Node composeMappingNode(String anchor) {
        MappingStartEvent startEvent = (MappingStartEvent) parser.getEvent();
        String tag = startEvent.getTag();
        Tag nodeTag;
        boolean resolved = false;
        if (tag == null || tag.equals("!")) {
            nodeTag = resolver.resolve(NodeId.mapping, null, startEvent.getImplicit());
            resolved = true;
        } else {
            nodeTag = new Tag(tag);
        }

        final List<NodeTuple> children = new ArrayList<NodeTuple>();
        MappingNode node = new MappingNode(nodeTag, resolved, children, startEvent.getStartMark(),
                null, startEvent.getFlowStyle());
        if (anchor != null) {
            anchors.put(anchor, node);
        }
        while (!parser.checkEvent(Event.ID.MappingEnd)) {
            Node itemKey = composeNode(node);
            if (itemKey.getTag().equals(Tag.MERGE)) {
                node.setMerged(true);
            }
            Node itemValue = composeNode(node);
            children.add(new NodeTuple(itemKey, itemValue));
        }
        Event endEvent = parser.getEvent();
        node.setEndMark(endEvent.getEndMark());
        return node;
    }
}
