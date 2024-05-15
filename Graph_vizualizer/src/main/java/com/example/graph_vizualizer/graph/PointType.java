package com.example.graph_vizualizer.graph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PointType")
@XmlEnum
public enum PointType {
    @XmlEnumValue("STATIC") STATIC,
    @XmlEnumValue("ORDINARY") ORDINARY,
    @XmlEnumValue("CLASS") CLASS,
    @XmlEnumValue("INTERFACE") INTERFACE,
    @XmlEnumValue("ENUM") ENUM,
    @XmlEnumValue("METHOD") METHOD
}
