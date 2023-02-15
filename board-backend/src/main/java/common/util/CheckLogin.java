package common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class CheckLogin {
	public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isLogin = true;
		
		HttpSession session = request.getSession(false);
		
		if (session == null) { // 세션이 없는 경우
			isLogin = false;
		} else if (session.getAttribute("member") == null) { // 세션이 있으나 내부에 Member 객체가 없는 경우
			isLogin = false;
		} else { // 로그인 되어있음
			return true;			
		}
		
		if (!isLogin) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json; charset=UTF-8");
		    PrintWriter out = response.getWriter();

			Map<String, Object> resp = new HashMap<String, Object>();
			resp.put("success", new Boolean(false));
			resp.put("msg", "로그인이 필요한 서비스입니다.");
		    out.print(new Gson().toJson(resp));
		    out.close();
		} 
		
		return false;
	}
}
