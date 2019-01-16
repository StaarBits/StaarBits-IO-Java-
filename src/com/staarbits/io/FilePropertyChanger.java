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

public class FilePropertyChanger
{

    /** The file whose the properties will be changed */
    private File file;

    /** The readable flag */
    private boolean readable;

    /** The writable flag */
    private boolean writable;

    /** The executable flag */
    private boolean executable;

    /** The boolean which determines if the readable flag is only for the owner */
    private boolean onlyReadableForOwner;

    /** The boolean which determines if the writable flag is only for the owner */
    private boolean onlyWritableForOwner;

    /** The boolean which determines if the executable flag is only for the owner */
    private boolean onlyExecutableForOwner;

    /** Constructs a new {@code FilePropertyChanger} */
    public FilePropertyChanger(File file)
    {
        this.file = file;
        this.readable = file.canRead();
        this.writable = file.canWrite();
        this.executable = file.canExecute();
        this.onlyReadableForOwner = false;
        this.onlyWritableForOwner = false;
        this.onlyExecutableForOwner = false;
    }

    /** The readable flag id */
    public static final int READABLE_FLAG_FOR_OWNER = 1;

    /** The writable flag id */
    public static final int WRITABLE_FLAG_FOR_OWNER = 2;

    /** The executable flag id */
    public static final int EXECUTABLE_FLAG_FOR_OWNER = 3;

    /**
     * Changes the property for the "onlyForOwner"
     * @param property The number which identifies the property which will be changed.
     * @return <code><strong>this</strong></code> instance.
     */
    public FilePropertyChanger onlyForOwner(int property)
    {
        switch (property)
        {
            case READABLE_FLAG_FOR_OWNER:
                this.onlyReadableForOwner = true;
                break;

            case WRITABLE_FLAG_FOR_OWNER:
                this.onlyWritableForOwner = true;
                break;

            case EXECUTABLE_FLAG_FOR_OWNER:
                this.onlyExecutableForOwner = true;
                break;
        }
        return this;
    }

    /**
     * Changes the "readable" property.
     * @param readable The property value.
     * @return <code><strong>this</strong></code> instance.
     */
    public FilePropertyChanger readable(boolean readable)
    {
        this.readable = readable;
        return this;
    }

    /**
     * Changes the "writable" property.
     * @param writable The property value.
     * @return <code><strong>this</strong></code> instance.
     */
    public FilePropertyChanger writable(boolean writable)
    {
        this.writable = writable;
        return this;
    }

    /**
     * Changes the "executable" property.
     * @param executable The property value.
     * @return <code><strong>this</strong></code> instance.
     */
    public FilePropertyChanger executable(boolean executable)
    {
        this.executable = executable;
        return this;
    }

    /**
     * Changes the properties of the argued <code>{@link File file}</code>.
     * @return The changed file.
     */
    public File change()
    {
        if (this.file != null)
        {
            this.file.setReadable(this.readable, this.onlyReadableForOwner);
            this.file.setWritable(this.writable, this.onlyWritableForOwner);
            this.file.setExecutable(this.executable, this.onlyExecutableForOwner);
            return this.file;
        }
        return null;
    }
}