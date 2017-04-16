package com.scalples.fountain.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.scalples.fountain.domain.City;

@Repository
public class CityRepository {

	private final SqlSession sqlSession;

	public CityRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public City selectCityById(long id) {
		return this.sqlSession.selectOne("selectCityById", id);
	}

}
