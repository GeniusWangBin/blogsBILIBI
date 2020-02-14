package org.king2.blogs.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.king2.blogs.pojo.BLogsArticleWithBLOBs;
import org.king2.blogs.pojo.BLogsSource;

import java.util.List;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/13
 **/
public interface BLogsSourceMapper {

    //获取所有来源
    @Select("SELECT * FROM blogs_source")
    List<BLogsSource> getAll();

    @Insert("INSERT INTO blogs_article (show_image,author_number,blogs_title," +
            "blogs_content,image_lists,if_public,create_time,update_time,blogs_source,read_size," +
            "`like`,retain1) VALUES (#{a.showImage},#{a.authorNumber},#{a.blogsTitle}," +
            "#{a.blogsContent},#{a.imageLists},#{a.ifPublic}" +
            ",#{a.createTime},#{a.updateTime},#{a.blogsSource},#{a.readSize}" +
            ",#{a.like} , #{a.retain1})")
    void insert(@Param("a") BLogsArticleWithBLOBs article);
}
