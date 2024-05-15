package com.example.graph_vizualizer.graph_patterns;

import com.example.graph_vizualizer.graph.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GraphPatternPlaceHolder implements GraphPattern {
    Graph res;
    public GraphPatternPlaceHolder() {
        res = new Graph();
        Point p = new Point("Place", AccessType.PUBLIC, PointType.CLASS);
        Point h = new Point("Holder", AccessType.PUBLIC, PointType.STATIC);
        res.AddPoint(p);
        res.AddPoint(h);
        res.AddEdge(new Edge(p, h, EdgeType.CONTAIN));
    }

    @Override
    public byte[] newGraph(File source) {
        try {
            JAXBContext context = JAXBContext.newInstance(Graph.class);
            Marshaller marshaller = context.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(res, baos);
            return baos.toByteArray();
        }catch (Exception ex) {
            return null;
        }
    }
    @Override
    public String getName() {
        return "Placeholder";
    }
}
