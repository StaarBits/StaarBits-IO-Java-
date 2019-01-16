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
package com.staarbits.io.yaml.nodes;

import java.util.List;

import com.staarbits.io.yaml.error.Mark;

/**
 * Represents a map.
 * <p>
 * A map is a collection of unsorted key-value pairs.
 * </p>
 */
public class MappingNode extends CollectionNode {
    private List<NodeTuple> value;
    private boolean merged = false;

    public MappingNode(Tag tag, boolean resolved, List<NodeTuple> value, Mark startMark,
            Mark endMark, Boolean flowStyle) {
        super(tag, startMark, endMark, flowStyle);
        if (value == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = value;
        this.resolved = resolved;
    }

    public MappingNode(Tag tag, List<NodeTuple> value, Boolean flowStyle) {
        this(tag, true, value, null, null, flowStyle);
    }

    @Override
    public NodeId getNodeId() {
        return NodeId.mapping;
    }

    /**
     * Returns the entries of this map.
     * 
     * @return List of entries.
     */
    public List<NodeTuple> getValue() {
        return value;
    }

    public void setValue(List<NodeTuple> merge) {
        value = merge;
    }

    public void setOnlyKeyType(Class<? extends Object> keyType) {
        for (NodeTuple nodes : value) {
            nodes.getKeyNode().setType(keyType);
        }
    }

    public void setTypes(Class<? extends Object> keyType, Class<? extends Object> valueType) {
        for (NodeTuple nodes : value) {
            nodes.getValueNode().setType(valueType);
            nodes.getKeyNode().setType(keyType);
        }
    }

    @Override
    public String toString() {
        String values;
        StringBuilder buf = new StringBuilder();
        for (NodeTuple node : getValue()) {
            buf.append("{ key=");
            buf.append(node.getKeyNode());
            buf.append("; value=");
            if (node.getValueNode() instanceof CollectionNode) {
                // to avoid overflow in case of recursive structures
                buf.append(System.identityHashCode(node.getValueNode()));
            } else {
                buf.append(node.toString());
            }
            buf.append(" }");
        }
        values = buf.toString();
        return "<" + this.getClass().getName() + " (tag=" + getTag() + ", values=" + values + ")>";
    }

    /**
     * @param merged
     *            - true if map contains merge node
     */
    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    /**
     * @return true if map contains merge node
     */
    public boolean isMerged() {
        return merged;
    }
}
