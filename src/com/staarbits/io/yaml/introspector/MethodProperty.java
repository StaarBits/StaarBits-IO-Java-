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
package com.staarbits.io.yaml.introspector;

import java.beans.PropertyDescriptor;

import com.staarbits.io.yaml.error.YAMLException;

/**
 * <p>
 * A <code>MethodProperty</code> is a <code>Property</code> which is accessed
 * through accessor methods (setX, getX). It is possible to have a
 * <code>MethodProperty</code> which has only setter, only getter, or both. It
 * is not possible to have a <code>MethodProperty</code> which has neither
 * setter nor getter.
 * </p>
 */
public class MethodProperty extends GenericProperty {

    private final PropertyDescriptor property;
    private final boolean readable;
    private final boolean writable;

    public MethodProperty(PropertyDescriptor property) {
        super(property.getName(), property.getPropertyType(),
                property.getReadMethod() == null ? null : property.getReadMethod()
                        .getGenericReturnType());
        this.property = property;
        this.readable = property.getReadMethod() != null;
        this.writable = property.getWriteMethod() != null;
    }

    @Override
    public void set(Object object, Object value) throws Exception {
        property.getWriteMethod().invoke(object, value);
    }

    @Override
    public Object get(Object object) {
        try {
            property.getReadMethod().setAccessible(true);// issue 50
            return property.getReadMethod().invoke(object);
        } catch (Exception e) {
            throw new YAMLException("Unable to find getter for property '" + property.getName()
                    + "' on object " + object + ":" + e);
        }
    }

    @Override
    public boolean isWritable() {
        return writable;
    }

    @Override
    public boolean isReadable() {
        return readable;
    }
}