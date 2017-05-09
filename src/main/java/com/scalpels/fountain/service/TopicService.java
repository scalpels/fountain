package com.scalpels.fountain.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.scalpels.fountain.model.Topic;

public interface TopicService {

	Topic createTopic(Topic topic);
	
	void updateTopic(Topic topic);
	
	Topic getTopicById(Long id);

	void deleteTopicById(Long id);
	
	List<Topic> getTopicList(Pageable pageable);
}
