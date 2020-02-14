package org.king2.blogs.interceptor;

import org.king2.blogs.appoint.BLogsInterAppoint;
import org.king2.blogs.dto.BLogsIndexDto;
import org.king2.blogs.enums.BLogSystemState;
import org.king2.blogs.pojo.BLogsUser;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsUserService;
import org.king2.blogs.service.impl.BLogsUserServiceImpl;
import org.king2.blogs.utils.CookieUtils;
import org.king2.webkcache.cache.interfaces.impl.ConcurrentWebCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 **/
@Component
public class BLogsInterceptor implements HandlerInterceptor {

    @Autowired
    private ConcurrentWebCache concurrentWebCache;
    @Autowired
    private BLogsUserService bLogsUserService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //从cookies中获取用户信息 如果没有用户信息 那就没有登录
        String cookieValue = CookieUtils.getCookieValue(request, BLogsUserServiceImpl.USER_KEY, true);

        //判断用户是否登录
        SystemResult systemResult = BLogsInterAppoint.checkUserIfLogin(cookieValue, request);
        if(systemResult.getStatus() != BLogSystemState.SUCCESS.getVALUE()){
            return true;
        }

        BLogsIndexDto cookieData = (BLogsIndexDto) systemResult.getData();

        //登陆成功 从缓存中取出数据
        BLogsUser cacheData =(BLogsUser) concurrentWebCache.get(BLogsUserServiceImpl.USER_KEY + cookieData.getUserName());

        //判断是否为空 因为缓存有可能被清空
        if(cacheData == null){
            //说明缓存中的数据已经被清空
            //需要查询数据库并且对比密码
            SystemResult login  = bLogsUserService.login(cookieData.getUserName(), cookieData.getPassWord(),
                        response, request, false);
            if (login.getStatus() == 200){
                BLogsIndexDto dto = (BLogsIndexDto) login.getData();
                cacheData =  (BLogsUser) concurrentWebCache.get(BLogsUserServiceImpl.USER_KEY + dto.getUserName());
            }else {
                cacheData = null;
            }
        }

        //判断cookies中的数据是否等于缓存中的数据或者数据库中的数据
        if(cacheData == null || !cookieData.getPassWord().equals(cacheData.getPassWord())){
            //不等于的话 需要清空cookie
            CookieUtils.deleteCookie(request, response, BLogsUserServiceImpl.USER_KEY);
            return  true;
        }

        //放到request域中
        request.setAttribute("user", cacheData);

        return true;
    }
}
