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
 * Servlet implementation class FollowsDestroyServlet
 */
@WebServlet("/follows/destroy")
public class FollowsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CSRFチェック
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            // ログイン中の社員取得
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            // employeeidクエリストリングの受け取り
            Integer employeeId;
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

            // 削除対象のフォローオブジェクトを取得
            Follows f = em.createNamedQuery("getFollowId", Follows.class)
                .setParameter("followerId", login_employee.getId())
                .setParameter("followedId", target_employee.getId())
                .getSingleResult();

            // 削除
            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();

            // アトリビュート設定
            request.setAttribute("flush", "フォロー解除しました。");

            // フォワード
            response.sendRedirect(request.getContextPath() + "/individual/show?employeeid=" + Integer.parseInt(request.getParameter("employeeid")));

        }

    }

}
