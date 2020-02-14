package org.king2.blogs.service.impl;

import org.king2.blogs.appoint.BLogsSearchAppoint;
import org.king2.blogs.dto.BLogsSearchDto;
import org.king2.blogs.enums.PojoExBLogsArticle;
import org.king2.blogs.mapper.BLogsSearchMapper;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.service.BLogsSearchService;
import org.king2.blogs.service.BLogsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 你爹写的类
 * @description
 * @date 2020/2/11
 **/
@Service
public class BLogsSearchServiceImpl implements BLogsSearchService {

    //注入mapper
    @Autowired
    private BLogsSearchMapper bLogsSearchMapper;

    //注入搜索的委托类
    @Autowired
    private BLogsSearchAppoint bLogsSearchAppoint;

    @Override
    public SystemResult getByQuery(String query) {

        if(StringUtils.isEmpty(query)){
            query = "";
        }

        //创建返回的dto
        BLogsSearchDto bLogsSearchDto = new BLogsSearchDto();

        //执行查询
        List<PojoExBLogsArticle> pojoExBLogsArticles = bLogsSearchMapper.get(query);

        //设置公共的属性
        bLogsSearchAppoint.setCommonFiled(query, bLogsSearchDto, pojoExBLogsArticles);

        return new SystemResult(bLogsSearchDto);
    }
}
