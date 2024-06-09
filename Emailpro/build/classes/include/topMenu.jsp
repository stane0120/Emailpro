<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="1" style="width: 100%">
    <tr>
       <td rowspan="2">
            <div style="height:40px; width:200px; text-align: center;">
                <a href="/Mission-Web" style="display: block; height: 100%; width: 100%;">LOGO</a>
            </div>
       </td>
       <td align="right">
           <c:if test="${ not empty userVO }">
               [${ userVO.NAME }(${ userVO.ID }) 님으로 로그인중...]
           </c:if>
           <c:if test="${ empty userVO }">
               GUEST 입니다.
           </c:if>
       </td>
    </tr>
    <tr>
	   <td>    
	       <c:if test="${ userVO.TYPE eq '1' }">
	           회원관리 | 
	       </c:if>
	       <a href="/Mission-Web/board/list2.jsp">게시판 </a> | 
	       <c:choose>
	           <c:when test="${ empty userVO }">
	           회원가입 | 
	           <a href="/Mission-Web/login/login.jsp">로그인 </a> | 
	           </c:when>
	           <c:otherwise>
	           마이페이지 | 
	           <a href="/Mission-Web/login/logout.jsp">로그아웃 </a>
	           </c:otherwise>
	       </c:choose>
       </td>
    </tr>  
</table>