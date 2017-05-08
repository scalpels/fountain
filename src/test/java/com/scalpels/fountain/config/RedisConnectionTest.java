package com.scalpels.fountain.config;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scalpels.fountain.FountainApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FountainApplication.class)
public class RedisConnectionTest {
  @Resource
  private RedisTemplate<String,String> redisTemplate;
  @Test
  public void testSetAndGet() {
    redisTemplate.opsForValue().set("ping","pong");
    System.out.println(redisTemplate.opsForValue().get("ping"));
  }
}