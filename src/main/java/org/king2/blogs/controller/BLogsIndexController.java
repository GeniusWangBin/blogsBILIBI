package org.king2.blogs.controller;

import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/11
 **/
@Controller
@RequestMapping("/logs")
public class BLogsIndexController {

    @Autowired
    private BLogsIndexService bLogsIndexService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) throws Exception{
        request.setAttribute("result", bLogsIndexService.index(request));
        return "index";
    }

    @RequestMapping("/get/{id}")
    public String get(@PathVariable("id") @NotBlank(message = "加载失败，请重试") String id,
                      HttpServletRequest request) throws Exception{

        SystemResult systemResult = bLogsIndexService.get(id, request);
        request.setAttribute("result", systemResult);

        return "blog-single";
    }

}
