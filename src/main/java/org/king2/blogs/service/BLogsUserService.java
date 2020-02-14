package org.king2.blogs.service;

import org.king2.blogs.pojo.BLogsUser;
import org.king2.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * =======================================================
 * 说明:    用户操作的Service
 * <p>
 * 作者		时间					注释
 * 鹿七		2019.11.21  		创建
 * <p>
 * =======================================================
 */
public interface BLogsUserService {

    SystemResult login (String userName, String passWord,  HttpServletResponse response, HttpServletRequest request)  throws Exception;

    SystemResult login(String userName, String passWord, HttpServletResponse response, HttpServletRequest re
            , Boolean isMd5) throws Exception;

    SystemResult register(BLogsUser bLogsUser);

}
