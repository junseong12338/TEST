package com.korea.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dto.ProjectDTO;
import lombok.RequiredArgsConstructor;
import service.ProjectService;
import util.Common;

@Controller
@RequiredArgsConstructor
public class ProjectController {
	
	final ProjectService projectService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("project_test")
	public String project_view(Model model) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		ProjectDTO dto = projectService.select_project(idx);
		
		model.addAttribute("dto",dto);
		
		return Common.Board.VIEW_PATH + "project_view.jsp";
	}
}
