package com.scalples.fountain.service;

import java.util.List;

import com.scalples.fountain.domain.Topic;

public interface TopicService {

	Topic createTopic(Topic topic);
	
	void updateTopic(Topic topic);
	
	Topic getTopicById(Long id);

	void deleteTopicById(Long id);
	
	List<Topic> getTopicList();
}
