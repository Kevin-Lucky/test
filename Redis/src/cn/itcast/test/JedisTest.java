package cn.itcast.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {

    /**
     * 快速入门
     */
    @Test
    public void jedis(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        Jedis jedis = new Jedis("119.23.238.216",6379);
        jedis.auth("zhangqiang2");
        jedis.set("username","zhangqiang");
        String username = jedis.get("username");
        System.out.println(username);
        jedis.close();
    }
}
