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
package com.staarbits.io.yaml.nodes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.staarbits.io.yaml.error.YAMLException;
import com.staarbits.io.yaml.util.UriEncoder;

public final class Tag implements Comparable<Tag> {
    public static final String PREFIX = "tag:yaml.org,2002:";
    public static final Tag YAML = new Tag(PREFIX + "yaml");
    public static final Tag MERGE = new Tag(PREFIX + "merge");
    public static final Tag SET = new Tag(PREFIX + "set");
    public static final Tag PAIRS = new Tag(PREFIX + "pairs");
    public static final Tag OMAP = new Tag(PREFIX + "omap");
    public static final Tag BINARY = new Tag(PREFIX + "binary");
    public static final Tag INT = new Tag(PREFIX + "int");
    public static final Tag FLOAT = new Tag(PREFIX + "float");
    public static final Tag TIMESTAMP = new Tag(PREFIX + "timestamp");
    public static final Tag BOOL = new Tag(PREFIX + "bool");
    public static final Tag NULL = new Tag(PREFIX + "null");
    public static final Tag STR = new Tag(PREFIX + "str");
    public static final Tag SEQ = new Tag(PREFIX + "seq");
    public static final Tag MAP = new Tag(PREFIX + "map");
    public static final Map<Tag, Set<Class<?>>> COMPATIBILITY_MAP;
    static {
        COMPATIBILITY_MAP = new HashMap<Tag, Set<Class<?>>>();
        Set<Class<?>> floatSet = new HashSet<Class<?>>();
        floatSet.add(Double.class);
        floatSet.add(Float.class);
        floatSet.add(BigDecimal.class);
        COMPATIBILITY_MAP.put(FLOAT, floatSet);
        //
        Set<Class<?>> intSet = new HashSet<Class<?>>();
        intSet.add(Integer.class);
        intSet.add(Long.class);
        intSet.add(BigInteger.class);
        COMPATIBILITY_MAP.put(INT, intSet);
        //
        Set<Class<?>> timestampSet = new HashSet<Class<?>>();
        timestampSet.add(Date.class);
        timestampSet.add(java.sql.Date.class);
        timestampSet.add(Timestamp.class);
        COMPATIBILITY_MAP.put(TIMESTAMP, timestampSet);
    }

    private final String value;
    private boolean secondary = false; // see http://www.yaml.org/refcard.html

    public Tag(String tag) {
        if (tag == null) {
            throw new NullPointerException("Tag must be provided.");
        } else if (tag.length() == 0) {
            throw new IllegalArgumentException("Tag must not be empty.");
        } else if (tag.trim().length() != tag.length()) {
            throw new IllegalArgumentException("Tag must not contain leading or trailing spaces.");
        }
        this.value = UriEncoder.encode(tag);
        this.secondary = !tag.startsWith(PREFIX);
    }

    public Tag(Class<? extends Object> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class for tag must be provided.");
        }
        this.value = Tag.PREFIX + UriEncoder.encode(clazz.getName());
    }

    public Tag(URI uri) {
        if (uri == null) {
            throw new NullPointerException("URI for tag must be provided.");
        }
        this.value = uri.toASCIIString();
    }

    public boolean isSecondary() {
        return secondary;
    }

    public String getValue() {
        return value;
    }

    public boolean startsWith(String prefix) {
        return value.startsWith(prefix);
    }

    public String getClassName() {
        if (!value.startsWith(Tag.PREFIX)) {
            throw new YAMLException("Invalid tag: " + value);
        }
        return UriEncoder.decode(value.substring(Tag.PREFIX.length()));
    }

    public int getLength() {
        return value.length();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tag) {
            return value.equals(((Tag) obj).getValue());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Java has more then 1 class compatible with a language-independent tag
     * (!!int, !!float, !!timestamp etc)
     * 
     * @param clazz
     *            - Class to check compatibility
     * @return true when the Class can be represented by this
     *         language-independent tag
     */
    public boolean isCompatible(Class<?> clazz) {
        Set<Class<?>> set = COMPATIBILITY_MAP.get(this);
        if (set != null) {
            return set.contains(clazz);
        } else {
            return false;
        }
    }

    /**
     * Check whether this tag matches the global tag for the Class
     * 
     * @param clazz
     *            - Class to check
     * @return true when the this tag can be used as a global tag for the Class
     */
    public boolean matches(Class<? extends Object> clazz) {
        return value.equals(Tag.PREFIX + clazz.getName());
    }

    public int compareTo(Tag o) {
        return value.compareTo(o.getValue());
    }
}
