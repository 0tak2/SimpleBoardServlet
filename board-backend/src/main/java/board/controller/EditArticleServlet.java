package board.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
import common.login.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class EditArticleServlet
 */
@WebServlet("/editArticle")
public class EditArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditArticleServlet() {
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
		int articleNum = Integer.parseInt(request.getParameter("articleNum"));
		
		// 2. 로직
		BoardService service = new BoardService();
		ArticleExtended param = new ArticleExtended();
		param.setArticleNum(articleNum);
		ArticleExtended result = service.getArticle(param);
		
		// 3. 출력
		Member currentUser = (Member)request.getSession().getAttribute("member");
		if (!result.getArticleAuthor().equals(currentUser.getMemberId())) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><script>");
			
			out.println("alert('작성자만 수정할 수 있습니다.'); history.back();");

			out.println("</script></html>");
			out.close();
			return;
		}
		
		RequestDispatcher dispatcher
			= request.getRequestDispatcher("editArticle.jsp");
		
		request.setAttribute("article", result);
		
		dispatcher.forward(request, response);
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
		int articleNum = Integer.parseInt(req.get("articleNum"));
		String articleTitle = req.get("articleTitle");
		String articleContent = req.get("articleContent");
		
		// 2. 로직
		BoardService service = new BoardService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		//  현재 로그인 사용자가 게시글 작성자인지 확인 후 수정
		ArticleExtended param = new ArticleExtended();
		param.setArticleNum(articleNum);
		param.setArticleTitle(articleTitle);
		param.setArticleContent(articleContent);
		ArticleExtended article = service.getArticle(param);
		if (article.getArticleAuthor().equals(currentUser.getMemberId())) {
			boolean successDB = service.editArticle(param);
			resp.put("success", successDB);
			if (successDB) {
				resp.put("msg", "게시글을 성공적으로 수정했습니다.");				
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.put("msg", "게시글을 작성하는데 실패했습니다.");
			}
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.put("msg", "작성자만 게시글을 수정할 수 있습니다.");
		}
		
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

}
