package com.scalples.fountain.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.scalples.fountain.model.Topic;

@Repository
public class TopicRepository {

	private final SqlSession sqlSession;

	public TopicRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Topic selectById(long id) {
		return this.sqlSession.selectOne("selectById", id);
	}

}
