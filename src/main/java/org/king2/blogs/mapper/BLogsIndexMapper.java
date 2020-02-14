package org.king2.blogs.mapper;

import org.apache.ibatis.annotations.Select;
import org.king2.blogs.pojo.BLogsArticleWithBLOBs;

import java.util.List;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/11
 **/
@SuppressWarnings("all")
public interface BLogsIndexMapper  {

    @Select("SELECT * FROM blogs_article WHERE retain1 = 1 ORDER BY read_size DESC LIMIT 0 , 3")
    List<BLogsArticleWithBLOBs> getArticle();

    @Select("SELECT * FROM blogs_article WHERE retain1 = 1 AND blogs_id = #{id}")
    BLogsArticleWithBLOBs get(String id);

}
