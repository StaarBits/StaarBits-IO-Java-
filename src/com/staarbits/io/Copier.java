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

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public final class Copier
{

    /** The source file */
    private File sourceFile;

    /** The destination file */
    private File destinationFile;

    /** The id of the operation */
    private int operation;

    /** Constructs a new {@code Copier} */
    private Copier(File source, File destination)
    {
        this.sourceFile = source;
        this.destinationFile = destination;
    }

    /** Constructs a new {@code Copier} */
    public static Copier copier(File source, File destination)
    {
        return new Copier(source, destination);
    }

    /**
     * Declares a type of the operation which is built by <code>{@link Copier this}</code> Copier.
     * @param operation The int value which identifies the operation that will be done.
     * @return <code><strong>this</strong></code> instance.
     */
    public Copier withOperation(int operation)
    {
        this.operation = operation;
        return this;
    }

    /**
     * Copies the <code>{@link #sourceFile source}</code> file data to the <code>{@link #destinationFile path}</code> which
     * will receive all the data.
     * @throws IOException If an I/O error occurs whilst the source-file is being copied to the destination-file.
     * @throws FileNotFoundException If the source file cannot be found or simply does not exist.
     */
    public void copy() throws IOException, FileNotFoundException
    {
        if (this.sourceFile == null || !this.sourceFile.exists())
        {
            throw new FileNotFoundException("The " + this.sourceFile.getPath() + " could not be found");
        }

        switch (this.operation)
        {
            case CONVENTIONAL_STREAM_WAY:
                InputStream in = null;
                OutputStream out = null;

                try
                {
                    in = new FileInputStream(this.sourceFile);
                    out = new FileOutputStream(this.destinationFile);
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = in.read(buffer)) > 0)
                    {
                        out.write(buffer, 0, length);
                    }
                } finally
                {
                    in.close();
                    out.close();
                }
                break;

            case CHANNELS_WAY:
                FileChannel sourceChannel = null;
                FileChannel destinationChannel = null;

                try
                {
                    sourceChannel = new FileInputStream(this.sourceFile).getChannel();
                    destinationChannel = new FileOutputStream(this.destinationFile).getChannel();
                    destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
                } finally
                {
                    sourceChannel.close();
                    destinationChannel.close();
                }
                break;

            case NIO_WAY:
                Files.copy(this.sourceFile.toPath(), this.destinationFile.toPath());
                break;

            default:
                throw new IOException("The " + operation + " could not be found. To load a valid operation, it needs to" +
                        " be between 1 and 3 (1 = Conventional Stream Sending Data, 2 = Sending data through the Channels" +
                        " and 3 = Using the java.io.no.Files.copy(sourcePath, destinationPath).");
        }
    }

    /** The conventional stream way */
    public static final int CONVENTIONAL_STREAM_WAY = 1;

    /** The way that uses the FileChannels to transfer the data */
    public static final int CHANNELS_WAY = 2;

    /** The way which calls the <code>Files</code> class from the java.io.nio package */
    public static final int NIO_WAY = 3;
}