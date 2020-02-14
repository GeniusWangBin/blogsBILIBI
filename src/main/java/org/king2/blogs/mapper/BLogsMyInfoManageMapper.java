package org.king2.blogs.mapper;

import org.apache.ibatis.annotations.Select;
import org.king2.blogs.pojo.BLogsUserElseInfo;
import org.king2.blogs.pojo.DtoExBLogsSkillValue;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.List;

@SuppressWarnings("all")
public interface BLogsMyInfoManageMapper {

    //通过用户名查询出用户的其他信息
    @Select("SELECT * FROM blogs_user_else_info WHERE retain = #{userName}")
    BLogsUserElseInfo getUserInfoByUserName(String username);

    //通过用户名查询出用户掌握的技能点
    @Select("SELECT sv.* , bs.skill_key `key` FROM `blogs_skill_value` sv , blogs_skill bs WHERE sv.skill_key_id = bs.skill_id" +
            " AND user_name = #{userName}")
    List<DtoExBLogsSkillValue> getSkillValueByUserName(String username);
}
