<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex flex-column align-items-center">
	<div class="box pl-3">
		<!-- 회원가입 텍스트 -->
		<h3 class="font-weight-bold mt-5">회원가입</h3>

		<div class="joinBox">
				<div class="p-3">
					<!-- id 박스 -->
					<div class="d-flex mb-3">
						<input type="text" id="signUp-loginId" name="signUp-loginId" class="form-control col-12 mr-3" placeholder="ID를 입력해주세요 (4자 이상)">
						<br>
					</div>
					
					<%-- 아이디 체크 결과 --%>
					<%-- d-none 클래스: display none (보이지 않게) --%>
					<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
					<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
					<div id="idCheckOk" class="small text-success d-none">사용 가능한ID 입니다.</div>
						
					<!-- nickName 박스 -->
					<input type="text" id="signUp-nickName" name="signUp-nickName" class="form-control col-12 mr-3 mb-3" placeholder="닉네임을 입력해주세요.(2자 이상)">
					<div id="nickNameCheckLength" class="small text-danger d-none">닉네임을 2자 이상 입력해주세요.</div>
					
					<!-- password 박스 -->
					<div class="mb-3">
						<input type="password" id="signUp-password" name="signUp-password" class="form-control col-12 mr-3" placeholder="비밀번호를 입력해주세요. (8자 이상)">
						<small id="password-warning">숫자/영문/특수문자를 모두 포함해야 합니다.</small>
					</div>
					
					<!-- 이메일 박스 -->
					<input type="text" id="signUp-email" name="signUp-email" class="form-control col-12 mr-3 mb-3" placeholder="ex) abc@naver.com">
					<div id="emailCheckLength" class="small text-danger d-none">이메일 형식으로만 사용이 가능합니다.</div>

					<!-- 성별 박스 -->
					<select class="form-control col-12 mr-3 mb-3" id="gender" name="gender">
			            <option id="female" value="female">여성</option>
			            <option id="male" value="male">남성</option>
			        </select>
			        
			        <!-- 연령대 박스 -->
					<select class="form-control col-12 mr-3 mb-2" id="age" name="age">
			            <option value="teenageMore">10 ~ 20대</option>
			            <option value="thirtyMore">30 ~ 40대</option>
			        </select>

					<!-- 회원가입 버튼 -->
					<div class="d-flex justify-content-center pt-3">
						<button type="button" class="btn btn-primary w-100" id="signUpBtn">가입하기</button>
					</div>
				</div>

		</div>

	</div>
</div>
