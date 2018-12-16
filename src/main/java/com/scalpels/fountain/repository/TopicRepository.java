package com.scalpels.fountain.repository;

import com.scalpels.fountain.entity.Topic;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
