package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;
import utils.Page;

/**
 * Servlet implementation class FollowsIndexServlet
 */
@WebServlet("/follows/index")
public class FollowsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ページ数取得
        Integer page = new Page().setPage(request.getParameter("page")).toInteger();

        // ログイン中の社員取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        // DBアクセス
        EntityManager em = DBUtil.createEntityManager();
        List<Report> reports = em.createNamedQuery("getFollowReports", Report.class)
                                .setParameter("employee_id", login_employee.getId())
                                .setFirstResult(15 * (page - 1))
                                .setMaxResults(15)
                                .getResultList();
        long reports_count = (long)em.createNamedQuery("getFollowReportsCount", Long.class)
                                .setParameter("employee_id", login_employee.getId())
                                .getSingleResult();
        em.close();

        // アトリビュート設定
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        // フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);

    }

}
