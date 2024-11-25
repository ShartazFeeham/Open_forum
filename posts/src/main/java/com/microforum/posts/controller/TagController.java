package com.microforum.posts.controller;

import com.microforum.posts.entity.Post;
import com.microforum.posts.entity.Tag;
import com.microforum.posts.service.interfaces.TagService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        var result = tagService.getAllTags(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable @NonNull String name) {
        var result = tagService.getTagByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagByName(@PathVariable @NonNull Long id) {
        var result = tagService.getTagById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/names")
    public ResponseEntity<List<Tag>> getTagsListByNames(@RequestParam @NonNull List<String> tags) {
        var result = tagService.getTagsListByNames(tags);
        return ResponseEntity.ok(result);
    }

}
