package com.huixin.framework.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.wx.hongbao.HongBaoBean;
import com.huixin.framework.wx.hongbao.SerializeUtil;
import com.huixin.framework.wx.util.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.ZParams.Aggregate;

public class RedisUtil {
	private static String serverUrl = PropertiesUtil.getValue("redis.properties", "redis.server.url");
	private static String serverPort = PropertiesUtil.getValue("redis.properties", "redis.server.port");
	private static String redisPwd = PropertiesUtil.getValue("redis.properties", "redis.server.pwd");
	private static RedisUtil instance0 = null;			//默认对应0号数据库的实例
	private static RedisUtil instance1 = null;			//默认对应1号数据库的实例
	private static RedisUtil instance2 = null;			//默认对应2号数据库的实例
	private static RedisUtil instance3 = null;			//默认对应3号数据库的实例
	private static RedisUtil instance4 = null;			//默认对应4号数据库的实例
	private static RedisUtil instance5 = null;			//配置参数
	private static RedisUtil instance6 = null;			//配置参数
	//所有数据库实例对应16个数据
	private static RedisUtil[] instances = {instance0,instance1,instance2,instance3,instance4,instance5,instance6};

	private JedisPool pool = null;
	
	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;
    
	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;


	private static int TIMEOUT = 10000;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;
	

	public static RedisUtil getInstance() {
		if (instance0 == null) {
			instance0 = new RedisUtil();
		}
		return instance0;
	}
	
	/**
	 * 指定存储数据库的实例生成
	 * @param database
	 * @return
	 */
	public static RedisUtil getInstance(int database) {
		if (instances[database] == null) {
			instances[database] = new RedisUtil(database);
		}
		return instances[database];
	}

	private RedisUtil() {
		if (pool == null) {
			JedisPoolConfig  poolConfig = new JedisPoolConfig ();
//			poolConfig.setMaxActive(MAX_ACTIVE);
			poolConfig.setMaxIdle(MAX_IDLE);
//			poolConfig.setMaxWait(MAX_WAIT);
			poolConfig.setTestOnBorrow(TEST_ON_BORROW);
			pool = new JedisPool(poolConfig, serverUrl, Integer.parseInt(serverPort),TIMEOUT,redisPwd);
		}
	}
	
	//指定存储数据库号
	private RedisUtil(int database) {
		if (pool == null) {
			JedisPoolConfig  poolConfig = new JedisPoolConfig ();
//			poolConfig.setMaxActive(MAX_ACTIVE);
			poolConfig.setMaxIdle(MAX_IDLE);
//			poolConfig.setMaxWait(MAX_WAIT);
			poolConfig.setTestOnBorrow(TEST_ON_BORROW);
			pool = new JedisPool(poolConfig, serverUrl, Integer.parseInt(serverPort), TIMEOUT, redisPwd, database);
		}
	}
	
	//销毁单例对象
	private void destoryRedisUtil(){
		
	}

	/**
	 * 添加数据到缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void setBytes(String key, byte[] value) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			if (value != null) {
				redis.set(key.getBytes(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	/**
	 * 添加数据到缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			if (value != null) {
				redis.set(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}

	/**
	 * 添加list数据到缓存中
	 *
	 * @param key
	 * @param value
	 */
	public void setList(String key, List<String> value) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			if (value != null) {
				for (int i = 0; i < value.size(); i++) {
					redis.lpush(key, value.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}

	public List<String> getList(String key) {
		List<String> result = new ArrayList<String>();
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.lrange(key, 0, -1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		return result;
	}

	/**
	 * 根据key获取数据
	 * 
	 * @param key
	 */
	public String get(String key) {
		String result = "";
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		if (result == null || result.equals("ok") || result.equals("OK")) {
			return "";
		} else {
			return result;
		}
	}
	
	/**
	 * 根据key获取数据
	 * 
	 * @param key
	 */
	public byte[] getByte(String key) {
		byte[] result = null;
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.get(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		return result;
	}

	/**
	 * 根据key删除
	 * 
	 * @param key
	 */
	public void del(String key) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			redis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}

	/**
	 * 添加map型数据
	 * 
	 * @param key1
	 * @param key2
	 * @param value
	 */
	public void hset(String key1, String key2, String value) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			if (value != null) {
				redis.hset(key1, key2, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	
	/**
	 * 添加map型数据
	 * 
	 * @param key1
	 * @param key2
	 * @param value
	 */
	public void hset(String key, Map value) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			if (value != null) {
				Iterator iter = value.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key1 = (String)entry.getKey();
					String val = (String)entry.getValue();
					redis.hset(key, key1, val);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	
	

	/**
	 * 根据双层key获取数据
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public String hget(String key1, String key2) {
		Jedis redis = null;
		String result = "";
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.hget(key1, key2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		if (result == null || result.equals("ok") || result.equals("OK")) {
			return "";
		} else {
			return result;
		}
	}

	/**
	 * 根据key获取map
	 * 
	 * @param key
	 */
	public Map<String, String> hgetAll(String key) {
		Jedis redis = null;
		Map<String, String> map = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			map = redis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		if (map == null) {
			return new HashMap<String, String>();
		} else {
			return map;
		}
	}

	/**
	 * 根据双层key删除元素
	 * 
	 * @param key1
	 * @param key2
	 */
	public void hdel(String key1, String key2) {
		Jedis redis = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			redis.hdel(key1, key2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	/**
	 * 获取当前数据库中的所有键值
	 * @return
	 */
	public Set getAllData(){
		Jedis redis = null;
		Set data = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			data = redis.keys("*");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		return data;
	}
	
	 /** 
     * Redis Lrange 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。  
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。 
     * @param string 
     * @param start 
     * @param end 
     * @return 
     */  
    public  List<String> lrange(String key, int start, int end) { 
    	Jedis redis = null;
    	List<String> result = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.lrange(key, start, end);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
        
        return result;  
    }  
	
    /**
     * 返回有序集 key 中，指定区间内的成员。
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set zrange(String key, long start, long end){
    	Jedis redis = null;
    	Set result = null;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.zrange(key, start, end);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    	return result;
    }
    
    /**
     * 返回有序集 key 中，指定区间内的成员。(成员位置从大到小)
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set zrevrangeWithScores(String key, long start, long end){
    	Jedis redis = null;
    	Set result = null;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.zrevrangeWithScores(key, start, end);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    	return result;
    }
	
    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
     * @param key
     * @param score
     * @param member
     */
    public void zadd(String key,double score,String member){
    	Jedis redis = null;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			redis.zadd(key, score, member);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    }
    
    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     * @param key
     * @param min
     * @param max
     */
    public Long zcount(String key, String min, String max){
    	Jedis redis = null;
    	Long result = 0L;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.zcount(key, min, max);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    	return result;
    }
    
    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     * @param key
     * @param min
     * @param max
     */
    public Long zunionstore(String dstkey, ZParams params, String sets){
    	Jedis redis = null;
    	Long result = 0l;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.zunionstore(dstkey, params, sets);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    	return result;
    }
    
    
    
    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 
     * @param key
     * @param score
     * @param member
     */
    public void zincrby(String key,double increment,String member){
    	Jedis redis = null;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			redis.zincrby(key, increment, member);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    }
    
    /**
     * 返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     * @param key
     * @param increment
     * @param member
     */
    public Double zscore(String key,String member){
    	Jedis redis = null;
    	Double result = null;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.zscore(key, member);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    	return result;
    }
    
    /**
     * 根据key添加list成员
     * @param key
     * @param member
     */
    public void setList(String key, String member){
    	Jedis redis = null;
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			redis.lpush(key, member);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    }
    
    
    /**
     * 根据key查询map，再转PageData
     * @param key
     * @return
     */
    public PageData hgetAllPd(String key){
    	PageData pd = new PageData();
    	Jedis redis = null;
		Map<String, String> map = null;
		try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			map = redis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		}
		if (map == null) {
			return pd;
		} else {
			for (Entry<String, String> entry: map.entrySet()) {  
			    String mapKey = entry.getKey();  
			    String value = entry.getValue();
			    pd.put(mapKey,value);
			}
			return pd;
		}
    }
    
    
    /**
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
     * @param key
     * @param member
     */
    public String srandmember(String key){
    	Jedis redis = null;
    	String result = "";
    	try {
			redis = pool.getResource();
			redis.auth(redisPwd);
			result = redis.srandmember(key);  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				pool.returnResource(redis);
			}
		} 
    	return result;
    }
    
    
    
	

	public static void main(String[] args) throws BusinessException {
		serverUrl = "121.40.194.154";
		// String serverUrl = "10.1.66.100";//正式库
//		RedisUtil ru = new RedisUtil();
//		HongBaoUtils.setHongBao("MapTest1",10, 5);
		
//		RedisUtil.getInstance(3).setBytes("MapTest1",  SerializeUtil.serialize(hbList));
		byte[] by= RedisUtil.getInstance(3).getByte("8f10bd64a5dd44b1a69671e34d4efb6f");
		List<HongBaoBean> hbList = (List<HongBaoBean>) SerializeUtil.unserialize(by);
		System.out.println(hbList.size());
		for (int j = 0; j < hbList.size(); j++) {
			HongBaoBean hb = hbList.get(j);
			System.out.println(hb.getMoney() + " :" + hb.isFlag());
		}
		
		
		// test.lpush("APP_URL","http://localhost:8080/bbs/","http://localhost:8080/qq/");
		// test.lpush("http://localhost:8080/bbs/","127.0.0.1","127.0.0.2","127.0.0.3");
		// test.lpush("http://localhost:8080/bbs/","127.0.0.4");
		// test.lpush("APP_URL","http://localhost:8080/bbs/");

		// test.set("http://localhost:8080/bbs/" +
		// ComConstants.APP_URL_TYPE_KEY,"1");
		// System.out.println(test.get("http://10.1.3.28/ls" +
		// ComConstants.APP_URL_CODE_KEY));
		// System.out.println(test.lrange("http://10.1.3.155:9080/casserver/exchange/exchangeLogin.htm",0,-1));
//		List<String> list = test.lrange("APP_URL", 0, -1);
//		for (int i = 0; i < list.size(); i++) {
//			String code = test.get(list.get(i) + ComConstants.APP_URL_CODE_KEY);
//			// System.out.println(test.get(list.get(i) +
//			// ComConstants.APP_URL_CODE_KEY) + "=======");
//			if (ru.getList(list.get(i)).size() != 0) {
//				System.out.println(code + " " + ru.getList(list.get(i)));
//			}
//		}
		// System.out.println(test.lrange("APP_URL",0,-1));
		// Map<String, String> map = test.hgetAll("271");
		// for (Map.Entry entry : map.entrySet()) {
		// System.out.println(entry.getKey() + ":" + entry.getValue() + "\n");
		// }

		/*
		 * ---------------------------------------------------------------------
		 * - -------------------------------------
		 */
		// Jedis redis = new Jedis("10.1.13.105",6379);
		/**
		 * KEY操作
		 * 
		 * //KEYS Set keys =
		 * redis.keys("*");//列出所有的key，查找特定的key如：redis.keys("foo") Iterator
		 * t1=keys.iterator() ; while(t1.hasNext()){ Object obj1=t1.next();
		 * System.out.println(obj1); }
		 * 
		 * //DEL 移除给定的一个或多个key。如果key不存在，则忽略该命令。 redis.del("name1");
		 * 
		 * //TTL 返回给定key的剩余生存时间(time to live)(以秒为单位) redis.ttl("foo");
		 * 
		 * //PERSIST key 移除给定key的生存时间。 redis.persist("foo");
		 * 
		 * //EXISTS 检查给定key是否存在。 redis.exists("foo");
		 * 
		 * //MOVE key db
		 * 将当前数据库(默认为0)的key移动到给定的数据库db当中。如果当前数据库(源数据库)和给定数据库(目标数据库)
		 * 有相同名字的给定key，或者key不存在于当前数据库，那么MOVE没有任何效果。 redis.move("foo",
		 * 1);//将foo这个key，移动到数据库1
		 * 
		 * //RENAME key newkey
		 * 将key改名为newkey。当key和newkey相同或者key不存在时，返回一个错误。当newkey已经存在时
		 * ，RENAME命令将覆盖旧值。 redis.rename("foo", "foonew");
		 * 
		 * //TYPE key 返回key所储存的值的类型。
		 * System.out.println(redis.type("foo"));//none
		 * (key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
		 * 
		 * //EXPIRE key seconds 为给定key设置生存时间。当key过期时，它会被自动删除。
		 * redis.expire("foo", 5);//5秒过期 //EXPIREAT
		 * EXPIREAT的作用和EXPIRE一样，都用于为key设置生存时间。不同在于EXPIREAT命令接受的时间参数是UNIX时间戳(unix
		 * timestamp)。
		 * 
		 * //一般SORT用法 最简单的SORT使用方法是SORT key。 redis.lpush("sort", "1");
		 * redis.lpush("sort", "4"); redis.lpush("sort", "6");
		 * redis.lpush("sort", "3"); redis.lpush("sort", "0");
		 * 
		 * List list = redis.sort("sort");//默认是升序 for(int
		 * i=0;i<list.size();i++){ System.out.println(list.get(i)); }
		 */
		/*
		 * ---------------------------------------------------------------------
		 * - -------------------------------------
		 */
		/**
		 * String 操作
		 * 
		 * //SET key value将字符串值value关联到key。 redis.set("name", "wangjun1");
		 * redis.set("id", "123456"); redis.set("address", "guangzhou");
		 * 
		 * //SETEX key seconds value将值value关联到key，并将key的生存时间设为seconds(以秒为单位)。
		 * redis.setex("foo", 5, "haha");
		 * 
		 * //MSET key value [key value ...]同时设置一个或多个key-value对。
		 * redis.mset("haha","111","xixi","222");
		 * 
		 * //redis.flushAll();清空所有的key
		 * System.out.println(redis.dbSize());//dbSize是多少个key的个数
		 * 
		 * //APPEND key value如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
		 * redis.append("foo",
		 * "00");//如果key已经存在并且是一个字符串，APPEND命令将value追加到key原来的值之后。
		 * 
		 * //GET key 返回key所关联的字符串值 redis.get("foo");
		 * 
		 * //MGET key [key ...] 返回所有(一个或多个)给定key的值 List list =
		 * redis.mget("haha","xixi"); for(int i=0;i<list.size();i++){
		 * System.out.println(list.get(i)); }
		 * 
		 * //DECR key将key中储存的数字值减一。 //DECRBY key
		 * decrement将key所储存的值减去减量decrement。 //INCR key 将key中储存的数字值增一。 //INCRBY
		 * key increment 将key所储存的值加上增量increment。
		 */
		/*
		 * ---------------------------------------------------------------------
		 * - -------------------------------------
		 */
		/**
		 * Hash 操作
		 * 
		 * //HSET key field value将哈希表key中的域field的值设为value。 redis.hset("website",
		 * "google", "www.google.cn"); redis.hset("website", "baidu",
		 * "www.baidu.com"); redis.hset("website", "sina", "www.sina.com");
		 * 
		 * //HMSET key field value [field value ...] 同时将多个field -
		 * value(域-值)对设置到哈希表key中。 Map map = new HashMap(); map.put("cardid",
		 * "123456"); map.put("username", "jzkangta"); redis.hmset("hash", map);
		 * 
		 * //HGET key field返回哈希表key中给定域field的值。
		 * System.out.println(redis.hget("hash", "username"));
		 * 
		 * //HMGET key field [field ...]返回哈希表key中，一个或多个给定域的值。 List list =
		 * redis.hmget("website","google","baidu","sina"); for(int
		 * i=0;i<list.size();i++){ System.out.println(list.get(i)); }
		 * 
		 * //HGETALL key返回哈希表key中，所有的域和值。 Map<String,String> map =
		 * redis.hgetAll("hash"); for(Map.Entry entry: map.entrySet()) {
		 * System.out.print(entry.getKey() + ":" + entry.getValue() + "\t"); }
		 * 
		 * //HDEL key field [field ...]删除哈希表key中的一个或多个指定域。 //HLEN key
		 * 返回哈希表key中域的数量。 //HEXISTS key field查看哈希表key中，给定域field是否存在。 //HINCRBY
		 * key field increment为哈希表key中的域field的值加上增量increment。 //HKEYS
		 * key返回哈希表key中的所有域。 //HVALS key返回哈希表key中的所有值。
		 */
		/*
		 * ---------------------------------------------------------------------
		 * - -------------------------------------
		 */
		/**
		 * LIST 操作 //LPUSH key value [value ...]将值value插入到列表key的表头。
		 * redis.lpush("list", "abc"); redis.lpush("list", "xzc");
		 * redis.lpush("list", "erf"); redis.lpush("list", "bnh");
		 * 
		 * //LRANGE key start
		 * stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。下标(index)参数start和stop都以0为底
		 * ，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。你也可以使用负数下标，以-1表示列表的最后一个元素，-2
		 * 表示列表的倒数第二个元素，以此类推。 List list = redis.lrange("list", 0, -1); for(int
		 * i=0;i<list.size();i++){ System.out.println(list.get(i)); }
		 * 
		 * //LLEN key返回列表key的长度。 //LREM key count
		 * value根据参数count的值，移除列表中与参数value相等的元素。
		 */
		/*
		 * ---------------------------------------------------------------------
		 * - -------------------------------------
		 */
		/**
		 * SET 操作 //SADD key member [member ...]将member元素加入到集合key当中。
		 * redis.sadd("testSet", "s1"); redis.sadd("testSet", "s2");
		 * redis.sadd("testSet", "s3"); redis.sadd("testSet", "s4");
		 * redis.sadd("testSet", "s5");
		 * 
		 * //SREM key member移除集合中的member元素。 redis.srem("testSet", "s5");
		 * 
		 * //SMEMBERS key返回集合key中的所有成员。 Set set = redis.smembers("testSet");
		 * Iterator t1=set.iterator() ; while(t1.hasNext()){ Object
		 * obj1=t1.next(); System.out.println(obj1); }
		 * 
		 * //SISMEMBER key member判断member元素是否是集合key的成员。是（true），否则（false）
		 * System.out.println(redis.sismember("testSet", "s4"));
		 * 
		 * //SCARD key返回集合key的基数(集合中元素的数量)。 //SMOVE source destination
		 * member将member元素从source集合移动到destination集合。
		 * 
		 * //SINTER key [key ...]返回一个集合的全部成员，该集合是所有给定集合的交集。 //SINTERSTORE
		 * destination key [key
		 * ...]此命令等同于SINTER，但它将结果保存到destination集合，而不是简单地返回结果集 //SUNION key [key
		 * ...]返回一个集合的全部成员，该集合是所有给定集合的并集。 //SUNIONSTORE destination key [key
		 * ...]此命令等同于SUNION，但它将结果保存到destination集合，而不是简单地返回结果集。 //SDIFF key [key
		 * ...]返回一个集合的全部成员，该集合是所有给定集合的差集 。 //SDIFFSTORE destination key [key
		 * ...]此命令等同于SDIFF，但它将结果保存到destination集合，而不是简单地返回结果集。
		 */
	}

}
