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

import com.staarbits.io.yaml.emitter.Emitter;
import com.staarbits.io.yaml.parser.Parser;
import com.staarbits.io.yaml.error.Mark;

/**
 * Basic unit of output from a {@link Parser} or input
 * of a {@link Emitter}.
 */
public abstract class Event {
    public enum ID {
        Alias, DocumentEnd, DocumentStart, MappingEnd, MappingStart, Scalar, SequenceEnd, SequenceStart, StreamEnd, StreamStart
    }

    private final Mark startMark;
    private final Mark endMark;

    public Event(Mark startMark, Mark endMark) {
        this.startMark = startMark;
        this.endMark = endMark;
    }

    public String toString() {
        return "<" + this.getClass().getName() + "(" + getArguments() + ")>";
    }

    public Mark getStartMark() {
        return startMark;
    }

    public Mark getEndMark() {
        return endMark;
    }

    /**
     * @see "__repr__ for Event in PyYAML"
     */
    protected String getArguments() {
        return "";
    }

    public abstract boolean is(Event.ID id);

    /*
     * for tests only
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            return toString().equals(obj.toString());
        } else {
            return false;
        }
    }

    /*
     * for tests only
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
