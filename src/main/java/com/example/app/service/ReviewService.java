package com.example.app.service;

import java.util.List;

import com.example.app.domain.Review;

public interface ReviewService {
	// 全件取得リスト
	List<Review> getReviewList() throws Exception;
	
	// idを元にデータを1件取得
	Review getReviewById(Integer id) throws Exception;
	
	// データ追加・更新・削除
	void addReview(Review review) throws Exception;
	
	void editReview(Review review) throws Exception;
	 
	void deleteReview(Integer id) throws Exception;
	
	void goodReview(Integer id) throws Exception;
	
	// 合計件数
	int getTotalPages(int numPerPage)throws Exception;
		
	// ページ分割
	List<Review> getReviewListByPage(int page, int numPerPage)throws Exception;
}
