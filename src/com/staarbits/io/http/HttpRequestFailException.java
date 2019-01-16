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

public class HttpRequestFailException extends Exception
{

    /** The type of this request-fail */
    private RequestFail requestFail;

    /** The error code */
    private int responseCode;

    /** The response */
    private String response;

    /** Constructs a new {@code HttpRequestFailException} */
    public HttpRequestFailException(int responseCode, String response)
    {
        super();
        this.requestFail = RequestFail.RESPONSE;
        this.responseCode = responseCode;
        this.response = response;
    }

    /** Constructs a new {@code HttpRequestFailException} */
    public HttpRequestFailException(RequestFail fail)
    {
        this.requestFail = fail;
    }

    /**
     * Changes the error code.
     * @param code The error code.
     */
    public void changeErrorCode(int code)
    {
        this.responseCode = code;
    }

    /**
     * Changes the response.
     * @param response The response.
     */
    public void changeResponse(String response)
    {
        this.response = response;
    }

    /**
     * Gets the type of the <code>{@link RequestFail request fail}</code>.
     * @return The request fail.
     */
    public RequestFail type()
    {
        return this.requestFail;
    }

    /**
     * Gets the <code><b>int</b></code> value which represents the code of the error which has been thrown by <code>{@link
     * HttpRequestFailException this}</code> HttpRequestFailException.
     * @return The error code.
     */
    public int code()
    {
        return this.responseCode;
    }

    /**
     * Gets a <code>String</code> which is equivalent to the <code>response</code>.
     * @return The response.
     */
    public String response()
    {
        return this.response;
    }
}