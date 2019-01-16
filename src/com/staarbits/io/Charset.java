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

package com.staarbits.io;

import com.staarbits.io.importance.Importance;
import com.staarbits.io.importance.PriorityLevel;

import java.io.Serializable;

/**
 * The "Charset" is an <code><strong>interface</strong></code> which represents a literal charset.
 */
@Importance(PriorityLevel.HIGHEST)
public interface Charset extends Serializable
{

    /**
     * Gets a <code><strong>char</strong></code> array of <code>{@link Charset this}</code> Charset.
     * @return An array of the characters.
     */
    char[] characters();

    /**
     * Gets the <code>String</code> representation of <code>{@link Charset this}</code> Charset.
     * <p>It always represents the literal "Charset", for example, if <code>{@link Charset this}</code> Charset is the
     * UFT-8, the <code>toString()</code> would return <code>"UTF-8"</code>.
     * @return The string representation.
     */
    @Override
    String toString();
}