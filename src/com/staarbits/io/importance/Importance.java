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

package com.staarbits.io.importance;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The "@Importance" is an annotation which works declaring the priority-level to specific types that contain this annotation on.
 */
@Target({TYPE})
@Retention(RUNTIME)
@SuppressWarnings({"unchecked"})
public @interface Importance
{

    /**
     * Gets an <code><strong>enum</strong></code> instance of <code>{@link PriorityLevel priority}</code> which <code>{@link
     * Importance this}</code> @Importance annotation demonstrates.
     * @return The priority level.
     */
    PriorityLevel value();
}
