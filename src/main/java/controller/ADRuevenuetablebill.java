// src/controller/BillController.java
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import dao.ADRuevenue;
import model.Bill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/getBills")
public class ADRuevenuetablebill extends HttpServlet {
    private ADRuevenue billDAO = new ADRuevenue();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        List<Bill> bills = billDAO.getBillsByDateRange(LocalDate.parse(startDateStr), LocalDate.parse(endDateStr));

        // Custom Serializer for LocalDateTime
        JsonSerializer<LocalDateTime> serializer = (src, typeOfSrc, context) ->
                context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, serializer).create();
        String json = gson.toJson(bills);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}

