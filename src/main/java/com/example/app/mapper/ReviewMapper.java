package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Review;

@Mapper
public interface ReviewMapper {

	List<Review> selectAll() throws Exception;
	
	Review selectById(Integer id) throws Exception;
	
	void insert(Review review) throws Exception;
	
	void update(Review review) throws Exception;
	
	void delete(Integer id) throws Exception;
	
	void good(Integer id) throws Exception;
	
	Long countReview() throws Exception;
	
	List<Review> selectLimited(@Param("offset") int offset, @Param("limit") int limit) throws Exception;
}
