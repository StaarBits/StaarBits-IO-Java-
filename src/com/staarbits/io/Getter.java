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

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public final class Getter
{

    /**
     * Loads all the <code>{@link Class classes}</code> found in the <code>packagePath</code>.
     * After loading the class types, a <code>{@link Collection collection}</code> is constructed and all the loaded classes
     * are added to it.
     * @param loader The loader class also called "host" or "main".
     * @param packagePath The path of the package where the classes which will be loaded are in.
     * @return A <code>Collection</code> containing the <code>{@link Class classes}</code> which have been loaded by this
     *         process.
     * @throws IOException If an I/O error occurs whilst the method is loading the classes.
     */
    public static Collection<? extends Class<?>> forPackage(Class<?> loader, String packagePath) throws IOException
    {
        return forPackage(loader, packagePath, false, (Logger) null);
    }

    /**
     * Loads all the <code>{@link Class classes}</code> found in the <code>packagePath</code>.
     * After loading the class types, a <code>{@link Collection collection}</code> is constructed and all the loaded classes
     * are added to it.
     * @param loader The loader class, also called "host" or "main".
     * @param packagePath The path of the package where the classes which will be loaded are in.
     * @param logEverything A boolean which determines if the directory of the class needs to be reported through a log.
     * @param logger The logger that, if the <code>logEverything</code> is true, sends the logs.
     * @return A <code>Collection</code> containing the <code>{@link Class classes}</code> which have been loaded by this
     *         process.
     * @throws IOException If an I/O error occurs whilst the method is loading the classes.
     */
    public static Collection<? extends Class<?>> forPackage(Class<?> loader, String packagePath, boolean logEverything,
                                                            Logger logger) throws IOException
    {
        List<Class<?>> instances = new ArrayList<Class<?>>();
        CodeSource source = loader.getProtectionDomain().getCodeSource();

        if (source != null)
        {
            URL resource = source.getLocation();
            resource.getPath();
            instances.addAll(processFiles(resource, packagePath));
        }
        ArrayList<String> classNames = new ArrayList<String>();
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

        for (Class<?> eachLoadedClass : instances)
        {
            classNames.add(eachLoadedClass.getSimpleName());
            classes.add(eachLoadedClass);
        }
        instances.clear();
        java.util.Collections.sort(classNames, String.CASE_INSENSITIVE_ORDER);

        for (String eachName : classNames)
        {
            for (Class<?> eachClass : classes)
            {
                if (eachClass.getSimpleName().equals(eachName))
                {
                    instances.add(eachClass);
                    if (logEverything && logger != null)
                    {
                        logger.info("The " + eachClass.getSimpleName() + " has been loaded in " + packagePath);
                    }
                    break;
                }
            }
        }
        return instances;
    }

    /**
     * Loads the <code><strong>class</strong></code> by the <code>className</code>.
     * @param className The class name which identifies the class to be loaded.
     * @return The <code><strong>class</strong></code> which has been loaded.
     * @throws IOException If an I/O error occurs.
     */
    public static Class<?> loadClass(String className) throws IOException
    {
        try
        {
            return Class.forName(className);
        } catch (ClassNotFoundException error)
        {
            throw new IOException(error.getMessage());
        }
    }

    /**
     * Processes the <code>{@link Class classes}</code> which are identified by the <code>{@link URL resource}</code> and
     * by the <code>packagePath</code>.
     * @param resource The URL resource to process the files.
     * @param packagePath The path of the package where the classes which will be loaded are in.
     * @return A <code>{@link List list}</code> containing the classes.
     * @throws IOException If an I/O error occurs.
     */
    public static List<Class<?>> processFiles(URL resource, String packagePath) throws IOException
    {
        if (packagePath == null || NULL.getNULL().equals(packagePath))
        {
            throw new IOException("The packagePath (" + packagePath + ") needs to be considered valid");
        }
        List<Class<?>> list = new ArrayList<Class<?>>();

        String releasePath = packagePath.replace('.', '/');
        String resourcePath = resource.getPath().replace("%20", " ");
        String jarPath = resourcePath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        JarFile file;

        try
        {
            file = new JarFile(jarPath);
        } catch (IOException error)
        {
            throw new IOException(error);
        }

        Enumeration<JarEntry> enumeration = file.entries();

        while (enumeration.hasMoreElements())
        {
            JarEntry entry = enumeration.nextElement();
            String entryName = entry.getName();
            String className = null;

            if (entryName.endsWith(".class") && entryName.startsWith(releasePath) && entryName.length() > (releasePath.length() + "/".length()))
            {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }

            if (className != null)
            {
                list.add(loadClass(className));
            }
        }
        return list;
    }

    /** Constructs a new {@code Getter} */
    private Getter()
    {       }
}