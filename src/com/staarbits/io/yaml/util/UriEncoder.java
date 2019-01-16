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
package com.staarbits.io.yaml.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import com.staarbits.io.yaml.error.YAMLException;
import com.staarbits.io.yaml.external.com.google.gdata.util.common.base.Escaper;
import com.staarbits.io.yaml.external.com.google.gdata.util.common.base.PercentEscaper;

public abstract class UriEncoder {
    private static final CharsetDecoder UTF8Decoder = Charset.forName("UTF-8").newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT);
    // Include the [] chars to the SAFEPATHCHARS_URLENCODER to avoid
    // its escape as required by spec. See
    // http://yaml.org/spec/1.1/#escaping%20in%20URI/
    private static final String SAFE_CHARS = PercentEscaper.SAFEPATHCHARS_URLENCODER + "[]/";
    private static final Escaper escaper = new PercentEscaper(SAFE_CHARS, false);

    /**
     * Escape special characters with '%'
     */
    public static String encode(String uri) {
        return escaper.escape(uri);
    }

    /**
     * Decode '%'-escaped characters. Decoding fails in case of invalid UTF-8
     */
    public static String decode(ByteBuffer buff) throws CharacterCodingException {
        CharBuffer chars = UTF8Decoder.decode(buff);
        return chars.toString();
    }

    public static String decode(String buff) {
        try {
            return URLDecoder.decode(buff, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new YAMLException(e);
        }
    }
}
