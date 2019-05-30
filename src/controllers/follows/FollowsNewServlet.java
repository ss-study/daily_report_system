package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follows;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsNewServlet
 */
@WebServlet("/follows/new")
public class FollowsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsNewServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CSRFチェック
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            // ログイン中の社員取得
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            // DBアクセス
            EntityManager em = DBUtil.createEntityManager();
            Follows f = new Follows(login_employee.getId(), Integer.parseInt(request.getParameter("employeeid")));
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();

            // アトリビュート設定
            request.setAttribute("flush", "フォローしました。");

            // フォワード
            response.sendRedirect(request.getContextPath() + "/individual/show?employeeid=" + Integer.parseInt(request.getParameter("employeeid")));

        }

    }

}
