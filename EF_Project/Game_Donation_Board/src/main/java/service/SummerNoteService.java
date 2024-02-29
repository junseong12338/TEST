package service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.ProjectDAO;
import dto.ProjectDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SummerNoteService {
	
	final ProjectDAO projectDAO;
	
	//글 작성 처리 메서드
	@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED,isolation = Isolation.SERIALIZABLE)
	public int insertProject(String editordata) throws Exception {
		
		ProjectDTO dto = new ProjectDTO();
		
		dto.setUser_idx(0);
		dto.setProject_title("1234556");
		dto.setProject_start("2020-03-09");
		dto.setProject_end("2020-03-29");
		dto.setProject_target(10000000);
		dto.setProject_status(0);
		
		
		
		int idx = projectDAO.select_idx();
		
		if(idx > 0) {
			
			dto.setProject_idx(idx);
			
			String replace_editordata = editordata.replaceAll("/temp/", "/"+idx+"/");
			
			dto.setProject_content(replace_editordata);
			
			
			projectDAO.insert_project(dto);
			
			return idx;
		}
		return -1;
	}
	
}
