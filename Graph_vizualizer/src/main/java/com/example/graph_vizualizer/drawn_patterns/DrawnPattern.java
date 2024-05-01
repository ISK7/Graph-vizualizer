package com.example.graph_vizualizer.drawn_patterns;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public interface DrawnPattern {
    public void drawGraph(Canvas canvas, String graph);
    public String getName();
}
