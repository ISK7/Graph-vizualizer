package com.example.graph_vizualizer.graph_patterns;

import com.example.graph_vizualizer.graph.*;

import java.io.File;

public class GraphPatternPlaceHolder implements GraphPattern {
    Graph res;
    public GraphPatternPlaceHolder() {
        res = new Graph();
        Point p = new Point("Place", AType.PUBLIC, PType.CLASS);
        Point h = new Point("Holder", AType.PUBLIC, PType.STATIC);
        res.AddPoint(p);
        res.AddPoint(h);
        res.AddEdge(new Edge(p, h, EType.CONTAIN));
    }

    @Override
    public Graph newGraph(File source) {
        return res;
    }
    @Override
    public String getName() {
        return "Placeholder";
    }
}
