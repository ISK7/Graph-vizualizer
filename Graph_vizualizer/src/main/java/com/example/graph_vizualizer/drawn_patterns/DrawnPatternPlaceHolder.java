package com.example.graph_vizualizer.drawn_patterns;

import com.example.graph_vizualizer.graph.Edge;
import com.example.graph_vizualizer.graph.Graph;
import com.example.graph_vizualizer.graph.Point;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class DrawnPatternPlaceHolder implements DrawnPattern{
    final private int classHeight = 15;
    final private int classWidth = 30;
    final private int staticHeight = 10;
    final private int staticWidth = 20;
    final private int ordinaryHeight = 10;
    final private int ordinaryWidth = 10;
    @Override
    public void drawGraph(StackPane pane, Graph sourceGraph) {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        Map<Point,Object> poObj = new HashMap<>();
        for(Point p : sourceGraph.getPoints()) {
            double w = getWidth(p);
            double h = getHeight(p);
            poObj.put(p, graph.insertVertex(parent,null, p.getName(), 0.0, 0.0, w, h));
        }
        for(Edge e : sourceGraph.getEdges()) {
            graph.insertEdge(parent,null,e.getType(), poObj.get(e.getInput()), poObj.get(e.getOutput()));
        }

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(parent);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        StackPane stackPane = new StackPane(graphComponent);
    }

    private int getWidth(Point p) {
        switch (p.getpType()) {
            case CLASS -> {return classWidth;}
            case STATIC -> {return staticWidth;}
            default -> {return ordinaryWidth;}
        }
    }

    private int getHeight(Point p) {
        switch (p.getpType()) {
            case CLASS -> {return classHeight;}
            case STATIC -> {return staticHeight;}
            default -> {return ordinaryHeight;}
        }
    }

    @Override
    public String getName() {
        return "Default_v_1";
    }
}
