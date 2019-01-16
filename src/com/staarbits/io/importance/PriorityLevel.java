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

package com.staarbits.io.importance;

public enum PriorityLevel
{

    /** The "Highest" importance priority */
    HIGHEST("Highest"),

    /** The "Higher" importance priority */
    HIGHER("Higher"),

    /** The "High" importance priority */
    HIGH("High"),

    /** The "Common" importance priority */
    COMMON("Common"),

    /** The "Low" importance priority */
    LOW("Low"),

    /** The "Lower" importance priority */
    LOWER("Lower"),

    /** The "Lowest" importance priority */
    LOWEST("Lowest")

    ;

    /** The string representation */
    private String toString;

    /** Constructs a new {@code PriorityLevel} */
    /* private */ PriorityLevel(String toString)
    {
        this.toString = toString;
    }

    /**
     * Gets the <code>String</code> representation of <code>{@link PriorityLevel this}</code> PriorityLevel.
     * @return The to string representation.
     */
    @Override
    public String toString()
    {
        return this.toString;
    }
}