package com.example.graph_vizualizer.graph_patterns;

import java.io.File;

public interface GraphPattern {
    public byte[] newGraph(File source);
    public String getName();
}
