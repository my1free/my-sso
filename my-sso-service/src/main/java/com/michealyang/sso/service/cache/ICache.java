package com.michealyang.sso.service.cache;

/**
 * Created by michealyang on 17/5/3.
 */
public interface ICache {
    public Object get(String key);

    public boolean set(String key, Object value);

    public boolean setex(String key, Object value, int expire);

    public boolean delete(String key);
}
