package board.controller;

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
import board.vo.Like;
import common.service.ServiceResult;
import common.util.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class SetLikeServlet
 */
@WebServlet("/like")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/** 좋아요 지정
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		// 1. 입력
		HttpSession session = request.getSession();
		Member currentUser = (Member)session.getAttribute("member");
		
		request.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map<String, String> req = new Gson().fromJson(buff.toString(), Map.class);
			
		int articleNum = Integer.parseInt(req.get("articleNum"));
		
		// 2. 로직
		BoardService service = new BoardService();
		Like param = new Like();
		param.setLikeArticle(articleNum);
		param.setLikeMemberId(currentUser.getMemberId());

		ServiceResult result = service.setLike(param);
		
		// 3. 출력
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();

		if (result == ServiceResult.SUCCESS) {
			resp.put("success", new Boolean(true));
			response.setStatus(HttpServletResponse.SC_OK);
			resp.put("msg", "좋아요를 성공적으로 추가했습니다.");
		} else if(result == ServiceResult.FAILED) {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("msg", "좋아요 추가에 실패했습니다.");
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.put("msg", "좋아요 추가에 실패했습니다. 이미 좋아요 처리된 게시글입니다.");
		}

		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/** 좋아요 취소
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		// 1. 입력
		HttpSession session = request.getSession();
		Member currentUser = (Member)session.getAttribute("member");
		
		request.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map<String, String> req = new Gson().fromJson(buff.toString(), Map.class);
		
		int articleNum = Integer.parseInt(req.get("articleNum"));
		
		// 2. 로직
		BoardService service = new BoardService();
		Like param = new Like();
		param.setLikeArticle(articleNum);
		param.setLikeMemberId(currentUser.getMemberId());

		boolean result = service.unSetLike(param);
		
		// 3. 출력
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();

		if (result) {
			resp.put("success", new Boolean(true));
			response.setStatus(HttpServletResponse.SC_OK);
			resp.put("msg", "좋아요를 성공적으로 취소했습니다.");
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("msg", "좋아요 취소에 실패했습니다.");
		}

		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}
}
