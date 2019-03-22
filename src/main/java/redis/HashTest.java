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

    public Long hset(String key, String field, String value, int expireTime) {
        Long result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.hset(key, field, value);
        }
        return result;
    }

    public Long hsetnx(String key, String field, String value, int expireTime) {
        Long result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.hsetnx(key, field, value);
        }
        return result;
    }

    public String hget(String key, String field) {
        String result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.hget(key, field);
        }
        return result;
    }

    public String set(String key, String value) {
        String result;
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.set(key, value);
        }
        return result;
    }

    public void subscribe(String channelName) {
        try (Jedis jedis = JedisFactory.getJedis()) {
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
        try (Jedis jedis = JedisFactory.getJedis()) {
            result = jedis.publish(channel, msg);
        }
        return result;
    }


    public static void main(String[] args) throws InterruptedException {
        final HashTest test = new HashTest();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                test.publish("channel", String.valueOf(i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.subscribe("channel");
        }).start();
    }
}
