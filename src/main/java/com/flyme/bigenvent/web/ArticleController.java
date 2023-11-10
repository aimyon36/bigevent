package com.flyme.bigenvent.web;

import com.flyme.bigenvent.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")

public class ArticleController {
@GetMapping("/list")
//获取请求头中name=au作为参数
public Result<String> list(){
//
////    验证token
//    try {
//        Map<String, Object> clains = JwtUtil.parseToken(token);
//        return Result.success("所有的文章数据");
//    }catch (Exception e){
//        httpServletResponse.setStatus(401);
//        return Result.error("未登录");
    return Result.success("获取所有的文章数据");
    }
    }
