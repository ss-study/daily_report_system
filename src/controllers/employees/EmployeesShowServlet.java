package controllers.employees;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;
import utils.Page;

/**
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet("/employees/show")
public class EmployeesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeesShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ページ数取得
        Integer page = new Page().setPage(request.getParameter("page")).toInteger();

        // DBアクセス
        EntityManager em = DBUtil.createEntityManager();
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        em.close();

        // アトリビュート設定
        request.setAttribute("employee", e);
        request.setAttribute("page", page);
        request.setAttribute("_token", request.getSession().getId());

        // フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/show.jsp");
        rd.forward(request, response);
    }

}