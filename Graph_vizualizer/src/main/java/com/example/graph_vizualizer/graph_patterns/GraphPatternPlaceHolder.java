package com.example.graph_vizualizer.graph_patterns;

import com.example.graph_vizualizer.graph.*;
import com.google.gson.Gson;

import java.io.File;

public class GraphPatternPlaceHolder implements GraphPattern {
    Graph res;
    public GraphPatternPlaceHolder() {
        res = new Graph();
        res.AddPoint(new Point("Place", AType.PUBLIC, PType.CLASS));
        res.AddPoint(new Point("Holder", AType.PUBLIC, PType.STATIC));
        res.AddEdge(new Edge("Place", "Holder", EType.POSSESSION));
    }

    @Override
    public String newGraph(File source) {
        Gson gson = new Gson();
        return gson.toJson(res);
    }
    @Override
    public String getName() {
        return "Placeholder";
    }
}
