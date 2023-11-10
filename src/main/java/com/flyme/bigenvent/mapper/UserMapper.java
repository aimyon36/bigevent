package com.flyme.bigenvent.mapper;

import com.flyme.bigenvent.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
//  根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);
//添加用户
    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{password},now(),now())")
    void add(@Param("username") String username,@Param("password") String password);
    @Select("select * from user")
    List<User> findAll();
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where big_event.user.id=#{id}")
//    为avatUrl指定名称
    void updateAvatar(@Param("avatarUrl") String avatarUrl,@Param("id") Integer id);
    @Update("update big_event.user set password=#{password},update_time=now() where id=#{id}")
    void updatePassword(@Param("password") String password,@Param("id") Integer id);
}
