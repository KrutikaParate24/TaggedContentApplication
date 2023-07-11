package com.example.tagged_content_application.helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RetrieveTaggedContentHelper {

    private final Map<String, List<String>> tagToDocuments;

    public RetrieveTaggedContentHelper() {
        // Initialize the tag to documents mapping
        tagToDocuments = new HashMap<>();
        loadTagHierarchyFromFile();
    }

    private void loadTagHierarchyFromFile() {
        try {
            Path filePath = Path.of("src\\main\\resources\\tag_hierarchy.txt");
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(":");
                String tag = parts[0].trim();
                String[] subTags = parts[1].split(",");

                tagToDocuments.put(tag, Arrays.asList(subTags));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<String> retrieveTaggedContent(String tag) {
        Set<String> taggedContent = new HashSet<>();

        // Recursively traverse the tag hierarchy to retrieve all documents
        retrieveTaggedContentRecursive(tag, taggedContent);

        return taggedContent;
    }

    private void retrieveTaggedContentRecursive(String tag, Set<String> taggedContent) {
        if (tagToDocuments.containsKey(tag)) {
            List<String> subTags = tagToDocuments.get(tag);
            for (String subTag : subTags) {
                // Add documents associated with the sub-tag
                taggedContent.addAll(retrieveTaggedContent(subTag));
            }
        }

        // Add documents associated with the current tag
        taggedContent.addAll(getDocumentsForTag(tag));
    }

    private List<String> getDocumentsForTag(String tag) {
        List<String> documents = new ArrayList<>();

        if (tag.equals("animals")) {
            documents.add("https://example.com/animal1");
            documents.add("https://example.com/animal2");
        } else if (tag.equals("mammals")) {
            documents.add("https://example.com/mammal1");
            documents.add("https://example.com/mammal2");
        } else if (tag.equals("birds")) {
            documents.add("https://example.com/bird1");
            documents.add("https://example.com/bird2");
        }

        return documents;
    }
}