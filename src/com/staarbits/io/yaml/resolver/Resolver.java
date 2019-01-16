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
package com.staarbits.io.yaml.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.staarbits.io.yaml.nodes.NodeId;
import com.staarbits.io.yaml.nodes.Tag;

/**
 * Resolver tries to detect a type by scalars's content (when the type is
 * implicit)
 */
public class Resolver {
    public static final Pattern BOOL = Pattern
            .compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");

    /**
     * The regular expression is taken from the 1.2 specification but '_'s are
     * added to keep backwards compatibility
     */
    public static final Pattern FLOAT = Pattern
            .compile("^([-+]?(\\.[0-9]+|[0-9_]+(\\.[0-9_]*)?)([eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
    public static final Pattern INT = Pattern
            .compile("^(?:[-+]?0b[0-1_]+|[-+]?0[0-7_]+|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x[0-9a-fA-F_]+|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
    public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
    public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
    public static final Pattern EMPTY = Pattern.compile("^$");
    public static final Pattern TIMESTAMP = Pattern
            .compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
    public static final Pattern VALUE = Pattern.compile("^(?:=)$");
    public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");

    protected Map<Character, List<ResolverTuple>> yamlImplicitResolvers = new HashMap<Character, List<ResolverTuple>>();

    protected void addImplicitResolvers() {
        addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
        /*
         * INT must be before FLOAT because the regular expression for FLOAT
         * matches INT (see issue 130)
         * http://code.google.com/p/snakeyaml/issues/detail?id=130
         */
        addImplicitResolver(Tag.INT, INT, "-+0123456789");
        addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
        addImplicitResolver(Tag.MERGE, MERGE, "<");
        addImplicitResolver(Tag.NULL, NULL, "~nN\0");
        addImplicitResolver(Tag.NULL, EMPTY, null);
        addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789");
        // The following implicit resolver is only for documentation
        // purposes.
        // It cannot work
        // because plain scalars cannot start with '!', '&', or '*'.
        addImplicitResolver(Tag.YAML, YAML, "!&*");
    }

    public Resolver() {
        addImplicitResolvers();
    }

    public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
        if (first == null) {
            List<ResolverTuple> curr = yamlImplicitResolvers.get(null);
            if (curr == null) {
                curr = new ArrayList<ResolverTuple>();
                yamlImplicitResolvers.put(null, curr);
            }
            curr.add(new ResolverTuple(tag, regexp));
        } else {
            char[] chrs = first.toCharArray();
            for (int i = 0, j = chrs.length; i < j; i++) {
                Character theC = Character.valueOf(chrs[i]);
                if (theC == 0) {
                    // special case: for null
                    theC = null;
                }
                List<ResolverTuple> curr = yamlImplicitResolvers.get(theC);
                if (curr == null) {
                    curr = new ArrayList<ResolverTuple>();
                    yamlImplicitResolvers.put(theC, curr);
                }
                curr.add(new ResolverTuple(tag, regexp));
            }
        }
    }

    public Tag resolve(NodeId kind, String value, boolean implicit) {
        if (kind == NodeId.scalar && implicit) {
            List<ResolverTuple> resolvers = null;
            if (value.length() == 0) {
                resolvers = yamlImplicitResolvers.get('\0');
            } else {
                resolvers = yamlImplicitResolvers.get(value.charAt(0));
            }
            if (resolvers != null) {
                for (ResolverTuple v : resolvers) {
                    Tag tag = v.getTag();
                    Pattern regexp = v.getRegexp();
                    if (regexp.matcher(value).matches()) {
                        return tag;
                    }
                }
            }
            if (yamlImplicitResolvers.containsKey(null)) {
                for (ResolverTuple v : yamlImplicitResolvers.get(null)) {
                    Tag tag = v.getTag();
                    Pattern regexp = v.getRegexp();
                    if (regexp.matcher(value).matches()) {
                        return tag;
                    }
                }
            }
        }
        switch (kind) {
        case scalar:
            return Tag.STR;
        case sequence:
            return Tag.SEQ;
        default:
            return Tag.MAP;
        }
    }
}