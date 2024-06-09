<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/Mission-Web/resource/css/layout.css">
<link rel="stylesheet" href="/Mission-Web/resource/css/table.css">
<script src="/Mission-Web/resource/js/my.JS.js"></script>

<script>
 
    // 쿠키를 통한 아이디 저장
    /*
    function setCookie(name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    function checkSavedId() {
        var savedId = getCookie("savedUsername");
        if (savedId) {
            document.loginForm.id.value = savedId;
            document.getElementById("saveIDCheckbox").checked = true;
        }
    }

    function handleLoginSubmit(event) {
        var username = document.loginForm.id.value;
        var saveIdCheckbox = document.getElementById("saveIDCheckbox").checked;
        if (saveIdCheckbox) {
            setCookie("savedUsername", username, 30);
        } else {
            setCookie("savedUsername", "", -1);
        }
        return checkForm();
    }

    window.onload = function() {
        checkSavedId();
    }
    
    let checkForm = function() {
        let f = document.loginForm;
        if (isNull(f.id, '아이디를 입력하세요'))
            return false;
        if (isNull(f.password, '패스워드를 입력하세요'))
            return false;
        return true;
    }
    */
    
    // 세션을 통한 아이디 저장
    
    let checkForm = function() {
    	
    	let f = document.loginForm
    	
    	if(isNull(f.id, '아이디를 입력하세요'))
    		return false
    		
    	if(isNull(f.password, '패스워드를 입력하세요'))
    		return false
    		
    	return true
    }
    
    
    
</script>
</head>
<body>
      <header>
          <jsp:include page="/include/topMenu.jsp"/>
      </header>
      <section>
         <div align="center">
            <br>
            <hr>
            <h2>로그인</h2>
            <hr>
            <br>
            <form action="loginProcess.jsp" method="post" name="loginForm" onsubmit="return handleLoginSubmit(event)">
                <table style="width:40%">
                   <tr>
                       <th>ID</th>
                       <th>
                          <input type="text" name="id"><br>
                          <span id="saveID"><input type="checkbox" id="saveIDCheckbox"> 아이디저장</span>
                       </th>
                   </tr>
                   <tr>
                       <th>PASSWORD</th>
                       <th>
                          <input type="password" name="password">
                       </th>
                   </tr>
                   </table>
                   <button>로그인</button>
            </form>
         </div>
      </section>
      <footer>
         <%@ include file="/include/footer.jsp" %>
      </footer>
</body>
</html>