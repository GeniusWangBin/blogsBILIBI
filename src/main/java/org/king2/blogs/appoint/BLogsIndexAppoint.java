package org.king2.blogs.appoint;

import org.king2.blogs.dto.BLogsIndexDto;
import org.king2.blogs.mapper.BLogsIndexMapper;
import org.king2.blogs.pojo.BLogsArticle;
import org.king2.blogs.pojo.BLogsArticleWithBLOBs;
import org.king2.webkcache.cache.interfaces.WebKingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/11
 **/
@Component
public class BLogsIndexAppoint {

    @Autowired
    private BLogsIndexMapper bLogsIndexMapper;

    @Autowired
    private WebKingCache webKingCache;

    //文章中存在缓存中的KEY
    public static final String ART_CACHE_KEY = "ART_CACHE_KEY";

    /**
      *这个东西干了啥
      * @author 爸爸的方法
      * @date   首页显示的公共信息
       * @param indexDto
      * @return
      */
    public void commonHandle(BLogsIndexDto indexDto) throws Exception {
        // 查询出文章的信息
        getArticle(indexDto);
    }

    /**
      *这个东西干了啥
      * @author 爸爸的方法
      * @date   查询文章中的信息
      * @return
      */
    public void getArticle(BLogsIndexDto indexDto) throws Exception {

        //枷锁 保证当前只有一份数据存入缓存
        synchronized (ART_CACHE_KEY){
            //判断内存中是否存在此文章
            Object cacheData = webKingCache.get(ART_CACHE_KEY);
            if(cacheData == null){
                //缓存中没有数据 要从数据库当中去查
                List<BLogsArticleWithBLOBs> article = bLogsIndexMapper.getArticle();
                //查询出来之后放入缓存当中
                webKingCache.set(ART_CACHE_KEY, article, true);
                cacheData = article;
            }

            //从缓存当中取出数据来
            indexDto.setArticleWithBLOBs((List<BLogsArticleWithBLOBs>) cacheData);

            //疑问 什么时候清除缓存中的数据
            //蟹哥定时器 定时清除缓存当中的内容

        }
    }
}
