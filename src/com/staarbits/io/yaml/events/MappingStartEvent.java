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
 * Marks the beginning of a mapping node.
 * <p>
 * This event is followed by a number of key value pairs. <br>
 * The pairs are not in any particular order. However, the value always directly
 * follows the corresponding key. <br>
 * After the key value pairs follows a {@link MappingEndEvent}.
 * </p>
 * <p>
 * There must be an even number of node events between the start and end event.
 * </p>
 * 
 * @see MappingEndEvent
 */
public final class MappingStartEvent extends CollectionStartEvent {
    public MappingStartEvent(String anchor, String tag, boolean implicit, Mark startMark,
            Mark endMark, Boolean flowStyle) {
        super(anchor, tag, implicit, startMark, endMark, flowStyle);
    }

    @Override
    public boolean is(Event.ID id) {
        return ID.MappingStart == id;
    }
}
