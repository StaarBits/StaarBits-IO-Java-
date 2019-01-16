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
package com.staarbits.io.yaml.scanner;

import com.staarbits.io.yaml.error.Mark;

/**
 * Simple keys treatment.
 * <p>
 * Helper class for {@link ScannerImpl}.
 * </p>
 * 
 * @see ScannerImpl
 */
final class SimpleKey {
    private int tokenNumber;
    private boolean required;
    private int index;
    private int line;
    private int column;
    private Mark mark;

    public SimpleKey(int tokenNumber, boolean required, int index, int line, int column, Mark mark) {
        this.tokenNumber = tokenNumber;
        this.required = required;
        this.index = index;
        this.line = line;
        this.column = column;
        this.mark = mark;
    }

    public int getTokenNumber() {
        return this.tokenNumber;
    }

    public int getColumn() {
        return this.column;
    }

    public Mark getMark() {
        return mark;
    }

    public int getIndex() {
        return index;
    }

    public int getLine() {
        return line;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        return "SimpleKey - tokenNumber=" + tokenNumber + " required=" + required + " index="
                + index + " line=" + line + " column=" + column;
    }
}
