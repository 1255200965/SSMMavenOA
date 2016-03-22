package cn.ssm.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.ssm.oa.mapper.ForumMapper;
import cn.ssm.oa.po.Forum;
import cn.ssm.oa.service.ForumService;
import tk.mybatis.mapper.entity.Example;

public class ForumServiceImpl implements ForumService {

	@Autowired
	private ForumMapper forumMapper;

	/**
	 * 根据position升序查询所有的列表
	 * @return
	 */
	@Override
	public List<Forum> findAll() {
		Example example = new Example(Forum.class);
		example.setOrderByClause("position"); // 添加order by条件
		return forumMapper.selectByExample(example);
	}

	@Override
	public void save(Forum forum) {
		forum.setTopicCount(0); // 新增的板块主题数默认为0
		forum.setArticleCount(0); // 新增的板块文章数默认为0
		forumMapper.insertSelective(forum); // 插入非空字段数据
		forum.setPosition(forum.getId().intValue()); // 新增的板块位置标记设置为和自动生成的id相同
		forumMapper.updateByPrimaryKeySelective(forum); // 更新position的值
	}

	@Override
	public void delete(Long id) {
		forumMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Forum getById(Long id) {
		return forumMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Forum forum) {
		forumMapper.updateByPrimaryKeySelective(forum);
		// 下面代码是另外一种方式，很繁琐，但是可以灵活的添加where条件
		Example example = new Example(Forum.class);
		// id作为where条件(默认无条件即：更新所有记录)
		example.or().andEqualTo("id", forum.getId());
		/*
		 * 通过example条件更新非空字段的值(即传递有值属性进行更新，其他保持原样)
		 */
		forumMapper.updateByExampleSelective(forum, example);
	}

	@Override
	public void moveUp(Long id) {
		Forum forum = forumMapper.selectByPrimaryKey(id); // 当前准备上移的对象
		Forum other = null; // 上面临近的对象
		Example example = new Example(Forum.class);
		example.setOrderByClause("position DESC"); // 设置排序条件(降序排列)
		example.or().andLessThan("position", forum.getPosition());
		List<Forum> forumList = forumMapper.selectByExample(example);
		if (forumList != null && !forumList.isEmpty()) {
			other = forumList.get(0);
		}
		int temp = forum.getPosition(); // 用来做交换的临时变量
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		forumMapper.updateByPrimaryKeySelective(forum); // 更新当前对象的position
		forumMapper.updateByPrimaryKeySelective(other); // 更新上一个对象的position
	}

	@Override
	public void moveDown(Long id) {
		Forum forum = forumMapper.selectByPrimaryKey(id); // 当前准备下移的对象
		Forum other = null; // 下面临近的对象
		Example example = new Example(Forum.class);
		example.setOrderByClause("position ASC"); // 设置排序条件(升序序排列，默认为升序)
		example.or().andGreaterThan("position", forum.getPosition());
		List<Forum> forumList = forumMapper.selectByExample(example);
		if (forumList != null && !forumList.isEmpty()) {
			other = forumList.get(0);
		}
		int temp = forum.getPosition(); // 用来做交换的临时变量
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		forumMapper.updateByPrimaryKeySelective(forum); // 更新当前对象的position
		forumMapper.updateByPrimaryKeySelective(other); // 更新下一个对象的position
	}
}
