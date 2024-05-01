package com.example.graph_vizualizer.graph;

public class Edge {
    String input;
    String output;
    EType type;
    public Edge(String in, String out, EType tp) {
        input = in;
        output = out;
        type = tp;
    }
    public Edge(Edge eg) {
        input = eg.input;
        output = eg.output;
        type = eg.type;
    }
}

