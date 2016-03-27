package cn.ssm.oa.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.ssm.oa.po.Reply;

public interface ReplyService {

	List<Reply> findByTopicId(Long id);

	PageInfo<Reply> findByTopicIdPage(Long id, Integer pageNum, Integer pageSize);

	void save(Reply reply);

}
