package dao;

import org.apache.ibatis.session.SqlSession;

import dto.ProjectDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectDAO {
	
	final SqlSession sqlsession;
	
	//�� �ۼ� �� �� ��ȣ �̸� ��������
	public int select_idx() {
		return sqlsession.selectOne("pj.select_idx");
	}
	
	public int insert_project(ProjectDTO dto) {
		return sqlsession.insert("pj.insert_project",dto);
	}
	
	public int select_idx(String project_content) {
		return sqlsession.selectOne("pj.select_idx",project_content);
	}
	
	//�� �Ѱ� ��������
	public ProjectDTO selectOne_project(int idx) {
		return sqlsession.selectOne("pj.selectOne_project",idx);
	}
	
}
