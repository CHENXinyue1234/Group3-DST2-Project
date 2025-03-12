package cn.edu.zju.servlet;

import cn.edu.zju.bean.Drug;
import cn.edu.zju.dao.DrugDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DrugServlet",  urlPatterns = "/drug")
public class DrugServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DrugServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DrugDao drugDao = new DrugDao();
        List<Drug> drugs = drugDao.findAll();
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>id</th>");
        out.println("<th>name</th>");
        out.println("<th>obj_cls</th>");
        out.println("<th>drug_url</th>");
        out.println("<th>biomarker</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        for (Drug d: drugs){
            out.println("<tr>");
            out.println("<td>"+d.getId()+"</td>");
            out.println("<td>"+d.getName()+"</td>");
            out.println("<td>"+d.getObjCls()+"</td>");
            out.println("<td>"+d.getDrugUrl()+"</td>");
            out.println("<td>"+d.isBiomarker()+"</td>");
            out.println("</tr>");
            log.info("Drug displayed: " + d.getName());
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}