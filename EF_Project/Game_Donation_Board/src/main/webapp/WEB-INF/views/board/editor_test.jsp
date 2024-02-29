<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>

    <!-- 부트스트랩 3.4.1 -->
    <!-- Latest compiled and minified CSS -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css"
    />

    <!-- Optional theme -->
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap-theme.min.css"
    />

    <!-- Latest compiled and minified JavaScript -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"
      integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
      crossorigin="anonymous"
    ></script>

    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
    <script src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
    <script src="../../../resources/js/summernote-lite.js"></script>
    <script src="../../../resources/js/summernote-ko-KR.js"></script>
    <link
      href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="../../../resources/css/summernote-lite.css" />

    <script>
      $(document).ready(function () {
        // 썸머노트 설정
        var setting = {};

        $("#summernote").summernote({
          height: 700, // 에디터 높이
          minHeight: null, // 최소 높이
          maxHeight: null, // 최대 높이
          focus: true, // 에디터 로딩후 포커스를 맞출지 여부
          lang: "ko-KR", // 한글 설정
          placeholder: "최대 2048자까지 쓸 수 있습니다", //placeholder 설정
          //콜백함수
          callbacks: {
            onImageUpload: function (files, editor, welEditable) {
              //파일 다중 업로드
              for (var i = files.length - 1; i >= 0; i--) {
                uploadSummernoteImageFile(files[i], this);
              }
            },

            onMediaDelete: function ($target, editor, $editable) {
              var deletedImageUrl = $target.attr("src").split("/").pop();
              deleteSummernoteImageFile(deletedImageUrl);
            },
          },
        });
        // 이미지,파일 업로드 함수
        function uploadSummernoteImageFile(file, el) {
          data = new FormData();
          data.append("file", file);

          $.ajax({
            data: data,
            type: "POST",
            url: "uploadSummernoteImageFile",
            contentType: false,
            enctype: "multipart/form-data",
            processData: false,
            success: function (data) {
              console.log(data.url);
              $(el).summernote("editor.insertImage", data.url);
            },
          });
        }
        // 이미지,파일 삭제 함수
        function deleteSummernoteImageFile(imageName) {
          data = new FormData();
          data.append("file", imageName);
          $.ajax({
            data: data,
            type: "POST",
            url: "deleteSummernoteImageFile",
            contentType: false,
            enctype: "multipart/form-data",
            processData: false,
          });
        }
      });
      //작성완료 함수
      function send(f) {
        let content = f.editordata.value;

        if (content == "") {
          alert("내용을 입력해주세요");
          return;
        }

        f.action = "summernote_send";
        f.submit();
      }
    </script>
  </head>
  <body>
    <form method="post" enctype="multipart/form-data">
      <textarea id="summernote" name="editordata"></textarea>
      <input type="button" value="작성 완료" onclick="send(this.form)" />
    </form>
  </body>
</html>
