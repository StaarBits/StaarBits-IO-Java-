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
package com.staarbits.io.yaml.events;

import java.util.Map;

import com.staarbits.io.yaml.DumperOptions;
import com.staarbits.io.yaml.error.Mark;

/**
 * Marks the beginning of a document.
 * <p>
 * This event followed by the document's content and a {@link DocumentEndEvent}.
 * </p>
 */
public final class DocumentStartEvent extends Event {
    private final boolean explicit;
    private final DumperOptions.Version version;
    private final Map<String, String> tags;

    public DocumentStartEvent(Mark startMark, Mark endMark, boolean explicit, DumperOptions.Version version,
                              Map<String, String> tags) {
        super(startMark, endMark);
        this.explicit = explicit;
        this.version = version;
        // TODO enforce not null
        // if (tags == null) {
        // throw new NullPointerException("Tags must be provided.");
        // }
        this.tags = tags;
    }

    public boolean getExplicit() {
        return explicit;
    }

    /**
     * YAML version the document conforms to.
     * 
     * @return <code>null</code>if the document has no explicit
     *         <code>%YAML</code> directive. Otherwise an array with two
     *         components, the major and minor part of the version (in this
     *         order).
     */
    public DumperOptions.Version getVersion() {
        return version;
    }

    /**
     * Tag shorthands as defined by the <code>%TAG</code> directive.
     * 
     * @return Mapping of 'handles' to 'prefixes' (the handles include the '!'
     *         characters).
     */
    public Map<String, String> getTags() {
        return tags;
    }

    @Override
    public boolean is(Event.ID id) {
        return ID.DocumentStart == id;
    }
}
