<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 메뉴부분 --%>
<div class="col-2 inventory">
   <ul class="nav nav-fill align-items-start py-3">
   	  <li class="nav-item w-100 py-3"><a href="/user/mypage_view" class="nav-link font-weight-bold">My Page</a></li>
      <li class="nav-item w-100 py-3"><a href="/user/timeline_view?userId=${userId }" class="nav-link font-weight-bold">내 타임라인</a></li>
      <li class="nav-item w-100 py-3"><a href="/user/save_view" class="nav-link font-weight-bold">저장한 글</a></li>
   </ul>
</div>