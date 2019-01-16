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

/**
 * The implicit flag of a scalar event is a pair of boolean values that indicate
 * if the tag may be omitted when the scalar is emitted in a plain and non-plain
 * style correspondingly.
 * 
 * @see <a href="http://pyyaml.org/wiki/PyYAMLDocumentation#Events">Events</a>
 */
public class ImplicitTuple {
    private final boolean plain;
    private final boolean nonPlain;

    public ImplicitTuple(boolean plain, boolean nonplain) {
        this.plain = plain;
        this.nonPlain = nonplain;
    }

    /**
     * @return true when tag may be omitted when the scalar is emitted in a
     *         plain style.
     */
    public boolean canOmitTagInPlainScalar() {
        return plain;
    }

    /**
     * @return true when tag may be omitted when the scalar is emitted in a
     *         non-plain style.
     */
    public boolean canOmitTagInNonPlainScalar() {
        return nonPlain;
    }

    public boolean bothFalse() {
        return !plain && !nonPlain;
    }

    @Override
    public String toString() {
        return "implicit=[" + plain + ", " + nonPlain + "]";
    }
}
