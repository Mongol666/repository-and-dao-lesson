package com.dbit.app.controllers;

import com.dbit.app.repositories.EmployeeRepository;
import com.dbit.app.repositories.fabric.RepositoryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employees")
public class EmployeeController extends HttpServlet {

    private EmployeeRepository repository = RepositoryFactory.getEmployeeRepository();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employees", repository.findAll());
        req.getRequestDispatcher("employees.jsp").forward(req, resp);
    }
}
