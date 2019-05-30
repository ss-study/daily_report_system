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
import utils.DBUtil;

/**
 * Servlet implementation class FollowsShowServlet
 */
@WebServlet("/follows/show")
public class FollowsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ログイン中の社員取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        // DBアクセス
        EntityManager em = DBUtil.createEntityManager();
        List<Employee> employees = em.createNamedQuery("getFollowEmployees", Employee.class)
                                    .setParameter("employee_id", login_employee.getId())
                                    .getResultList();
        em.close();

        // アトリビュート設定
        request.setAttribute("employees", employees);

        // フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/show.jsp");
        rd.forward(request, response);

    }

}
