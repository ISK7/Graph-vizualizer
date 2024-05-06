package com.example.graph_vizualizer.graph;

import java.util.ArrayList;

public class Graph {
    ArrayList<Point> points;
    ArrayList<Edge> edges;
    public Graph() {
        points = new ArrayList<>();
        edges = new ArrayList<>();
    }
    public Graph(ArrayList<Point> p, ArrayList<Edge> e) {
        points = new ArrayList<>(p);
        edges = new ArrayList<>(e);
    }
    public Graph(Graph gr) {
        points = new ArrayList<>();
        edges = new ArrayList<>();
        for(Point i : gr.points) {
            points.add(new Point(i));
        }
        for(Edge i : gr.edges) {
            edges.add(new Edge(i));
        }
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void AddPoint(Point p) {
        points.add(p);
    }
    public void AddEdge(Edge e) {
        edges.add(e);
    }
}
