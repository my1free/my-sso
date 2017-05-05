package com.michealyang.sso.service.cache;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by michealyang on 17/5/3.
 */
@Service("cacheProxy")
public class CacheProxy implements ICache{

    private static final Logger logger = LoggerFactory.getLogger(CacheProxy.class);

    private static final boolean useLocalCache = true;

    @Resource
    private ICache localCache;

    @Resource
    private ICache distributedCache;

    //暂时返回JsonObject，返回具体Java类型，需要进一步的处理
    @Override
    public Object get(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key));
        if(useLocalCache) {
            return localCache.get(key);
        }else {
            return distributedCache.get(key);
        }
    }

    @Override
    public boolean set(String key, Object value) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key));
        return false;
    }

    @Override
    public boolean setex(String key, Object value, int expire) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key));
        if(useLocalCache) {
            return localCache.setex(key, value, expire);
        }else {
            return distributedCache.setex(key, value, expire);
        }
    }

    @Override
    public boolean delete(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key));
        if(useLocalCache) {
            return localCache.delete(key);
        }else {
            return distributedCache.delete(key);
        }
    }
}
