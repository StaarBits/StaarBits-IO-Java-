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
package com.staarbits.io.yaml.serializer;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.staarbits.io.yaml.DumperOptions;
import com.staarbits.io.yaml.emitter.Emitable;
import com.staarbits.io.yaml.events.AliasEvent;
import com.staarbits.io.yaml.events.DocumentEndEvent;
import com.staarbits.io.yaml.events.DocumentStartEvent;
import com.staarbits.io.yaml.events.ImplicitTuple;
import com.staarbits.io.yaml.events.MappingEndEvent;
import com.staarbits.io.yaml.events.MappingStartEvent;
import com.staarbits.io.yaml.events.ScalarEvent;
import com.staarbits.io.yaml.events.SequenceEndEvent;
import com.staarbits.io.yaml.events.SequenceStartEvent;
import com.staarbits.io.yaml.events.StreamEndEvent;
import com.staarbits.io.yaml.events.StreamStartEvent;
import com.staarbits.io.yaml.nodes.AnchorNode;
import com.staarbits.io.yaml.nodes.CollectionNode;
import com.staarbits.io.yaml.nodes.MappingNode;
import com.staarbits.io.yaml.nodes.Node;
import com.staarbits.io.yaml.nodes.NodeId;
import com.staarbits.io.yaml.nodes.NodeTuple;
import com.staarbits.io.yaml.nodes.ScalarNode;
import com.staarbits.io.yaml.nodes.SequenceNode;
import com.staarbits.io.yaml.nodes.Tag;
import com.staarbits.io.yaml.resolver.Resolver;

public final class Serializer {
    private final Emitable emitter;
    private final Resolver resolver;
    private boolean explicitStart;
    private boolean explicitEnd;
    private DumperOptions.Version useVersion;
    private Map<String, String> useTags;
    private Set<Node> serializedNodes;
    private Map<Node, String> anchors;
    private int lastAnchorId;
    private Boolean closed;
    private Tag explicitRoot;

    public Serializer(Emitable emitter, Resolver resolver, DumperOptions opts, Tag rootTag) {
        this.emitter = emitter;
        this.resolver = resolver;
        this.explicitStart = opts.isExplicitStart();
        this.explicitEnd = opts.isExplicitEnd();
        if (opts.getVersion() != null) {
            this.useVersion = opts.getVersion();
        }
        this.useTags = opts.getTags();
        this.serializedNodes = new HashSet<Node>();
        this.anchors = new HashMap<Node, String>();
        this.lastAnchorId = 0;
        this.closed = null;
        this.explicitRoot = rootTag;
    }

    public void open() throws IOException {
        if (closed == null) {
            this.emitter.emit(new StreamStartEvent(null, null));
            this.closed = Boolean.FALSE;
        } else if (Boolean.TRUE.equals(closed)) {
            throw new SerializerException("serializer is closed");
        } else {
            throw new SerializerException("serializer is already opened");
        }
    }

    public void close() throws IOException {
        if (closed == null) {
            throw new SerializerException("serializer is not opened");
        } else if (!Boolean.TRUE.equals(closed)) {
            this.emitter.emit(new StreamEndEvent(null, null));
            this.closed = Boolean.TRUE;
        }
    }

    public void serialize(Node node) throws IOException {
        if (closed == null) {
            throw new SerializerException("serializer is not opened");
        } else if (closed) {
            throw new SerializerException("serializer is closed");
        }
        this.emitter.emit(new DocumentStartEvent(null, null, this.explicitStart, this.useVersion,
                useTags));
        anchorNode(node);
        if (explicitRoot != null) {
            node.setTag(explicitRoot);
        }
        serializeNode(node, null);
        this.emitter.emit(new DocumentEndEvent(null, null, this.explicitEnd));
        this.serializedNodes.clear();
        this.anchors.clear();
        this.lastAnchorId = 0;
    }

    private void anchorNode(Node node) {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode) node).getRealNode();
        }
        if (this.anchors.containsKey(node)) {
            String anchor = this.anchors.get(node);
            if (null == anchor) {
                anchor = generateAnchor();
                this.anchors.put(node, anchor);
            }
        } else {
            this.anchors.put(node, null);
            switch (node.getNodeId()) {
            case sequence:
                SequenceNode seqNode = (SequenceNode) node;
                List<Node> list = seqNode.getValue();
                for (Node item : list) {
                    anchorNode(item);
                }
                break;
            case mapping:
                MappingNode mnode = (MappingNode) node;
                List<NodeTuple> map = mnode.getValue();
                for (NodeTuple object : map) {
                    Node key = object.getKeyNode();
                    Node value = object.getValueNode();
                    anchorNode(key);
                    anchorNode(value);
                }
                break;
            }
        }
    }

    private String generateAnchor() {
        this.lastAnchorId++;
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumIntegerDigits(3);
        format.setMaximumFractionDigits(0);// issue 172
        format.setGroupingUsed(false);
        String anchorId = format.format(this.lastAnchorId);
        return "id" + anchorId;
    }

    private void serializeNode(Node node, Node parent) throws IOException {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode) node).getRealNode();
        }
        String tAlias = this.anchors.get(node);
        if (this.serializedNodes.contains(node)) {
            this.emitter.emit(new AliasEvent(tAlias, null, null));
        } else {
            this.serializedNodes.add(node);
            switch (node.getNodeId()) {
            case scalar:
                ScalarNode scalarNode = (ScalarNode) node;
                Tag detectedTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);
                Tag defaultTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false);
                ImplicitTuple tuple = new ImplicitTuple(node.getTag().equals(detectedTag), node
                        .getTag().equals(defaultTag));
                ScalarEvent event = new ScalarEvent(tAlias, node.getTag().getValue(), tuple,
                        scalarNode.getValue(), null, null, scalarNode.getStyle());
                this.emitter.emit(event);
                break;
            case sequence:
                SequenceNode seqNode = (SequenceNode) node;
                boolean implicitS = node.getTag().equals(this.resolver.resolve(NodeId.sequence,
                        null, true));
                this.emitter.emit(new SequenceStartEvent(tAlias, node.getTag().getValue(),
                        implicitS, null, null, seqNode.getFlowStyle()));
                List<Node> list = seqNode.getValue();
                for (Node item : list) {
                    serializeNode(item, node);
                }
                this.emitter.emit(new SequenceEndEvent(null, null));
                break;
            default:// instance of MappingNode
                Tag implicitTag = this.resolver.resolve(NodeId.mapping, null, true);
                boolean implicitM = node.getTag().equals(implicitTag);
                this.emitter.emit(new MappingStartEvent(tAlias, node.getTag().getValue(),
                        implicitM, null, null, ((CollectionNode) node).getFlowStyle()));
                MappingNode mnode = (MappingNode) node;
                List<NodeTuple> map = mnode.getValue();
                for (NodeTuple row : map) {
                    Node key = row.getKeyNode();
                    Node value = row.getValueNode();
                    serializeNode(key, mnode);
                    serializeNode(value, mnode);
                }
                this.emitter.emit(new MappingEndEvent(null, null));
            }
        }
    }
}
