package com.example.graph_vizualizer.drawn_patterns;


import com.example.graph_vizualizer.graph.Graph;
import javafx.scene.layout.StackPane;

public interface DrawnPattern {
    public void drawGraph(StackPane pane, Graph graph);
    public String getName();
}
