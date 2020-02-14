package org.king2.blogs.appoint;

import org.king2.blogs.dto.BLogsIndexDto;
import org.king2.blogs.enums.BLogSystemState;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.impl.BLogsUserServiceImpl;
import org.king2.blogs.utils.CookieUtils;
import org.king2.blogs.utils.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 你爹写的类
 * @description 博客拦截器的委托类
 * @date 2020/2/11
 **/
@Component
public class BLogsInterAppoint {

    /**
      *这个东西干了啥
      *
        判断用户是否登录 并且返回用户信息
     *  响应和请求 返回是否登录
      *
      */
    public static SystemResult checkUserIfLogin(String userCookieInfo, HttpServletRequest request) {

        if(StringUtils.isEmpty(userCookieInfo)){
            return new SystemResult(BLogSystemState.USER_NO_LOGIN.getVALUE(), "用户未登录");
        }

        BLogsIndexDto userInfoDto = null;
        try {
            userInfoDto = JsonUtils.jsonToPojo(userCookieInfo, BLogsIndexDto.class);
        } catch (Exception e) {
            return new SystemResult(BLogSystemState.USER_EDIT_COOKIE.getVALUE(), "请勿修改Cookie信息");
        }
        if(userInfoDto == null){
            return new SystemResult(BLogSystemState.USER_NO_LOGIN.getVALUE(), "用户没有登录");
        }
        //登陆成功
        return new SystemResult(BLogSystemState.SUCCESS.getVALUE(), BLogSystemState.SUCCESS.getKEY(), userInfoDto);
    }
}
