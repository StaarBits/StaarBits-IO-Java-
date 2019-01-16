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

public interface Suppressor
{

    /**
     * Suppresses the given <code>{@link Throwable suppressed}</code> Throwable which was thrown when attempting to close
     * the given <code>{@link Closeable closeable}</code>. <code>{@link Throwable thrown}</code> is the exception that is
     * actually being thrown from the method. Implementations of this method should not throw under any circumstances.
     * @param closeable The closeable instance.
     * @param thrown The thrown throwable.
     * @param suppressed The throwable which has been thrown whilst something was trying to closing the closeable.
     */
    void suppress(Closeable closeable, Throwable thrown, Throwable suppressed);
}
