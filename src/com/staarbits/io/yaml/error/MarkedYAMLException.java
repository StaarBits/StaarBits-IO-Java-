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
package com.staarbits.io.yaml.error;

public class MarkedYAMLException extends YAMLException {

    private static final long serialVersionUID = -9119388488683035101L;
    private String context;
    private Mark contextMark;
    private String problem;
    private Mark problemMark;
    private String note;

    protected MarkedYAMLException(String context, Mark contextMark, String problem,
            Mark problemMark, String note) {
        this(context, contextMark, problem, problemMark, note, null);
    }

    protected MarkedYAMLException(String context, Mark contextMark, String problem,
            Mark problemMark, String note, Throwable cause) {
        super(context + "; " + problem + "; " + problemMark, cause);
        this.context = context;
        this.contextMark = contextMark;
        this.problem = problem;
        this.problemMark = problemMark;
        this.note = note;
    }

    protected MarkedYAMLException(String context, Mark contextMark, String problem, Mark problemMark) {
        this(context, contextMark, problem, problemMark, null, null);
    }

    protected MarkedYAMLException(String context, Mark contextMark, String problem,
            Mark problemMark, Throwable cause) {
        this(context, contextMark, problem, problemMark, null, cause);
    }

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        StringBuilder lines = new StringBuilder();
        if (context != null) {
            lines.append(context);
            lines.append("\n");
        }
        if (contextMark != null
                && (problem == null || problemMark == null
                        || contextMark.getName().equals(problemMark.getName())
                        || (contextMark.getLine() != problemMark.getLine()) || (contextMark
                        .getColumn() != problemMark.getColumn()))) {
            lines.append(contextMark.toString());
            lines.append("\n");
        }
        if (problem != null) {
            lines.append(problem);
            lines.append("\n");
        }
        if (problemMark != null) {
            lines.append(problemMark.toString());
            lines.append("\n");
        }
        if (note != null) {
            lines.append(note);
            lines.append("\n");
        }
        return lines.toString();
    }

    public String getContext() {
        return context;
    }

    public Mark getContextMark() {
        return contextMark;
    }

    public String getProblem() {
        return problem;
    }

    public Mark getProblemMark() {
        return problemMark;
    }
}
