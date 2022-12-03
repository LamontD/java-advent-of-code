package com.lamontd.aoc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalResourceInput {
    private static final Logger logger = LoggerFactory.getLogger(LocalResourceInput.class);
    private String resource;
    private Map<Integer, String> rawInput;

    public LocalResourceInput(String resourceFile) throws IOException {
        this.resource = resourceFile;
        this.rawInput = new HashMap<>();
        readInputFromSource();
    }

    public void readInputFromSource() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(classLoader.getResourceAsStream(resource)));
            String line = null;
            while ((line = lineNumberReader.readLine()) != null) {
                rawInput.put(lineNumberReader.getLineNumber(), line);
            }
    }

    public List<String> getInput() {
        return new ArrayList<String>(this.rawInput.values());
    }

    public Map<Integer, String> getRawInput() { return rawInput; }

}
