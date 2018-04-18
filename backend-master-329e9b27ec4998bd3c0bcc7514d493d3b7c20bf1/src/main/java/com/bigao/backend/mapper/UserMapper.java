package com.bigao.backend.mapper;

import com.bigao.backend.module.sys.model.PageInfo;
import com.bigao.backend.module.sys.model.User;
import com.bigao.backend.util.SqlUtil;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wait on 2015/11/27.
 */
@Component
public interface UserMapper {
    String TABLE_NAME = "gccp_user";
    String ALL_FIELDS = "id,username,password,roleId,deleted,createTime";

    String BRIEF_FIELDS = "username,password,roleId,deleted,createTime";

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where deleted=false order by createTime asc limit #{startRow},#{pageSize}")
    List<User> getAllUsers(PageInfo pageInfo);

    @Select(SqlUtil.COUNT + TABLE_NAME + " where deleted=false")
    Integer getAllUsersCount();

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where username=#{0}")
    User getUserByName(String name);

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + SqlUtil.WHERE_ID)
    User getUserById(int id);

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where username=#{0}")
    List<String> loadUserAuthoritiesByName(String username);

    @Insert(SqlUtil.INSERT + TABLE_NAME + "(" + BRIEF_FIELDS + ") values(#{username},#{password},#{roleId},#{deleted},#{createTime})")
    Integer addUser(User user);

    @Delete(SqlUtil.UPDATE + TABLE_NAME +" set deleted=true where id=#{id}")
    Integer deleteUser(User user);

    @Update(SqlUtil.UPDATE + TABLE_NAME + " set id=#{id},username=#{username},password=#{password},roleId=#{roleId},deleted=#{deleted},createTime=#{createTime} where id=#{id}")
    Integer updateUser(User user);
}
