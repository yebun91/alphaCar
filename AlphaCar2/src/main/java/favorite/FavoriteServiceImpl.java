package favorite;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired private FavoriteDAO dao;
	
	@Override
	public List<FavoriteCustomerVO> favorite_search(String customer_email) {
		
		return dao.favorite_search(customer_email);
	}

	
	@Override
	public int favorite_insert(FavoriteCustomerVO vo) {
		// TODO Auto-generated method stub
		return dao.favorite_insert(vo);
	}


	@Override
	public int favorite_delete(int fav_number) {
		// TODO Auto-generated method stub
		return dao.favorite_delete(fav_number);
	}
	

}