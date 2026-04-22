package com.object.usercenter.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(RedisConfigProperties.class)
@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient(RedisConfigProperties properties) {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword());
        return Redisson.create(config);
    }

}
