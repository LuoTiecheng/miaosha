package com.ltc.miaosha.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: luotiecheng
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String host;

    private int port;

    private int timeout;

    private String password;

    private int poolMaxTotal;

    private int poolMaxIdle;

    private int poolMaxWait;
}
