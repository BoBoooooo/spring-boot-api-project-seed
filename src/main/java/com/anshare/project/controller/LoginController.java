package com.anshare.project.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.anshare.project.configurer.ConstantKey;
import com.anshare.project.core.Result;
import com.anshare.project.core.ResultGenerator;
import com.anshare.project.model.Users;
import com.anshare.project.service.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Example;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin

@RestController
@Api(value = "登录管理", description = "登录管理")
@RequestMapping("/login")
public class LoginController  {
    private UsersService usersService;

    @ApiOperation(value = "登录接口")
    @PostMapping(value = "/login")
    public Result login(String username, String password, HttpServletResponse response) {
//
//        Condition condition = new Condition(Users.class);
//        tk.mybatis.mapper.entity.Example.Criteria c = condition.createCriteria();
//        c.andEqualTo("UserName",username);
//
//        Users userVo = usersService.findAll().get(0);
//        if (userVo == null) {
            /**
             自定义生成Token，因为使用了自定义登录，就不会执行JWTLoginFilter了，所以需要字段调用生成token并返给前端
             */
            // 这里可以根据用户信息查询对应的角色信息，这里为了简单，我直接设置个空list
            List roleList = new ArrayList<>();
//            String subject = userVo.getUsername() + "-" + roleList;
        String subject = "admin" + "-" + roleList;

        String token = Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() +  24 * 60 * 60 * 1000)) // 设置过期时间 1 * 24 * 60 * 60秒情况修改)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面

        response.addHeader("auth",  token);
            return ResultGenerator.genSuccessResult(token);

//        }
//        else{
//
//            return ResultGenerator.genFailResult("用户名或密码错误");
//
//        }
    }
}
