/**
 * Copyright (c) 2010-2014, SauryFramework.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.saury.core.Driver;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import cn.saury.core.Activerecord.Record;
import cn.saury.core.Activerecord.TableInfo;

/**
 * MysqlDialect.
 */
public class Mysql extends Abstract {
	
	public String forTableInfoBuilderDoBuildTableInfo(String tableName) {
		return "select * from `" + tableName + "` where 1 = 2";
	}
	
	public void forModelSave(TableInfo tableInfo, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
		sql.append("insert into `").append(tableInfo.getTableName()).append("`(");
		StringBuilder temp = new StringBuilder(") values(");
		for (Entry<String, Object> e: attrs.entrySet()) {
			String colName = e.getKey();
			if (tableInfo.hasColumnLabel(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
					temp.append(", ");
				}
				sql.append("`").append(colName).append("`");
				temp.append("?");
				paras.add(e.getValue());
			}
		}
		sql.append(temp.toString()).append(")");
	}
	
	public String forModelDeleteById(TableInfo tInfo) {
		String primaryKey = tInfo.getPrimaryKey();
		StringBuilder sql = new StringBuilder(45);
		sql.append("delete from `");
		sql.append(tInfo.getTableName());
		sql.append("` where `").append(primaryKey).append("` = ?");
		return sql.toString();
	}
	
	public void forModelUpdate(TableInfo tableInfo, Map<String, Object> attrs, Set<String> modifyFlag, String primaryKey, Object id, StringBuilder sql, List<Object> paras) {
		sql.append("update `").append(tableInfo.getTableName()).append("` set ");
		for (Entry<String, Object> e: attrs.entrySet()) {
			String colName = e.getKey();
			if (!primaryKey.equalsIgnoreCase(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" WHERE `").append(primaryKey).append("` = ?");	// .append(" limit 1");
		paras.add(id);

	}
	
	
	@SuppressWarnings("null")
	public String forModelFindAll(TableInfo tInfo, String conditions, String sort, String fields) {
		StringBuilder sql = new StringBuilder("SELECT ");
		if (fields.trim().equals("*")) {
			sql.append(fields);
		}
		else {
			String[] columnsArray = fields.split(",");
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append(columnsArray[i].trim());
			}
		}
		sql.append(" FROM ");
		sql.append(" ");
		sql.append(tInfo.getTableName());
		sql.append(" ");
		System.out.print(sql.toString());
		if(conditions != null && !"".equals(conditions.trim())){
			sql.append(" WHERE ");
			sql.append(" ");
			sql.append(conditions);
		}
		/*
		if(sort != null || sort.length()>= 0){
			sql.append(sort);
		}
		*/
		System.out.print(sql.toString());
		return sql.toString();
	}
	public String forModelFindById(TableInfo tInfo, String columns) {
		StringBuilder sql = new StringBuilder("select ");
		if (columns.trim().equals("*")) {
			sql.append(columns);
		}
		else {
			String[] columnsArray = columns.split(",");
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append("`");
				sql.append(columnsArray[i].trim());
				sql.append("`");
			}
		}
		sql.append(" from `");
		sql.append(tInfo.getTableName());
		sql.append("` where `").append(tInfo.getPrimaryKey()).append("` = ?");
		return sql.toString();
	}
	
	public String forDbFindById(String tableName, String primaryKey, String columns) {
		StringBuilder sql = new StringBuilder("select ");
		if (columns.trim().equals("*")) {
			sql.append(columns);
		}
		else {
			String[] columnsArray = columns.split(",");
			for (int i=0; i<columnsArray.length; i++) {
				if (i > 0)
					sql.append(", ");
				sql.append("`").append(columnsArray[i].trim()).append("`");
			}
		}
		sql.append(" from `");
		sql.append(tableName.trim());
		sql.append("` where `").append(primaryKey).append("` = ?");
		return sql.toString();
	}
	
	public String forDbDeleteById(String tableName, String primaryKey) {
		StringBuilder sql = new StringBuilder("delete from `");
		sql.append(tableName.trim());
		sql.append("` where `").append(primaryKey).append("` = ?");
		return sql.toString();
	}
	
	public void forDbSave(StringBuilder sql, List<Object> paras, String tableName, Record record) {
		sql.append("insert into `");
		sql.append(tableName.trim()).append("`(");
		StringBuilder temp = new StringBuilder();
		temp.append(") values(");
		
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("`").append(e.getKey()).append("`");
			temp.append("?");
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(")");
	}
	
	public void forDbUpdate(String tableName, String primaryKey, Object id, Record record, StringBuilder sql, List<Object> paras) {
		sql.append("update `").append(tableName.trim()).append("` set ");
		for (Entry<String, Object> e: record.getColumns().entrySet()) {
			String colName = e.getKey();
			if (!primaryKey.equalsIgnoreCase(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" WHERE `").append(primaryKey).append("` = ?");	// .append(" limit 1");
		paras.add(id);
	}
	
	public void forPaginate(StringBuilder sql, int length, int offset) {
		sql.append(" LIMIT ").append(offset).append(", ").append(length);	
	}
}
