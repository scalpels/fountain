package com.scalpels.fountain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.scalpels.fountain.config.redis.ScalpelsCacheable;
import com.scalpels.fountain.mapper.TopicMapper;
import com.scalpels.fountain.model.Topic;
import com.scalpels.fountain.model.TopicExample;
import com.scalpels.fountain.util.MybatisOrderByClauseUtil;

@Service
public class TopicServiceHandler implements TopicService {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private TopicMapper topicMapper;
	
	@Override
//	@Cacheable(value = "topic", keyGenerator = "keyGenerator")
	@ScalpelsCacheable(value="nonumber1989",key="#id")
	public Topic getTopicById(Long id) {
		logger.info("get from db {}",id);
		return topicMapper.selectByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value = "topic", keyGenerator = "keyGenerator")
	public void deleteTopicById(Long id) {
		topicMapper.deleteByPrimaryKey(id);
	}

	@Override	
	@ScalpelsCacheable(value="nonumber1989",key="nonumber1989")
	public List<Topic> getTopicList(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		TopicExample example = new TopicExample();
		MybatisOrderByClauseUtil.generateOrderByClause(pageable.getSort()).ifPresent(orderByClause -> {
			example.setOrderByClause(orderByClause);
		});
		return topicMapper.selectByExample(example);
	}

	@Override
	@CachePut(value = "topic", keyGenerator = "keyGenerator")
	public Topic createTopic(Topic topic) {
		topicMapper.insert(topic);
		return topic;
	}


	@Override
//	@CachePut(value = "topic", keyGenerator = "keyGenerator")
	@ScalpelsCacheable(value="nonumber1989",key="#topic.id")
	public void updateTopic(Topic topic) {
		topicMapper.updateByPrimaryKeySelective(topic);
	}
	
}
