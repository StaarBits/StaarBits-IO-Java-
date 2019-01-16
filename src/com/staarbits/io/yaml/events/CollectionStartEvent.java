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
 * Base class for the start events of the collection nodes.
 */
public abstract class CollectionStartEvent extends NodeEvent {
    private final String tag;
    // The implicit flag of a collection start event indicates if the tag may be
    // omitted when the collection is emitted
    private final boolean implicit;
    // flag indicates if a collection is block or flow
    private final Boolean flowStyle;

    public CollectionStartEvent(String anchor, String tag, boolean implicit, Mark startMark,
            Mark endMark, Boolean flowStyle) {
        super(anchor, startMark, endMark);
        this.tag = tag;
        this.implicit = implicit;
        this.flowStyle = flowStyle;
    }

    /**
     * Tag of this collection.
     * 
     * @return The tag of this collection, or <code>null</code> if no explicit
     *         tag is available.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * <code>true</code> if the tag can be omitted while this collection is
     * emitted.
     * 
     * @return True if the tag can be omitted while this collection is emitted.
     */
    public boolean getImplicit() {
        return this.implicit;
    }

    /**
     * <code>true</code> if this collection is in flow style, <code>false</code>
     * for block style.
     * 
     * @return If this collection is in flow style.
     */
    public Boolean getFlowStyle() {
        return this.flowStyle;
    }

    @Override
    protected String getArguments() {
        return super.getArguments() + ", tag=" + tag + ", implicit=" + implicit;
    }
}
