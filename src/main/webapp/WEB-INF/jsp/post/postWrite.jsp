<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center my-4">
	<div class="contents-box">
	
		<h4 class="font-weight-bold my-3">타임라인 포스팅</h4>

		<%-- 제목, 파일, 내용 박스 --%>
		<div class="my-3">
			<%-- 제목 박스 --%>
			<input type="text" id="post-title" class="form-control" placeholder="제목을 입력해주세요.">
			
			<%-- 파일 박스 --%>
			<div class="my-3">
				<input class="d-none" type="file" id="post-file-btn" accept=".jpg, .png, .jpeg, .gif">
				<button type="button" id="fileUploadBtn" class="btn">사진 올리기</button>
				<small>이미지는 최대 3장까지 가능합니다.</small>
				<div id="fileList"></div>
			</div>
			
			<%-- 내용 박스 --%>
			<textarea rows="10" id="post-subject"  class="form-control" placeholder="내용을 입력해주세요."></textarea>
		</div>
		
		
		
		<%-- 버튼 --%>
		<div class="d-flex justify-content-end">
			<button type="button" class="btn-success btn" id="post-upload-btn">업로드</button>
		</div>
	</div>
</div>
<script>
	let fileNo = 0;
	let filesArr = [];
	
	// 파일 삭제
	function deleteFile(i) {
		$('#post-file-btn' + i).remove();
		delete filesArr[i];
	}
	
</script>