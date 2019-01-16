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
import java.util.*;

public class PropertyInterpreter implements Closeable
{

    /** A boolean which determines if this interpreter is currently open or closed */
    private boolean open;

    /** Constructs a new {@code PropertyInterpreter} */
    public PropertyInterpreter()
    {
        this.open = false;
    }

    /**
     * The properties instance which has been loaded, by default, it is equivalent to <b>null</b>.
     * <p>This <code>Properties</code> is loaded when the <code>{@link #load(String) load(filename)}</code> method is invoked,
     * before open <code>{@link PropertyInterpreter this}</code> PropertyInterpreter, that method creates a new instance of
     * the properties, and loads all the resource in the properties instance.
     * <p>If the <code>PropertyInterpreter</code> is closed or if its operation is aborted, this <code>Properties</code> will
     * return to be equivalent to <code><b>null</b></code>.
     */
    private Properties loadedProperties = null;

    /** The filename which has been reported as the name of the resource */
    private String reportedFilename = null;

    /**
     * Writes the properties present in the <code>{@link Map map}</code> in a XML format.
     * @param filename The filename.
     * @param map The map containing all the properties.
     * @param comment The comment.
     * @throws IOException If an I/O error occurs.
     * @throws NullPointerException If the filename is null.
     */
    public void writeXML(String filename, Map<String, Object> map, String comment) throws IOException, NullPointerException
    {
        if (NULL.getNULL().equals(filename))
        {
            throw new NullPointerException("The " + filename + " cannot be equivalent to null");
        } else if (filename.isEmpty())
        {
            throw new IOException("The " + filename + " must not be empty");
        } else if (map == null || map.isEmpty())
        {
            throw new IOException("The map needs to have received a value for this method to write");
        } else if (!filename.endsWith(".xml"))
        {
            throw new IOException("The " + filename + " cannot get a format different from the XMl");
        }

        Properties properties = new Properties();
        properties.putAll(map);
        properties.storeToXML(new FileOutputStream(filename), (NULL.getNULL().equals(comment) ? "" : comment));
    }

    /**
     * Writes the properties present in the <code>{@link Map map}</code>.
     * @param filename The filename.
     * @param map The map containing all the properties.
     * @param comment The comment.
     * @throws IOException If an I/O error occurs.
     * @throws NullPointerException If the filename is null.
     */
    public void write(String filename, Map<String, Object> map, String comment) throws IOException
    {
        if (NULL.getNULL().equals(filename))
        {
            throw new NullPointerException("The " + filename + " cannot be equivalent to null");
        } else if (filename.isEmpty())
        {
            throw new IOException("The " + filename + " must not be empty");
        } else if (map == null || map.isEmpty())
        {
            throw new IOException("The map needs to have received a value for this method to write");
        }

        Properties properties = new Properties();
        properties.putAll(map);
        properties.store(new FileWriter(filename), (NULL.getNULL().equals(comment) ? "" : comment));
    }

    /** THe input stream which represents the resource */
    private InputStream resourceStream = null;

    /**
     * Gets the filename which identifies the name of the file and its extension.
     * <p>The filename is set when the <code>{@link #load(String) load(filename)}</code> is invoked. So, if the properties
     * has not been loaded yet, the reported filename will demonstrate it being equivalent to <code><b>null</b></code>.
     * @return The reported filename.
     */
    public String getFilename()
    {
        return this.reportedFilename;
    }

    /** A set containing the reported-keys */
    private Set<Object> reportedKeys = null;

    /**
     * Gets a <code>Set</code> containing all the keys which have been reported by the last time when the <code>{@link #readAll()
     * readALl()}</code> method was invoked.
     * <p>If this method is called and the <code>{@link #readAll() readAll()}</code> method has not still been invoked, this value
     * will simply be equivalent to <code><strong>null</strong></code>.
     * @return The <code>Set</code> which contains the keys that have been reported by the last time that the readAll() method was
     *         invoked; or <code><strong>null</strong></code> if the <code>{@link #readAll() readAll()}</code> method has not been
     *         called yet.
     */
    public Set<Object> lastReportedKeys()
    {
        return this.reportedKeys;
    }

    /**
     * Reads all the <code>{@link Properties}</code> individually, without changing any value of <code>{@link PropertyInterpreter
     * this}</code> PropertyInterpreter...
     * <p>This method needs that <code>{@link PropertyInterpreter this}</code> PropertyInterpreter has already been <code>
     * {@link #load(String) loaded}</code>. Otherwise, it will not be possible to find the reported filename to reload all the
     * data again.
     * @return The <code>Map</code> containing all the properties copied from the source, in this case, the reported file.
     * @throws IOException If <code>{@link PropertyInterpreter this}</code> PropertyInterpreter is not open.
     */
    public Map<String, Object> readAll() throws IOException
    {
        if (!this.isOpen())
            throw new IOException("The PropertyInterpreter cannot be read because it is not open");

        Properties properties = new Properties();
        FileReader reader = new FileReader(this.reportedFilename);
        properties.load(reader);

        Set<Object> reportedKeys = properties.keySet();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        for (Object eachReportedKey : reportedKeys)
        {
            if (!(eachReportedKey instanceof String))
                throw new IOException("The " + eachReportedKey.toString() +  " must be considered a String");

            hashMap.put((String) eachReportedKey, properties.getProperty((String) eachReportedKey));
        }
        this.reportedKeys = reportedKeys;
        return hashMap;
    }

    /**
     * Loads the resource which is loaded through the <code>filename</code> as a XML file.
     * <p>Differently from <code>{@link #load(String) load(filename)}</code> method, this one will just load the resource as
     * a <code>{@link Properties}</code> instance, but as a <code>InputStream</code>.
     * @param filename The filename to identify the file whose the properties will be interpreted.
     * @throws IOException If <code>{@link PropertyInterpreter this}</code> PropertyInterpreter is closed.
     */
    public void loadXML(String filename) throws IOException
    {
        if (this.isOpen())
            throw new IOException("The PropertyInterpreter has already been open");

        if (!filename.endsWith(".xml"))
            throw new IOException("The XML file could not be found");

        this.loadedProperties = new Properties();
        this.reportedFilename = filename;

        InputStream in = new FileInputStream(filename);
        this.loadedProperties.loadFromXML(in);
        this.reportedKeys = this.loadedProperties.keySet();
        this.open = true;
    }

    /**
     * Loads the resource which is loaded through the <code>filename</code>.
     * <p>This resource will be loaded by a couple of ways, one of them is as the <code>Properties</code>, it will receive all
     * the data considered "property" by the system which is contained by the resource and the other way is as a InputStream.
     * @param filename The filename to identify the file whose the properties will be interpreted.
     * @throws IOException If <code>{@link PropertyInterpreter this}</code> PropertyInterpreter is closed.
     */
    public void load(String filename) throws IOException
    {
        if (this.isOpen())
            throw new IOException("The PropertyInterpreter has already been open");

        this.loadedProperties = new Properties();
        this.reportedFilename = filename;
        this.resourceStream = PropertyInterpreter.class.getResourceAsStream(filename);
        this.loadedProperties.load(this.resourceStream);
        this.open = true;
    }

    /**
     * Closes <code>{@link PropertyInterpreter this}</code> PropertyInterpreter, setting all the variables to null and setting
     * the <code>{@link #open open}</code> variable to <code><b>false</b></code>.
     * @throws IOException If <code>{@link PropertyInterpreter this}</code> PropertyInterpreter is not open.
     */
    @Override
    public void close() throws IOException
    {
        if (!this.isOpen())
            throw new IOException("The PropertyInterpreter is not open");

        this.loadedProperties = null;
        this.reportedFilename = null;
        this.resourceStream = null;
        this.loadedProperties.clear();
        this.loadedProperties = null;
        this.open = true;
    }

    /**
     * Gets the <code>{@link Object object}</code> value which is contained by the <code>{@link Properties properties}</code>
     * of the loaded resource,
     * @param key The key which has been associated to the object-value that will be returned.
     * @return The <code>Object</code> value that is associated to the given <code>key</code>; or <code><b>null</b></code> if
     *         no object-value could be found.
     * @throws IOException If <code>{@link PropertyInterpreter this}</code> PropertyInterpreter is closed; if the <code>key</code>
     *                     is equivalent to <code><b>null</b></code> or if the <code>key</code> is empty.
     */
    public Object getProperty(String key) throws IOException
    {
        if (!this.isOpen())
        {
            throw new IOException("The PropertyInterpreter is not open");
        }

        if (NULL.getNULL().equals(key))
        {
            throw new IOException("The key must be different from null");
        } else if (key.isEmpty())
        {
            throw new IOException("The key must not be an empty character sequence");
        }

        return this.loadedProperties.getProperty(key);
    }

    /** Aborts all the operation which has been making for <code>{@link PropertyInterpreter this}</code> PropertyInterpreter */
    public void abort() throws IOException
    {
        if (!this.isOpen())
            throw new IOException("The PropertyInterpreter cannot be aborted because it is not open");

        this.open = false;
        this.loadedProperties = null;
        this.reportedFilename = null;
        this.resourceStream = null;
    }

    /**
     * Checks whether <code>{@link PropertyInterpreter this}</code> PropertyInterpreter has already been open or not.
     * @return <code><strong>true</strong></code> if <code>{@link PropertyInterpreter this}</code> PropertyInterpreter is
     *         currently open; or <code><strong>false</strong></code> if it has not been open since it was constructed or
     *         since it was closed.
     */
    public boolean isOpen()
    {
        return open;
    }
}