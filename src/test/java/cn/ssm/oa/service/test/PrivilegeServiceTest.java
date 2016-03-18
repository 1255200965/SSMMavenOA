package cn.ssm.oa.service.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.ssm.oa.po.Privilege;
import cn.ssm.oa.service.PrivilegeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-dao.xml", 
"classpath:spring/applicationContext-service.xml"})
public class PrivilegeServiceTest {

	private static final Logger logger = Logger.getLogger(PrivilegeServiceTest.class);
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@Test
	public void test() {
		List<Privilege> topList = privilegeService.findTopList();
		for (Privilege top : topList) {
			logger.warn(top.getName());
			for (Privilege child : top.getChildren()) {
				logger.warn("---" + child.getName());
				for (Privilege gandson : child.getChildren()) {
					logger.warn("------" + gandson.getName());
				}
			}
		}
	}

}
