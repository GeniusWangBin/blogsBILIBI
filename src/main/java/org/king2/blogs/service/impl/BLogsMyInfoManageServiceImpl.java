package org.king2.blogs.service.impl;

import org.king2.blogs.appoint.BLogsMyInfoManageAppoint;
import org.king2.blogs.dto.BLogsUserElseInfoDto;
import org.king2.blogs.enums.BLogSystemState;
import org.king2.blogs.mapper.BLogsMyInfoManageMapper;
import org.king2.blogs.pojo.BLogsUser;
import org.king2.blogs.pojo.BLogsUserElseInfo;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsMyInfoManageService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/13
 **/
@Service
public class BLogsMyInfoManageServiceImpl implements BLogsMyInfoManageService {

    @Autowired
    private BLogsMyInfoManageMapper bLogsMyInfoManageMapper;

    @Override
    public SystemResult index(HttpServletRequest request) {

        //从request中取出用户信息
        Object user = request.getAttribute("user");
        if(user == null){
            //说明用户没有登录 没有登录应该是进不来 因为会写一个拦截器 专门拦截/acl开头的
            return new SystemResult(BLogSystemState.USER_NO_LOGIN.getVALUE(),
                    BLogSystemState.USER_NO_LOGIN.getKEY());
        }
        //创建返回的dto
        BLogsUserElseInfoDto elseInfoDto = new BLogsUserElseInfoDto();
        //强转用户信息
        BLogsUser userInfo = (BLogsUser) user;

        //查询该用户的其他信息
        BLogsUserElseInfo userInfoByUserName = bLogsMyInfoManageMapper.getUserInfoByUserName(userInfo.getUserName());
        elseInfoDto.setValues(bLogsMyInfoManageMapper.getSkillValueByUserName(userInfo.getUserName()));
        elseInfoDto.setbLogsUser(userInfo);
        elseInfoDto.setbLogsUserElseInfo(userInfoByUserName);

        //查询出来有两种结果 正常  不正常
        /**
          *c存入缓存中前1的一个处理器
          */
        BLogsMyInfoManageAppoint.addCacheBeforeProcessor(elseInfoDto);
        BLogsMyInfoManageAppoint.returnBeforeProcessor(elseInfoDto);

        return new SystemResult(BLogSystemState.SUCCESS.getVALUE(),
                BLogSystemState.SUCCESS.getKEY(), elseInfoDto);
    }
}
