package com.scalpels.fountain.resource;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalpels.fountain.model.Topic;
import com.scalpels.fountain.resource.entity.TopicEntity;
import com.scalpels.fountain.service.TopicService;

@RestController
@RequestMapping("/topics")
public class TopicController {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private TopicService topicService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public List<Topic> getTopicList(@PageableDefault(page = 1, size = 10,sort = { "lastModifiedOn" }, direction = Direction.DESC) Pageable pageable) {
		logger.info("pageable information : {} {}",pageable.getPageNumber(),pageable.getOffset());
		return topicService.getTopicList(pageable);
	}
	@Secured("ROLE_USER")
	@PostMapping
	public TopicEntity createTopic(@Valid @RequestBody TopicEntity topicEntity,Principal principal) {
		logger.info("principal information : {}",principal.getName());
		topicEntity.setCreatedBy(principal.getName());
		Topic topic = objectMapper.convertValue(topicEntity, Topic.class);
		return objectMapper.convertValue(topicService.createTopic(topic), TopicEntity.class) ;
	}

	@GetMapping(value = "/{id}")
	public Topic getTopicById(@PathVariable Long id) {
		logger.info("get topic id: {}",id);
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
