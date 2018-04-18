package com.bigao.backend.module.register;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.AccountCreateLog;
import com.bigao.backend.log.RoleCreateLog;
import com.bigao.backend.module.register.dto.RegisterInfo;
import com.bigao.backend.util.NumberUtil;
import com.bigao.backend.util.SqlUtil;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by wait on 2015/12/14.
 */
@Service
public class RegisterService {

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    public List<RoleCreateLog> queryCreateRole(int platform, int server, String startDate, String endDate) throws SQLException {
        String querySql = "select DISTINCT roleId from {0} where " + SqlUtil.formatDate(startDate, endDate);
        List<List<RoleCreateLog>> all = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleCreateLog.class, startDate, endDate, new BeanListHandler<>(RoleCreateLog.class)));
        if (all == null) {
            return Collections.emptyList();
        }
        return dbUtil4ServerLog.toOneList(all);
    }

    public int queryRegisterNum(int platform, int server, String startDate, String endDate) throws SQLException {
        ResultSetHandler<Integer> resultSetHandler = rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        };
        String querySql = "select count(id) from {0} where " + SqlUtil.formatDate(startDate, endDate);
        List<Integer> registerNum = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, AccountCreateLog.class, startDate, endDate, resultSetHandler));
        if (registerNum != null && !registerNum.isEmpty()) {
            return registerNum.get(0);
        }
        return 0;
    }

    public RegisterInfo queryRegisterAndCreateRole(int platform, int server, String startDate, String endDate) throws SQLException {
        ResultSetHandler<Integer> resultSetHandler = rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        };
        String roleSql = "select count(roleId) from {0} where " + SqlUtil.formatDate(startDate, endDate);
        List<Integer> createRoleNum = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, roleSql, RoleCreateLog.class, startDate, endDate, resultSetHandler));
        // 查询创建角色数量
        RegisterInfo info = new RegisterInfo();
        info.setPlatform(platform);
        info.setServer(server);
        info.setRegisterNum(queryRegisterNum(platform, server, startDate, endDate));
        if (!createRoleNum.isEmpty()) {
            info.setCreateRoleNum(createRoleNum.get(0));
        }
        if (info.getCreateRoleNum() > 0 && info.getRegisterNum() > 0) {
            info.setCreateRate(NumberUtil.divide(info.getCreateRoleNum(), info.getRegisterNum()));
        }
        return info;
    }
}
