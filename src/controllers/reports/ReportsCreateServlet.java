package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            // 新しくレポートオブジェクトを生成
            Report r = new Report();

            // 必要な情報をオブジェクトに詰める
            r.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            if(rd_str != null && !rd_str.equals("")) {
                report_date = Date.valueOf(request.getParameter("report_date"));
            }
            r.setReport_date(report_date);
            r.setTitle(request.getParameter("title"));
            r.setContent(request.getParameter("content"));
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            // バリデーションチェック
            List<String> errors = ReportValidator.validate(r);
            if(errors.size() > 0) { // エラーの時

                // アトリビュート設定
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);

                // フォワード
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);

            } else { // 正常の時

                // DBアクセス
                EntityManager em = DBUtil.createEntityManager();
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();

                // アトリビュート設定
                request.getSession().setAttribute("flush", "登録が完了しました。");

                // フォワード
                response.sendRedirect(request.getContextPath() + "/reports/index");

            }
        }
    }

}