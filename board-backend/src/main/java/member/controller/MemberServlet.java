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

import board.service.BoardService;
import board.vo.ArticleExtended;
import common.util.CheckLogin;
import member.service.MemberService;
import member.vo.Member;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet(urlPatterns = {"/member", "/member/*"})
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 입력
		String[] urlChunks = request.getRequestURL().toString().split("/");
		String userID = urlChunks[urlChunks.length-1];
		
		String checkExist = request.getParameter("checkExist");
		
		// 2. 로직
		MemberService service = new MemberService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		Member newMember = new Member();
		newMember.setMemberId(userID);
		Member result = service.getMember(newMember);

		if (checkExist != null && checkExist.equals("true")) {
			if (result != null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.put("msg", "중복 아이디입니다. 사용할 수 없습니다.");				
			} else {
				response.setStatus(HttpServletResponse.SC_OK);
				resp.put("msg", "사용할 수 있는 아이디입니다.");
			}
		} else {
			// 로그인 검사
			boolean isLogin = CheckLogin.checkLogin(request, response);
			if (!isLogin) {
				return;
			}
			
			if (result != null) {
				result.setMemberPw("");
				response.setStatus(HttpServletResponse.SC_OK);
				resp.put("success", true);
				resp.put("msg", "회원 정보를 잘 불러왔습니다.");
				resp.put("memberInfo", result);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.put("success", false);
				resp.put("msg", "해당하는 회원 정보를 찾을 수 없습니다.");
			}
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map<String, String> req = new Gson().fromJson(buff.toString(), Map.class);
		String userID = req.get("userID");
		String userPW = req.get("userPW");
		String userName = req.get("userName");
		
		// 2. 로직
		MemberService service = new MemberService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		Member newMember = new Member(userID, userName, userPW);
		boolean result = service.registerMember(newMember);
		if (result) {
			resp.put("success", true);
			resp.put("msg", "새로운 회원을 성공적으로 등록했습니다.");				
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("success", false);
			resp.put("msg", "새로운 회원을 등록하는데 실패했습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map<String, String> req = new Gson().fromJson(buff.toString(), Map.class);
		String userID = req.get("userID");
		String userPW = req.get("userPW");
		String userName = req.get("userName");
		
		HttpSession session = request.getSession();
		Member currentMember = (Member)session.getAttribute("member");
		
		// 2. 로직
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		MemberService service = new MemberService();
		Member newMember = null;
		if (!userPW.equals("")) {
			newMember = new Member(userID, userName, userPW);		
		} else {
			String originalPW = currentMember.getMemberPw();
			newMember = new Member(userID, userName, originalPW);
		}
		
		if (!currentMember.getMemberId().equals(userID)) {
			newMember = null;
		}
		
		boolean result = service.editMemberInfo(newMember);
		if (result) {
			session.invalidate();
			resp.put("success", true);
			resp.put("msg", "회원정보를 성공적으로 수정했습니다.");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("success", false);
			resp.put("msg", "회원정보를 수정하는데 실패했습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		// 1. 입력
		String[] urlChunks = request.getRequestURL().toString().split("/");
		String userID = urlChunks[urlChunks.length-1];
		
		HttpSession session = request.getSession();
		Member currentMember = (Member)session.getAttribute("member");
		
		// 2. 로직
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		MemberService service = new MemberService();
		if (!currentMember.getMemberId().equals(userID)) {
			return;
		}
		
		Member param = new Member();
		param.setMemberId(userID);
		
		boolean result = service.getOut(param);
		if (result) {
			session.invalidate();
			resp.put("success", true);
			resp.put("msg", "회원 탈퇴에 성공했습니다.");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("success", false);
			resp.put("msg", "회원 탈퇴에 실패했습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

}
