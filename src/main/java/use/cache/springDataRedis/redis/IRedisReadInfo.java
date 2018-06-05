package use.cache.springDataRedis.redis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface IRedisReadInfo <T>{

	T readInfo();

	//查询了Java API，在Class类中有这么两个方法： getGenericInterfaces()和getGenericSuperclass()
	@SuppressWarnings("unchecked")
	public default Class <T> getTemplateClass()
	{
	   Type[] types = getClass().getGenericInterfaces();
	   for (Type type : types)
	   {
		   if( type instanceof ParameterizedType ){
			   ParameterizedType pType = (ParameterizedType)type;
		       Type claz = pType.getActualTypeArguments()[0];
		       if( claz instanceof Class ){
		    	   return (Class<T>) claz;
		       }
		    }
	   }
	    return null;
	}
}
