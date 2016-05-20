package org.cc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.cc.service.RedisTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTemplateServiceImpl implements RedisTemplateService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/** -------------------->> pojo操作 <<-------------------- */
	public boolean set(String key, Object value) {
		if (key == null || value == null)
			return false;
		redisTemplate.opsForValue().set(key, value);
		return true;
	}

	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/** --------------->> list操作 <<--------------- */
	public boolean setList(String key, List<Object> list) {
		for (Object value : list)
			redisTemplate.opsForList().rightPush(key, value);
		return true;
	}

	public List<Object> getList(String key) {
		return getListByRange(key, 0, -1);
	}

	/**
	 * 获取list长度
	 * 
	 * @param key
	 * @return
	 */
	public Long getListSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 按范围索引
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> getListByRange(String key, int start, int end) {
		List<Object> list = redisTemplate.opsForList().range(key, start, end);
		return list;
	}

	/**
	 * 按索引赋值
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public boolean setByIndex(String key, long index, Object value) {
		redisTemplate.opsForList().set(key, index, value);
		return true;
	}

	/**
	 * 按索引取值
	 * 
	 * @param key
	 * @param indexredisTemplate
	 *            .opsForSet()
	 * @return
	 */
	public Object getByIndex(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	/**
	 * 按索引删除
	 */
	public boolean deleteByIndex(String key, long index, Object value) {
		redisTemplate.opsForList().remove(key, index, value);
		return true;
	}

	/** --------------------->> set操作 <<--------------------- */
	/**
	 * 存set
	 */
	public boolean addSet(String key, Set<Object> set) {
		for (Object value : set)
			redisTemplate.opsForSet().add(key, value);
		return true;
	}

	/**
	 * 取set
	 */
	public Set<Object> getSet(String key) {
		Set<Object> set = redisTemplate.opsForSet().members(key);
		return set;
	}

	/**
	 * 获取set长度
	 */
	public Long getSetSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	/**
	 * 删除指定key中的set集合的某个元素
	 */
	public boolean deleteElement(String key, Object value) {
		return redisTemplate.opsForSet().remove(key, value) != null;
	}

	/**
	 * 判断指定key中的set集合是否包含某元素
	 */
	public boolean isMemBer(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	/** --------------------->> map操作 <<--------------------- */
	public boolean putAll(String key, Map<Object, Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
		return true;
	}

	public Map<Object, Object> getMap(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/** --------------------->> zset操作 <<--------------------- */

	/** --------------------->> 通用操作 <<--------------------- */
	/**
	 * 删除
	 */
	public boolean delete(String key) {
		redisTemplate.opsForValue().getOperations().delete(key);
		return true;
	}

	/**
	 * 清空缓存
	 */
	public boolean clear() {
		return redisTemplate.execute(new RedisCallback<Object>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "okay";
			}
		}).equals("okay");
	}

	/**
	 * 设置失效时间 时间单位为秒
	 */
	public boolean expire(String key, long timeout) {
		return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置到期时间
	 */
	public boolean expireAt(String key, Date expireDate) {
		return redisTemplate.expireAt(key, expireDate);
	}

	/**
	 * 确定key值是否存在
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
}
