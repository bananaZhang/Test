package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ZJY
 * @ClassName: JedisFactory
 * @Description: JedisFactory
 * @date 2019/3/18 11:44
 */
public class JedisFactory {
    private static JedisPool jedisPool = null;
    private static Jedis jedis = null;

    public synchronized static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(200);

        jedisPool = new JedisPool(config, "39.105.8.99", 6379, 0, null);
    }

    public static Jedis getJedis() {
        try {
            if (jedisPool == null) {
                init();
            }
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedis;
    }
}
