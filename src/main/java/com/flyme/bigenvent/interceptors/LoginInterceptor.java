package com.flyme.bigenvent.interceptors;

import com.flyme.bigenvent.utils.JwtUtil;
import com.flyme.bigenvent.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        令牌验证
        String token = request.getHeader("Authorization");
        try {
//             把业务数据存储到threadLocal中
            Map<String, Object> clains = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(clains);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }

    }
//    请求完成移除掉

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        清空ThreadLocal
        ThreadLocalUtil.remove();

    }
}
//this：隐式参数，表示当前对象
