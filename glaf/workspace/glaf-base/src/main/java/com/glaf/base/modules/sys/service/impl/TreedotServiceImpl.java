package com.glaf.base.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

import com.glaf.base.modules.sys.model.ObjectEntity;
import com.glaf.base.modules.sys.model.Treedot;
import com.glaf.base.modules.sys.mapper.MyTreedotMapper;
import com.glaf.base.modules.sys.query.TreedotQuery;
import com.glaf.base.modules.sys.service.ITreedotService;

@Service("treedotService")
@Transactional(readOnly = true)
public class TreedotServiceImpl implements ITreedotService {
	protected static final Log logger = LogFactory
			.getLog(TreedotServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MyTreedotMapper myTreedotMapper;

	public TreedotServiceImpl() {

	}

	public int count(TreedotQuery query) {
		return myTreedotMapper.getTreedotCountByQueryCriteria(query);
	}

	@Transactional
	public void deleteById(String id) {
		myTreedotMapper.deleteTreedotById(id);
	}

	public void deleteByPrimaryKey(int indexId) {
		myTreedotMapper.deleteTreedotByIndexId(indexId);
	}

	public Treedot getTreedotById(String id) {
		Treedot treedot = myTreedotMapper.getTreedotById(id);
		return treedot;
	}

	public Treedot getTreedotByPrimaryKey(int indexId) {
		return myTreedotMapper.getTreedotByIndexId(indexId);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreedotCount(Map<String, Object> parameter) {
		return myTreedotMapper.getTreedotCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreedotCountByQueryCriteria(TreedotQuery query) {
		return myTreedotMapper.getTreedotCountByQueryCriteria(query);
	}

	public List<String> getTreedotIdsByIndexId(int indexId) {
		List<String> rowIds = new ArrayList<String>();
		List<ObjectEntity> rows = myTreedotMapper.getTreedotIdsByIndexId(indexId);
		if (rows != null && !rows.isEmpty()) {
			for (ObjectEntity o : rows) {
				rowIds.add(o.getStringValue());
			}
		}
		return rowIds;
	}

	/**
	 * 根据index_id获取其下层所有节点
	 */
	@Override
	public List<Treedot> getAllChildrenTreedotByIndexId(int indexId) {
		return myTreedotMapper.getAllChildrenTreedotByIndexId(indexId);
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<Treedot> getTreedots(Map<String, Object> parameter) {
		return myTreedotMapper.getTreedots(parameter);
	}

	public List<Treedot> getTreedotsByIdLike(String idLike) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("idLike", idLike);
		return myTreedotMapper.getTreedotsByIdLike(parameter);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Treedot> getTreedotsByQueryCriteria(int start, int pageSize,
			TreedotQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Treedot> rows = sqlSession.selectList(
				"getTreedotsByQueryCriteria", query, rowBounds);
		return rows;
	}

	public List<Treedot> list(TreedotQuery query) {
		List<Treedot> list = myTreedotMapper.getTreedotsByQueryCriteria(query);
		return list;
	}

	@Transactional
	public void save(Treedot treedot) {
		if (treedot.getIndexId() == 0) {
			treedot.setIndexId(idGenerator.nextId().intValue());
			myTreedotMapper.insertTreedot(treedot);
		} else {
			Treedot model = this.getTreedotByPrimaryKey(treedot.getIndexId());
			if (model != null) {
				if (treedot.getId() != null) {
					model.setId(treedot.getId());
				}
				if (treedot.getIndexName() != null) {
					model.setIndexName(treedot.getIndexName());
				}
				model.setNlevel(treedot.getNlevel());
				if (treedot.getNum() != null) {
					model.setNum(treedot.getNum());
				}
				if (treedot.getContent() != null) {
					model.setContent(treedot.getContent());
				}
				if (treedot.getSindexName() != null) {
					model.setSindexName(treedot.getSindexName());
				}
				model.setNodeico(treedot.getNodeico());
				model.setListno(treedot.getListno());
				model.setMenuid(treedot.getMenuid());
				model.setIsend(treedot.getIsend());
				if (treedot.getSysmenuid() != null) {
					model.setSysmenuid(treedot.getSysmenuid());
				}
				model.setType(treedot.getType());
				if (treedot.getFilenumid() != null) {
					model.setFilenumid(treedot.getFilenumid());
				}
				if (treedot.getFilenumid2() != null) {
					model.setFilenumid2(treedot.getFilenumid2());
				}
				model.setProjIndex(treedot.getProjIndex());
				model.setDomainIndex(treedot.getDomainIndex());
				myTreedotMapper.updateTreedot(model);
			}
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource 
	public void setMyTreedotMapper(MyTreedotMapper myTreedotMapper) {
		this.myTreedotMapper = myTreedotMapper;
	}

}
