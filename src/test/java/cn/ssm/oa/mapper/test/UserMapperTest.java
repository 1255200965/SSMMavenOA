package cn.ssm.oa.mapper.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.ssm.oa.mapper.UserMapper;
import cn.ssm.oa.po.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-dao.xml", 
				"classpath:spring/applicationContext-service.xml"})
public class UserMapperTest {

	private static final Logger logger = Logger.getLogger(UserMapperTest.class);
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 测试关联查询的自定义方法(连表查询)
	 */
	@Test
	public void testFindAll() {
		List<User> list = userMapper.findAll();
//		logger.info(JSON.toJSONString(list.get(0)));
		logger.info(JSON.toJSONString(list));
	}

}
