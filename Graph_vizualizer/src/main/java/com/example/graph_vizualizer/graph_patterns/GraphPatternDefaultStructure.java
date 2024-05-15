package com.example.graph_vizualizer.graph_patterns;

import com.example.graph_vizualizer.graph.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphPatternDefaultStructure implements GraphPattern {
    ArrayList<File> javaFiles = new ArrayList<>();
    private static final Pattern classPattern = Pattern.compile("(\\b(?:public|private|protected)\\b\\s+)?(\\b(?:class|interface|enum)\\b)\\s+(\\w+)\\b(?:\\s+extends\\s+(\\w+))?(?:\\s+implements\\s+(.+))?");
    private static final Pattern variablePattern = Pattern.compile("((?:\\b(?:public|private|protected|static|final)\\b\\s+)*)\\b(\\w+)\\b\\s+(\\w+)\\s*[=;]");
    Graph res;
    protected static String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf + 1);
    }
    protected void findFiles(File folder) {
        File[] files = folder.listFiles();
        if(files == null || files.length == 0) return;
        for(File f : files) {
            if(f.isFile() && getFileExtension(f.getName()).equals("java")) {
                javaFiles.add(f);
            }
            else if(f.isDirectory()) {
                findFiles(f);
            }
        }
    }
    protected void processJavaFile(File f) {
        List<String> commands = new ArrayList<>();
        try {
            commands = Files.readAllLines(Paths.get(f.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Point currentClass = new Point("", AccessType.PUBLIC, PointType.CLASS);
        for(String command : commands) {
            Matcher classMatcher = classPattern.matcher(command);
            if (classMatcher.find()) {
                String accessModifier = classMatcher.group(1);
                String classType = classMatcher.group(2);
                String className = classMatcher.group(3);
                String superClass = classMatcher.group(4);
                String implementedInterfacesStr = classMatcher.group(5);
                String[] implementedInterfaces = implementedInterfacesStr != null ? implementedInterfacesStr.split(",\\s*") : new String[0];
                AccessType access = AccessType.DEFAULT;
                PointType typeOfClass = PointType.CLASS;
                switch (accessModifier) {
                    case "public" : access = AccessType.PUBLIC; break;
                    case "protected" : access = AccessType.PROTECTED; break;
                    case "private" : access = AccessType.PRIVATE; break;
                }
                switch (classType) {
                    case "interface" : typeOfClass = PointType.INTERFACE; break;
                    case "class" : typeOfClass = PointType.CLASS; break;
                    case "enum": {
                        typeOfClass = PointType.ENUM;
                        res.pushPoint(new Point(className,access,typeOfClass));
                        continue;
                    }
                }
                Point newClass = new Point(className, access, typeOfClass);
                currentClass = newClass;
                res.pushPoint(newClass);
                if (superClass != null) {
                    if(res.findPoint(superClass) == null) res.AddPoint(new Point(superClass, AccessType.PUBLIC, PointType.CLASS));
                    res.AddEdge(new Edge(res.findPoint(superClass), res.findPoint(className), EdgeType.EXTENDS));
                }
                if (implementedInterfaces.length > 0) {
                    for(String newInterface : implementedInterfaces) {
                        if (res.findPoint(newInterface) == null)
                            res.AddPoint(new Point(newInterface, AccessType.PUBLIC, PointType.INTERFACE));
                        res.AddEdge(new Edge(res.findPoint(newInterface), res.findPoint(className), EdgeType.EXTENDS));
                    }
                }
            }
            Matcher variableMatcher = variablePattern.matcher(command);
            if (variableMatcher.find()) {
                Point currentVariable = new Point("", AccessType.DEFAULT, PointType.ORDINARY);
                String modifiersStr = variableMatcher.group(1);
                String[] modifiers = modifiersStr.trim().split("\\s+");
                String variableType = variableMatcher.group(2);
                if(variableType.equals("return") || variableType.equals("throw")) continue;
                for(String modifier : modifiers) {
                    switch (modifier) {
                        case "public" -> currentVariable.setaType(AccessType.PUBLIC);
                        case "protected" -> currentVariable.setaType(AccessType.PROTECTED);
                        case "private" -> currentVariable.setaType(AccessType.PRIVATE);
                        case "static" -> currentVariable.setpType(PointType.STATIC);
                    }
                }
                currentVariable.setName(variableType);
                if(res.findPoint(currentVariable.getName()) == null) res.AddPoint(currentVariable);
                res.AddEdge(new Edge(res.findPoint(currentClass.getName()), res.findPoint(currentVariable.getName()), EdgeType.CONTAIN));
            }
        }
    }
    @Override
    public byte[] newGraph(File source) {
        if(source.isFile()) {
            if(getFileExtension(source.getName()).equals("java")) {
                javaFiles.add(source);
            } else if (getFileExtension(source.getName()).equals("xml")) {
                try {
                    byte[] fileContent = Files.readAllBytes(Paths.get(source.getAbsolutePath()));
                    return fileContent;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        else {
            findFiles(source);
        }
        res = new Graph();
        for(File f : javaFiles) {
            processJavaFile(f);
        }
        try {
            return serialize(res);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private byte[] serialize(Graph graph)  throws javax.xml.bind.JAXBException {
        JAXBContext context = JAXBContext.newInstance(Graph.class);
        Marshaller marshaller = context.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(graph, baos);
        return baos.toByteArray();
    }
    @Override
    public String getName() {
        return "Default structure 1";
    }
}
