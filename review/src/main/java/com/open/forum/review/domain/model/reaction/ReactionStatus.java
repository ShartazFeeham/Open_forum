package com.open.forum.review.domain.model.reaction;

/**
 * If a user updates the post or comment privacy, no further reactions should be allowed.
 * However, users with an unrefreshed page or API access can still attempt to react.
 * To handle this, we need to check the post/comment privacy before posting a reaction.
 *
 * However, this approach could result in significantly higher load on the post service,
 * as this service might generate many requests for the same privacy data.
 * A better approach is to cache the privacy data after the first request and reuse it
 * for subsequent checks.
 *
 * Instead of making direct service calls for every reaction, we can use a state-based,
 * event-driven approach. This approach would first check the cache, hold the event for
 * a short period, and then fire a single request instead of multiple redundant ones.
 */
public enum ReactionStatus {
    PENDING, APPROVED
}
