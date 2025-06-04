package controllers;

import java.io.IOException;

import jakarta.persistence.EntityManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        
        // 該当のIDのタスク1件のみをデータベースから取得
        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));
        
        em.close();
        
        // タスク情報とセッションIDをリクエストスコープに登録
        request.setAttribute("task", t);
        request.setAttribute("_token", request.getSession().getId());
        
        // タスクデータが存在しているときのみ
        // タスクIDをセッションスコープに登録
        if (t != null) {
            request.getSession().setAttribute("task_id", t.getId());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);
    }

}
