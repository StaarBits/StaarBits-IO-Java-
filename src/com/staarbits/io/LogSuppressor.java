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
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogSuppressor implements Suppressor
{

    /** The logger instance where the suppress-message will be sent through */
    private Logger logger;

    /** Constructs a new {@code LogSuppressor} */
    private LogSuppressor(Logger logger)
    {
        this.logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed)
    {
        if (this.logger != null)
        {
            this.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, suppressed);
        }
    }

    /** Constructs a new instance of <code>Suppress</code> */
    public static Suppressor suppressor(Logger logger)
    {
        return new LogSuppressor(logger);
    }
}