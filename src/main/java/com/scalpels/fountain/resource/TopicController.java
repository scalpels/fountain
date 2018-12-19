package com.scalpels.fountain.resource;

import com.scalpels.fountain.entity.Topic;
import com.scalpels.fountain.resource.model.TopicModel;
import com.scalpels.fountain.service.TopicService;
import com.scalpels.fountain.util.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private TopicService topicService;

	@Autowired
	private BeanMapper beanMapper;


//	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public List<Topic> getTopicList(@PageableDefault(page = 1, size = 10,sort = { "lastModifiedOn" }, direction = Direction.DESC) Pageable pageable) {
		logger.info("pageable information : {} {}",pageable.getPageNumber(),pageable.getOffset());
		return topicService.getTopicList(pageable);
	}
	@Secured("ROLE_USER")
	@PostMapping
	public TopicModel createTopic(@Valid @RequestBody TopicModel topicEntity, Principal principal) {
		logger.info("principal information : {}",principal.getName());
		topicEntity.setCreatedBy(principal.getName());
		Topic topic = beanMapper.map(topicEntity,Topic.class);
		return beanMapper.map(topicService.createTopic(topic), TopicModel.class);
	}

	@GetMapping(value = "/{id}")
	public TopicModel getTopicById(@PathVariable Long id) {
		logger.info("get topic id: {}",id);
		return beanMapper.map(topicService.getTopicById(id), TopicModel.class);
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
