package com.example.graph_vizualizer.graph;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer {
    public static byte[] readFile(File file) {
        try {
            return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static byte[] serialize(Graph graph) {
        try {
            JAXBContext context = JAXBContext.newInstance(Graph.class);
            Marshaller marshaller = context.createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(graph, baos);
            return baos.toByteArray();
        } catch (JAXBException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static Graph deserialize(byte[] xmlGraph) {
        try {
            JAXBContext context = JAXBContext.newInstance(Graph.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlGraph);
            return  (Graph) unmarshaller.unmarshal(bais);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
