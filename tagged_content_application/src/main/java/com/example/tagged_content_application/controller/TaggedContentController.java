package com.example.tagged_content_application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tagged_content_application.helper.RetrieveTaggedContentHelper;

@RestController
public class TaggedContentController {

    private RetrieveTaggedContentHelper helper = new RetrieveTaggedContentHelper();

    @GetMapping("/taggedContent")
    public ResponseEntity<List<String>> getTaggedContent(@RequestParam("tag") String tag) {

        // Retrieve documents associated with the given tag and its sub-tags
        Set<String> taggedContent = helper.retrieveTaggedContent(tag);

        if (!taggedContent.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>(taggedContent));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}