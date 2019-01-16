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

import java.util.Arrays;

public final class Constant {
    private final static String ALPHA_S = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-_";

    private final static String LINEBR_S = "\n\u0085\u2028\u2029";
    private final static String FULL_LINEBR_S = "\r" + LINEBR_S;
    private final static String NULL_OR_LINEBR_S = "\0" + FULL_LINEBR_S;
    private final static String NULL_BL_LINEBR_S = " " + NULL_OR_LINEBR_S;
    private final static String NULL_BL_T_LINEBR_S = "\t" + NULL_BL_LINEBR_S;
    private final static String NULL_BL_T_S = "\0 \t";
    private final static String URI_CHARS_S = ALPHA_S + "-;/?:@&=+$,_.!~*\'()[]%";

    public final static Constant LINEBR = new Constant(LINEBR_S);
    public final static Constant FULL_LINEBR = new Constant(FULL_LINEBR_S);
    public final static Constant NULL_OR_LINEBR = new Constant(NULL_OR_LINEBR_S);
    public final static Constant NULL_BL_LINEBR = new Constant(NULL_BL_LINEBR_S);
    public final static Constant NULL_BL_T_LINEBR = new Constant(NULL_BL_T_LINEBR_S);
    public final static Constant NULL_BL_T = new Constant(NULL_BL_T_S);
    public final static Constant URI_CHARS = new Constant(URI_CHARS_S);

    public final static Constant ALPHA = new Constant(ALPHA_S);

    private String content;
    boolean[] contains = new boolean[128];
    boolean noASCII = false;

    private Constant(String content) {
        Arrays.fill(contains, false);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);
            if (ch < 128)
                contains[ch] = true;
            else
                sb.append(ch);
        }
        if (sb.length() > 0) {
            noASCII = true;
            this.content = sb.toString();
        }
    }

    public boolean has(char ch) {
        return (ch < 128) ? contains[ch] : noASCII && content.indexOf(ch, 0) != -1;
    }

    public boolean hasNo(char ch) {
        return !has(ch);
    }

    public boolean has(char ch, String additional) {
        return has(ch) || additional.indexOf(ch, 0) != -1;
    }

    public boolean hasNo(char ch, String additional) {
        return !has(ch, additional);
    }
}
