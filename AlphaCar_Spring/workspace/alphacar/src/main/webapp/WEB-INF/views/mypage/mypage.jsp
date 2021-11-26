<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="mypage_image">
    </div>
    <div class="mypage_select">
      <div class="mypage_userinfo">
        <a href=""><p class="mypage_userinfo_select">회원정보</p></a>
        <a href=""><p>보안설정</p></a>
      </div>
    </div>
  </nav>
  <!-- 메인 시작 -->
  <main class="mypage">
    <div id="page">
      <div class="mypage_boxs">
        <div class="mypage_box mypage_info_update">
          <div>
            <h1>회원정보수정</h1>
            <div class="mypage_userinfo_view">
              <div class="mypage_user_image">
                <img src="" alt="">
              </div>
              <div class="mypage_user_name">
                <h3>이름</h3>
                <h3>알파카</h3>
              </div>
            </div>
          </div>
          <button onclick="location.href='memberUpdate.mp'">수정하기</button>
        </div>
        <div class="mypage_box mypage_contact">
          <h1>1:1 문의 내역</h1>
          <div class="mypage_content">
            <p>알파카 이용시 궁금하셨던 내용,</p>
            <p>1:1 문의 내역을 편하게 이용해 보세요.</p>
          </div>
          <button onclick="location.href='memberContact.mp'">보러가기</button>
        </div>
        <div class="mypage_box mypage_contact">
          <h1>내 가게 정보</h1>
          <div class="mypage_content">
            <p>내가 저장한 가게의 정보를 확인하거나</p>
            <p>관리할 수 있습니다.</p>
          </div>
          <button onclick="location.href='memberCompany.mp'">보러가기</button>
        </div>
        <div class="mypage_box mypage_contact">
          <h1>전체 회원 관리</h1>
          <div class="mypage_content">
            <p>알파카에 가입한 모든 회원의 정보를</p>
            <p>검색하고 수정할 수 있습니다.</p>
          </div>
          <button onclick="location.href='masterMemberList.mp'">수정하기</button>
        </div>
        <div class="mypage_box mypage_contact">
          <h1>1:1 문의 처리</h1>
          <div class="mypage_content">
            <p>회원이 작성한 문의내용을</p>
            <p>답변할 수 있습니다.</p>
          </div>
          <button onclick="location.href='masterContact.mp'">처리하기</button>
        </div>
      </div>
      <div class="user_delete">
        <p>더 이상 알파카를 이용하지 않는다면 </p>
        <button>회원탈퇴 바로가기</button>
      </div>
    </div>
    
  </main>