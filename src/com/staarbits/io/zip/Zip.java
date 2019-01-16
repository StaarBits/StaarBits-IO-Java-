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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip
{
  
  /** The source folder */
  private String sourceFolder;
  
  /** The name of the zip-file which will be created */
  private String zipFilename;
  
  /** A list containing all the folder names */
  private List<String> folders;
  
  /** A list containing all the filenames */
  private List<String> files;
  
  /** Constructs a new {@code Zip} */
  public Zip(String sourceFolder, String zipFilename, List<String> folders, List<String> files)
  {
    this.sourceFolder = sourceFolder;
    this.zipFilename = zipFilename;
    this.folders = folders;
    this.files = files;
  }
  
  /** Zips all the files */
  public void zip() throws IOException
  {
    ZipOutputStream zipOutputStream = null;
    FileOutputStream fileOutputStream = null;
    FileInputStream fileInputStream = null;
    BufferedOutputStream out = null;
    
    List<String> fileList = new ArrayList<String>();
    byte[] buffer = new byte[2048];
    
    fileOutputStream = new FileOutputStream(this.zipFilename);
    out = new BufferedOutputStream(fileOutputStream);
    zipOutputStream = new ZipOutputStream(out);
    ZipEntry entry;
    
    for (String eachFile : this.files)
    {
      fileList.add(this.generateZipEntry(this.sourceFolder, new File(eachFile).getAbsolutePath().toString()));
    }
    
    for (String eachFolder : this.folders)
    {
      this.getFileList(fileList, this.sourceFolder, new File(eachFolder));
    }
    
    for (String eachFile : fileList)
    {
      entry = new ZipEntry(eachFile);
      zipOutputStream.putNextEntry(entry);
      fileInputStream = new FileInputStream(this.sourceFolder + File.separator + eachFile);
      
      int length;
      while ((length = fileInputStream.read(buffer)) > 0)
      {
        zipOutputStream.write(buffer, 0, length);
      }
      fileInputStream.close();
    }
    zipOutputStream.flush();
    zipOutputStream.close();
    
    out.flush();
    out.close();
  }
  
  private String generateZipEntry(String sourceFolder, String file)
  {
    return file.substring(sourceFolder.length() + 1, file.length());
  }
  
  private void getFileList(List<String> fileList, String sourceFolder, File node)
  {
    // add file only
    if (node.isFile())
    {
      fileList.add(this.generateZipEntry(sourceFolder, node.getAbsoluteFile().toString()));
    }
    
    if (node.isDirectory())
    {
      String[] subNote = node.list();
      for (String filename : subNote)
      {
        this.getFileList(fileList, sourceFolder, new File(node, filename));
      }
    }
  }
}
