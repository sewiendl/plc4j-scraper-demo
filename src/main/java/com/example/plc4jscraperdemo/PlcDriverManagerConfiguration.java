package com.example.plc4jscraperdemo;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.utils.connectionpool.PooledPlcDriverManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlcDriverManagerConfiguration {

    @Bean
    // see https://issues.apache.org/jira/browse/PLC4X-158
    public PlcDriverManager plcDriverManager() {
        return new PooledPlcDriverManager(pooledPlcConnectionFactory -> {
            GenericKeyedObjectPoolConfig<PlcConnection> poolConfig = new GenericKeyedObjectPoolConfig<>();
            poolConfig.setMaxTotalPerKey(1);
            poolConfig.setBlockWhenExhausted(true);
            poolConfig.setMaxWaitMillis(1_000L);
            return new GenericKeyedObjectPool<>(pooledPlcConnectionFactory, poolConfig);
        });
    }

}
