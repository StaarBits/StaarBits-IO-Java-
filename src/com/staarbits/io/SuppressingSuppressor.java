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

package com.staarbits.io;

import java.io.Closeable;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class SuppressingSuppressor implements Suppressor
{

    /** Constructs a new {@code SuppressingSuppressor} */
    private SuppressingSuppressor()
    {       }

    @Override
    public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed)
    {
        if (thrown == suppressed)
            return;

        try
        {
            SuppressingSuppressor.METHOD.invoke(thrown, suppressed);
        } catch (Throwable throwable)
        {
            LogSuppressor.suppressor(Logger.getGlobal()).suppress(closeable, thrown, suppressed);
        }
    }

    /**
     * Checks whether <code>{@link SuppressingSuppressor this}</code> SuppressingSuppressor is available.
     * @return <code><strong>true</strong></code> if <code>{@link SuppressingSuppressor this}</code> SuppressingSuppressor
     *         is available; or <code><strong>false</strong></code> if it is not.
     */
    /* private-package */ static boolean available()
    {
        return METHOD != null;
    }

    /** The instance of the Method ("addSuppressed") */
    /* private-package */ static final Method METHOD = SuppressingSuppressor.getMethod();

    /**
     * Loads a possible way to invoke the <code>addSuppressed(Throwable)</code> method from the Throwable.
     * @return The method invoker.
     */
    /* private-package */ static Method getMethod()
    {
        try
        {
            return Throwable.class.getMethod("addSuppressed", Throwable.class);
        } catch (Throwable error)
        {
            return null;
        }
    }

    /** Constructs a new {@code SuppressingSuppressor} */
    public static Suppressor suppressor()
    {
        return new SuppressingSuppressor();
    }
}