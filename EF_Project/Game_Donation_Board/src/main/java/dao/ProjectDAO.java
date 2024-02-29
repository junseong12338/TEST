package dao;

import org.apache.ibatis.session.SqlSession;

import dto.ProjectDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectDAO {
	
	final SqlSession sqlsession;
	
	//글 작성 전 글 번호 미리 가져오기
	public int select_idx() {
		return sqlsession.selectOne("pj.select_idx");
	}
	
	public int insert_project(ProjectDTO dto) {
		return sqlsession.insert("pj.insert_project",dto);
	}
	
	public int select_idx(String project_content) {
		return sqlsession.selectOne("pj.select_idx",project_content);
	}
	
	//글 한개 가져오기
	public ProjectDTO selectOne_project(int idx) {
		return sqlsession.selectOne("pj.selectOne_project",idx);
	}
	
}
