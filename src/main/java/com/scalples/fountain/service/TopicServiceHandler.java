package com.scalples.fountain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scalples.fountain.mapper.TopicMapper;
import com.scalples.fountain.model.Topic;
import com.scalples.fountain.model.TopicExample;

@Service
public class TopicServiceHandler implements TopicService {
	@Autowired
	private TopicMapper topicMapper;
	
	@Override
	public Topic getTopicById(Long id) {
		return topicMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteTopicById(Long id) {
		topicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Topic> getTopicList() {
		TopicExample example = new TopicExample();
		return topicMapper.selectByExample(example);
	}

	@Override
	public Topic createTopic(Topic topic) {
		topicMapper.insertSelective(topic);
		return topic;
	}

	@Override
	public void updateTopic(Topic topic) {
		topicMapper.updateByPrimaryKeySelective(topic);
	}
	
}
