package org.king2.blogs.controller;

import org.king2.blogs.appoint.BLogsWriteAppoint;
import org.king2.blogs.pojo.BLogsArticle;
import org.king2.blogs.pojo.BLogsArticleWithBLOBs;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/13
 **/
@Controller
@RequestMapping("/acl")
@Validated
@SuppressWarnings("all")
public class BLogsController {

    //注入写文章的委托类
    @Autowired
    private BLogsWriteAppoint bLogsWriteAppoint;

    //注入文章的service
    @Autowired
    private BLogsService bLogsService;

    //打开写文章的页面
    @RequestMapping("/open/write")
    @SuppressWarnings("all")
    public String openWrite(HttpServletRequest request) throws Exception{

        SystemResult init = bLogsWriteAppoint.init(request);
        request.setAttribute("result", init);

        return "writeBlogs/writeBlogs";
    }

    //上传图片
    @RequestMapping("/upload")
    @ResponseBody
    public SystemResult upload(HttpServletRequest request, MultipartFile file,
                               @NotBlank(message = "加载失败，请重新打开页面") String token) throws Exception{
        //调用上传
        SystemResult upload = bLogsWriteAppoint.upload(request, file, token);

        return upload;
    }

    //保存草稿
    @RequestMapping("/save/draft")
    @ResponseBody
    public SystemResult draft(HttpServletRequest request,
                              @NotBlank(message = "加载失败，请重新打开页面") String token,
                              @NotBlank(message = "请填写内容") String content) throws Exception{

        SystemResult systemResult = bLogsWriteAppoint.saveDraft(request, token, content);

        return systemResult;

    }

    //显示确认发布的页面
    @RequestMapping("/confirm/release")
    public String confirmRelease(HttpServletRequest request,
                                 @NotBlank(message = "加载失败,请重新打开页面") String token) throws Exception {

        request.setAttribute("result", bLogsService.initConfirmRelease());
        request.setAttribute("token", token);
        return "writeBlogs/ConfirmRelease";
    }

    //写博客
    @RequestMapping("/write/logs")
    @ResponseBody
    public SystemResult writeLogs(HttpServletRequest request,
                                  @NotBlank(message = "加载失败，请重新打开页面") String token,
                                  @Validated BLogsArticleWithBLOBs bLogsArticle) throws  Exception{
        SystemResult systemResult = bLogsService.writeLogs(bLogsArticle, token, request);

        return systemResult;

    }

}
