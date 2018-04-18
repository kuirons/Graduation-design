package com.bigao.backend.mapper;

import com.bigao.backend.module.sys.model.GmCommand;
import com.bigao.backend.util.SqlUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wait on 2015/12/26.
 */
@Component
public interface GmCommandMapper {
    String TABLE_NAME = "gccp_gm_command";
    String FIELD_NAMES = "id,platformId,serverId,asyncKey,command,sendTime,sendUser,result,ip,param";


    @Insert(SqlUtil.INSERT + TABLE_NAME + "( " + FIELD_NAMES + ") values(#{id},#{platformId},#{serverId},#{asyncKey},#{command},#{sendTime},#{sendUser},#{result},#{ip},#{param})")
    Integer insertLog(GmCommand log);

    @Select(SqlUtil.SELECT_ALL + TABLE_NAME + " where sendTime>=#{0} and sendTime<=#{1} and asyncKey=1 order by id desc limit 1000")
    List<GmCommand> selectByTime(long startTime, long endTime);
}
