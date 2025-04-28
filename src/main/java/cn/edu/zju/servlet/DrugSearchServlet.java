package cn.edu.zju.servlet;

import cn.edu.zju.dao.DrugDao;
import cn.edu.zju.bean.Drug;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.io.IOException;

@WebServlet("/searchDrug")  // This connects to your <form action="/searchDrug">
public class DrugSearchServlet extends HttpServlet {

    private DrugDao drugDao = new DrugDao();  // Create DAO to talk to the DB

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("drugName");  // Get search term from user

        Drug drug = drugDao.getDrugByName(name);  // Search for that drug in DB

        if (drug != null) {
            // If found, redirect to the detail page
            List<Drug> result = new ArrayList<>();
            result.add(drug);
            request.setAttribute("searchResult", result);
            request.getRequestDispatcher("/views/drugs.jsp").forward(request, response);
        } else {
            // If not found, show error message
            request.setAttribute("searchResult", new ArrayList<Drug>()); // empty list
            request.setAttribute("error", "No drug found for: " + name);
            request.getRequestDispatcher("/views/drugs.jsp").forward(request, response);
        }
    }
}
