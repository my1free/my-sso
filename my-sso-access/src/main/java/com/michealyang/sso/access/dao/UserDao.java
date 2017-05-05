package com.michealyang.sso.access.dao;

import com.michealyang.sso.access.model.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.commons.lang3.StringUtils;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by michealyang on 17/4/21.
 */
public interface UserDao {

    final String TABLE_NAME = "user";

    @Select("select count(user_name) from " + TABLE_NAME + " where user_name=#{userName} and valid=1")
    public int getUserNameNum(String userName);

    @Select("select salt from " + TABLE_NAME + " where user_name=#{userName} and valid=1")
    public String getSaltByUserName(String userName);

    @Select("select * from " + TABLE_NAME + " where user_name=#{userName} and valid=1")
    public User getUserByUserName(String userName);

    @InsertProvider(type = SqlProvider.class, method = "insert")
    public int insert(User user);

    class SqlProvider {
        public String insert(User user) {
            BEGIN();
            INSERT_INTO(TABLE_NAME);
            if(StringUtils.isNotBlank(user.getUserName())){
                VALUES("user_name", "#{userName}");
            }
            if(StringUtils.isNotBlank(user.getPasswd())){
                VALUES("passwd", "#{passwd}");
            }
            if(StringUtils.isNotBlank(user.getSalt())){
                VALUES("salt", "#{salt}");
            }
            if(StringUtils.isNotBlank(user.getPhoneNum())){
                VALUES("phone_num", "#{phoneNum}");
            }

            return SQL();
        }
    }
}
