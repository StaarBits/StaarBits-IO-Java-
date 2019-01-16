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
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogDeleter extends Deleter
{

    /** An instance of Logger */
    private Logger logger;

    /** Constructs a new {@code LogDeleter} */
    /* private-package */ LogDeleter(String pathname, Logger logger)
    {
        super(pathname);
        this.logger = logger;
    }

    /**
     * Deletes the <code>{@link File file}</code> (or the files if the pathname indicates a directory).
     * @return <code><strong>true</strong></code> if the associated file(s) could successfully be deleted; or <code><strong>
     *         false</strong></code> if something went wrong.
     */
    @Override
    public boolean delete()
    {
        if (!new File(pathname).exists())
            return false;

        File file = new File(pathname);

        if (file.isDirectory())
        {
            for (File eachArchive : file.listFiles())
            {
                new LogDeleter(eachArchive.getPath(), this.logger).delete();
            }
        }
        this.logger.log(Level.INFO, "The " + pathname + " has successfully been deleted");
        file.delete();
        return true;
    }
}
