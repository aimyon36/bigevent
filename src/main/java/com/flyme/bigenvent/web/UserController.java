package com.flyme.bigenvent.web;

import com.flyme.bigenvent.pojo.Result;
import com.flyme.bigenvent.pojo.User;
import com.flyme.bigenvent.service.UserService;
import com.flyme.bigenvent.utils.JwtUtil;
import com.flyme.bigenvent.utils.Md5Util;
import com.flyme.bigenvent.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Resource
    private UserService userService;

//    @GetMapping("/findAll")
//    public List<User> findAll() {
//        List<User> all = userService.findAll();
//        log.info("{}", all);
//        return userService.findAll();
//    }
//    测试springboot启动
@GetMapping("/hello")
public String hello(){
    return "hello";
}
//测试数据库连接
    @GetMapping("/findAll")
//    起到约束作用
    public Result<List<User>> findAll(){
        return Result.success(userService.findAll());
    }

    /**
     *
     */
    @PostMapping("/register")

    public Result register(@RequestParam("username")@Pattern(regexp = "^\\S{5,16}$") String username, @RequestParam("password")@Pattern(regexp = "^\\S{5,16}$") String password) {
//        查询用户
        User user = userService.findByUsername(username);
        log.info("{},{}", username,password);
        if (user == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }

    }
//身份认证，令牌
//在未登录的状态下，不能访问其他的资源
//    可以携带业务数据，减少查询次数
//    保证信息的合法性
//jwt 头，有效负荷 签名
    //    登录接口
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUsername(username);
        if(loginUser==null){
            return Result.error("用户不存在");
        }
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
//放入业务数据
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            return Result.success(token);

        }
        return Result.error("密码错误");
    }
    @GetMapping("userInfo")
    public Result<User> userInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }
    @PutMapping("/update")
//    实体参数如何校验
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
//    只有key value吻合时才能直接使用实体类,json转map
    public Result updatePassword(@RequestBody Map<String,String> map){
        String oldPwd = map.get("old_pwd");
        String newPwd = map.get("new_pwd");
        String rePwd = map.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少参数");
        }
        Map<String,Object> map2 = ThreadLocalUtil.get();
        String username = (String) map2.get("username");
        User loginUser = userService.findByUsername(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        if(!rePwd.equals(newPwd)){
            return Result.error("密码不一样");
        }
        userService.updatePassword(newPwd);

        return Result.success();
    }
}
