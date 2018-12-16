package com.scalpels.fountain.service;

import java.util.List;

import com.scalpels.fountain.entity.Topic;
import org.springframework.data.domain.Pageable;

public interface TopicService {

	Topic createTopic(Topic topic);
	
	void updateTopic(Topic topic);
	
	Topic getTopicById(Long id);

	void deleteTopicById(Long id);
	
	List<Topic> getTopicList(Pageable pageable);
}
