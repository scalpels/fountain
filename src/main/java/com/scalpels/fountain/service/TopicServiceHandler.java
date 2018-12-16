package com.scalpels.fountain.service;

import com.github.pagehelper.PageHelper;
import com.scalpels.fountain.entity.Topic;
import com.scalpels.fountain.entity.TopicExample;
import com.scalpels.fountain.mapper.TopicMapper;
import com.scalpels.fountain.util.MybatisOrderByClauseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceHandler implements TopicService {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private TopicMapper topicMapper;

	@Override
	public Topic getTopicById(Long id) {
		logger.info("get from db {}",id);
		return topicMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteTopicById(Long id) {
		topicMapper.deleteByPrimaryKey(id);
	}

	@Override	
	public List<Topic> getTopicList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		TopicExample example = new TopicExample();
		MybatisOrderByClauseUtil.generateOrderByClause(pageable.getSort()).ifPresent(orderByClause -> {
			example.setOrderByClause(orderByClause);
		});
		return topicMapper.selectByExample(example);
	}

	@Override
	public Topic createTopic(Topic topic) {
		topicMapper.insert(topic);
		return topic;
	}


	@Override
	public void updateTopic(Topic topic) {
		topicMapper.updateByPrimaryKeySelective(topic);
	}
	
}
