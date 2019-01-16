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

import com.staarbits.io.yaml.tokens.Token;

/**
 * This interface represents an input stream of {@link Token Tokens}.
 * <p>
 * The parser and the scanner form together the 'Parse' step in the loading
 * process (see chapter 3.1 of the <a href="http://yaml.org/spec/1.1/">YAML
 * Specification</a>).
 * </p>
 * 
 * @see Token
 */
public interface Scanner {

    /**
     * Check if the next token is one of the given types.
     * 
     * @param choices
     *            token IDs.
     * @return <code>true</code> if the next token can be assigned to a variable
     *         of at least one of the given types. Returns <code>false</code> if
     *         no more tokens are available.
     * @throws ScannerException
     *             Thrown in case of malformed input.
     */
    boolean checkToken(Token.ID... choices);

    /**
     * Return the next token, but do not delete it from the stream.
     * 
     * @return The token that will be returned on the next call to
     *         {@link #getToken}
     * @throws ScannerException
     *             Thrown in case of malformed input.
     */
    Token peekToken();

    /**
     * Returns the next token.
     * <p>
     * The token will be removed from the stream.
     * </p>
     * 
     * @throws ScannerException
     *             Thrown in case of malformed input.
     */
    Token getToken();
}
