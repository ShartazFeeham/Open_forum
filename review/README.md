# Open Forum | Review service
The Review service is a microservice responsible for managing comments and reactions on posts in the Open Forum application. It allows users to add, edit, and delete comments, as well as react to posts with various emotions.
It is designed to be scalable, efficient, and easy to maintain, following best practices in software architecture and design.

## `🛠 Features (a.k.a. "The Wishlist")`

- Comments (CRUD style)
- Reacts (likes, loves, whatever)
- Replies to comments
- Comment edit history
- @Mentions

### `😵‍💫 Challenge #1: Post Privacy Bottleneck`
Problem: The review service faces significantly higher traffic than the post service, requiring frequent checks for post privacy (public, private, subscribers-only). This dependency could overwhelm the post service.

Solution:🎉 Cache! Review service keeps a local copy of post privacy. When privacy changes, Post sends an event. Problem (mostly) solved. No more spamming Post.

### `🫣 Challenge #2: Subscriber-Only Madness`

Problem:Verifying subscriber status for each comment or reaction on a subscriber-only post requires interaction with the user service. Caching all subscriber relationships is not feasible due to potential scale.

Solution:💀 Postponed. No "subscribers-only" for now. Later maybe a graph DB or a high-QPS Subscriber service. For now, skip the pain and build cool features first.

### `🌀 Challenge #3: I. I.. Idempotency`

Problem: Ensuring that duplicate reacts or comment requests do not lead to unintended side effects.

Solution: 🔁 For reacts: Make them stateless — same input (with reaction type), same result.
🔍 For comments: Hash the text, check before storing. Like a comment DNA test.

### `📈 Challenge #4 The Trillion hits Math`
Problem: Some posts will get TONS of reacts and comments. How to handle this? Let's assume there are 1 billion users
and each user creates 1000 posts in lifetime. And each post has 1000 reacts on average. 
That's: 1 billion * 1000 * 1000 = 1 quadrillion (10^15) reacts. The comment table also would reach trillion rows scale. 
Now a user reacts to a post we just need to check if the user reacted before or not, it's a simple yet groundbreaking query!
Solution: Use index and partitioning will reduce the problem significantly, but it's not enough.
- Approx row size: user_id(8 bytes) + post_id(8 bytes) + reaction_type(4 bytes) + created_at(8 bytes) = 28 bytes
- 1 billion rows = 28 GB (indexing can work fine) | remaining: 10^15/10^9 = 10^6
- 1000 partitions per shard | remaining: 10^6/10^3 = 10^3, well need 1000 shards
- 1000 shards | DONE

For simplicity & demonstration, I'll go with 3 shards + 10 partitions per shard + 1 million rows per partition + indices.  
If a company that has 1 billion users hires me than I'll consider the full math 😁.
Keys: user_id for sharding key to avoid hotkeys, post_id for partition.
But this will introduce querying in many shards for aggregation results! Now I really want to go with CQRS and NoSQL. 

### 🔄 `Challenge #5: Distributed Counter Chaos`

Problem: How do I handle concurrent read-write anomalies when incrementing reaction & comment count? The service will have many instances and data will be sharded across different database. So event count(table_colum) kind of query won't help as there are many tables storing same data.

Solution: Atomic operation could solve it if all data for a single post were in a single shard but that would 
case the celebrity problem aka hotkey problem. Shards will be based on user_id. Distributed counter using redis 
is a good choice but requires another service, optimistic locking would help but sometimes retry waiting queue 
would blast. ⚙️ I'll go with an event based counter - kafka stream + cache.

### `🧩 Challenge #6: Devide & get conquered`

Problem: Sharding by user_id prevents hotkeys, but now getting “latest comments on a post” is a nightmare. 
Because the latest comments are divided across shards, we need to query all of them!

Solution: I wish could choose post_id to be sharding key. (:D). A read service with NoSQL big data seems like best choice to me. 
But I'm not going for another service, it's too costly just to solve one query issue
🪓 So, brute-force query across shards. Not pretty, but cheaper than spinning up a whole new fancy read service. 

### `✏️ Challenge #7: Mood swing, lets edit!`

Problem: How do we handle edit history efficiently?

Solution: A delta storage that finds diff and stores seems to be the ultimate choice. However, it 
requires much more computation power, comparing to that, and considering that edit is rare,
📚 we can go with a separate table in SQL or list of versions in NoSQL. 

## Designing Open Forum - Review service : CLEAN ARCHITECTURE
```
└── src
├── main
│   ├── java
│   │   └── com
│   │       └── open.forum
│   │           └── review
│   │               ├── domain
│   │               │   ├── model
│   │               │   │   ├── Comment.java
│   │               │   │   ├── Reaction.java
│   │               │   │   ├── ReactionType.java
│   │               │   │   ├── ...
│   │               │   ├── repository
│   │               │   │   ├── CommentRepository.java
│   │               │   │   ├── ReactionRepository.java
│   │               │   │   ├── ...
│   │               │   ├── events
│   │               │   │   ├── CommentCreatedEvent.java
│   │               │   │   ├── ReactionAddedEvent.java
│   │               │   │   ├── PostPrivacyUpdatedEvent.java
│   │               │   │   ├── ...
│   │               │   │   ├── publisher
│   │               │   │   │   ├── CommentEventPublisher.java
│   │               │   │   │   ├── ...
│   │               │   │── client
│   │               │   │   ├── PostServiceClient.java
│   │               │   │   ├── ...
│   │               │   ├── cache
│   │               │   │   ├── PostPrivacyCache.java
│   │               │   │   ├── ReactionCounterCache.java
│   │               │   │   ├── ...
│   │               │
│   │               ├── application
│   │               │   ├── use cases
│   │               │   │   ├── comment
│   │               │   │   │   ├── AddCommentUseCase.java
│   │               │   │   │   ├── EditCommentUseCase.java
│   │               │   │   │   ├── GetCommentsForPostUseCase.java
│   │               │   │   │   ├── UpdatePostPrivacyUseCase.java
│   │               │   │   │   ├── ...
│   │               │   │   ├── reaction
│   │               │   │   │   ├── AddReactionUseCase.java
│   │               │   │   │   ├── ...
│   │               │   │   ├── ...
│   │               │   ├── dto
│   │               │   │   ├── command
│   │               │   │   │   ├── AddCommentCommand.java
│   │               │   │   │   ├── AddReactionCommand.java
│   │               │   │   │   ├── ...
│   │               │   │   ├── response
│   │               │   │   │   ├── CommentResponse.java
│   │               │   │   │   ├── ReactionResponse.java
│   │               │   │   │   ├── ...
│   │               │   ├── mapper
│   │               │   │   ├── CommentMapper.java
│   │               │   │   ├── ReactionMapper.java
│   │               │   │   ├── ...
│   │               │   ├── service
│   │               │   │   ├── CommentService.java
│   │               │   │   ├── ReactionService.java
│   │               │   │   ├── ...
│   │               │   ├── validator
│   │               │   │   ├── CommentValidator.java
│   │               │   │   ├── ...
│   │               │  
│   │               ├── infrastructure
│   │               │   ├── persistence
│   │               │   │   ├── jpa
│   │               │   │   │   ├── JpaCommentRepository.java
│   │               │   │   │   ├── JpaReactionRepository.java
│   │               │   │   │   ├── ...
│   │               │   ├── cache
│   │               │   │   ├── redis
│   │               │   │   │   ├── PostPrivacyCache.java
│   │               │   │   │   ├── ReactionCounterCache.java
│   │               │   │   │   ├── ...
│   │               │   ├── messaging
│   │               │   │   ├── kafka
│   │               │   │   │   ├── KafkaCommentEventPublisher.java
│   │               │   │   │   ├── CommentEventConsumer.java
│   │               │   │   │   ├── PostPrivacyEventConsumer.java
│   │               │   │   │   ├── ...
│   │               │   ├── client
│   │               │   │   ├── PostServiceClient.java
│   │               │   │   ├── UserServiceClient.java
│   │               │   │   ├── ...
│   │               │   ├── config
│   │               │   │   ├── KafkaConfig.java
│   │               │   │   ├── RedisConfig.java
│   │               │   ├── monitoring
│   │               │   │   ├── MetricsConfig.java
│   │               │
│   │               ├── presentation
│   │               │   ├── controller
│   │               │   │   ├── CommentController.java
│   │               │   │   ├── ReactionController.java
│   │               │   │   ├── ...
│   │               │
│   │               ├── shared
│   │               │   ├── exception
│   │               │   │   ├── CommentNotFoundException.java
│   │               │   │   ├── AccessDeniedException.java
│   │               │   │   ├── ...
│   │               │   ├── utils
│   │               │   ├── logging
│   │               │   ├── constants
│   │               │   ├── security
│   │               │   ├── ...
│   ├── resources
├── test
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── review
│   │               ├── application
│   │               ├── domain
│   │               ├── infrastructure
│   │               ├── presentation
└── README.md
```