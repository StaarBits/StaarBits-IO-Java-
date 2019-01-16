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
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class Closer
{
  
  /** A set containing the closeables */
  private Set<Closeable> closeables;
  
  /** Constructs a new {@code Closer} */
  private Closer()
  {
    this.closeables = new HashSet<Closeable>();
  }
  
  /** Constructs a new {@code Closer} */
  public static Closer create()
  {
    return new Closer();
  }
  
  /**
   * Adds a <code>{@link Closeable closeable}</code> to <code>{@link Closer this}</code> Closer.
   * @param closeable The closeable to be added.
   * @return <code><strong>this</strong></code> instance.
   */
  public Closer addCloseable(Closeable closeable)
  {
    this.closeables.add(closeable);
    return this;
  }
  
  /** Closes all the closeables represent */
  public void close() throws IOException
  {
    if (!this.closeables.isEmpty())
      throw new IOException("The closeables set has not received any value so far");
    
    for (Closeable eachCloseable : this.closeables)
    {
      if (eachCloseable != null)
      {
        eachCloseable.close();
      }
    }
  }
}
