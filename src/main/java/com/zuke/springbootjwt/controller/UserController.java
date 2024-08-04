package com.zuke.springbootjwt.controller;

import com.zuke.springbootjwt.entity.User;
import com.zuke.springbootjwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zukedog@163.com
 * @date 2024/8/4 12:55
 */
@RestController
public class UserController {
    //模拟数据库密码
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private final String SIGNATURE = "root";

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        if (USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())){
            //添加token
            return JwtUtils.generateToken(user.getUsername(),"user",1000*60*60,SIGNATURE);
        }
        return null;
    }

    @GetMapping("/checkToken")
    public boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return JwtUtils.checkToken(token,SIGNATURE);
    }
}
