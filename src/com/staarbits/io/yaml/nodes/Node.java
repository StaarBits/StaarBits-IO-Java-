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

import com.staarbits.io.yaml.composer.Composer;
import com.staarbits.io.yaml.error.Mark;
import com.staarbits.io.yaml.resolver.Resolver;

/**
 * Base class for all nodes.
 * <p>
 * The nodes form the node-graph described in the <a
 * href="http://yaml.org/spec/1.1/">YAML Specification</a>.
 * </p>
 * <p>
 * While loading, the node graph is usually created by the
 * {@link Composer}, and later transformed into
 * application specific Java classes by the classes from the
 * {@link org.yaml.snakeyaml.constructor} package.
 * </p>
 */
public abstract class Node {
    private Tag tag;
    private Mark startMark;
    protected Mark endMark;
    private Class<? extends Object> type;
    private boolean twoStepsConstruction;
    /**
     * true when the tag is assigned by the resolver
     */
    protected boolean resolved;
    protected Boolean useClassConstructor;

    public Node(Tag tag, Mark startMark, Mark endMark) {
        setTag(tag);
        this.startMark = startMark;
        this.endMark = endMark;
        this.type = Object.class;
        this.twoStepsConstruction = false;
        this.resolved = true;
        this.useClassConstructor = null;
    }

    /**
     * Tag of this node.
     * <p>
     * Every node has a tag assigned. The tag is either local or global.
     * 
     * @return Tag of this node.
     */
    public Tag getTag() {
        return this.tag;
    }

    public Mark getEndMark() {
        return endMark;
    }

    /**
     * For error reporting.
     * 
     * @see "class variable 'id' in PyYAML"
     * @return scalar, sequence, mapping
     */
    public abstract NodeId getNodeId();

    public Mark getStartMark() {
        return startMark;
    }

    public void setTag(Tag tag) {
        if (tag == null) {
            throw new NullPointerException("tag in a Node is required.");
        }
        this.tag = tag;
    }

    /**
     * Two Nodes are never equal.
     */
    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Class<? extends Object> getType() {
        return type;
    }

    public void setType(Class<? extends Object> type) {
        if (!type.isAssignableFrom(this.type)) {
            this.type = type;
        }
    }

    public void setTwoStepsConstruction(boolean twoStepsConstruction) {
        this.twoStepsConstruction = twoStepsConstruction;
    }

    /**
     * Indicates if this node must be constructed in two steps.
     * <p>
     * Two-step construction is required whenever a node is a child (direct or
     * indirect) of it self. That is, if a recursive structure is build using
     * anchors and aliases.
     * </p>
     * <p>
     * Set by {@link Composer}, used during the
     * construction process.
     * </p>
     * <p>
     * Only relevant during loading.
     * </p>
     * 
     * @return <code>true</code> if the node is self referenced.
     */
    public boolean isTwoStepsConstruction() {
        return twoStepsConstruction;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    public boolean useClassConstructor() {
        if (useClassConstructor == null) {
            if (!tag.isSecondary() && isResolved() && !Object.class.equals(type)
                    && !tag.equals(Tag.NULL)) {
                return true;
            } else if (tag.isCompatible(getType())) {
                // the tag is compatible with the runtime class
                // the tag will be ignored
                return true;
            } else {
                return false;
            }
        }
        return useClassConstructor.booleanValue();
    }

    public void setUseClassConstructor(Boolean useClassConstructor) {
        this.useClassConstructor = useClassConstructor;
    }

    /**
     * Indicates if the tag was added by
     * {@link Resolver}.
     * 
     * @return <code>true</code> if the tag of this node was resolved</code>
     */
    public boolean isResolved() {
        return resolved;
    }
}
