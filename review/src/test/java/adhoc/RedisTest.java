package adhoc;

import jakarta.annotation.PostConstruct;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    public class RedisTest {
        private final RedissonClient redissonClient;
        private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

        public RedisTest(RedissonClient redissonClient) {
            this.redissonClient = redissonClient;
        }

        @PostConstruct
        public void init() {
            // Initialize the Redisson client or perform any setup needed
            log.info("Redisson client initialized: {}", redissonClient);
            long time = System.currentTimeMillis();
            int num = 69;
            long fib = fibonacci(num);
            log.info("Time taken to calculate Fibonacci: {} ms", System.currentTimeMillis() - time);
            log.info("Fibonacci of {}: {}", num, fib);
        }

        private long fibonacci(long number) {
            RBucket<Object> fibonacciBucket = redissonClient.getBucket("fibonacci:" + number);
            RAtomicDouble atomicDouble = redissonClient.getAtomicDouble("fibonacci:" + number);
            if (fibonacciBucket.isExists()) {
                return (long) fibonacciBucket.get();
            } else {
                long result = number <= 1 ? number : fibonacci(number - 1) + fibonacci(number - 2);
                fibonacciBucket.set(result);
                return result;
            }
        }
    }