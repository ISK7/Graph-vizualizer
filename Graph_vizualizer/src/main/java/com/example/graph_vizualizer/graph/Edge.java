package com.example.graph_vizualizer.graph;

public class Edge {
    Point input;
    Point output;
    EType type;
    public Edge(Point in, Point out, EType tp) {
        input = in;
        output = out;
        type = tp;
    }
    public Edge(Edge eg) {
        input = eg.input;
        output = eg.output;
        type = eg.type;
    }

    public Point getInput() {
        return input;
    }

    public Point getOutput() {
        return output;
    }

    public EType getType() {
        return type;
    }
}

