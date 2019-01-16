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

/**
 * The "CharsetUTF16" is an extension of <code>{@link AbstractCharset}</code> and an implementation of <code>{@link Charset}</code>
 * which is equivalent to "UTF-16".
 */
public class CharsetUTF16 extends AbstractCharset implements Charset
{

    /** Constructs a new {@code CharsetUTF8} */
    public CharsetUTF16()
    {
        super(new char[]
                {
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 'v', 'u', 'w',
                        'x', 'y', 'z', /* Upper */ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'K', 'L', 'M', 'N', 'O', 'Q',
                        'P', 'R', 'S', 'V', 'U', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '\'',
                        '!', '@', '#', '$', '%', '¨', '&', '*', '(', ')', '-', '_', '+', '=', '[', ']', '{', '}', 'ª', 'º',
                        ';', ':', '\\', '|', '"', ',', '.', '?', '/', '°'
                }, "UTF-16");
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(Charset charset)
    {
        if (charset instanceof CharsetUTF16)
        {
            return 0;
        } else if (charset.characters() == super.characters())
        {
            return 0;
        }
        return -1;
    }
}