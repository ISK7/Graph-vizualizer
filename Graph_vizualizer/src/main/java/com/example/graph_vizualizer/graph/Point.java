package com.example.graph_vizualizer.graph;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "pType", "aType"})
public class Point {
    String name;
    AccessType accessType;
    PointType pointType;
    public Point(String n, AccessType at, PointType pt) {
        name = n;
        accessType = at;
        pointType = pt;
    }
    public Point(Point p) {
        name = p.name;
        accessType = p.accessType;
        pointType = p.pointType;
    }
    public Point() {
        name = "";
        accessType = AccessType.DEFAULT;
        pointType = PointType.ORDINARY;
    }

    @XmlID
    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "aType")
    public AccessType getaType() {
        return accessType;
    }
    public void setaType(AccessType type) {
        accessType = type;
    }

    @XmlElement(name = "pType")
    public PointType getpType() {
        return pointType;
    }
    public void setpType(PointType type) {
        pointType = type;
    }

}

