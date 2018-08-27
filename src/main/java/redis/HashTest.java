package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * @author ZJY
 * @ClassName: HashTest
 * @Description: HashTest
 * @date 2018/6/8 14:36
 */
public class HashTest {

    private static JedisPool jedisPool = null;
    private static Jedis jedis = null;

    public synchronized static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(200);

        jedisPool = new JedisPool(config, "39.105.8.99", 6379, 0, null);
    }

    private static Jedis getJedis() {
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

    public Long hset(String key, String field, String value) {
        Long result;
        try (Jedis jedis = getJedis()) {
            result = jedis.hset(key, field, value);
        }
        return result;
    }

    public String hget(String key, String field) {
        String result;
        try (Jedis jedis = getJedis()) {
            result = jedis.hget(key, field);
        }
        return result;
    }

    public void subscribe(String channelName) {
        try (Jedis jedis = getJedis()) {
            JedisPubSub subscriber = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    System.out.println("receive channel: " + channel + "'s message: " + message + ", dealing......");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("deal success");
                }

                @Override
                public void onSubscribe(String channel, int subscribedChannels) {
                    System.out.println("subscribe channel " + channel);
                }
            };
            jedis.subscribe(subscriber, channelName);
        }
    }

    public Long publish(String channel, String msg) {
        Long result;
        try (Jedis jedis = getJedis()) {
            result = jedis.publish(channel, msg);
        }
        return result;
    }


    public static void main(String[] args) throws InterruptedException {
        HashTest test = new HashTest();
        test.hset("test:hash:store", "aaa", "111");// 以":"形式隔开在redis中以分级文件夹形式展示
        System.out.println("redis get: " + test.hget("test:hash:store", "aaa"));

//        final String channelName = "channel";
//
//        new Thread(() -> {
//            test.subscribe(channelName);
//        }).start();
//
//        System.out.println("subscribe success...");
//
//        Thread.sleep(2000);
//
//        System.out.println("start send message...");
//
//        test.publish(channelName, "send a message111");
//        test.publish(channelName, "send a message222");
//        test.publish(channelName, "send a message333");
//        test.publish(channelName, "send a message444");
//        test.publish(channelName, "send a message555");
//
//        System.out.println("send message end...");
    }
}
