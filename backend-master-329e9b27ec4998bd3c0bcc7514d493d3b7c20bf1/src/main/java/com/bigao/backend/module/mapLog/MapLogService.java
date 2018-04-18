package com.bigao.backend.module.mapLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigao.backend.common.QueryBuilder;
import com.bigao.backend.db.DBUtil4ServerLog;
import com.bigao.backend.log.RoleLevelUpLog;
import com.bigao.backend.log.RoleMapLog;
import com.bigao.backend.module.mapLog.dto.MapLogDto;
import com.bigao.backend.module.mapLog.dto.MapLogInfo;
import com.bigao.backend.util.NumberUtil;
import com.bigao.backend.util.SqlUtil;

/**
 * Created by wait on 2015/12/14.
 */
@Service
public class MapLogService {

    private Logger logger = LoggerFactory.getLogger(MapLogService.class);
   
    @Autowired
    private DBUtil4ServerLog dbUtil4ServerLog;

	public MapLogDto queryTask(int platform, int server, String startDate, String endDate, int way) throws SQLException {
		MapLogDto mapLogDto=new MapLogDto();
		ResultSetHandler<Integer> resultSetHandler = rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        };
		QueryBuilder builder=QueryBuilder.build(platform, server, RoleLevelUpLog.class, startDate, endDate, resultSetHandler);
		List<String> tableName=dbUtil4ServerLog.getTableName(builder);
		StringBuilder sql=new StringBuilder("SELECT COUNT(1) sureNum FROM ");
		StringBuilder roleNumSql=new StringBuilder("SELECT COUNT(1) roleNum FROM ");
		int level=0;
		if(way==133){
			level=45;
		}else if(way==115){
			level=30;
		}else if(way==112){
			level=45;
		}
		if(tableName==null||tableName.size()<1){
			return null;
		}else if(tableName.size()>1){
			sql.append("(");
			for (int i = 0; i < tableName.size(); i++) {
				sql.append("(SELECT roleId FROM "+tableName.get(i)+" WHERE "+SqlUtil.formatDate2(startDate, endDate)+"and newLevel>"+level+" GROUP BY roleId) ");
				if(tableName.size()-1==i){
					sql.append(") r1");
				}else{
					sql.append(" UNION "); 
				}
			}
		}else{
			sql.append("(SELECT roleId FROM "+tableName.get(0)+" WHERE "+SqlUtil.formatDate2(startDate, endDate)+"and newLevel>"+level+" GROUP BY roleId) r1");
		}

		builder.setSql(sql.toString());
		mapLogDto.setSureNum(dbUtil4ServerLog.BeanQuery(builder));
		
		QueryBuilder roleMapbuilder=QueryBuilder.build(platform, server, RoleMapLog.class, startDate, endDate, new BeanListHandler<>(MapLogInfo.class));
		List<String> roleMaps=dbUtil4ServerLog.getTableName(roleMapbuilder);
		String roleMapSql="";
		List<List<MapLogInfo>> ls=new ArrayList<>();
		if(roleMaps==null||roleMaps.size()<1){
			return null;
		}else if(roleMaps.size()>1){
			roleNumSql.append("(");
			for (int i = 0; i < roleMaps.size(); i++) {
				roleMapSql="SELECT opt,UNIX_TIMESTAMP(`time`) differTime FROM "+roleMaps.get(i)+" r WHERE "+SqlUtil.formatDate2(startDate, endDate)+ "and map="+way+" ORDER BY roleId ASC,TIME ASC,opt ASC";
				roleMapbuilder.setSql(roleMapSql);
				List<MapLogInfo> maInfos=dbUtil4ServerLog.BeanQuery(roleMapbuilder);
				ls.add(maInfos);
				roleNumSql.append("(SELECT roleId FROM "+roleMaps.get(i)+" WHERE "+SqlUtil.formatDate2(startDate, endDate)+ "and map="+way+" GROUP BY roleId) ");
				if(tableName.size()-1==i){
					roleNumSql.append(") r1");
				}else{
					roleNumSql.append(" UNION "); 
				}
			}
		}else{
			roleMapSql="SELECT opt,UNIX_TIMESTAMP(`time`) differTime FROM "+roleMaps.get(0)+" r WHERE "+SqlUtil.formatDate2(startDate, endDate)+ "and map="+way+" ORDER BY roleId ASC,TIME ASC,opt ASC";
			roleMapbuilder.setSql(roleMapSql);
			List<MapLogInfo> maInfos=dbUtil4ServerLog.BeanQuery(roleMapbuilder);
			ls.add(maInfos);
			roleNumSql.append("(SELECT roleId FROM "+roleMaps.get(0)+" WHERE "+SqlUtil.formatDate2(startDate, endDate)+ "and map="+way+" GROUP BY roleId) r1");
		}
		QueryBuilder roleNumbuilder=QueryBuilder.build(platform, server, RoleMapLog.class, startDate, endDate,resultSetHandler );
		roleNumbuilder.setSql(roleNumSql.toString());
		mapLogDto.setRoleNum(dbUtil4ServerLog.BeanQuery(roleNumbuilder));

		int count=0;
		boolean tag=false;
		long minTime=0;
		long maxTime=0;
		long num=0;
		for (List<MapLogInfo> infos : ls) {
			for (MapLogInfo mapLogInfo : infos) {
				if(mapLogInfo.getOpt().equals("ENTER")){
					tag=false;
					minTime=mapLogInfo.getDifferTime();
				}else{
					tag=true;
					maxTime=mapLogInfo.getDifferTime();
				}
				if(tag){
					count++;
					num=num+(maxTime-minTime);
				}
			}
		}
		mapLogDto.setAvgTime(num/count);
		mapLogDto.setInvolvRate(NumberUtil.divide(mapLogDto.getRoleNum(), mapLogDto.getSureNum()));  
		
		return mapLogDto;
	}
}
