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
package com.staarbits.io.yaml;

import java.util.Map;
import java.util.TimeZone;

import com.staarbits.io.yaml.error.YAMLException;
import com.staarbits.io.yaml.emitter.Emitter;

public class DumperOptions {
    /**
     * YAML provides a rich set of scalar styles. Block scalar styles include
     * the literal style and the folded style; flow scalar styles include the
     * plain style and two quoted styles, the single-quoted style and the
     * double-quoted style. These styles offer a range of trade-offs between
     * expressive power and readability.
     * 
     * @see <a href="http://yaml.org/spec/1.1/#id903915">Chapter 9. Scalar
     *      Styles</a>
     * @see <a href="http://yaml.org/spec/1.1/#id858081">2.3. Scalars</a>
     */
    public enum ScalarStyle {
        DOUBLE_QUOTED(Character.valueOf('"')), SINGLE_QUOTED(Character.valueOf('\'')), LITERAL(
                Character.valueOf('|')), FOLDED(Character.valueOf('>')), PLAIN(null);
        private Character styleChar;

        private ScalarStyle(Character style) {
            this.styleChar = style;
        }

        public Character getChar() {
            return styleChar;
        }

        @Override
        public String toString() {
            return "Scalar style: '" + styleChar + "'";
        }

        public static ScalarStyle createStyle(Character style) {
            if (style == null) {
                return PLAIN;
            } else {
                switch (style) {
                case '"':
                    return DOUBLE_QUOTED;
                case '\'':
                    return SINGLE_QUOTED;
                case '|':
                    return LITERAL;
                case '>':
                    return FOLDED;
                default:
                    throw new YAMLException("Unknown scalar style character: " + style);
                }
            }
        }
    }

    /**
     * Block styles use indentation to denote nesting and scope within the
     * document. In contrast, flow styles rely on explicit indicators to denote
     * nesting and scope.
     * 
     * @see <a href="http://www.yaml.org/spec/current.html#id2509255">3.2.3.1.
     *      Node Styles (http://yaml.org/spec/1.1)</a>
     */
    public enum FlowStyle {
        FLOW(Boolean.TRUE), BLOCK(Boolean.FALSE), AUTO(null);

        private Boolean styleBoolean;

        private FlowStyle(Boolean flowStyle) {
            styleBoolean = flowStyle;
        }

        public Boolean getStyleBoolean() {
            return styleBoolean;
        }

        @Override
        public String toString() {
            return "Flow style: '" + styleBoolean + "'";
        }
    }

    /**
     * Platform dependent line break.
     */
    public enum LineBreak {
        WIN("\r\n"), MAC("\r"), UNIX("\n");

        private String lineBreak;

        private LineBreak(String lineBreak) {
            this.lineBreak = lineBreak;
        }

        public String getString() {
            return lineBreak;
        }

        @Override
        public String toString() {
            return "Line break: " + name();
        }

        public static LineBreak getPlatformLineBreak() {
            String platformLineBreak = System.getProperty("line.separator");
            for (LineBreak lb : values()) {
                if (lb.lineBreak.equals(platformLineBreak)) {
                    return lb;
                }
            }
            return LineBreak.UNIX;
        }
    }

    /**
     * Specification version. Currently supported 1.0 and 1.1
     */
    public enum Version {
        V1_0(new Integer[] { 1, 0 }), V1_1(new Integer[] { 1, 1 });

        private Integer[] version;

        private Version(Integer[] version) {
            this.version = version;
        }

        public int major() { return version[0]; }
        public int minor() { return version[1]; }

        public String getRepresentation() {
            return version[0] + "." + version[1];
        }

        @Override
        public String toString() {
            return "Version: " + getRepresentation();
        }
    }

    private ScalarStyle defaultStyle = ScalarStyle.PLAIN;
    private FlowStyle defaultFlowStyle = FlowStyle.AUTO;
    private boolean canonical = false;
    private boolean allowUnicode = true;
    private boolean allowReadOnlyProperties = false;
    private int indent = 2;
    private int bestWidth = 80;
    private boolean splitLines = true;
    private LineBreak lineBreak = LineBreak.UNIX;
    private boolean explicitStart = false;
    private boolean explicitEnd = false;
    private TimeZone timeZone = null;

    private Version version = null;
    private Map<String, String> tags = null;
    private Boolean prettyFlow = false;

    public boolean isAllowUnicode() {
        return allowUnicode;
    }

    /**
     * Specify whether to emit non-ASCII printable Unicode characters (to
     * support ASCII terminals). The default value is true.
     * 
     * @param allowUnicode
     *            if allowUnicode is false then all non-ASCII characters are
     *            escaped
     */
    public void setAllowUnicode(boolean allowUnicode) {
        this.allowUnicode = allowUnicode;
    }

    public ScalarStyle getDefaultScalarStyle() {
        return defaultStyle;
    }

    /**
     * Set default style for scalars. See YAML 1.1 specification, 2.3 Scalars
     * (http://yaml.org/spec/1.1/#id858081)
     * 
     * @param defaultStyle
     *            set the style for all scalars
     */
    public void setDefaultScalarStyle(ScalarStyle defaultStyle) {
        if (defaultStyle == null) {
            throw new NullPointerException("Use ScalarStyle enum.");
        }
        this.defaultStyle = defaultStyle;
    }

    public void setIndent(int indent) {
        if (indent < Emitter.MIN_INDENT) {
            throw new YAMLException("Indent must be at least " + Emitter.MIN_INDENT);
        }
        if (indent > Emitter.MAX_INDENT) {
            throw new YAMLException("Indent must be at most " + Emitter.MAX_INDENT);
        }
        this.indent = indent;
    }

    public int getIndent() {
        return this.indent;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return this.version;
    }

    /**
     * Force the emitter to produce a canonical YAML document.
     * 
     * @param canonical
     *            true produce canonical YAML document
     */
    public void setCanonical(boolean canonical) {
        this.canonical = canonical;
    }

    public boolean isCanonical() {
        return this.canonical;
    }

    /**
     * Force the emitter to produce a pretty YAML document when using the flow
     * style.
     * 
     * @param prettyFlow
     *            true produce pretty flow YAML document
     */
    public void setPrettyFlow(boolean prettyFlow) {
        this.prettyFlow = prettyFlow;
    }

    public boolean isPrettyFlow() {
        return this.prettyFlow;
    }

    /**
     * Specify the preferred width to emit scalars. When the scalar
     * representation takes more then the preferred with the scalar will be
     * split into a few lines. The default is 80.
     * 
     * @param bestWidth
     *            the preferred width for scalars.
     */
    public void setWidth(int bestWidth) {
        this.bestWidth = bestWidth;
    }

    public int getWidth() {
        return this.bestWidth;
    }

    /**
     * Specify whether to split lines exceeding preferred width for
     * scalars. The default is true.
     *
     * @param splitLines
     *            whether to split lines exceeding preferred width for scalars.
     */
    public void setSplitLines(boolean splitLines) {
        this.splitLines = splitLines;
    }

    public boolean getSplitLines() {
        return this.splitLines;
    }

    public LineBreak getLineBreak() {
        return lineBreak;
    }

    public void setDefaultFlowStyle(FlowStyle defaultFlowStyle) {
        if (defaultFlowStyle == null) {
            throw new NullPointerException("Use FlowStyle enum.");
        }
        this.defaultFlowStyle = defaultFlowStyle;
    }

    public FlowStyle getDefaultFlowStyle() {
        return defaultFlowStyle;
    }

    /**
     * Specify the line break to separate the lines. It is platform specific:
     * Windows - "\r\n", old MacOS - "\r", Unix - "\n". The default value is the
     * one for Unix.
     */
    public void setLineBreak(LineBreak lineBreak) {
        if (lineBreak == null) {
            throw new NullPointerException("Specify line break.");
        }
        this.lineBreak = lineBreak;
    }

    public boolean isExplicitStart() {
        return explicitStart;
    }

    public void setExplicitStart(boolean explicitStart) {
        this.explicitStart = explicitStart;
    }

    public boolean isExplicitEnd() {
        return explicitEnd;
    }

    public void setExplicitEnd(boolean explicitEnd) {
        this.explicitEnd = explicitEnd;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    // TODO should use Tag ???
    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    /**
     * Report whether read-only JavaBean properties (the ones without setters)
     * should be included in the YAML document
     * 
     * @return false when read-only JavaBean properties are not emitted
     */
    public boolean isAllowReadOnlyProperties() {
        return allowReadOnlyProperties;
    }

    /**
     * Set to true to include read-only JavaBean properties (the ones without
     * setters) in the YAML document. By default these properties are not
     * included to be able to parse later the same JavaBean.
     * 
     * @param allowReadOnlyProperties
     *            - true to dump read-only JavaBean properties
     */
    public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties) {
        this.allowReadOnlyProperties = allowReadOnlyProperties;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Set the timezone to be used for Date. If set to <code>null</code> UTC is
     * used.
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
