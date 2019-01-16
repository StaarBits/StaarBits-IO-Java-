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

import java.net.URL;

public abstract class HttpMethodExecutor<T>
{

    /** The method to be executed */
    private final String method;

    /** The timeout */
    private int timeout;

    /** The URL */
    protected final URL url;

    private boolean useCache = false;

    /** Constructs a new {@code HttpMethodExecutor} */
    protected HttpMethodExecutor(String method, int timeout, URL url)
    {
        this.method = method;
        this.timeout = timeout;
        this.url = url;
    }

    /**
     * Checks whether the <code>{@link HttpMethodExecutor this}</code> HttpMethodExecutor is using the cache or not.
     * @return <code><strong>true</strong></code> if <code>{@link HttpMethodExecutor this}</code> HttpMethodExecutor uses
     *         the cache option; <code><strong>false</strong></code> otherwise.
     */
    public boolean useCaches()
    {
        return this.useCache;
    }

    /**
     * Sets the <code>useCache</code> value to the given boolean.
     * @param useCache The boolean which determines if this method needs to use the cache or not.
     */
    public void useCache(boolean useCache)
    {
        this.useCache = useCache;
    }

    /**
     * Executes <code>{@link HttpMethodExecutor this}</code> HttpMethodExecutor.
     * <p>The method is returned the result with the execution made by this own method. Or <code><strong>null</strong></code>
     * if the <code>{@link #timeout() timeout}</code> reaches the expire time.
     * @return The <code>T</code> as the resulting value.
     */
    public abstract T execute() throws HttpRequestFailException;

    /**
     * Gets the method which is executed to <code>{@link HttpMethodExecutor this}</code> HttpMethodExecutor.
     * @return The method.
     */
    public final String method()
    {
        return this.method;
    }

    /**
     * Gets the timeout for <code>{@link HttpMethodExecutor this}</code> HttpMethodExecutor.
     * @return The timeout.
     */
    public final int timeout()
    {
        return this.timeout;
    }
}