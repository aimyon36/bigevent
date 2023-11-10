package com.flyme.bigenvent.service;

import com.flyme.bigenvent.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void updateAvatar(String avatarUrl);

    User findByUsername(String username);

    void register(String username, String password);

    List<User> findAll();

//    更新

    void update(User user);

    void updatePassword(String newPwd);
}
