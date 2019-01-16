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
package com.staarbits.io.yaml.events;

import com.staarbits.io.yaml.error.Mark;

/**
 * Base class for all events that mark the beginning of a node.
 */
public abstract class NodeEvent extends Event {

    private final String anchor;

    public NodeEvent(String anchor, Mark startMark, Mark endMark) {
        super(startMark, endMark);
        this.anchor = anchor;
    }

    /**
     * Node anchor by which this node might later be referenced by a
     * {@link AliasEvent}.
     * <p>
     * Note that {@link AliasEvent}s are by it self <code>NodeEvent</code>s and
     * use this property to indicate the referenced anchor.
     * 
     * @return Anchor of this node or <code>null</code> if no anchor is defined.
     */
    public String getAnchor() {
        return this.anchor;
    }

    @Override
    protected String getArguments() {
        return "anchor=" + anchor;
    }
}
