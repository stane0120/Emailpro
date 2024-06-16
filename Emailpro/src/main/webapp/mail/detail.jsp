<%@ page import="mail.vo.MailVO" %>
<%@ page import="mail.dao.MailDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // 1. 파라미터 추출 (메일 번호)
    int mailCd = Integer.parseInt(request.getParameter("mailCd"));

    // 2. 추출된 번호에 해당하는 메일 조회 (t_mail)
    MailDAO mailDao = new MailDAO();
    MailVO mail = mailDao.selectByMailCd(mailCd); // 메서드 이름은 selectByMailCd로 가정

    // 2-1. 공유영역에 메일 등록
    pageContext.setAttribute("mail", mail);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상세메일</title>
    <link rel="stylesheet" href="/Mission-Web/resource/css/layout.css">
    <link rel="stylesheet" href="/Mission-Web/resource/css/table.css">
    <script>
        function doAction(type) {
            switch(type) {
                case 'U':
                    location.href = "update.jsp?mailCd=" + ${ mail.getMailCd() };
                    break;
                case 'D':
                    if (confirm('정말 삭제하시겠습니까?')) {
                        location.href = "delete.jsp?mailCd=" + ${ mail.getMailCd() };
                    }
                    break;
                case 'L':
                    location.href = "list.jsp";
                    break;
                default:
                    alert('지원하지 않는 액션입니다.');
            }
        }
    </script>
</head>
<body>
    <header>
        <jsp:include page="/include/topMenu.jsp" />
    </header>
    <section>
        <div align="center">
            <h2>상세메일</h2>
            <br>
            <table style="width: 100%" border="1">
                <tr>
                    <th width="25%">번호</th>
                    <td>${ mail.getMailCd() }</td>
                </tr>
                <tr>
                    <th width="25%">제목</th>
                    <td>${ mail.getMailTitle() }</td>
                </tr>
                <tr>
                    <th width="25%">작성자</th>
                    <td>${ mail.getMailSenderCd() }</td>
                </tr>
                <tr>
                    <th width="25%">내용</th>
                    <td>${ mail.getMailContent() }</td>
                </tr>
                <tr>
                    <th width="25%">조회수</th>
                    <td>${ mail.getMailOpenChk() }</td>
                </tr>
                <tr>
                    <th width="25%">등록일</th>
                    <td>${ mail.getMailSentDate() }</td>
                </tr>
            </table>
            <br>
            <button onclick="doAction('U')">수 정</button>
            <button onclick="doAction('D')">삭 제</button>
            <button onclick="doAction('L')">목 록</button>
        </div>
    </section>
    <footer>
        <%@ include file="/include/footer.jsp" %>
    </footer>
</body>
</html>