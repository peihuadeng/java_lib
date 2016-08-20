package com.dph.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dph.common.entity.BaseEntity;
import com.dph.common.entity.Page;
import com.dph.common.persistence.BaseDao;
import com.dph.common.utils.StringUtils;

public abstract class BaseService<T extends BaseEntity<T>, Dao extends BaseDao<T>> {

	@Autowired
	protected Dao dao;

	/**
	 * 保存对象
	 * 如id为空则使用插入语句，否则使用更新语句
	 * @param t
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveAll(final T t) {
		if (t == null) {
			return 0;
		}

		if (StringUtils.isBlank(t.getId())) {
			t.preInsert();
			return dao.insert(t);
		} else {
			return dao.updateByPrimaryKey(t);
		}
	}
	
	/**
	 * 保存对象
	 * 如id为空则使用插入语句，否则使用更新语句
	 * @param t
	 * @return
	 */
	@Transactional(readOnly = false)
	public int save(final T t) {
		if (t == null) {
			return 0;
		}

		if (StringUtils.isBlank(t.getId())) {
			t.preInsert();
			return dao.insert(t);
		} else {
			return dao.updateByPrimaryKey(t);
		}
	}

	/**
	 * 移除对象
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int remove(final String id) {
		if (StringUtils.isBlank(id)) {
			return 0;
		}

		return dao.deleteByPrimaryKey(id);
	}

	/**
	 * 获得对象
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public T get(final String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}

		return dao.selectByPrimaryKey(id);
	}
	
	/**
	 * 获得对象，不关联其他对象
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public T getSimple(final String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}

		return dao.selectSimpleByPrimaryKey(id);
	}
	
	/**
	 * 获取所有符合条件的对象
	 * @param t
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> getList(final T t) {
		return dao.select(t);
	}
	
	/**
	 * 获取所有符合条件的对象，不关联其他对象
	 * @param t
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> getSimpleList(final T t) {
		return dao.selectSimple(t);
	}

	/**
	 * 分页获取符合条件的对象
	 * @param t
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<T> findPage(final T t) {
		if (t == null) {
			return null;
		}

		// 设置默认分页
		if (t.getPage() == null) {
			Page<T> page = new Page<T>();
			t.setPage(page);
		}

		List<T> list = dao.select(t);
		Page<T> page = t.getPage();
		page.setResults(list);

		return page;
	}
	/**
	 * 分页获取符合条件的对象
	 * @param t
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<T> findSimplePage(final T t) {
		if (t == null) {
			return null;
		}

		// 设置默认分页
		if (t.getPage() == null) {
			Page<T> page = new Page<T>();
			t.setPage(page);
		}

		List<T> list = dao.selectSimple(t);
		Page<T> page = t.getPage();
		page.setResults(list);

		return page;
	}
}
