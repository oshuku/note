# Redis

## 1.Redis的下载和安装

- 将Redis安装包上传到linux
- 解压安装包：tar -zxvf redis-4.0.0.tar.gz -C /usr/local
- 安装Redis的依赖环境gcc，命令：yum install gcc-c++
- 进入/usr/local/redis-4.0.0，进行编译，命令：make
- 进入redis的src目录，进行安装，命令：make install

## 2.服务启动

- 执行src目录下的redis-server

  注意： 默认为前台启动服务。若要后台运行，需要修改配置文件

配置文件，redis.conf

- vim redis.conf 打开配置文件
- /dae 回车        搜索关键字
- 修改为 yes
- redis-server ../redis.conf   启动服务并加载配置文件

设置验证密码

- vim redis.conf 打开配置文件
- /requirepass 回车        搜索关键字
- 设定密码
- redis-server ../redis.conf  启动服务并加载配置文件
- redis-cli -h localhost -p 6379 -a password  连接服务并验证

允许远程连接

- vim redis.conf 打开配置文件
- /bind 回车        搜索关键字
- \# bind 127.0.0.1  注释掉



## 3.Redis与SpringBoot的整合

配置文件：

```yaml
spring:
	redis:
		host: localhost
		port: 6379
		password: 1234
		database: 0 # redis默认提供了16个数据库，默认使用0号数据库
		jedis:
			# Redis连接池配置
			pool:
				max-active: 8 # 最大连接数
				max-wait: 1ms # 连接池最大阻塞等待时间
				max-idle: 4 # 连接池中的最大空闲连接
				min-idle: 0 # 连接池中的最小空闲连接
```

设置序列化器

```java
/**
 * redis配置类
 * @author zhuwang
 *
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport{
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
		//默认的Key序列化器为：JdkSerializationRedisSerializer
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}

}

```





