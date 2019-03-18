package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * @author ZJY
 * @ClassName: ListTest
 * @Description: ListTest
 * @date 2019/1/22 10:41
 */
public class ListTest {

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作
     * 当 key 存在但不是列表类型时，返回一个错误
     */
    public Long lpush(String key, String... items) {
        Long result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.lpush(key, items);
        }
        return result;
    }

    /**
     * 移除并返回列表 key 的头元素
     * 返回值：列表的头元素。当 key 不存在时，返回 nil
     */
    public String lpop(String key) {
        String result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.lpop(key);
        }
        return result;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作
     */
    public Long rpush(String key, String... items) {
        Long result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.rpush(key, items);
        }
        return result;
    }

    /**
     * 移除并返回列表 key 的尾元素
     * 返回值：列表的头元素。当 key 不存在时，返回 nil
     */
    public String rpop(String key) {
        String result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.rpop(key);
        }
        return result;
    }

    public static void main(String[] args) {
//        ListTest test = new ListTest();
//        test.lpush("L1", "1", "2", "3", "4", "5", "6", "7");

//        System.out.println(test.lpop("L1"));
//        System.out.println(test.rpop("L1"));

//        System.out.println(test.rpush("L1", "4"));

        JedisFactory.getJedis().expireAt("123:456", 1550318400);

    }
}
