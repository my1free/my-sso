package com.michealyang.sso.service.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.michealyang.commons.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michealyang on 17/5/3.
 */
@Service("localCache")
public class LocalCache implements ICache{
    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    private Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object get(String key) {
        if(StringUtils.isBlank(key)) return null;
        JSONObject jsonObject = (JSONObject)cacheMap.get(key);
        if(jsonObject == null) return null;
        Integer expire = jsonObject.getInteger("expire");
        Integer valid = jsonObject.getInteger("valid");
        if(expire != null && expire > 0){
            Long ctime = jsonObject.getLong("ctime");
            if(ctime == null) {
                return null;
            }else{
                if(DateUtil.now() - ctime > expire) {
                    delete(key);
                    return null;
                }
            }
        }
        return jsonObject;
    }

    @Override
    public boolean set(String key, Object value) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key should not be blank");
        Preconditions.checkArgument(value != null, "value should not be null");
        JSONObject jsonObject = (JSONObject)JSON.toJSON(value);
        jsonObject.put("ctime", DateUtil.now());
        if(cacheMap.containsKey(key)) {
            return false;
        }
        cacheMap.put(key, jsonObject);
        return true;
    }

    /**
     *
     * @param key
     * @param value
     * @param expire    <=0的值表示永不失效，单位为s
     * @return
     */
    @Override
    public boolean setex(String key, Object value, int expire) {
        JSONObject jsonObject = (JSONObject)JSON.toJSON(value);
        jsonObject.put("ctime", DateUtil.now());
        jsonObject.put("expire", expire);
        if(cacheMap.containsKey(key)) {
            return false;
        }
        cacheMap.put(key, jsonObject);
        return true;
    }

    @Override
    public boolean delete(String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "key should not be blank");
        return cacheMap.remove(key) != null;
    }
}
