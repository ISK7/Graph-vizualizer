package com.example.graph_vizualizer.graph_patterns;

import com.example.graph_vizualizer.graph.*;

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
        int clamCount = 0;
        for(String command : commands) {
            command = command.trim();
            if(command.contains("//")) command = command.substring(0,command.indexOf("//"));
            Matcher classMatcher = classPattern.matcher(command);
            clamCount += clamFinder(command);
            if (classMatcher.find()) {
                String accessModifier = classMatcher.group(1);
                if (accessModifier != null) accessModifier = accessModifier.trim();
                String classType = classMatcher.group(2);
                if (classType != null) classType = classType.trim();
                String className = classMatcher.group(3);
                if (className != null) className = className.trim();
                String superClass = classMatcher.group(4);
                if (superClass != null) superClass = superClass.trim();
                String implementedInterfacesStr = classMatcher.group(5);
                if (implementedInterfacesStr != null) {
                    if(implementedInterfacesStr.lastIndexOf("{") != -1) implementedInterfacesStr = implementedInterfacesStr.replace("{", " ");
                    implementedInterfacesStr = implementedInterfacesStr.trim();
                }
                String[] implementedInterfaces = implementedInterfacesStr != null ? implementedInterfacesStr.split(",\\s*") : new String[0];
                for (int i = 0; i < implementedInterfaces.length; i++) {
                    implementedInterfaces[i] = implementedInterfaces[i].trim();
                }
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
                        res.AddPoint(new Point(className,access,typeOfClass));
                        continue;
                    }
                }
                Point newClass = new Point(className, access, typeOfClass);
                currentClass = newClass;
                res.pushPoint(newClass);
                if (superClass != null) {
                    if(res.findPoint(superClass, access) == null) res.AddPoint(new Point(superClass, AccessType.PUBLIC, PointType.CLASS));
                    res.AddEdge(new Edge(res.findPoint(superClass, AccessType.PUBLIC), res.findPoint(className, access), EdgeType.EXTENDS));
                }
                if (implementedInterfaces.length > 0) {
                    for(String newInterface : implementedInterfaces) {
                        if (res.findPoint(newInterface, AccessType.PUBLIC) == null)
                            res.AddPoint(new Point(newInterface, AccessType.PUBLIC, PointType.INTERFACE));
                        res.AddEdge(new Edge(res.findPoint(newInterface, AccessType.PUBLIC), res.findPoint(className, access), EdgeType.EXTENDS));
                    }
                }
            }

            Matcher variableMatcher = variablePattern.matcher(command);
            if (variableMatcher.find() && clamCount == 1) {
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
                if(res.findPoint(currentVariable.getName(), currentVariable.getaType()) == null) res.AddPoint(currentVariable);
                res.AddEdge(new Edge(res.findPoint(currentClass.getName(), currentClass.getaType()),
                        res.findPoint(currentVariable.getName(), currentVariable.getaType()), EdgeType.CONTAIN));
            }
        }
    }

    private int clamFinder(String command) {
        int res = 0;
        for(int i = 0; i < command.length(); i++) {
            if(command.charAt(i) == '{') res++;
            if(command.charAt(i) == '}') res--;
        }
        return res;
    }

    @Override
    public byte[] newGraph(File source) {
        res = new Graph();
        if(source.isFile()) {
            if(getFileExtension(source.getName()).equals("java")) {
                javaFiles.add(source);
            } else if (getFileExtension(source.getName()).equals("xml")) {
                return Serializer.readFile(source);
            }
        }
        else {
            findFiles(source);
        }
        res = new Graph();
        for(File f : javaFiles) {
            processJavaFile(f);
        }
        return Serializer.serialize(res);
    }
    @Override
    public String getName() {
        return "Default structure 1";
    }
}
