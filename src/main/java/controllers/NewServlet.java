package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/new")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();
        
        // Taskのインスタンスを生成
        Task t = new Task();
        
        // tの各フィールドにデータを代入
        String content = "test_task";
        t.setContent(content);
        
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        t.setCreated_at(currentTime);
        t.setUpdated_at(currentTime);
        
        // データベースに保存
        em.persist(t);
        em.getTransaction().commit();
        
        // 自動採番されたIDの値を表示
        response.getWriter().append(Integer.valueOf(t.getId()).toString());
        
        em.close();
    }

}
