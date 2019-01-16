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

package com.staarbits.io.http;

public enum RequestFail
{

    /** The RESPONSE type of the request-fail */
    RESPONSE("Response"),

    /** The TIMEOUT type of the request-fail */
    TIMEOUT("Timeout")

    ;

    /** The string representation */
    private String toString;

    /** Constructs a new {@code RequestFail} */
    /* private */ RequestFail(String toString)
    {
        this.toString = toString;
    }

    /**
     * Gets the <code>String</code> representation of <code>{@link RequestFail this}</code> RequestFail.
     * @return The string representation.
     */
    @Override
    public String toString()
    {
        return this.toString;
    }
}