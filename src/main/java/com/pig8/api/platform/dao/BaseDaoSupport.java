package com.pig8.api.platform.dao;

import com.pig8.api.business.common.entity.response.Page;
import com.pig8.api.platform.exception.DaoException;
import com.pig8.api.platform.util.LogUtils;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * @author navy
 *
 */
@Repository
public class BaseDaoSupport extends SqlSessionDaoSupport {
	
	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	private SqlSessionTemplate sqlSessionTemplate;

	public static final String START = "start";
	public static final String PAGE_SIZE = "pageSize";
	
	public static final String INSERT_ERR_MSG = "对数据库执行插入时异常!";
	public static final String UPDATE_ERR_MSG = "对数据库执行修改时异常!";
	public static final String DELETE_ERR_MSG = "对数据库执行删除时异常!";
	public static final String LOAD_ERR_MSG = "对数据库执行查询单条记录时异常!";
	public static final String FIND_ERR_MSG = "对数据库执行查询多条记录时异常!";
	public static final String COUNT_ERR_MSG = "对数据库执行查询记录数时异常!";

	/**
	 * 新增时向数据库插入数据
	 * 
	 * @param insertSQL配置SQL对应ID
	 * @param param待保存对象
	 * @return 保存后对象
	 */

	public boolean insert(String insertSQL, Object param) {
		try {
			if (sqlSessionTemplate.insert(insertSQL, param) > 0)
				return true;
			return false;
		} catch (Exception e) {
			logger.error(INSERT_ERR_MSG, e);
			LogUtils.error(e);
			throw new DaoException(INSERT_ERR_MSG, e);
		}
	}

	/**
	 * 执行数据库修改
	 * 
	 * @param updateSQL配置SQL对应ID
	 * @param param待保存对象
	 * @return 修改的行数
	 */
	public int update(String updateSQL, Object param) {
		try {
			return sqlSessionTemplate.update(updateSQL, param);
		} catch (Exception e) {
			logger.error(UPDATE_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(UPDATE_ERR_MSG,e);
		}
	}
	/**
	 * 查询一条数据
	 *
	 * @param loadSQL配置SQL对应ID
	 * @param param查询条件对象
	 * @return 查询结果对象
	 */
	public <T> T selectOne(String loadSQL, Object param) {
		try {
			return sqlSessionTemplate.selectOne(loadSQL, param);
		} catch (Exception e) {
			logger.error(LOAD_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(LOAD_ERR_MSG,e);
		}
	}
	/**
	 * 删除数据库数据
	 * 
	 * @param deleteSQL配置SQL对应ID
	 * @param param待执行对象
	 * @return 删除的行数
	 */
	public int delete(String deleteSQL, Object param) {
		try {
			return sqlSessionTemplate.delete(deleteSQL, param);
		} catch (Exception e) {
			logger.error(DELETE_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(DELETE_ERR_MSG,e);
		}
	}

	/**
	 * 查询一条数据
	 * 
	 * @param loadSQL配置SQL对应ID
	 * @param param查询条件对象
	 * @return 查询结果对象
	 */
	public <T> T load(String loadSQL, Object param) {
		try {
			return sqlSessionTemplate.selectOne(loadSQL, param);
		} catch (Exception e) {
			logger.error(LOAD_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(LOAD_ERR_MSG,e);
		}
	}

	/**
	 * 查询多条数（分页）
	 * 
	 * @param querySQL配置SQL对应ID
	 * @param model查询条件对象
	 * @return查询结果队列
	 */
	
	public <E> List<E> find(String querySQL, Object param) {
		try {
			return sqlSessionTemplate.selectList(querySQL, param);
		} catch (Exception e) {
			logger.error(FIND_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(FIND_ERR_MSG,e);
		}
	}
	/**
	 * 查询多条数据（不分页）
	 * 
	 * @param querySQL配置SQL对应ID
	 * @param model查询条件对象
	 * @return查询结果队列
	 */
	public <E> List<E> find(String querySQL) {
		try {			
			return sqlSessionTemplate.selectList(querySQL);
		} catch (Exception e) {
			logger.error(FIND_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(FIND_ERR_MSG,e);
		}
	}
	/**
	 * 根据查询结果判断是否存在
	 * 
	 * @param querySQL配置SQL对应ID
	 * @param model查询条件对象
	 * @return true存在，false不存在
	 */
	public boolean exists(String querySQL, Object param) {
		try {
			Integer result = (Integer) (sqlSessionTemplate.selectOne(querySQL, param));
			if (result != null && result > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(FIND_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(FIND_ERR_MSG,e);
		}
	}

	/**
	 * 计算总记录
	 * 
	 * @param sqlSQL配置SQL对应ID
	 * @param model查询条件对象 
	 * @return总行数
	 */
	public long count(String sqlSQL, Object param) {
		try {
			return ((Long) sqlSessionTemplate.selectOne(sqlSQL, param));
		} catch (Exception e) {
			logger.error(COUNT_ERR_MSG,e);
			LogUtils.error(e);
			throw new DaoException(COUNT_ERR_MSG, COUNT_ERR_MSG);
		}
	}
	
	/**
	 * 调用存储过程
	 * @param callSql
	 * @param map
	 * @return
	 */
	public Map<String, Object> callProcedure(String callSql, Map<String, Object> map) {
		this.sqlSessionTemplate.selectOne(callSql, map);
		return map;
	}
	
	/**
	 * 获取数据源
	 * 
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		return this.sqlSessionTemplate.getConfiguration().getEnvironment().getDataSource();
	}

	/**
	 * 按分页查询多条数据，分页条件和分页结果放入model中
	 * 
	 * @param pageSQL主查询语句
	 * @param countSQL查询总行数语句
	 * @param current当前页
	 * @param param查询条件对象
	 * @param pageSize页宽
	 * @return查询结果队列
	 */
	public <T> Page<T> page(String pageSQL, String countSQL, Map<String, Object> param, Long pageNo, Integer pageSize) {
		try{
			if ( pageNo != null  && pageSize != null ) {
				pageNo = (pageNo<1 ? 1 : pageNo);
				long count = count(countSQL, param);
				long start = pageSize * (pageNo - 1);
				long pageCount = (count>0 ? 1 : 0);
				if (count % pageSize == 0) {
					pageCount = count / pageSize;
				} else {
					pageCount = count / pageSize + 1;
				}

				if (param == null)
					param = new HashMap<String, Object>();

				param.put(START, start);
				param.put(PAGE_SIZE, pageSize);

				if (pageNo > pageCount)
					pageNo = pageCount;
				List<T> data = find(pageSQL, param);
				return new Page<T>(pageNo, pageCount, count, data,pageSize);
 			}
			List<T> data = find(pageSQL, param);
			return new Page<T>(0,0,0,data,0);
		}catch(Exception e){
			LogUtils.error(e);
		}finally{

		}
		return new Page<T>(0,0,0,new ArrayList<T>(),0);
	}
	
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}


}
