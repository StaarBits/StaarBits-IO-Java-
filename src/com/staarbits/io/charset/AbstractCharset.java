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

package com.staarbits.io.charset;

import com.staarbits.io.Charset;

/** The <code>AbstractCharset</code> is the first implementation of <code>Charset</code>, it is extended by all other sub-classes */
public abstract class AbstractCharset implements Charset, Comparable<Charset>
{

    /** An array of the characters which are allowed for this charset */
    protected char[] array;

    /** A string representation of this charset (it is usually the name of) */
    private final String toString;

    /** Constructs a new {@code AbstractCharset} */
    protected AbstractCharset(char[] array, String toString)
    {
        this.array = array;
        this.toString = toString;
    }

    /**
     * Compares <code>{@link AbstractCharset this}</code> AbstractCharset with the given <code>{@link Charset charset}</code> in
     * order.
     * @param charset The charset to be compared to this one.
     * @return A negative number whether <code>{@link AbstractCharset this}</code> AbstractCharset is less than; <code>0</code>
     *         whether <code>{@link AbstractCharset this}</code> AbstractCharset is equal to <code>{@link Charset charset}</code>;
     *         or a positive number whether <code>{@link AbstractCharset this}</code> AbstractCharset is greater than the given
     *         <code>{@link Charset charset}</code>.
     */
    @Override
    public abstract int compareTo(Charset charset);

    /**
     * Checks whether the given <code>character</code> is considered a legal character by <code>{@link AbstractCharset this}</code>
     * AbstractCharset.
     * @param character The character to be checked.
     * @return <code><strong>true</strong></code> if <code>{@link AbstractCharset this}</code> AbstractCharset is considered a
     *         valid <code><b>char</b></code>; or <code><strong>false</strong></code> if it is not.
     */
    public final boolean considers(char character)
    {
        for (char eachCharacter : this.array)
        {
            if (eachCharacter == character)
            {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public char[] characters()
    {
        return this.array;
    }

    /** {@inheritDoc} */
    @Override
    public String toString()
    {
        return this.toString;
    }
}