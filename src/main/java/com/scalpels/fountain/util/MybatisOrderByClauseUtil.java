package com.scalpels.fountain.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class MybatisOrderByClauseUtil {
	public static String toLowUnderscore(String words) {
		String[] splitedWords = words.split("(?=\\p{Upper})");
		String lowerUnderscoreWord = Arrays.stream(splitedWords).map(word -> word.toLowerCase())
				.collect(Collectors.joining("_"));
		return lowerUnderscoreWord;
	}

	public static Optional<String> generateOrderByClause(Sort sort) {

		int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;
		Spliterator<Order> spliterator = Spliterators.spliteratorUnknownSize(sort.iterator(), characteristics);

		Stream<Order> orderStream = StreamSupport.stream(spliterator, false);

		String orderByClause = orderStream
				.map(order -> toLowUnderscore(order.getProperty()) + " " + order.getDirection())
				.collect(Collectors.joining(","));

		return Optional.of(orderByClause);
	}
}
