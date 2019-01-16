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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

abstract public class GenericProperty extends Property {

    private Type genType;

    public GenericProperty(String name, Class<?> aClass, Type aType) {
        super(name, aClass);
        genType = aType;
        actualClassesChecked = aType == null;
    }

    private boolean actualClassesChecked;
    private Class<?>[] actualClasses;

    public Class<?>[] getActualTypeArguments() { // should we synchronize here ?
        if (!actualClassesChecked) {
            if (genType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    actualClasses = new Class<?>[actualTypeArguments.length];
                    for (int i = 0; i < actualTypeArguments.length; i++) {
                        if (actualTypeArguments[i] instanceof Class<?>) {
                            actualClasses[i] = (Class<?>) actualTypeArguments[i];
                        } else if (actualTypeArguments[i] instanceof ParameterizedType) {
                            actualClasses[i] = (Class<?>) ((ParameterizedType) actualTypeArguments[i])
                                    .getRawType();
                        } else if (actualTypeArguments[i] instanceof GenericArrayType) {
                            Type componentType = ((GenericArrayType) actualTypeArguments[i])
                                    .getGenericComponentType();
                            if (componentType instanceof Class<?>) {
                                actualClasses[i] = Array.newInstance((Class<?>) componentType, 0)
                                        .getClass();
                            } else {
                                actualClasses = null;
                                break;
                            }
                        } else {
                            actualClasses = null;
                            break;
                        }
                    }
                }
            } else if (genType instanceof GenericArrayType) {
                Type componentType = ((GenericArrayType) genType).getGenericComponentType();
                if (componentType instanceof Class<?>) {
                    actualClasses = new Class<?>[] { (Class<?>) componentType };
                }
            } else if (genType instanceof Class<?>) {// XXX this check is only
                                                     // required for IcedTea6
                Class<?> classType = (Class<?>) genType;
                if (classType.isArray()) {
                    actualClasses = new Class<?>[1];
                    actualClasses[0] = getType().getComponentType();
                }
            }
            actualClassesChecked = true;
        }
        return actualClasses;
    }
}
