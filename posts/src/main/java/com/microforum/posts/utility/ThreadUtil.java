package com.microforum.posts.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadUtil {
    private final ExecutorService executorService;
    private final Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    public ThreadUtil() {
        executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * This method is used to execute a task asynchronously with a virtual thread.
     * The advantage of using a virtual thread is that it doesn't block a thread from the pool.
     * This way, the use of a virtual thread is more efficient than using a regular thread pool.
     *
     * @param runnable the task to execute
     * @param onSuccess the task to execute if the task is successful
     * @param onFailure the task to execute if the task is failed
     */
    public void executeWithVirtualThread(Runnable runnable, Runnable onSuccess, Runnable onFailure) {
        executorService.execute(runnable);
    }

    /**
     * This method is used to execute a task asynchronously with a virtual thread.
     * And it doesn't throw any exception. Logs the exception if any.
     * The advantage of using a virtual thread is that it doesn't block a thread from the pool.
     * This way, the use of a virtual thread is more efficient than using a regular thread pool.
     *
     * @param runnable the task to execute
     * @param onSuccess the task to execute if the task is successful
     * @param onFailure the task to execute if the task is failed
     */
    public void executeWithVirtualThreadWithoutException(Runnable runnable, Runnable onSuccess, Runnable onFailure) {
        try {
            executeWithVirtualThread(runnable, onSuccess, onFailure);
        } catch (Exception e) {
            logger.error("Error executing task: {}", runnable, e);
        }
    }
}
