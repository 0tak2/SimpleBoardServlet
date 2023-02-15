package board.controller;

import java.io.BufferedReader;
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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import board.service.BoardService;
import board.vo.ArticleExtended;
import board.vo.Comment;
import board.vo.Like;
import common.util.CheckLogin;
import member.vo.Member;

/**
 * Servlet implementation class BoardMain
 */
@WebServlet(urlPatterns = {"/article", "/article/*"})
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/** 게시글 조회
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 검사
		boolean isLogin = CheckLogin.checkLogin(request, response);
		if (!isLogin) {
			return;
		}
		
		Map<String, Object> resp = new HashMap<String, Object>(); // 응답 객체
		
		String[] urlChunks = request.getRequestURL().toString().split("/");
		if (urlChunks[urlChunks.length-1].equals("article")) {
			// 전체 게시글 조회
			// 	/boardApi/article
			List<ArticleExtended> list = null;
			BoardService service = new BoardService();
			list = service.getAllArticles();
			
			response.setContentType("application/json; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    
		    if (list != null) {
				resp.put("success", new Boolean(true));
				resp.put("msg", "전체 게시글을 성공적으로 가져왔습니다.");
				resp.put("articles", list);
		    } else {
		    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.put("success", new Boolean(false));
				resp.put("msg", "게시글을 가져오는데 실패했습니다.");
		    }
		} else {
			// 특정 게시글 조회
			// 	/boardApi/article/:articleNum
			HttpSession session = request.getSession();
			Member currentUser = (Member)session.getAttribute("member");
			
			int articleNum = Integer.parseInt(urlChunks[urlChunks.length-1]);
			BoardService service = new BoardService();
			ArticleExtended articleParam = new ArticleExtended();
			articleParam.setArticleNum(articleNum);
			ArticleExtended result = service.getArticle(articleParam);
			
			List<Comment> commentsList = service.getAllComments(result);
			
			Like likeParam = new Like();
			likeParam.setLikeArticle(articleNum);
			likeParam.setLikeMemberId(currentUser.getMemberId());
			
			boolean didLike = false;
			Like like = service.getLike(likeParam);
			if (like != null) {
				didLike = true;
			} else {
				didLike = false;
			}
			
			// 3. 출력
			resp.put("success", new Boolean(true));
			resp.put("msg", "게시글을 성공적으로 불러왔습니다.");
			resp.put("article", result);
			resp.put("comments", commentsList);
			resp.put("didLike", didLike);
		}
		
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/** 게시글 작성
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
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.put("success", new Boolean(false));
			resp.put("msg", "게시글을 작성하는데 실패했습니다.");
		}
		
	    out.print(new Gson().toJson(resp));
	    out.close();
	}

	/** 게시글 수정
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
		
		String[] urlChunks = request.getRequestURL().toString().split("/");
		int articleNum = Integer.parseInt(urlChunks[urlChunks.length-1]);
		
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
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			resp.put("msg", "작성자만 게시글을 수정할 수 있습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}
	
	/** 게시글 삭제
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
		int articleNum = Integer.parseInt(urlChunks[urlChunks.length-1]);
		
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		Member currentUser = (Member) session.getAttribute("member");
		
		// 2. 로직
		BoardService service = new BoardService();
		
		// 응답 객체
		Map<String, Object> resp = new HashMap<String, Object>();
		
		//  현재 로그인 사용자가 게시글 작성자인지 확인 후 삭제
		ArticleExtended param = new ArticleExtended();
		param.setArticleNum(articleNum);
		ArticleExtended article = service.getArticle(param);
		if (article.getArticleAuthor().equals(currentUser.getMemberId())) {
			boolean successDB = service.deleteArticle(param);
			resp.put("success", successDB);
			if (successDB) {
				resp.put("msg", "게시글을 성공적으로 삭제했습니다.");				
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.put("msg", "게시글을 삭제하는데 실패했습니다.");
			}
		} else {
			resp.put("success", new Boolean(false));
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			resp.put("msg", "작성자만 게시글을 삭제할 수 있습니다.");
		}
		
		// 3. 출력
		response.setContentType("application/json; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(new Gson().toJson(resp));
	    out.close();
	}
}
