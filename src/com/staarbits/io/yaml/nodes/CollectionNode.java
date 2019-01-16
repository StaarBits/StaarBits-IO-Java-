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

import com.staarbits.io.yaml.error.Mark;

/**
 * Base class for the two collection types {@link MappingNode mapping} and
 * {@link SequenceNode collection}.
 */
public abstract class CollectionNode extends Node {
    private Boolean flowStyle;

    public CollectionNode(Tag tag, Mark startMark, Mark endMark, Boolean flowStyle) {
        super(tag, startMark, endMark);
        this.flowStyle = flowStyle;
    }

    /**
     * Serialization style of this collection.
     * 
     * @return <code>true</code> for flow style, <code>false</code> for block
     *         style.
     */
    public Boolean getFlowStyle() {
        return flowStyle;
    }

    public void setFlowStyle(Boolean flowStyle) {
        this.flowStyle = flowStyle;
    }

    public void setEndMark(Mark endMark) {
        this.endMark = endMark;
    }
}
