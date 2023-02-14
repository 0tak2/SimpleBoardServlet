package board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import board.service.BoardService;
import board.vo.Comment;
import common.login.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class WriteCommentServlet
 */
@WebServlet("/writeComment")
public class WriteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		// 1. 입력
//		HttpSession session = request.getSession();
//		request.setCharacterEncoding("UTF-8");
//		int articleNum = Integer.parseInt(request.getParameter("articleNum"));
//		
//		Member currentUser = (Member) session.getAttribute("member");

//		
//		String commentContent = request.getParameter("commentContent");
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
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><script>");
		
		if (success) {
			out.printf("window.location.href='viewArticle?articleId=' + %d;", articleNum);
		} else {
			out.printf("alert('실패'); window.location.href='viewArticle?articleId=' + %d;", articleNum);
		}

		out.println("</script></html>");
	}

}
