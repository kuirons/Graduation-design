package com.bigao.backend.module.consume;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.RoleMonetaryLog;
import com.bigao.backend.util.SqlUtil;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wait on 2016/1/2.
 */
@Service
public class ConsumeService {

    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

    public int getConsumeGold(int platform, int server, String startDate, String endDate) throws SQLException {
        String querySql = "select sum(`change`) from {0} where " + SqlUtil.formatDate(startDate, endDate) + " and monetary=''GOLD'' and `change` < 0";
        ResultSetHandler<Integer> rs = rs1 -> {
            if (rs1.next()) {
                return rs1.getInt(1);
            }
            return 0;
        };
        List<Integer> all = dbUtil4ServerLog.query(QueryBuilder.build(platform, server, querySql, RoleMonetaryLog.class, startDate, endDate, rs));
        if (all == null || all.isEmpty()) {
            return 0;
        }
        return all.stream().mapToInt(Integer::intValue).sum();
    }
}
