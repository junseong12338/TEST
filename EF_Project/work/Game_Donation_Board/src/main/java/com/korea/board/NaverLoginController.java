package com.korea.board;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import dto.UserDTO;
import lombok.RequiredArgsConstructor;
import service.NaverLoginService;
import service.UserService;
import util.Common;

@Controller
@RequiredArgsConstructor
public class NaverLoginController {
	
	final UserService userService;
	/* naverLoginService */
	final NaverLoginService naverLoginService;
	// 토큰을 통해 값을 저장할 사용자 정보
	private String apiResult = null;
		
	// 로그인페이지
	// 로그인 첫 화면 요청 메소드
	
	
	@RequestMapping(value = "login_form", method = { RequestMethod.GET, RequestMethod.POST })
	public String login_form(Model model, HttpSession session) {
		/* 네아로 인증 URL을 생성하기 위하여 naverLoginService클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginService.getAuthorizationUrl(session);
		/* 인증요청문 확인 */
		System.out.println("네이버:" + naverAuthUrl);
		/* 객체 바인딩 */
		model.addAttribute("urlNaver", naverAuthUrl);

		/* 생성한 인증 URL을 View로 전달 */
		return Common.User.VIEW_PATH + "login_form.jsp";
	}
	
	//네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/callbackNaver.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String callbackNaver(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws Exception {
		System.out.println("로그인 성공 callbackNaver");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginService.getAccessToken(session, code, state); // 토큰 
        //로그인 사용자 정보를 읽어온다.
	    apiResult = naverLoginService.getUserProfile(oauthToken);
	    
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj;
		
		jsonObj = (JSONObject) jsonParser.parse(apiResult);
		JSONObject response_obj = (JSONObject) jsonObj.get("response");			
		// 프로필 조회
		
		String email = (String) response_obj.get("email");
		String name = (String) response_obj.get("name");
		

		UserDTO dto = userService.checkEmail(email);
		// DB에 정보가 없으면 자동으로 회원 가입 
		// 비밀번호는 NULL 유효성 처리로 일반 로그인 불가 + 중복 생성 방지
		if(dto == null) {
			dto = new UserDTO();
			dto.setUser_email(email);
			dto.setUser_name(name);
			userService.userInsert(dto);

		}
		// 사용자 정보
		session.setAttribute("user_email", dto);
		
	    // 난수 인증 세션 삭제
	    session.removeAttribute(state);

        /* 네이버 로그인 성공 페이지 View 호출 */
		return "redirect:board_list";
	}
  	
	// logout -> UserLoginController
}
