package org.king2.blogs.service;

import org.king2.blogs.pojo.BLogsArticleWithBLOBs;
import org.king2.blogs.pojo.BLogsSource;
import org.king2.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
  *这个东西干了啥
  * @author 爸爸的方法
  * @date    写blog的servcie
  * @return
  */
public interface BLogsService {

    /**
      *这个东西干了啥  初始化确认提交的信息
      * @author 爸爸的方法
      * @return
      */
    List<BLogsSource> initConfirmRelease() throws Exception;


    /**
      *这个东西干了啥 写博客
      * @author 爸爸的方法
      * @date
       * @param article
     * @param token
     * @param request
      * @return
      */
    SystemResult writeLogs(BLogsArticleWithBLOBs article, String token, HttpServletRequest request)
            throws Exception;
}
