<%@page import="mail.vo.MailVO"%>
<%@page import="util.ConnectionFactory"%>
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
    1. 오라클의 t_mail 테이블에서 게시글 조회 ==> JAVA
    2. <html> 화면에 게시글 출력
--%>
<%  
    List<MailVO> mailList = new ArrayList<>();
    
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT MAIL_CD, MAIL_TITLE, MAIL_CONTENT, MAIL_SENDER_CD, MAIL_RECEIVER_CD, MAIL_SENT_DATE ");
    sql.append("FROM TBL_MAIL ");
    sql.append("ORDER BY MAIL_CD DESC ");
    try (
        Connection conn = new ConnectionFactory().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
    ) {
        while (rs.next()) {
            int mailCd = rs.getInt("MAIL_CD");
            String mailTitle = rs.getString("MAIL_TITLE");
            int mailSenderCd = rs.getInt("MAIL_SENDER_CD");
            String mailSentDate = rs.getString("MAIL_SENT_DATE");
            
            MailVO mail = new MailVO(mailCd, mailTitle, mailSenderCd, mailSentDate);
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
<link rel="stylesheet" href="/Mission-Web/resource/css/layout.css">
<link rel="stylesheet" href="/Mission-Web/resource/css/table.css">
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>     
    window.onload = function() {
        let btn = document.querySelector('#addBtn')
        btn.addEventListener('click', function() {
            location.href = 'writeForm.jsp'
        })
    }     
    
    function goDetail(mailCd) {
        <c:if test="${not empty userVO}">
            location.href = 'detail.jsp?mailCd=' + mailCd;
        </c:if>
        
        <c:if test="${empty userVO}">
            if (confirm('로그인 후 사용 가능합니다. 로그인 페이지로 이동하시겠습니까?')) {
                location.href = '/Mission-Web/login/login.jsp';
            }
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
                <c:forEach items="${mailList}" var="mail">
                    <tr>
                        <td>${mail.mailCd}</td>                        
                        <td> 
                            <a href="javascript:goDetail(${mail.mailCd})">
                                <c:out value="${mail.mailTitle}"/>
                            </a>                    
                        </td>
                        <td>${mail.mailSenderCd}</td>
                        <td>${mail.mailSentDate}</td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <c:if test="${not empty userVO}">
                <button id="addBtn">새글등록</button>
            </c:if> 
        </div>
    </section>
    <footer>
        <%@ include file="/include/footer.jsp"%>
    </footer>
</body>
</html>