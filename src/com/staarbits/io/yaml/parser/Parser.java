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
package com.staarbits.io.yaml.parser;

import com.staarbits.io.yaml.events.Event;

/**
 * This interface represents an input stream of {@link Event Events}.
 * <p>
 * The parser and the scanner form together the 'Parse' step in the loading
 * process (see chapter 3.1 of the <a href="http://yaml.org/spec/1.1/">YAML
 * Specification</a>).
 * </p>
 * 
 * @see Event
 */
public interface Parser {

    /**
     * Check if the next event is one of the given type.
     * 
     * @param choice
     *            Event ID.
     * @return <code>true</code> if the next event can be assigned to a variable
     *         of the given type. Returns <code>false</code> if no more events
     *         are available.
     * @throws ParserException
     *             Thrown in case of malformed input.
     */
    public boolean checkEvent(Event.ID choice);

    /**
     * Return the next event, but do not delete it from the stream.
     * 
     * @return The event that will be returned on the next call to
     *         {@link #getEvent}
     * @throws ParserException
     *             Thrown in case of malformed input.
     */
    public Event peekEvent();

    /**
     * Returns the next event.
     * <p>
     * The event will be removed from the stream.
     * </p>
     * 
     * @throws ParserException
     *             Thrown in case of malformed input.
     */
    public Event getEvent();
}
