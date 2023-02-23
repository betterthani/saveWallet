<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 메뉴부분 --%>
<div class="col-2 inventory">
   <ul class="nav nav-fill align-items-start py-3">
      <li class="nav-item w-100 py-3"><a href="/calendar" class="nav-link font-weight-bold">Monthly</a></li>
      <li class="nav-item w-100 py-3"><a href="/product/shopping_list_view" class="nav-link font-weight-bold">Shopping List</a></li>
      <li class="nav-item w-100 py-3"><a href="/product/wish_list_view" class="nav-link font-weight-bold">Wish List</a></li>
      <li class="nav-item w-100 py-3"><a href="/exchangeRate/paste_view" class="nav-link font-weight-bold">환율 조회</a></li>
      <li class="nav-item w-100 py-3"><a href="/post/timeline_view" class="nav-link font-weight-bold">이거 어때?</a></li>
      <li class="nav-item w-100 py-3"><a href="/user/in/timeline_view?userId=${userId}" class="nav-link font-weight-bold">My Page</a></li>
   </ul>
</div>