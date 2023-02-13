package member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import member.service.MemberService;
import member.vo.LoginResponse;
import member.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. [입력] 뷰로부터 입력을 받음
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map req = new Gson().fromJson(buff.toString(), Map.class);
		
		String userID = (String) req.get("userID");
		String userPW = (String) req.get("userPW");
		
		// 2. [로직] 서비스로 VO 넘김
		Member member = new Member();
		member.setMemberId(userID);
		member.setMemberPw(userPW);
		
		MemberService service = new MemberService();
		// 나중에 스프링에 들어가면 IoC를 도입하여 이런 코드는 쓰지 않는다.
		// 100명이 접속한다면 100개의 인스턴스를 생성하게 된다.
		// 스프링이 싱글톤으로 만들어 주입해줄 것이다.
		
		Member result = service.login(member);
		// Boolean으로 받을 수도 있지만, VO로 주고 받는 것이 일반적으로 가장 좋다.
		// 로그인에 성공하면, 결과에서 회원 이름까지 VO에 포함해 들고온다.
		// 로그인에 실패하면 null 리턴	
		
		// 3. [출력] 뷰로 넘김
		Map<String, Object> resp = new HashMap<String, Object>();
		if (result != null) {
			// 로그인 성공
			HttpSession session = request.getSession(true);
			session.setAttribute("member", result); // VO 자체를 저장
			
			Member userInfo = new Member();
			userInfo.setMemberId(result.getMemberId());
			userInfo.setMemberName(result.getMemberPw());
			
			response.setStatus(HttpServletResponse.SC_OK);
			resp.put("success", new Boolean(true));
			resp.put("msg", "로그인에 성공하였습니다.");
			resp.put("userInfo", userInfo);
		} else {
			// 로그인 실패
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.put("success", new Boolean(false));
			resp.put("msg", "로그인에 실패하였습니다.");
		}
		
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

}
