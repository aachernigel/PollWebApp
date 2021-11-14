package web;

import PollManagerLib.PollWrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "AccessAdminServlet", value = "/AccessAdminServlet")
public class AccessAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection.getConnection();
        try {
            // set a parameter to inform a user that he cannot use admin functions for this poll
            PreparedStatement preparedStatement = DBConnection.conn.prepareStatement("SELECT pollID FROM poll WHERE creatorID = ?");
            preparedStatement.setString(1, (String) request.getSession().getAttribute("userID"));
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean found = false;
            while (resultSet.next())
                if (resultSet.getString("pollID").equals(PollWrapper.manager.getPollID())) {
                    found = true;
                    response.sendRedirect("adminMenu.jsp");
                }
            if (!found)
                response.sendRedirect("home.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
