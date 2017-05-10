package com.scalpels.fountain.service;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.scalpels.fountain.mapper.TopicMapper;
import com.scalpels.fountain.model.Topic;
import com.scalpels.fountain.model.TopicExample;

@Service
public class TopicServiceHandler implements TopicService {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private TopicMapper topicMapper;
	
	@Override
	@Cacheable(value = "topic", keyGenerator = "keyGenerator")
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
//		Iterator<Order> orders = ;
		pageable.getSort().iterator().forEachRemaining(order -> {
			example.setOrderByClause(order.getProperty()+" "+order.getDirection());
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
