package com.microforum.posts.service;

import com.microforum.posts.entity.Tag;
import com.microforum.posts.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final Logger logger = LoggerFactory.getLogger(TagService.class);

    public List<Tag> getTagsListByIds(List<String> tags) {
        List<Tag> tagsList = new ArrayList<>();
        for (String tag : tags) {
            String name = tag.toLowerCase();
            Tag result;
            Optional<Tag> existingTag = tagRepository.findByName(name);
            if (existingTag.isPresent()) {
                result = existingTag.get();
            } else {
                Tag newTag = Tag.builder().name(name).build();
                logger.info("Creating new tag: {}", newTag);
                result = tagRepository.save(newTag);
                logger.info("Created new tag: {}", result);
            }
            tagsList.add(result);
        }
        return tagsList;
    }
}
