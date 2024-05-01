package com.example.graph_vizualizer.graph;

public class Point {
    String name;
    AType aType;
    PType pType;
    public Point(String n, AType at, PType pt) {
        name = n;
        aType = at;
        pType = pt;
    }
    public Point(Point p) {
        name = p.name;
        aType = p.aType;
        pType = p.pType;
    }
}

