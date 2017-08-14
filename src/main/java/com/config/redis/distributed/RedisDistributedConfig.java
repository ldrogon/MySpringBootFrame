package com.config.redis.distributed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/7/22.
 */
//@Configuration
//@EnableConfigurationProperties(RedisDistributedProperties.class)
public class RedisDistributedConfig {
    @Autowired
    private RedisDistributedProperties redisDistributedProperties;

    @Bean
    public ShardedJedisPool shardedJedisPool(){
        List<JedisShardInfo> jedisShardInfoList= redisDistributedProperties.getNodes().stream().map(node->node.toJedisShardInfo()).collect(Collectors.toList());
        //1、构造分布式pool
        ShardedJedisPool shardedJedisPool=new ShardedJedisPool(redisDistributedProperties,jedisShardInfoList);
        return shardedJedisPool;
    }
}
