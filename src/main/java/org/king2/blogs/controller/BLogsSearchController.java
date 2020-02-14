package org.king2.blogs.controller;

import org.king2.blogs.service.BLogsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 你爹写的类
 *
 * @description
 * @date 2020/2/11
 **/
@Controller
@RequestMapping("/logs/search")
public class BLogsSearchController {

    @Autowired
    private BLogsSearchService bLogsSearchService;

    @RequestMapping("/index")
    public String index(String query, HttpServletRequest request){

        //存入request
        request.setAttribute("result", bLogsSearchService.getByQuery(query));
        return "search";
    }
}
