package com.korea.board;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.UserDTO;
import lombok.RequiredArgsConstructor;
import service.UserService;

@Controller
@RequiredArgsConstructor
public class UserLoginController {
	final UserService userService;
	
	@Autowired
	HttpSession session;

	// 로그인 
	@RequestMapping("login")
	@ResponseBody
	public String login(String user_email, String user_pw) {
	
		UserDTO dto = userService.checkEmail(user_email);
		// 이메일 유무 확인 
		if (dto == null) return "[{\"param\":\"no_email\"}]";
		if (!dto.getUser_pw().equals(user_pw)) return "[{\"param\":\"no_pw\"}]";
		
		session.setMaxInactiveInterval(3600);
		session.setAttribute("user_email", dto);
	
		return "[{\"param\":\"clear\"}]";
	}
	
	// 로그아웃 
    // 세션에서 토큰을 삭제합니다.
	@RequestMapping("logout")
	public String logout() {
		
		session.removeAttribute("user_email");
		session.removeAttribute("accessToken");
 
		return "redirect:board_list";
	}
	
	// 회원 가입
	@RequestMapping("user_insert")
	public String user_insert(UserDTO dto) {
		int res = userService.userInsert(dto);
		
		if (res > 0) {
			session.setAttribute("user_email", dto);
			return "redirect:board_list";
		}
		
		return null;
	}
	
	// 아이디 중복 확인
	@RequestMapping("check_email")
	@ResponseBody
	public String check_email(String user_email) {
		
		UserDTO dto = userService.checkEmail(user_email);
		
		if (dto == null) return "[{\"res\":\"yes\"}]"; 
		
		else return "[{\"res\":\"no\"}]"; 
	}
}
