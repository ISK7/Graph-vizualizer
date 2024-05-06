package com.example.graph_vizualizer.graph_patterns;

import com.example.graph_vizualizer.graph.Graph;

import java.io.File;

public interface GraphPattern {
    public Graph newGraph(File source);
    public String getName();
}
