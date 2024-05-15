package com.example.graph_vizualizer.graph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "EdgeType")
@XmlEnum
public enum EdgeType {
    @XmlEnumValue("IMPLEMENTS") IMPLEMENTS,
    @XmlEnumValue("CONTAIN") CONTAIN,
    @XmlEnumValue("EXTENDS") EXTENDS
}
