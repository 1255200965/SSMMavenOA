package cn.ssm.oa.mapper.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.ssm.oa.mapper.RoleMapper;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.service.RoleService;

/**
 * 读取配置文件中Bean的方式
 * @author RocHuang
 *
 */
public class RoleMapperTest2 {

	// 定义一个静态日志打印器
	private static final Logger logger = Logger.getLogger(RoleMapperTest2.class);

	private ApplicationContext context;
	private RoleMapper roleMapper;
	private RoleService roleService;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/applicationContext-dao.xml",
				"classpath:spring/applicationContext-service.xml" });
		roleMapper = (RoleMapper) context.getBean("roleMapper");
		roleService = (RoleService) context.getBean("roleService");
	}

	/**
	 * 测试Mapper接口
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMapper() throws Exception {
		Role role = roleMapper.selectByPrimaryKey(2L);
		logger.info(JSON.toJSONString(role)); // 注意日志的级别，并且调用fastjson的toString方法打印对象
	}

	/**
	 * 测试Service接口
	 * 
	 * @throws Exception
	 */
	@Test
	public void testService() throws Exception {
		List<Role> list = roleService.findAll();
		logger.info(JSON.toJSONString(list));
	}

}
