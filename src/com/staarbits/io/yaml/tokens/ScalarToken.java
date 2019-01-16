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
package com.staarbits.io.yaml.tokens;

import com.staarbits.io.yaml.error.Mark;

public final class ScalarToken extends Token {
    private final String value;
    private final boolean plain;
    private final char style;

    public ScalarToken(String value, Mark startMark, Mark endMark, boolean plain) {
        this(value, plain, startMark, endMark, (char) 0);
    }

    public ScalarToken(String value, boolean plain, Mark startMark, Mark endMark, char style) {
        super(startMark, endMark);
        this.value = value;
        this.plain = plain;
        this.style = style;
    }

    public boolean getPlain() {
        return this.plain;
    }

    public String getValue() {
        return this.value;
    }

    public char getStyle() {
        return this.style;
    }

    @Override
    protected String getArguments() {
        return "value=" + value + ", plain=" + plain + ", style=" + style;
    }

    @Override
    public Token.ID getTokenId() {
        return ID.Scalar;
    }
}
