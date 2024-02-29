<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
  </head>
  <body>
    <h1>글 번호</h1>
    <div>${dto.project_idx}</div>
    <h1>글 내용</h1>
    <div>${dto.project_content}</div>
    <input
      type="button"
      value="돌아가기"
      onnclick="location.href='board_list'"
    />
  </body>
</html>
