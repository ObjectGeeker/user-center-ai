package com.object.usercenter.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisConfigProperties {

    private String host;

    private String port;

    private String password;

}
