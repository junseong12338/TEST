package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
	private int project_idx;
	
	private int user_idx;
	
	private String project_title;
	private String project_content;
	private int project_status;
	private String project_img;
	private String project_start;
	private String project_end;
	private int project_target;
}
