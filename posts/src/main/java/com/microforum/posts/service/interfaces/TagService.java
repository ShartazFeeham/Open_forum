package com.microforum.posts.service.interfaces;

import com.microforum.posts.entity.Tag;
import com.microforum.posts.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TagService {
    /**
     * Get list of tags by tag names
     * @param tags list of tag names
     * @return list of tags
     */
    List<Tag> getTagsListByNames(List<String> tags);

    /**
     * Get all tags
     * @param page page number
     * @param size page size
     * @return list of tags
     */
    List<Tag> getAllTags(int page, int size);

    /**
     * Get tag by name
     * @param name tag name
     * @return tag
     */
    Tag getTagByName(String name) throws ResourceNotFoundException;

    /**
     * Get tag by id
     * @param id tag id
     * @return tag
     */
    Tag getTagById(Long id) throws ResourceNotFoundException;
}
