package controllers.individual;

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
 * Servlet implementation class IndividualShowServlet
 */
@WebServlet("/individual/show")
public class IndividualShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IndividualShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // ページ数取得
        Integer page = new Page().setPage(request.getParameter("page")).toInteger();

        // ログイン中の社員取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        // employeeidクエリストリングの受け取り
        int employeeId;
        try{
            employeeId = Integer.parseInt(request.getParameter("employeeid"));
        } catch(Exception e) {
            // エラー時はログイン中のユーザのidが入る
            employeeId = login_employee.getId();
        }

        // DBアクセス
        EntityManager em = DBUtil.createEntityManager();

        // 対象の社員オブジェクトを取得
        Employee target_employee = em.createNamedQuery("getEmployeeFromId", Employee.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();

        // 対象の社員のレポートを取得
        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", target_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        // 対象の社員のレポート総数を取得
        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                   .setParameter("employee", target_employee)
                   .getSingleResult();

        // ログイン中のユーザが対象の社員をフォローしているかチェック
        boolean isFollow = em.createNamedQuery("isFollow", Long.class)
                .setParameter("followerId", login_employee.getId())
                .setParameter("followedId", target_employee.getId())
                .getSingleResult()
                != 0;

        em.close();

        // アトリビュート設定
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("employee", target_employee);
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("isFollow", isFollow);
        request.setAttribute("isOthers", login_employee.getId() != target_employee.getId());

        // フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/individual/show.jsp");
        rd.forward(request, response);

    }

}
