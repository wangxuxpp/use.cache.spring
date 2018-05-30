package use.cache.springDataShiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;

import use.cache.springDataRedis.initial.CacheManagerOperation;
import use.cache.springDataRedis.initial.SpringRedisFactoryUtil;
import use.cache.springDataShiro.memStorage.IMemoryStorage;
import use.cache.springDataShiro.memStorage.MemoryStorageFactory;

@SuppressWarnings({"rawtypes"})
public class SpringCatchToShiroCatchAdapter implements Cache{

	private String keyPrefix = SpringRedisFactoryUtil.getConfigure().getShiroPreFix();
	private IMemoryStorage fMem = null;

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	private String getCacheName(int index)
	{
		switch(index)
		{
			case 0: return CacheManagerOperation.defaultCache;
			case 1: return CacheManagerOperation.secondCache;
			case 2: return CacheManagerOperation.limitsCache;
			default: return CacheManagerOperation.defaultCache;
		}
	}
	public IMemoryStorage getSpringCache()
	{
		if(fMem == null)
		{
			String key = getCacheName(SpringRedisFactoryUtil.getConfigure().getShiroDataBaseIndex());
			org.springframework.cache.Cache fCache = CacheManagerOperation.getCacheManager().getCache(key);
			fMem = MemoryStorageFactory.getMemoryStorage(fCache);
		}
		
		return fMem;
	}
	
	public Object get(Object key) throws CacheException {
		return getSpringCache().get(key);
	}

	public Object put(Object key, Object value) throws CacheException {
		return getSpringCache().put(key, value);
	}

	public Object remove(Object key) throws CacheException {
		if(key instanceof SimplePrincipalCollection)
		{
			return key;
		}
		return getSpringCache().remove(key);
	}

	public void clear() throws CacheException {
		getSpringCache().clear();
	}

	public int size() {
		return getSpringCache().size();
	}
	public Set keys(String key) {
		return getSpringCache().keys(key);
	}
	public Collection values(String key) {
		return getSpringCache().values(key);
	}
	public Set keys() {
		return keys(this.keyPrefix);
	}
	public Collection values() {
		return values(this.keyPrefix);
	}
	public Object set(Object key,Object value,int expire) throws Exception
	{
		return getSpringCache().set(key, value, expire);
	}
	

}
