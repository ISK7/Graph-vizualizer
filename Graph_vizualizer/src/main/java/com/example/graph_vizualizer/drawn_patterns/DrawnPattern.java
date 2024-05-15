package com.example.graph_vizualizer.drawn_patterns;


import javafx.scene.layout.AnchorPane;

public interface DrawnPattern {
    public boolean drawGraph(AnchorPane pane, byte[] xmlGraph);
    public String getName();
}
