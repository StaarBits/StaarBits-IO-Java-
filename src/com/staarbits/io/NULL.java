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

@Importance(PriorityLevel.LOWEST)
public final class NULL
{

    /**
     * Checks whether the given <code>{@link Object object}</code> is equivalent to <code><strong>null</strong></code>.
     * @param object An object to be compared to a <code><strong>null</strong></code> value.
     * @return <code><strong>true</strong></code> if the given <code>{@link Object object}</code> is equivalent to a
     *         invalid type (<code><strong>null</strong></code>); or <code><strong>false</strong></code> if this same
     *         <code>{@link Object object}</code> is considered valid. So, different from <code><strong>null</strong></code>.
     */
    @Override
    public final boolean equals(Object object)
    {
        if (object instanceof String)
        {
            if (((String) object).equalsIgnoreCase(this.toString()))
            {
                return true;
            }
        }
        return object == null || object.equals(this);
    }

    /**
     * Gets the <code>String</code> representation of <code>{@link NULL this}</code> NULL type.
     * <p>As the <code>NULL</code> class always represents an invalid type or number, this method will return an invalid
     * string. So, it will return a <code>"null"</code> string.
     * @return The "null" value.
     */
    @Override
    public final String toString()
    {
        return "null";
    }

    /**
     * Gets the hash code for <code>{@link NULL}</code>.
     * <p>As the <code>NULL</code> class always represents an invalid type or number. The developers usually uses the
     * <code>-1</code> as a <code>{@link Number number}</code> which they consider invalid. We, this method will also
     * return <code>-1</code>, with the same view of them.
     * @return The <code>-1</code> value (an <code><b>int</b></code> which is considered invalid).
     */
    @Override
    public final int hashCode()
    {
        return -1;
    }

    /** Constructs a new {@code NULL}*/
    public static NULL getNULL()
    {
        return new NULL();
    }

    /** Constructs a new {@code NULL} */
    private NULL()
    {       }
}