package service;

import org.springframework.stereotype.Service;

import dao.UserDAO;
import dto.UserDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
	final UserDAO userDAO;

	public UserDTO checkEmail(String user_email) {
		return userDAO.checkEmail(user_email);
	}
	
	public int userInsert(UserDTO dto) {
		return userDAO.userInsert(dto);
	}
	
}
