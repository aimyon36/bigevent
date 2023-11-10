package com.flyme.bigenvent.service.impl;

import com.flyme.bigenvent.mapper.UserMapper;
import com.flyme.bigenvent.pojo.User;
import com.flyme.bigenvent.service.UserService;
import com.flyme.bigenvent.utils.Md5Util;
import com.flyme.bigenvent.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public User findByUsername(String username) {
        log.info("服务层的用户名+{}",username);
        User user=userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        log.info("服务层注册的用户名+{}",username);
//        加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username,md5String);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updatePassword(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        log.info("{}+已更新密码,新密码为+{}",username,newPwd);
        String password = Md5Util.getMD5String(newPwd);
        userMapper.updatePassword(password,id);
    }
}
