package com.bigao.backend.util;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by wait on 2015/11/27.
 */
public interface SqlUtil {
    String INSERT = "insert into ";
    String SELECT_ALL = "select * from ";
    String DELETE = "delete from ";
    String UPDATE = "update ";
    String WHERE_ID = " where id=#{id}";
    String COUNT = "select count(*) from ";
    String SELECT_TABLE = "select * from {0} ";

    static String formatDate(String startDate, String endDate) {
        // 取开始日期的凌晨, 和截止日期的24点
        LocalDate startLocalDate = DateUtil.toLocalDateByDate(startDate);
        LocalDate endLocalDate = DateUtil.toLocalDateByDate(endDate);
        return " time >=''" + startLocalDate.atTime(0, 0, 0).toString() + "'' and time <=''" + endLocalDate.atTime(23, 59, 59) + "''";
    }

    static String formatDate2(String startDate, String endDate) {
        // 取开始日期的凌晨, 和截止日期的24点
        LocalDate startLocalDate = DateUtil.toLocalDateByDate(startDate);
        LocalDate endLocalDate = DateUtil.toLocalDateByDate(endDate);
        return " time >='" + startLocalDate.atTime(0, 0, 0).toString() + "' and time <='" + endLocalDate.atTime(23, 59, 59) + "' ";
    }
    
    static String formatTime(LocalDateTime startTime, LocalDateTime endTime) {
        return " time >=''" + startTime + "'' and time <=''" + endTime + "''";
    }

    static String formatTime(String startTime, String endTime) {
        return " time >=''" + startTime + "'' and time <=''" + endTime + "''";
    }

    static String listToSqlString(Collection<Long> data) {
        StringBuilder builder = new StringBuilder();
        data.forEach(id -> builder.append(id).append(","));
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }
    
    
    /**
     * 只限UNION连接sql语句
     * @param prefixSql
     * @param repeatSql
     * @param tableName
     * @return
     */
    static String getSqlByTableCount(String prefixSql,String repeatSql,String suffixSql,List<String> tableName){
    	StringBuffer buffer=new StringBuffer();
    	buffer.append(prefixSql);
    	if(tableName==null||tableName.size()<1){
			return null;
		}else if(tableName.size()>1){
			buffer.append("(");
			for (int i = 0; i < tableName.size(); i++) {
				buffer.append(MessageFormat.format(repeatSql, tableName.get(i)));
				if(tableName.size()-1==i){
					buffer.append(")");
				}else{
					buffer.append(" UNION "); 
				}
			}
		}else{
			buffer.append(MessageFormat.format(repeatSql, tableName.get(0)));
		}
    	buffer.append(suffixSql);
    	return buffer.toString();
    }
}
