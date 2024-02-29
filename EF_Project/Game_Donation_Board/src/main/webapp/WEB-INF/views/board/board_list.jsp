<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<meta charset="UTF-8">
<head>
<title>Insert title here</title>
<style>
	a{text-decoration: none;}
	table{
		border-collapse: collapse;
		margin:0 auto;
	}
	
	img{
		cursor: pointer;
	}
</style>
</head>
<body>

	 <table border="1">
		<tr>
			<td colspan = "5" align="right">
			<c:choose>
				<c:when test="${empty user_email }">
					<input type ="button" value="로그인" onclick="location.href='login_form'">
				</c:when>
				<c:when test="${not empty user_email }">
					<input type ="button" value="로그아웃" onclick="location.href='logout'">
				</c:when>
			</c:choose>
			</td>
		</tr>
	</table> 
  <input
  type="button"
  onclick="location.href='editor_test'"
  value="에디터 페이지 이동"
/>
<form action="project_test" method="get">
  <input type="text" name="idx" placeholder="글번호" />
  <input type="submit" value="글보기" />
</form>

    <input
      type="button"
      onclick="location.href='editor_test'"
      value="에디터 페이지 이동"
    />
    <form action="project_test" method="get">
      <input type="text" name="idx" placeholder="글번호" />
      <input type="submit" value="글보기" />
    </form>
</body>


  
  
 