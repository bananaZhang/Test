package redis;

import redis.clients.jedis.Jedis;

/**
 * @author ZJY
 * @ClassName: StringTest
 * @Description: StringTest
 * @date 2019/3/18 11:43
 */
public class StringTest {
    public static void main(String[] args) {
    }

    /**
     * incr是线程安全的操作
     */
    public void incrTest() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try (Jedis jedis = JedisFactory.getJedis()) {
                    jedis.incr("incr");
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try (Jedis jedis = JedisFactory.getJedis()) {
                    jedis.incr("incr");
                }
            }
        }).start();
    }
}
