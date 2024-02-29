package context;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dao.ProjectDAO;
import dao.UserDAO;
import service.KakaoLoginService;
import service.NaverLoginService;
import service.ProjectService;
import service.SummerNoteService;
import service.UserService;

@Configuration
public class Context_2_dao {

	@Bean
	public UserService userService(UserDAO userDAO) {
		return new UserService(userDAO);
	}
	
	@Bean
	public UserDAO userDAO(SqlSession sqlSession) {
		return new UserDAO(sqlSession);
	}
	
	
	@Bean
	public ProjectDAO projectDAO(SqlSession sqlSession) {
		return new ProjectDAO(sqlSession);
	}
	
	@Bean public SummerNoteService summerNoteService(ProjectDAO projectDAO) {
		return new SummerNoteService(projectDAO);
	}
	
	@Bean
	public ProjectService projectService(ProjectDAO projectDAO) {
		return new ProjectService(projectDAO);
	}
	
	@Bean
    public NaverLoginService naverLoginService() {
        return new NaverLoginService();
    }
	
	@Bean
    public KakaoLoginService kakaoLoginService() {
        return new KakaoLoginService();
    }
}
