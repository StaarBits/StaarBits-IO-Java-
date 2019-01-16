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
package com.staarbits.io.yaml.reader;

import com.staarbits.io.yaml.error.YAMLException;

public class ReaderException extends YAMLException {
    private static final long serialVersionUID = 8710781187529689083L;
    private final String name;
    private final char character;
    private final int position;

    public ReaderException(String name, int position, char character, String message) {
        super(message);
        this.name = name;
        this.character = character;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public char getCharacter() {
        return character;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "unacceptable character '" + character + "' (0x"
                + Integer.toHexString((int) character).toUpperCase() + ") " + getMessage()
                + "\nin \"" + name + "\", position " + position;
    }
}
