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
package com.staarbits.io.yaml.constructor;

import com.staarbits.io.yaml.error.YAMLException;
import com.staarbits.io.yaml.nodes.Node;

/**
 * Because recursive structures are not very common we provide a way to save
 * some typing when extending a constructor
 */
public abstract class AbstractConstruct implements Construct {

    /**
     * Fail with a reminder to provide the seconds step for a recursive
     * structure
     * 
     * @see Construct#construct2ndStep(Node,
     *      java.lang.Object)
     */
    public void construct2ndStep(Node node, Object data) {
        if (node.isTwoStepsConstruction()) {
            throw new IllegalStateException("Not Implemented in " + getClass().getName());
        } else {
            throw new YAMLException("Unexpected recursive structure for Node: " + node);
        }
    }
}
