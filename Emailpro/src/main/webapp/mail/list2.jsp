<%@page import="util.ConnectionFactory"%>
<%@page import="mail.vo.MailVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--
     전체게시글 조회 순서
     1. 오라클의 t_board 테이블에서 게시글 조회 ==> JAVA
     2. <html> 화면에 게시글 출력
 --%>
<%  
     List<MailVO> mailList = new ArrayList<>();
     
     StringBuilder sql = new StringBuilder();
     sql.append("select mailTitle, mailContent, mailSenderCd, mailReceiverCd");
     sql.append("from tbl_mail ");
     sql.append("order by no desc ");
     try (
    	Connection conn = new ConnectionFactory().getConnection();
    	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
     ) {
    	ResultSet rs = pstmt.executeQuery(); 
    	
    	while(rs.next()){
    		String mailTitle = rs.getString("mailTitle");
    		String mailContent = rs.getString("mailContent");
    		int mailSenderCd = rs.getInt("mailSenderCd");
    		int mailReceiverCd = rs.getInt("mailReceiverCd");
    		
    		MailVO mail = new MailVO(mailTitle, mailContent, mailSenderCd, mailReceiverCd);
    		mailList.add(mail);
    	}
 
     } catch(Exception e) {
        e.printStackTrace();
     }
     
     // 공유영역 등록
     pageContext.setAttribute("mailList", mailList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="/Emailpro/resource/css/layout.css">
<link rel="stylesheet" href="/Emailpro/resource/css/table.css">
<script src="http://code.jquery.com/jquery.3.7.1.min.js"></script>
<script>     
    window.onload = function() {
    	let btn = document.querySelector('#addBtn')
    	btn.addEventListener('click', function() {
    		location.href = 'writeForm.jsp'
    	})
    }     
    
    let goDetail = function(boardNo) {
    	<c:if test="${ not empty MemberVO }">
    	   location.href = 'detail.jsp?no='+ memberNo
    	</c:if>
    	   
    	<c:if test="${ empty MemberVO }">
    	   if(confirm('로그인 후 사용가능합니다\n로그인페이지로 이동하시겠습니까?'))
    	   location.href = '/Mission-Web/login/login.jsp'
    	</c:if>
    }
    
</script>
</head>
<body>
	<header>
		<jsp:include page="/include/topMenu.jsp" />
	</header>
	<section>
		<div align="center">
		    <br>
			<h2>전체게시글 조회</h2>			
			<table border="1" style="width: 100%;" class="listodd">
				<tr>
					<th width="7%">번호</th>
					<th>제목</th>
					<th width="17%">작성자</th>
					<th width="20%">등록일</th>
				</tr>
				<c:forEach items="${ mailList }" var="board">
					<tr>
						<td>${ mail.no }</td>						
						<td> 
						     <a href="javascript:goDetail(${ mail.no })">
						         <c:out value="${ bmail.title }"/>
						     </a>					
						</td>
						<td>${ mail.writer }</td>
						<td>${ mail.regDate }</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<c:if test="${ not empty MemberVO }">
			   <button id="addBtn">새글등록</button>
			</c:if> 
		</div>
	</section>
	<footer>
		<%@ include file="/include/footer.jsp"%>
	</footer>
</body>
</html>