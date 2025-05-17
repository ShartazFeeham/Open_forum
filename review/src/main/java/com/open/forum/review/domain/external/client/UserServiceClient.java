package com.open.forum.review.domain.external.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Map;

public interface UserServiceClient {

    /**
     * Retrieves the user ID associated with a given username.
     *
     * @param username the username of the user
     * @return the user ID associated with the username
     */
    Long getUserIdByUsername(@NotNull @Positive String username);

    /**
     * Retrieves the username associated with a given user ID.
     *
     * @param userId the ID of the user
     * @return the username associated with the user ID
     */
    String getUsernameByUserId(@NotNull @Positive Long userId);

    /**
     * Retrieves the details of a user by their ID.
     *
     * @param userId the ID of the user
     * @return a map containing the details of the user
     */
    Map<String, Object> getUserById(@NotNull @Positive Long userId);

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user
     * @return true if the user exists, false otherwise
     */
    boolean isUserExist(@NotNull @Positive Long userId);
}
