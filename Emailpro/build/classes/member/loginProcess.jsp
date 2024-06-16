<%@page import="member.dao.MemberDAO"%>
<%@page import="member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
    request.setCharacterEncoding("utf-8");

    String id = request.getParameter("id");
    String password = request.getParameter("password");
    
    System.out.println("id : " + id);
    System.out.println("password : " + password);
    
    MemberVO loginVO = new MemberVO();
    loginVO.setMemberId(id);
    loginVO.setMemberPw(password);
    
    MemberDAO memberDao = new MemberDAO();
    MemberVO userVO = memberDao.login(loginVO);

    String url = "";
    String msg = "";
    if(userVO == null){
        // 로그인 실패
        url = "login.jsp";
        msg = "아이디 또는 패스워드를 잘못 입력하셨습니다";
    } else {
        // 로그인 성공
        url = "/Mission-Web";
        msg = userVO.getMemberNm() + "님, 로그인 성공";

        // 세션에 등록
        session.setAttribute("userVO", userVO);
    }
    
    pageContext.setAttribute("url", url);
    pageContext.setAttribute("msg", msg);
%>   
<script>
     alert('${ msg }');
     location.href = '${ url }';
</script>