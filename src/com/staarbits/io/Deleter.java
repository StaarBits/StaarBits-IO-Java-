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

import java.io.File;
import java.util.logging.Logger;

public class Deleter
{

    /** The pathname */
    /* private-package */ String pathname;

    /** Constructs a new {@code Deleter} */
    /* private-package */ Deleter(String pathname)
    {
        this.pathname = pathname;
    }

    /** Constructs a new {@code Deleter} */
    public static Deleter create(String pathname, Logger logger)
    {
        return new LogDeleter(pathname, logger);
    }

    /** Constructs a new {@code Deleter} */
    public static Deleter create(String pathname)
    {
        return new Deleter(pathname);
    }

    /**
     * Deletes the <code>{@link File file}</code> (or the files if the pathname indicates a directory).
     * @return <code><strong>true</strong></code> if the associated file(s) could successfully be deleted; or <code><strong>
     *         false</strong></code> if something went wrong.
     */
    public boolean delete()
    {
        if (!new File(this.pathname).exists())
            return false;

        File file = new File(this.pathname);

        if (file.isDirectory())
        {
            for (File eachArchive : file.listFiles())
            {
                new Deleter(eachArchive.getPath()).delete();
            }
        }
        file.delete();
        return true;
    }
}
