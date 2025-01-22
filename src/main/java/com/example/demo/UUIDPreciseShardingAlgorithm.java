package com.example.demo;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UUIDPreciseShardingAlgorithm implements StandardShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        // Use UUID hash to determine target shard
        int index = Math.abs(shardingValue.getValue().hashCode()) % availableTargetNames.size();
        return availableTargetNames.toArray(new String[0])[index];
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        // TODO Auto-generated method stub
        // For now we don't need  range base sharding
        throw new UnsupportedOperationException("Unimplemented method 'doSharding'");
    }
}
