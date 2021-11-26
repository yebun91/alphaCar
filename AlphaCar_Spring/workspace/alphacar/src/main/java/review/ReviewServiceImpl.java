package review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired private ReviewDAO dao;
	
	@Override
	public int review_insert(ReviewVO vo) {
		return dao.review_insert(vo);
	}

	@Override
	public ReviewVO review_select(int review_id) {
		return dao.review_select(review_id);
	}
	
	
	@Override
	public List<ReviewVO> review_list(String customer_email) {
		// TODO Auto-generated method stub
		return dao.review_list(customer_email);
	}

}