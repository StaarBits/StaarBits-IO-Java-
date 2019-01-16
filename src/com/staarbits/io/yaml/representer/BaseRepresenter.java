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
package com.staarbits.io.yaml.representer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.staarbits.io.yaml.DumperOptions;
import com.staarbits.io.yaml.introspector.PropertyUtils;
import com.staarbits.io.yaml.nodes.AnchorNode;
import com.staarbits.io.yaml.nodes.MappingNode;
import com.staarbits.io.yaml.nodes.Node;
import com.staarbits.io.yaml.nodes.NodeTuple;
import com.staarbits.io.yaml.nodes.ScalarNode;
import com.staarbits.io.yaml.nodes.SequenceNode;
import com.staarbits.io.yaml.nodes.Tag;

/**
 * Represent basic YAML structures: scalar, sequence, mapping
 */
public abstract class BaseRepresenter {
    protected final Map<Class<?>, Represent> representers = new HashMap<Class<?>, Represent>();
    /**
     * in Java 'null' is not a type. So we have to keep the null representer
     * separately otherwise it will coincide with the default representer which
     * is stored with the key null.
     */
    protected Represent nullRepresenter;
    // the order is important (map can be also a sequence of key-values)
    protected final Map<Class<?>, Represent> multiRepresenters = new LinkedHashMap<Class<?>, Represent>();
    protected Character defaultScalarStyle;
    protected DumperOptions.FlowStyle defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
    protected final Map<Object, Node> representedObjects = new IdentityHashMap<Object, Node>() {
        private static final long serialVersionUID = -5576159264232131854L;

        public Node put(Object key, Node value) {
            return super.put(key, new AnchorNode(value));
        }
    };

    protected Object objectToRepresent;
    private PropertyUtils propertyUtils;
    private boolean explicitPropertyUtils = false;

    public Node represent(Object data) {
        Node node = representData(data);
        representedObjects.clear();
        objectToRepresent = null;
        return node;
    }

    protected final Node representData(Object data) {
        objectToRepresent = data;
        // check for identity
        if (representedObjects.containsKey(objectToRepresent)) {
            Node node = representedObjects.get(objectToRepresent);
            return node;
        }
        // }
        // check for null first
        if (data == null) {
            Node node = nullRepresenter.representData(null);
            return node;
        }
        // check the same class
        Node node;
        Class<?> clazz = data.getClass();
        if (representers.containsKey(clazz)) {
            Represent representer = representers.get(clazz);
            node = representer.representData(data);
        } else {
            // check the parents
            for (Class<?> repr : multiRepresenters.keySet()) {
                if (repr.isInstance(data)) {
                    Represent representer = multiRepresenters.get(repr);
                    node = representer.representData(data);
                    return node;
                }
            }

            // check defaults
            if (multiRepresenters.containsKey(null)) {
                Represent representer = multiRepresenters.get(null);
                node = representer.representData(data);
            } else {
                Represent representer = representers.get(null);
                node = representer.representData(data);
            }
        }
        return node;
    }

    protected Node representScalar(Tag tag, String value, Character style) {
        if (style == null) {
            style = this.defaultScalarStyle;
        }
        Node node = new ScalarNode(tag, value, null, null, style);
        return node;
    }

    protected Node representScalar(Tag tag, String value) {
        return representScalar(tag, value, null);
    }

    protected Node representSequence(Tag tag, Iterable<?> sequence, Boolean flowStyle) {
        int size = 10;// default for ArrayList
        if (sequence instanceof List<?>) {
            size = ((List<?>) sequence).size();
        }
        List<Node> value = new ArrayList<Node>(size);
        SequenceNode node = new SequenceNode(tag, value, flowStyle);
        representedObjects.put(objectToRepresent, node);
        boolean bestStyle = true;
        for (Object item : sequence) {
            Node nodeItem = representData(item);
            if (!(nodeItem instanceof ScalarNode && ((ScalarNode) nodeItem).getStyle() == null)) {
                bestStyle = false;
            }
            value.add(nodeItem);
        }
        if (flowStyle == null) {
            if (defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                node.setFlowStyle(defaultFlowStyle.getStyleBoolean());
            } else {
                node.setFlowStyle(bestStyle);
            }
        }
        return node;
    }

    protected Node representMapping(Tag tag, Map<?, ?> mapping, Boolean flowStyle) {
        List<NodeTuple> value = new ArrayList<NodeTuple>(mapping.size());
        MappingNode node = new MappingNode(tag, value, flowStyle);
        representedObjects.put(objectToRepresent, node);
        boolean bestStyle = true;
        for (Map.Entry<?, ?> entry : mapping.entrySet()) {
            Node nodeKey = representData(entry.getKey());
            Node nodeValue = representData(entry.getValue());
            if (!(nodeKey instanceof ScalarNode && ((ScalarNode) nodeKey).getStyle() == null)) {
                bestStyle = false;
            }
            if (!(nodeValue instanceof ScalarNode && ((ScalarNode) nodeValue).getStyle() == null)) {
                bestStyle = false;
            }
            value.add(new NodeTuple(nodeKey, nodeValue));
        }
        if (flowStyle == null) {
            if (defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                node.setFlowStyle(defaultFlowStyle.getStyleBoolean());
            } else {
                node.setFlowStyle(bestStyle);
            }
        }
        return node;
    }

    public void setDefaultScalarStyle(DumperOptions.ScalarStyle defaultStyle) {
        this.defaultScalarStyle = defaultStyle.getChar();
    }

    public void setDefaultFlowStyle(DumperOptions.FlowStyle defaultFlowStyle) {
        this.defaultFlowStyle = defaultFlowStyle;
    }

    public DumperOptions.FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }

    public void setPropertyUtils(PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        this.explicitPropertyUtils = true;
    }

    public final PropertyUtils getPropertyUtils() {
        if (propertyUtils == null) {
            propertyUtils = new PropertyUtils();
        }
        return propertyUtils;
    }

    public final boolean isExplicitPropertyUtils() {
        return explicitPropertyUtils;
    }
}
