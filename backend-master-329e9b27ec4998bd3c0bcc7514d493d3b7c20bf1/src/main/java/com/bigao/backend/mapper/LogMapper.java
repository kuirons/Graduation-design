package com.bigao.backend.mapper;

import com.bigao.backend.module.sys.model.Log;
import com.bigao.backend.util.SqlUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

/**
 * Created by wait on 2015/11/27.
 */
@Component
public interface LogMapper {
    String TABLE_NAME = "gccp_log";
    String FIELD_NAMES = "id,username,content,ip,creatTime";

    @Insert(SqlUtil.INSERT + TABLE_NAME + "( " + FIELD_NAMES + ") values(#{id}, #{username},#{content},#{ip},#{creatTime})")
    Integer insertLog(Log log);
}
