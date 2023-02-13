package board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import board.service.BoardService;
import board.vo.ArticleExtended;
import common.login.CheckLogin;

/**
 * Servlet implementation class BoardMain
 */
@WebServlet("/article")
public class ArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticlesServlet() {
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
		
		List<ArticleExtended> list = null;
		BoardService service = new BoardService();
		list = service.getAllArticles();
		
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();

		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("success", new Boolean(true));
		resp.put("msg", "게시물 가져오기에 성공하였습니다.");
		resp.put("articles", list);
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
