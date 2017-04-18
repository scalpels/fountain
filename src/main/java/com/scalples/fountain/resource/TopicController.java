package com.scalples.fountain.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalples.fountain.model.Topic;
import com.scalples.fountain.resource.entity.TopicEntity;
import com.scalples.fountain.service.TopicService;

@RestController
@RequestMapping("/topics")
public class TopicController {
	@Autowired
	private TopicService topicService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping
	public List<Topic> getTopicList() {
		return topicService.getTopicList();
	}

	@PostMapping
	public TopicEntity createTopic(@Valid @RequestBody TopicEntity topicEntity) {
		Topic topic = objectMapper.convertValue(topicEntity, Topic.class);
		return objectMapper.convertValue(topicService.createTopic(topic), TopicEntity.class) ;
	}

	@GetMapping(value = "/{id}")
	public Topic getTopicById(@PathVariable Long id) {
		return topicService.getTopicById(id);
	}

	@PutMapping(value = "/{id}")
	public void updateTopicById(@PathVariable Long id, @Valid @RequestBody Topic topic) {
		topicService.updateTopic(topic);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteTopic(@PathVariable Long id) {
		topicService.deleteTopicById(id);
	}

}
