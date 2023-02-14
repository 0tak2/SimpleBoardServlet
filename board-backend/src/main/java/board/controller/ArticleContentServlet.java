package board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
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
import board.vo.Like;
import common.login.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class ArticleContentServlet
 */
@WebServlet("/article/*")
public class ArticleContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleContentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		// 1. 입력
		HttpSession session = request.getSession();
		Member currentUser = (Member)session.getAttribute("member");
		
		String[] urlChunks = request.getRequestURL().toString().split("/");
		int articleId = Integer.parseInt(urlChunks[urlChunks.length-1]);
		
		// 2. 로직
		BoardService service = new BoardService();
		ArticleExtended articleParam = new ArticleExtended();
		articleParam.setArticleNum(articleId);
		ArticleExtended result = service.getArticle(articleParam);
		
		List<Comment> commentsList = service.getAllComments(result);
		
		Like likeParam = new Like();
		likeParam.setLikeArticle(articleId);
		likeParam.setLikeMemberId(currentUser.getMemberId());
		
		boolean didLike = false;
		Like like = service.getLike(likeParam);
		if (like != null) {
			didLike = true;
		} else {
			didLike = false;
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();

		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("success", new Boolean(true));
		resp.put("msg", "게시글을 성공적으로 불러왔습니다.");
		resp.put("article", result);
		resp.put("comments", commentsList);
		resp.put("didLike", didLike);
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
