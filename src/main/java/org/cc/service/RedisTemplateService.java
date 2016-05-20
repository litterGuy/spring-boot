package org.cc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisTemplateService {
	/** -------------------->> pojo操作 <<-------------------- */
	public boolean set(String key, Object value);

	public Object get(String key);

	/** --------------->> list操作 <<--------------- */
	public boolean setList(String key, List<Object> list);

	public List<Object> getList(String key);

	public Long getListSize(String key);

	public List<Object> getListByRange(String key, int start, int end);

	public boolean setByIndex(String key, long index, Object value);

	public Object getByIndex(String key, long index);

	public boolean deleteByIndex(String key, long index, Object value);

	/** --------------------->> set操作 <<--------------------- */
	public boolean addSet(String key, Set<Object> set);

	public Set<Object> getSet(String key);

	public Long getSetSize(String key);

	public boolean deleteElement(String key, Object value);

	public boolean isMemBer(String key, Object value);

	/** --------------------->> map操作 <<--------------------- */
	public boolean putAll(String key, Map<Object, Object> map);

	public Map<Object, Object> getMap(String key);

	/** --------------------->> 通用操作 <<--------------------- */
	public boolean delete(String key);

	public boolean clear();

	public boolean expire(String key, long timeout);

	public boolean expireAt(String key, Date expireDate);

	public boolean hasKey(String key);
}
