package com.example.graph_vizualizer.graph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AccessType")
@XmlEnum
public enum AccessType {
    @XmlEnumValue("PUBLIC") PUBLIC,
    @XmlEnumValue("PROTECTED")  PROTECTED,
    @XmlEnumValue("PRIVATE") PRIVATE,
    @XmlEnumValue("DEFAULT") DEFAULT
}
