package com.microforum.posts.service;

import com.microforum.posts.entity.Tag;
import com.microforum.posts.exception.ResourceNotFoundException;
import com.microforum.posts.repository.TagRepository;
import com.microforum.posts.service.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    /**
     * Get list of tags by tag names
     * @param tags list of tag names
     * @return list of tags
     */
    @Override
    public List<Tag> getTagsListByNames(List<String> tags) {
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

    /**
     * Get all tags
     *
     * @param page page number
     * @param size page size
     * @return list of tags
     */
    @Override
    public List<Tag> getAllTags(int page, int size) {
        var result = tagRepository
                .findAllTags(getPageRequest(page, size))
                .getContent();
        logger.info("Got all tags, result: {}", result);
        return result;
    }

    /**
     * Get tag by name
     *
     * @param name tag name
     * @return tag
     */
    @Override
    public Tag getTagByName(String name) throws ResourceNotFoundException {
        var result = tagRepository.findByName(name);
        if (result.isPresent()) {
            logger.info("Got tag by name: {}, result: {}", name, result);
            return result.get();
        } else {
            logger.error("Tag not found by name: {}", name);
            throw new ResourceNotFoundException("Tag", " where tag name = " + name);
        }
    }

    /**
     * Get tag by id
     *
     * @param id tag id
     * @return tag
     */
    @Override
    public Tag getTagById(Long id) throws ResourceNotFoundException {
        var result = tagRepository.findById(id);
        if (result.isPresent()) {
            logger.info("Got tag by id: {}, result: {}", id, result);
            return result.get();
        } else {
            logger.error("Tag not found by id: {}", id);
            throw new ResourceNotFoundException("Tag", " where tag id = " + id);
        }
    }

    private Pageable getPageRequest(int page, int size) {
        return PageRequest.of(page - 1, size);
    }
}
