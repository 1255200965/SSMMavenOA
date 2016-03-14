package cn.ssm.oa.mapper.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.ssm.oa.mapper.RoleMapper;
import cn.ssm.oa.po.Role;
import cn.ssm.oa.service.RoleService;

/**
 * 使用测试注解自动注入装配Bean的方式
 * @author RocHuang
 *
 */
// 指定单元测试执行类(Spring的单元测试执行类),让测试运行于Spring测试环境
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( // 指定Spring配置文件所在的路径，可以同时指定多个文件
		locations = { 
				"classpath:spring/applicationContext-dao.xml", 
				"classpath:spring/applicationContext-service.xml", /*
				"classpath:spring/applicationContext-transaction.xml"*/
		}
)
public class RoleMapperTest {
	
	// 定义一个静态日志打印器
	private static final Logger logger = Logger.getLogger(RoleMapperTest.class);

	@Autowired // 注入mapper
	private RoleMapper roleMapper;
	@Autowired
	private RoleService roleService;

	/**
	 * 测试Mapper接口
	 * @throws Exception
	 */
	@Test
	public void testMapper() throws Exception {
		Role role = roleMapper.selectByPrimaryKey(2L);
		logger.info(JSON.toJSONString(role)); // 注意日志的级别，并且调用fastjson的toString方法打印对象
	}
	
	/**
	 * 测试Service接口
	 * @throws Exception
	 */
	@Test
	public void testService() throws Exception {
		List<Role> list = roleService.findAll();
		logger.info(JSON.toJSONString(list));
	}

}
