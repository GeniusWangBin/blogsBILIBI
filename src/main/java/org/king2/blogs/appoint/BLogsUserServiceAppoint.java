package org.king2.blogs.appoint;

import org.king2.blogs.dto.BLogsIndexDto;
import org.king2.blogs.enums.BLogSystemState;
import org.king2.blogs.enums.UserStateEnum;
import org.king2.blogs.mapper.BLogsUserMapper;
import org.king2.blogs.pojo.BLogsUser;
import org.king2.blogs.result.SystemResult;
import org.king2.blogs.utils.MD5Utils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * =======================================================
 * 说明:    用户操作的Service委派类
 * <p>
 * 作者		时间					注释
 * 鹿七		2019.11.21  		创建
 * <p>
 * =======================================================
 */
@Component
public class BLogsUserServiceAppoint {

    //注入头像信息
    @Value("http://39.105.41.2:9000/king2-product-image/kodinger.jpg")
    private String USER_DEFAULT_IMAGE;

    //注入用户的mapper
    @Autowired
    private BLogsUserMapper bLogsUserMapper;

    public static SystemResult checkUserInfo(String userName, String passWord){

        if(StringUtils.isEmpty(userName) || userName.length()<7  ){
            return new SystemResult(100,"用户名的长度为7-11位",null);
        }

        if(StringUtils.isEmpty(passWord) || passWord.length()<7  ){
            return new SystemResult(100,"密码的长度为7-11位",null);
        }

        return new SystemResult(200);
    }
/**
  *这个东西干了啥
  * @author 爸爸的方法  写入数据到dto当中
  * @date
   * @param dto
 * @param user
  * @return
  */
    public static void writeUserInfo(BLogsIndexDto dto, BLogsUser user){
        dto.setuImage(user.getuImage());
        dto.setuName(user.getuName());
        dto.setUserName(user.getUserName());
        dto.setPassWord(user.getPassWord());
    }

    /**
      *这个东西干了啥
     * 校验用户注册信息，并且填充一些默认的数据
      * @author 爸爸的方法
      * @date
       * @return
      */
    public SystemResult checkRegisterInfo(BLogsUser bLogsUser) throws Exception{

        if(bLogsUser == null){
            return new SystemResult(100, "请填写表单信息");
        }
        //判断用户名是否符合规范
        //判断密码是否属于正常约束
        SystemResult checkUserResult = checkUserInfo(bLogsUser.getUserName(), bLogsUser.getPassWord());
        if(checkUserResult.getStatus() != 200){
            return checkUserResult;
        }
        if(StringUtils.isEmpty(bLogsUser.getuName()) || bLogsUser.getuName().length() >5
                || bLogsUser.getuName().length()<1){
            return new SystemResult(100,"名称的长度为1-5个字符");
        }
        //校验用户名是否存在
        BLogsUser checkUserName = bLogsUserMapper.get(bLogsUser.getUserName());
        if(checkUserName != null){
            return new SystemResult(100, "用户名已经存在");
        }
        //填充默认数据
        bLogsUser.setCreateTime(new Date());
        bLogsUser.setuImage(USER_DEFAULT_IMAGE);
        bLogsUser.setState(BLogSystemState.SUCCESS.getVALUE());
        bLogsUser.setPassWord(MD5Utils.md5(bLogsUser.getPassWord()));

        return new SystemResult("ok");
    }
}
