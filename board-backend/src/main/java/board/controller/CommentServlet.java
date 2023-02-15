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
import board.vo.ArticleExtended;
import board.vo.Comment;
import common.util.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class WriteCommentServlet
 */
@WebServlet(urlPatterns = {"/comment", "/comment/*"})
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/** 새로운 댓글 작성
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
		String commentAuthor = currentUser.getMemberId();
		
		request.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map<String, String> req = new Gson().fromJson(buff.toString(), Map.class);
		
		String commentContent = req.get("commentContent");		
		int articleNum = Integer.parseInt(req.get("articleNum"));
		
		// 2. 로직
		BoardService service = new BoardService();
		Comment newComment = new Comment(commentContent, commentAuthor, articleNum);
		boolean success = service.writeComment(newComment);
		
		// 3. 출력
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();

		if (success) {
			resp.put("success", new Boolean(true));
			response.setStatus(HttpServletResponse.SC_OK);
			resp.put("msg", "댓글을 성공적으로 작성했습니다.");
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("msg", "댓글 작성에 실패했습니다.");
		}

		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}
	
	/** 댓글 수정
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String commentContent = req.get("commentContent");
		
		String[] urlChunks = request.getRequestURL().toString().split("/");
		int commentNum = Integer.parseInt(urlChunks[urlChunks.length-1]);
		
		// 2. 로직
		BoardService service = new BoardService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		//  현재 로그인 사용자가 게시글 작성자인지 확인 후 수정
		Comment param = new Comment();
		param.setCommentNum(commentNum);
		param.setCommentContent(commentContent);

		Comment comment = service.getOneComment(param);
		if (comment.getCommentAuthor().equals(currentUser.getMemberId())) {
			boolean successDB = service.editComment(param);
			resp.put("success", successDB);
			if (successDB) {
				resp.put("msg", "댓글을 성공적으로 수정했습니다.");				
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.put("msg", "댓글을 작성하는데 실패했습니다.");
			}
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			resp.put("msg", "작성자만 댓글을 수정할 수 있습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/** 댓글 삭제
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		// 1. 입력
		String[] urlChunks = request.getRequestURL().toString().split("/");
		int commentNum = Integer.parseInt(urlChunks[urlChunks.length-1]);
		
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		Member currentUser = (Member) session.getAttribute("member");
		
		// 2. 로직
		BoardService service = new BoardService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		//  현재 로그인 사용자가 게시글 작성자인지 확인 후 삭제
		Comment param = new Comment();
		param.setCommentNum(commentNum);
		Comment comment = service.getOneComment(param);
		if (comment.getCommentAuthor().equals(currentUser.getMemberId())) {
			boolean successDB = service.deleteComment(param);
			resp.put("success", successDB);
			if (successDB) {
				resp.put("msg", "댓글을 성공적으로 삭제했습니다.");				
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.put("msg", "댓글을 삭제하는데 실패했습니다.");
			}
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			resp.put("msg", "작성자만 댓글을 삭제할 수 있습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}
}
