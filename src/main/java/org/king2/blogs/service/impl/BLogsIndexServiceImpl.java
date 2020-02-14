package org.king2.blogs.service.impl;

import org.king2.blogs.appoint.BLogsIndexAppoint;
import org.king2.blogs.appoint.BLogsUserServiceAppoint;
import org.king2.blogs.dto.BLogsIndexDto;
import org.king2.blogs.mapper.BLogsIndexMapper;
import org.king2.blogs.pojo.BLogsArticleWithBLOBs;
import org.king2.blogs.pojo.BLogsUser;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsIndexService;
import org.king2.blogs.service.BLogsUserService;
import org.king2.webkcache.cache.interfaces.WebKingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/11
 **/
@Service
public class BLogsIndexServiceImpl implements BLogsIndexService {

    @Autowired
    private BLogsIndexAppoint bLogsIndexAppoint;

    @Autowired
    private WebKingCache webKingCache;

    @Autowired
    private BLogsIndexMapper bLogsIndexMapper;

    public static final String LOGS_ARTICLE_KEY = "LOGS_ARTICLE_KEY:";

    @Override
    public SystemResult get(String id, HttpServletRequest request) throws Exception {

        BLogsArticleWithBLOBs cacheInfoById = getCacheInfoById(id);
        if(cacheInfoById == null){
            //从数据库中查询
            cacheInfoById = getInfoByMysqlAndAddCache(id);

        }

        return new SystemResult(cacheInfoById);
    }

    private BLogsArticleWithBLOBs getInfoByMysqlAndAddCache(String id) throws Exception {
        BLogsArticleWithBLOBs bLogsArticleWithBLOBs = bLogsIndexMapper.get(id);
        webKingCache.set(LOGS_ARTICLE_KEY + id, bLogsArticleWithBLOBs, true);
        return bLogsArticleWithBLOBs;
    }

    //从缓存中获取一篇文章的信息
    private BLogsArticleWithBLOBs getCacheInfoById(String id) throws Exception{

        Object o = webKingCache.get(LOGS_ARTICLE_KEY + id);
        return (BLogsArticleWithBLOBs) o;
    }

    @Override
    public SystemResult index(HttpServletRequest request) throws Exception {
        //从request中获取用户的信息
        Object user = request.getAttribute("user");
        //创建返回的dto信息
        BLogsIndexDto indexDto = new BLogsIndexDto();
        if(user == null){
            indexDto.setLogin(false);
        }else {
            //说明登陆了 进行其他的处理
            BLogsUser logsUser = (BLogsUser) user;
            BLogsUserServiceAppoint.writeUserInfo(indexDto, logsUser);
            indexDto.setLogin(true);
        }

        //公共处理
        //比如首页的内容 推荐的波博客

        bLogsIndexAppoint.commonHandle(indexDto);

        return new SystemResult(indexDto);
    }
}
