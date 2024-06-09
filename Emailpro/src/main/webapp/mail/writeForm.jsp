<%@page import="member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    MemberVO memberVO = new MemberVO();

    session.setAttribute("memberVO", memberVO);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/Mission-Web/resource/css/layout.css">
<link rel="stylesheet" href="/Mission-Web/resource/css/table.css">
<script>
let isNull = function(obj, msg) {
	if(obj.value == '') {
		alert(msg)
		obj.focus()
		return true
	}
	return false
}

let checkForm = function() {
	
	let f = document.inputForm
	
	if(f.title.value == '') {
		alert('제목을 입력해주세요')
		f.title.focus()
		return false
	}
	
	/* if(isNull(f.writer, '작성자를 입력해주세요'))
		 return false
	 */
	if(isNull(f.content, '내용을 입력해주세요')) 
		return false       
        
        //파일 확장자 체크
        if(checkExt(f.attachfile1))
        	return false
        if(checkExt(f.attachfile2))
        	return false
        
        return true
    }
    
    let checkExt = function(obj) {
    	let forbidName = ['exe', 'bat', 'java', 'class', 'jsp']
    	let fileName = obj.value         
    	let ext = fileName.substring(fileName.lastIndexOf('.')+1)
    	
    	for(let forbid of forbidName) {
    		if(forbid == ext){
    			alert(`[${ext}] 확장자는 파일 업로드 정책에 위배됩니다`)
    			return true
    		}
    	}
		return false
    }
</script>
</head>
<body>
	<header>
		<jsp:include page="/include/topMenu.jsp" />
	</header>
	<section>
		<div align="center">
			<h2>새글등록폼</h2>
			<form name="inputform" action="write.jsp" method="post"
				enctype="multipart/form-data" onsubmit="return checkForm()">
				<input type="hidden" name="writer" value="${mailVO.ID}">
				<table style="width: 80%" border="1">
					<tr>
						<th width="25%">제목</th>
						<td><input type="text" name="title" size="80"></td>
					</tr>
					<tr>
						<th width="25%">작성자</th>
						<td><c:out value="${ mailVO.ID }" /></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea rows="7" name="content" cols="80"></textarea></td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td><input type="file" name="attachfile1"><br> <input
							type="file" name="attachfile2"><br></td>
					</tr>
				</table>
				<br>
				<button type="submit">등록</button>
			</form>
		</div>
	</section>
	<footer>
		<%@ include file="/include/footer.jsp"%>
	</footer>
</body>
</html>