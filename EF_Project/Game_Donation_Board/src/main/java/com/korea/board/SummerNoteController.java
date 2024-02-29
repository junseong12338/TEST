package com.korea.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import service.SummerNoteService;

@Controller
@RequiredArgsConstructor
public class SummerNoteController {
	
	final SummerNoteService summerNoteService;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	
	
	/*
	 * ���ӳ�Ʈ �̹���,���� ���ε� ó��
	 * �����Ϳ� �̹���, ������ ����ϸ�
	 * �ӽ������� ����(fileRoot)
	 */
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile)  {
		JsonObject jsonObject = new JsonObject();
		
		
        /*
		 * String fileRoot = "C:\\summernote_image\\"; // �ܺΰ�η� ������ ����Ҷ�.
		 */
		//���� ���ε� �⺻���
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		System.out.println("uploadSummernoteImageFile colled.....");
		// ���ΰ�η� ����
		String fileRoot = contextRoot+"resources/fileupload/temp/io";
		
		String originalFileName = multipartFile.getOriginalFilename();	//�������� ���ϸ�
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//���� Ȯ����
		String savedFileName = UUID.randomUUID() + extension;	//����� ���� ��
		
		File targetFile = new File(fileRoot + savedFileName);	
		try {
			InputStream fileStream = multipartFile.getInputStream();			
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//���� ����
			
			System.out.println("contextRoot : " + contextRoot.toString());
			jsonObject.addProperty("url","/board/resources/fileupload/temp/io"+savedFileName); // contextroot + resources + ������ ���� ������
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//����� ���� ����
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();
		
		
		return a;
	}
	
	/*
	 * �� �ۼ��� �̹���,���� ��� �� ���� �� 
	 * �̹� ����� �̹���, ���� ���� 
	 */
	@RequestMapping(value = "/deleteSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public void deleteSummernoteImageFile(@RequestParam("file") String fileName) {
		System.out.println("deleteSummernoteImageFile colled.....");
		//���� ���ε� �⺻���
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
	    // ���� ��ġ
	    String filePath = contextRoot + "resources/fileupload/temp/";
	    
	    // �ش� ���� ����
	    deleteFile(filePath, fileName);
	}
	/*
	 * �� �ۼ� �Ϸ� ���� �޼���
	 */
	@RequestMapping("/summernote_send")
	public String summernote_send(String editordata) {

		//���� ���ε� �⺻���
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		// ���� ��ġ
	    String filePath = contextRoot + "resources/fileupload/temp/";
		
	    
		
		try {
			int idx = summerNoteService.insertProject(editordata);
			
			if(idx == -1) {
				return "redirect:/";
			}
			System.out.println("idx :" + idx);
			
			// temp ���� ����� �����͵� ���ε�
		    String temp_folder = contextRoot + "resources/fileupload/temp/";
		    String idx_folder = contextRoot + "resources/fileupload/" + idx + "/";
		    fileUpload(temp_folder, idx_folder);
			
		    //temp ���� �� session�� ����Ǿ��ִ� ����email�� �̸��� ���Ե� �̹��� ����
		    removeDummyFiles(getFileNamesFromFolder(temp_folder), temp_folder);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		
		return "redirect:/";
	}
	
	// ��ġ������ ���� ���� �̸� ��������
	private List<String> getFileNamesFromFolder(String folderName) {
	    // ���� �̸��� ������ ����Ʈ ����
	    List<String> fileNames = new ArrayList<>();

	    // �־��� ���� ��θ� ������� ���� ��ü ����
	    File folder = new File(folderName);
	    // ���� ���� ���ϵ��� ������
	    File[] files = folder.listFiles();
	    if (files != null) {
	        // ������ ��� ���� �̸��� ����Ʈ�� �߰�
	        for (File file : files) {
	            if (file.isFile()) {
	            	if(file.getName().contains("io")) {
	            		fileNames.add(file.getName());	            		
	            	}
	            }
	        }
	    }
	    // ���� �̸��� ���� ����Ʈ ��ȯ
	    return fileNames;
	}
	
	
	// ���� ���� ����
	private void removeDummyFiles(List<String> fileNames, String filePath) {
	    // �־��� ���� �̸� ����Ʈ�� ������� ������ ����
	    for (String fileName : fileNames) {
	        // contents ���ڿ��� ���� �̸��� ���ԵǾ� ���� ���� ��� ���� ����
	        
	            deleteFile(filePath, fileName);
	        
	    }
	}
	
	// ���� �ϳ� ����
		private void deleteFile(String filePath, String fileName) {
		    Path path = Paths.get(filePath, fileName);
		    try {
		        Files.delete(path);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	
	// ���� ���� ����
	private void fileUpload(String temp_folder, String idx_folder) {
	    // �־��� ��θ� ������� ���� ��ü ����
	    File folder1;
	    File folder2;
	    folder1 = new File(temp_folder);
	    folder2 = new File(idx_folder);

	    // ������ �������� ������ ���� ����
	    if (!folder1.exists())
	        folder1.mkdirs();
	    if (!folder2.exists())
	        folder2.mkdirs();
	    
	    // ����1�� ���ϵ��� ������
	    File[] target_files = folder1.listFiles();
	    for (File file : target_files) {
	        // ����2�� ������ ���� ����
	    	File temp = null; 	    		
	    	if(file.getName().contains("io"))
	    	{
	    		temp = new File(folder2.getAbsolutePath() + File.separator + file.getName()); 
	    	}
	        
	    	if(temp != null) {
		    		// ������ ���
		    		if (file.isDirectory()) {
		    			// ������ ��� ������ ���� ����
		    			temp.mkdir();
		    		} else {
		    			// ������ �ƴ� ��� ���� ����
		    			FileInputStream fis = null;
		    			FileOutputStream fos = null;
		    			try {
		    				fis = new FileInputStream(file);
		    				fos = new FileOutputStream(temp);
		    				byte[] b = new byte[4096];
		    				int cnt = 0;
		    				while ((cnt = fis.read(b)) != -1) {
		    					fos.write(b, 0, cnt);
		    				}
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			} finally {
		    				try {
		    					// ���� �߻� ���ο� ������� ���� ����� ��Ʈ���� ����
		    					fis.close();
		    					fos.close();
		    				} catch (IOException e) {
		    					e.printStackTrace();
		    				}
		    			}
		    		}
		    	}
	    		
	    	}
	}
	
	
	
}
