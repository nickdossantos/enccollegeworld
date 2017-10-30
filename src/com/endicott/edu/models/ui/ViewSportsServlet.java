package com.endicott.edu.models.ui;// Created by abrocken on 8/25/2017.

        import com.endicott.edu.models.datalayer.SimTalker;
        import com.endicott.edu.models.datalayer.SportsSimTalker;

        import javax.servlet.RequestDispatcher;
        import java.io.IOException;
        import java.util.logging.Logger;

public class ViewSportsServlet extends javax.servlet.http.HttpServlet {

    static private Logger logger = Logger.getLogger("ViewSportsServlet");

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String runId=request.getParameter("runid"); //college ID
        String server=request.getParameter("server");
        String sportName=request.getParameter("sportName");

        logger.info("Attempting to add sport: " + sportName + " to " + runId + " at server " + server);
        if (runId == null || server == null || sportName == null) {
            UiMessage message = new UiMessage("Can't add a team because missing information");
            request.setAttribute("message", message);
            logger.severe("Parameters bad for adding sport.");
        }
        else {
            SportsSimTalker.addSport(runId, server, sportName);
            logger.info("Added sport: " + sportName + " to " + runId + " at server " + server);
        }

        request.setAttribute("server", server);
        SimTalker.openCollegeAndStoreInRequest(server, runId, request);

        RequestDispatcher dispatcher=request.getRequestDispatcher("/viewsports.jsp");
        dispatcher.forward(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String runId=request.getParameter("runid"); //college ID
        String server=request.getParameter("server");
        request.setAttribute("server", server);

        if (request.getParameter("nextDayButton") != null) {
            SimTalker.nextDayAtCollege(server, runId);
        }

        // Attempt to fetch the college and load into
        // request attributes to pass to the jsp page.
        SimTalker.openCollegeAndStoreInRequest(server, runId, request);

        RequestDispatcher dispatcher=request.getRequestDispatcher("/viewsports.jsp");
        dispatcher.forward(request, response);
    }
}
