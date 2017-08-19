package com.lvmeng.qm.api.qn.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lvmeng.qm.api.qn.dao.IQnDao;
import com.lvmeng.qm.base.commons.CodeTable;
import com.lvmeng.qm.base.commons.util.JacksonUtil;
import com.lvmeng.qm.base.datasource.BaseDao;
import com.lvmeng.qm.base.vo.BaseQn;
import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;
@Repository("qnDao")
public class QnDaoImpl extends BaseDao implements IQnDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public int saveQn(List<Questionnaire> list, String createTime) throws Exception {
		String sql = "INSERT questionnaire SET panelId = ?,body= ?,type = ?,createTime = ?";
		int[] updateCount =jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, list.get(i).getPanelId());
				ps.setString(2, JacksonUtil.toJson(list.get(i)));
				ps.setInt(3, CodeTable.codeMap.get(CodeTable.QN_sour));
				ps.setString(4, createTime);
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}

		});
		int count = 0;
		for (int i : updateCount) {
			count += i;
		}
		return count;
	}

	@Override
	public int saveBaseQn(List<? extends BaseQn> list, String createTime, Integer type) throws Exception {
		String sql = "INSERT questionnaire SET panelId = ?,body= ?,type = ?,createTime = ?";
		int[] updateCount =jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, list.get(i).getPanelId());
				ps.setString(2, JacksonUtil.toJson(list.get(i)));
				ps.setInt(3, type);
				ps.setString(4, createTime);
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}

		});
		int count = 0;
		for (int i : updateCount) {
			count += i;
		}
		return count;
	}

}
