package com.scalples.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.scalples.domain.City;

@Repository
public class CityDao {

	private final SqlSession sqlSession;

	public CityDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public City selectCityById(long id) {
		return this.sqlSession.selectOne("selectCityById", id);
	}

}
