package com.example.graph_vizualizer.graph;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"points", "edges"})
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

    @XmlElementWrapper(name = "points")
    @XmlElement(name = "point")
    public ArrayList<Point> getPoints() {
        return points;
    }
    public void setPoints(ArrayList<Point> points) { this.points = points; }

    @XmlElementWrapper(name = "edges")
    @XmlElement(name = "edge")
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    public void setEdges(ArrayList<Edge> edges) { this.edges = edges; }

    public void AddPoint(Point p) {
        points.add(p);
    }
    public boolean changePoint(Point newP, String name) {
        for(Point p : points) {
            if(p.getName().equals(name)) {
                p = newP;
                return true;
            }
        }
        return false;
    }
    public void pushPoint(Point newP) {
        String name = newP.getName();
        AccessType access = newP.getaType();
        for(Point p : points) {
            if(p.getName().equals(name) && p.getaType().equals(access)) {
                p = newP;
                return;
            }
        }
        points.add(newP);
    }
    public void AddEdge(Edge e) {
        edges.add(e);
    }
    public Point findPoint(String name, AccessType accessType) {
        for(Point p : points) {
            if(p.getName().equals(name) && p.getaType().equals(accessType)) return p;
        }
        return null;
    }
}
