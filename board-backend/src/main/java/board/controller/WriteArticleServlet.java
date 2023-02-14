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
import common.login.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class WriteArticleServlet
 */
@WebServlet("/writeArticle")
public class WriteArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteArticleServlet() {
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
		HttpSession session = request.getSession();
		Member currentUser = (Member)session.getAttribute("member");
		String articleAuthor = currentUser.getMemberId();
		
		request.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			buff.append(line);
		}
		
		Map<String, String> req = new Gson().fromJson(buff.toString(), Map.class);
		
		String articleTitle = req.get("articleTitle");
		String articleContent = req.get("articleContent");
		
		// 2. 로직
		BoardService service = new BoardService();
		ArticleExtended newArticle = new ArticleExtended(articleTitle, articleContent, articleAuthor);
		boolean success = service.writeArticle(newArticle);
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();

		Map<String, Object> resp = new HashMap<String, Object>();
		
		if (success) {
			resp.put("success", new Boolean(true));
			resp.put("msg", "게시글을 성공적으로 작성했습니다.");
		} else {
			resp.put("success", new Boolean(false));
			resp.put("msg", "게시글을 작성하는데 실패했습니다.");
		}
		
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

}
