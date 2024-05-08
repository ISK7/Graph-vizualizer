package com.example.graph_vizualizer.drawn_patterns;


import com.example.graph_vizualizer.graph.Graph;
import javafx.scene.layout.AnchorPane;

public interface DrawnPattern {
    public void drawGraph(AnchorPane pane, Graph graph);
    public String getName();
}
