package org.king2.blogs.controller;

import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsMyInfoManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/13
 **/
@Controller
@RequestMapping("/acl/my/info")
public class BLogsMyInfoManageController {

    @Autowired
    private BLogsMyInfoManageService bLogsMyInfoManageService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        SystemResult index = bLogsMyInfoManageService.index(request);
        request.setAttribute("result", index);
        return "MyInfo/index";
    }
}
