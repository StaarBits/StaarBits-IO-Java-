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

import com.staarbits.io.yaml.nodes.Node;

/**
 * Provide a way to construct a Java instance out of the composed Node. Support
 * recursive objects if it is required. (create Native Data Structure out of
 * Node Graph)
 * 
 * @see <a href="http://yaml.org/spec/1.1/#id859109">Chapter 3. Processing YAML
 *      Information</a>
 */
public interface Construct {
    /**
     * Construct a Java instance with all the properties injected when it is
     * possible.
     * 
     * @param node
     *            composed Node
     * @return a complete Java instance
     */
    Object construct(Node node);

    /**
     * Apply the second step when constructing recursive structures. Because the
     * instance is already created it can assign a reference to itself.
     * 
     * @param node
     *            composed Node
     * @param object
     *            the instance constructed earlier by
     *            <code>construct(Node node)</code> for the provided Node
     */
    void construct2ndStep(Node node, Object object);
}
