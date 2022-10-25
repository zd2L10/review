package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Review;
import com.example.app.mapper.ReviewMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	ReviewMapper RMapper;
	
	@Override
	public List<Review> getReviewList() throws Exception {
		return RMapper.selectAll();
	}

	@Override
	public Review getReviewById(Integer id) throws Exception {
		return RMapper.selectById(id);
	}

	@Override
	public void addReview(Review review) throws Exception {
		RMapper.insert(review);
	}

	@Override
	public void editReview(Review review) throws Exception {
		RMapper.update(review);
	}

	@Override
	public void deleteReview(Integer id) throws Exception {
		RMapper.delete(id);
	}

	@Override
	public void goodReview(Integer id) throws Exception {
		RMapper.good(id);
	}
	
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) RMapper.countReview();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Review> getReviewListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return RMapper.selectLimited(offset, numPerPage);
	}


}
