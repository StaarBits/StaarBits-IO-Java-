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

package com.staarbits.io.zip;

import com.staarbits.io.Closer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip
{
  
  /** The path where the zip file is at */
  private String path;
  
  /** The directory where this Unzip will unzip the files from the path */
  private String outputDirectory;
  
  /** Constructs a new {@code Unzip}*/
  public Unzip(String path, String outputDirectory)
  {
    this.path = path;
    this.outputDirectory = outputDirectory;
  }
  
  /** Unzips all the files in <code>path</code> to <code>outputDirectory</code> */
  public void unzip() throws IOException
  {
    this.fileInputStream = new FileInputStream(this.path);
    this.in = new BufferedInputStream(this.fileInputStream);
    this.zipInputStream = new ZipInputStream(this.in);
    
    ZipEntry entry;
    while ((entry = this.zipInputStream.getNextEntry()) != null)
    {
      int size;
      byte[] buffer = new byte[2048];
      
      this.fileOutputStream = new FileOutputStream(this.outputDirectory + File.separator + entry.getName());
      this.out = new BufferedOutputStream(this.fileOutputStream, buffer.length);
      
      while ((size = this.zipInputStream.read(buffer, 0, buffer.length)) != -1)
      {
        this.out.write(buffer, 0, size);
      }
      this.out.flush();
      this.out.close();
      this.fileOutputStream.flush();
      this.fileOutputStream.close();
    }
    Closer closer = Closer.create();
    closer.addCloseable(this.zipInputStream);
    closer.addCloseable(this.in);
    closer.addCloseable(this.fileInputStream);
    closer.close();
  }
  
  /** A file input stream for the <code>{@link #path}</code> */
  private FileInputStream fileInputStream = null;
  
  /** The zip output stream */
  private ZipInputStream zipInputStream = null;
  
  /** The file output stream */
  private FileOutputStream fileOutputStream = null;
  
  /** The buffered input stream */
  private BufferedInputStream in = null;
  
  /** The buffered output stream */
  private BufferedOutputStream out = null;
}