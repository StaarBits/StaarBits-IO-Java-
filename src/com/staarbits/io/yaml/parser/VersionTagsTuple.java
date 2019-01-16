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
package com.staarbits.io.yaml.parser;

import java.util.Map;

import com.staarbits.io.yaml.DumperOptions;

/**
 * Store the internal state for directives
 */
class VersionTagsTuple {
    private DumperOptions.Version version;
    private Map<String, String> tags;

    public VersionTagsTuple(DumperOptions.Version version, Map<String, String> tags) {
        this.version = version;
        this.tags = tags;
    }

    public DumperOptions.Version getVersion() {
        return version;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return String.format("VersionTagsTuple<%s, %s>", version, tags);
    }
}
