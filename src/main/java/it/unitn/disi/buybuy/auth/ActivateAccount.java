package it.unitn.disi.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivateAccount extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve id GET parameter
        String confirmationID = request.getParameter("id");
        if (confirmationID == null || confirmationID.isEmpty()) {
            sendErrorResponse(response);
            return;
        }

        // Activate user
        User user = null;
        try {            
            user = userDAO.getByConfirmationID(confirmationID);
            if (user == null || user.getType() != User.Type.REGISTRATION_PENDING) {
                throw new DAOException();
            }
            if (request.getParameter("seller") != null) {
                user.setType(User.Type.SELLER);
            } else {
                user.setType(User.Type.REGISTERED);
            }
            user.setConfirmationID(null);
            // Update user in DB
            userDAO.update(user);
        } catch (DAOException ex) {
            sendErrorResponse(response);
            return;
        }
        
        // Login user & redirect to home page
        request.getSession().setAttribute("user", user);
        response.sendRedirect(response.encodeRedirectURL(""));

    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<b>Impossibile attivare l'account. Si prega di contattare gli amministratori.</b>");
    }

}
