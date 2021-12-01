package homeMypage;


import java.util.List;

import member.WebMemberVO;

public interface HomeMyPageService {
	//회원 정보 수정
	int home_member_update(WebMemberVO vo);
	//내 회사 불러오기
	List<HomeCompanyVO> company_list(String customer_email);
	//회사 삭제
	void company_delete(int store_number);
	//회원 정보 리스트로 불러오기
	List<WebMemberVO> customer_list();
	//공지글 목록 조회 - 페이지 처리된
	CustomerPage customer_list(CustomerPage page);
	//회원정보 하나 불러오기
	WebMemberVO home_member_select(String customer_email);
	//신규 가게 등록
	int company_insert(HomeStoreVO vo);
	
	//가게 수정
	void company_update(HomeStoreVO vo);
	
	//가게 id를 기준으로 회사 정보 조회
	HomeStoreVO companyId_list(int store_number);
	//회원 정보 삭제
	void home_member_delete(String customer_email);
	//가게 이미지 조회
	List<HomeStoreVO> company_img(int store_number);
}
