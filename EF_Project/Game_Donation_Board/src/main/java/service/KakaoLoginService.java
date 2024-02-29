package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class KakaoLoginService {
	
	
	public String getAccessToken (String authorize_code) throws Exception {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		URL url = new URL(reqURL);
        
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
        
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
        
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=authorization_code");
		sb.append("&client_id=abc320a5a43005c6fe87b9f765fda4cb"); //본인이 발급받은 key
		sb.append("&redirect_uri=http://localhost:9090/board/kakaoLogin"); // 본인이 설정한 주소
        
		sb.append("&code=" + authorize_code);
		bw.write(sb.toString());
		bw.flush();
        
		// 결과 코드가 200이라면 성공
		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);
        
		// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
		String result = "";
        
		while ((line = br.readLine()) != null) {
			result += line;
		}
		System.out.println("response body : " + result);
        
		// json-simple 라이브러리를 사용하여 JSON 파싱
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);

        access_Token = (String) jsonObject.get("access_token");
        refresh_Token = (String) jsonObject.get("refresh_token");

        System.out.println("access_token : " + access_Token);
        System.out.println("refresh_token : " + refresh_Token);
        
		br.close();
		bw.close();
		
		return access_Token;
	}
	
	
	
	
	   public HashMap<String, Object> getUserInfo(String access_Token) throws Exception {
	        HashMap<String, Object> userInfo = new HashMap<>();
	        String reqURL = "https://kapi.kakao.com/v2/user/me";
	        
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            StringBuilder result = new StringBuilder();
	            while ((line = br.readLine()) != null) {
	                result.append(line);
	            }
	            System.out.println("response body : " + result);
	            
	            // json-simple 라이브러리를 사용하여 JSON 파싱
	            JSONParser parser = new JSONParser();
	            JSONObject jsonObject = (JSONObject) parser.parse(result.toString());
	            JSONObject properties = (JSONObject) jsonObject.get("properties");
	            JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");
	            String nickname = (String) properties.get("nickname");
	            String email = (String) kakao_account.get("email");
	            userInfo.put("nickname", nickname);
	            userInfo.put("email", email);
	            
	            br.close();
	       
	        return userInfo;
	    }

	
}