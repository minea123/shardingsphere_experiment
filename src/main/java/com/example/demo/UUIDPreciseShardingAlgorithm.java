package com.example.demo;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@Component
public class UUIDPreciseShardingAlgorithm implements StandardShardingAlgorithm<String> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    Logger logger;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        // Use UUID hash to determine target shard
        int index = Math.abs(shardingValue.getValue().hashCode()) % availableTargetNames.size();
        String shardTarget = availableTargetNames.toArray(new String[0])[index];
        String shardTargetName = shardTarget.contains("ds") ? "database" : "table";
        LOGGER.debug("UUID " + shardingValue.getValue() + " shard to " + shardTargetName + " " + availableTargetNames.toArray(new String[0])[index]);
        return shardTarget;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        // TODO Auto-generated method stub
        // For now we don't need  range base sharding
        throw new UnsupportedOperationException("Unimplemented method 'doSharding'");
    }
}
