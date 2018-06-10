package use.cache.springDataShiro.memStorage;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.CacheException;

/**
 * 
 * 项目名称:use.cache.spring
 * 类型名称:IMemoryStorage
 * 类型描述:
 * 作者:wx
 * 创建时间:2018年6月10日
 * @version:
 */
@SuppressWarnings("rawtypes")
public interface IMemoryStorage {

	public Object getSpringCache();
	public void setSpringCache(Object cache);
	
	public Object get(Object key) throws CacheException ;
	public Object put(Object key, Object value) throws CacheException ;
	
	public Object remove(Object key) throws CacheException ;
	public void clear() throws CacheException ;
	public int size() ;
	
	public Set keys(String key);
	public Collection values(String key);
	
	public Object set(Object key,Object value,int expire) throws Exception;
}
