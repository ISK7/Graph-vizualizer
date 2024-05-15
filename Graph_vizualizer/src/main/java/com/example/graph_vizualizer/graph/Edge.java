package com.example.graph_vizualizer.graph;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"input", "output", "type"})
public class Edge {
    Point input;
    Point output;
    EdgeType type;
    public Edge(Point from, Point to, EdgeType tp) {
        input = from;
        output = to;
        type = tp;
    }
    public Edge(Edge eg) {
        input = eg.input;
        output = eg.output;
        type = eg.type;
    }
    public Edge() {
        input = new Point();
        output = new Point();
        type = EdgeType.CONTAIN;
    }

    @XmlIDREF
    @XmlElement(name = "input")
    public Point getInput() { return input; }
    public void setInput(Point input) { this.input = input; }

    @XmlIDREF
    @XmlElement(name = "output")
    public Point getOutput() { return output; }
    public void setOutput(Point output) { this.output = output; }

    @XmlElement(name = "type")
    public EdgeType getType() {
        return type;
    }
    public void setType(EdgeType type) {
        this.type = type;
    }
}

