package config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.korea.board.BoardController;
import com.korea.board.KakaoLoginController;
import com.korea.board.NaverLoginController;
import com.korea.board.ProjectController;
import com.korea.board.SummerNoteController;
import com.korea.board.UserLoginController;

import service.KakaoLoginService;
import service.NaverLoginService;
import service.ProjectService;
import service.SummerNoteService;
import service.UserService;


@Configuration
@EnableWebMvc
public class ServletContext implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	
	@Bean
	public BoardController boardController() {
		return new BoardController();
	}
	
	@Bean
	public UserLoginController userController(UserService userService) {
		return new UserLoginController(userService);
	}
	
	@Bean
	public NaverLoginController naverController(UserService userService, NaverLoginService naverLoginService) {
		return new NaverLoginController(userService,naverLoginService);
	}
	
	@Bean
	public KakaoLoginController kakaoController(UserService userService, KakaoLoginService kakaoLoginService) {
		return new KakaoLoginController(userService,kakaoLoginService);
	}
	
	@Bean SummerNoteController summerNoteController(SummerNoteService summerNoteService) {
		return new SummerNoteController(summerNoteService);
	}
	
	@Bean ProjectController projectController(ProjectService projectService) {
		return new ProjectController(projectService);
	}
	
	 @Bean(name = "multipartResolver")
	    public CommonsMultipartResolver commonsMultipartResolver() {
	        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	        resolver.setMaxUploadSize(100000000);
	        return resolver;
	    }
}
