package com.bigao.backend.mapper;

import com.bigao.backend.module.sys.model.RolePlatform;
import com.bigao.backend.util.SqlUtil;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wait on 2015/11/27.
 */
@Component
public interface RolePlatformMapper {
    String TABLE_NAME = "gccp_role_platform";
    String all_field = "id,roleId,platformId";

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where roleId=#{0}")
    List<RolePlatform> getRPByRoleId(int roleId);


    @Delete(SqlUtil.DELETE + TABLE_NAME + SqlUtil.WHERE_ID)
    Integer deleteRP(RolePlatform rp);
}
