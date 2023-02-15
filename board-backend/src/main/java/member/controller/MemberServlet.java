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
@WebServlet("/member")
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
		String userID = request.getParameter("userID");
		
		// 2. 로직
		MemberService service = new MemberService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		Member newMember = new Member();
		newMember.setMemberId(userID);
		boolean result = service.isExist(newMember);
		if (result) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.put("msg", "중복 아이디입니다. 사용할 수 없습니다.");				
		} else {
			resp.put("msg", "사용할 수 있는 아이디입니다.");
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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
