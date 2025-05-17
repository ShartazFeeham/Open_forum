package com.open.forum.review.infrastructure.external;

import com.open.forum.review.domain.external.client.UserServiceClient;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceClientImpl implements UserServiceClient {
    /**
     * Retrieves the user ID associated with a given username.
     *
     * @param username the username of the user
     * @return the user ID associated with the username
     */
    @Override
    public Long getUserIdByUsername(@NotNull String username) {
        return 0L;
    }

    /**
     * Retrieves the username associated with a given user ID.
     *
     * @param userId the ID of the user
     * @return the username associated with the user ID
     */
    @Override
    public String getUsernameByUserId(@NotNull Long userId) {
        return "";
    }

    /**
     * Retrieves the details of a user by their ID.
     *
     * @param userId the ID of the user
     * @return a map containing the details of the user
     */
    @Override
    public Map<String, Object> getUserById(@NotNull Long userId) {
        return Map.of();
    }

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean isUserExist(@NotNull Long userId) {
        return false;
    }
}
