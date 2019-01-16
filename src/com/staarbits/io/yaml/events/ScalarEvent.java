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

import com.staarbits.io.yaml.error.Mark;

/**
 * Marks a scalar value.
 */
public final class ScalarEvent extends NodeEvent {
    private final String tag;
    // style flag of a scalar event indicates the style of the scalar. Possible
    // values are None, '', '\'', '"', '|', '>'
    private final Character style;
    private final String value;
    // The implicit flag of a scalar event is a pair of boolean values that
    // indicate if the tag may be omitted when the scalar is emitted in a plain
    // and non-plain style correspondingly.
    private final ImplicitTuple implicit;

    public ScalarEvent(String anchor, String tag, ImplicitTuple implicit, String value,
            Mark startMark, Mark endMark, Character style) {
        super(anchor, startMark, endMark);
        this.tag = tag;
        this.implicit = implicit;
        this.value = value;
        this.style = style;
    }

    /**
     * Tag of this scalar.
     * 
     * @return The tag of this scalar, or <code>null</code> if no explicit tag
     *         is available.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Style of the scalar.
     * <dl>
     * <dt>null</dt>
     * <dd>Flow Style - Plain</dd>
     * <dt>'\''</dt>
     * <dd>Flow Style - Single-Quoted</dd>
     * <dt>'"'</dt>
     * <dd>Flow Style - Double-Quoted</dd>
     * <dt>'|'</dt>
     * <dd>Block Style - Literal</dd>
     * <dt>'>'</dt>
     * <dd>Block Style - Folded</dd>
     * </dl>
     * 
     * @see <a href="http://yaml.org/spec/1.1/#id864487">Kind/Style
     *      Combinations</a>
     * @return Style of the scalar.
     */
    public Character getStyle() {
        return this.style;
    }

    /**
     * String representation of the value.
     * <p>
     * Without quotes and escaping.
     * </p>
     * 
     * @return Value as Unicode string.
     */
    public String getValue() {
        return this.value;
    }

    public ImplicitTuple getImplicit() {
        return this.implicit;
    }

    @Override
    protected String getArguments() {
        return super.getArguments() + ", tag=" + tag + ", " + implicit + ", value=" + value;
    }

    @Override
    public boolean is(Event.ID id) {
        return ID.Scalar == id;
    }
}
