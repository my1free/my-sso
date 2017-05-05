package com.michealyang.sso.service.cache;

import org.springframework.stereotype.Service;

/**
 * Created by michealyang on 17/5/3.
 */
@Service("distributedCache")
public class DistributedCache implements ICache{
    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public boolean set(String key, Object value) {
        return false;
    }

    @Override
    public boolean setex(String key, Object value, int expire) {
        return false;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }
}
