package org.king2.blogs.service.impl;

import org.apache.ibatis.cache.decorators.WeakCache;
import org.king2.blogs.appoint.BLogsWriteAppoint;
import org.king2.blogs.enums.BLogSystemState;
import org.king2.blogs.locks.BLogsWriteLock;
import org.king2.blogs.mapper.BLogsSourceMapper;
import org.king2.blogs.pojo.BLogsArticleWithBLOBs;
import org.king2.blogs.pojo.BLogsSource;
import org.king2.blogs.pojo.BLogsUser;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsService;
import org.king2.webkcache.cache.interfaces.WebKingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/13
 **/
@Service
@SuppressWarnings("all")
public class BLogsServiceImpl implements BLogsService {

    @Autowired
    private WebKingCache webKingCache;

    @Autowired
    private BLogsSourceMapper bLogsSourceMapper;

    @Autowired
    private BLogsWriteAppoint bLogsWriteAppoint;


    //文章来源存在于缓存中的KEY
    public static final String SOURCE_KEY = "LOGS_SOURCE_KEY";

    /*
      *这个东西干了啥 初始确认提交
      * @author 爸爸的方法
      * @date
       * @param null
      * @return
      */
    @Override
    public List<BLogsSource> initConfirmRelease() throws Exception {

        //先从缓存中拿取数据
        synchronized (this){
            List<BLogsSource> sources = (List<BLogsSource>) webKingCache.get(SOURCE_KEY);
            if(CollectionUtils.isEmpty(sources)){
                //缓存中没有数据 从数据库中查出来
                sources = bLogsSourceMapper.getAll();
                //存入缓存当中去
                webKingCache.set(SOURCE_KEY, sources);
            }

            return sources;
        }

    }

    /**
      *这个东西干了啥   写博客
      * @author 爸爸的方法
      * @date
       * @param article
     * @param token
     * @param request
      * @return
      */
    @Override
    public SystemResult writeLogs(BLogsArticleWithBLOBs article, String token, HttpServletRequest request) throws Exception {

        //枷锁防止数据安全
        ReentrantReadWriteLock lock = BLogsWriteLock.getInstance().getLock();
        lock.writeLock().lock();

        try{
            //获取用户的信息
            BLogsUser userInfo =(BLogsUser) request.getAttribute("user");
            BLogsWriteAppoint.checkInfo(article);
            bLogsWriteAppoint.checkHandleIsLoginUser(userInfo, token);

            //插入数据库之前的操作
            bLogsWriteAppoint.beforeCreateMySqlProceeeor(article, userInfo);

            //校验完成 插入数据库
            bLogsSourceMapper.insert(article);
            //插入成功 删除缓存中的数据
            bLogsWriteAppoint.deleteSuccess(userInfo, token);

        }catch (Exception e){
            throw e;
        }finally {
            lock.writeLock().unlock();
        }


        return new SystemResult(BLogSystemState.SUCCESS);
    }
}
