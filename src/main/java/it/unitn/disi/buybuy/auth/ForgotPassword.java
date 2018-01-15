package it.unitn.disi.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgotPassword extends HttpServlet {

    private UserDAO userDAO;
    private PasswordHashing passwordHashing;
    private EmailUtil emailUtil;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        try {
            if (daoFactory == null) {
                throw new DAOFactoryException();
            }
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Failed to get DAO factory: " + ex.getMessage());
        }
        passwordHashing = new PasswordHashing();
        emailUtil = new EmailUtil();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/forgot_password.jsp"));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String emailAddress = request.getParameter("email");
        if (EmailUtil.isValidEmail(emailAddress)) {
            // Find user by email address
            emailAddress = emailAddress.trim();
            try {
                User user = userDAO.getByEmail(emailAddress);
                if (user != null && user.getType() != User.Type.REGISTRATION_PENDING) {
                    // Generate confirmation ID
                    String confirmationID = passwordHashing.getConfirmationID();
                    // Update user
                    user.setConfirmationID(confirmationID);
                    userDAO.update(user);
                    // Send email
                    sendConfirmationEmail(emailAddress, confirmationID);
                    request.setAttribute("successMessage", "Ti abbiamo inviato una email di conferma per impostare una nuova password");
                } else {
                    // User not found or registration pending
                    request.setAttribute("errorMessage", "Non esistono utenti registrati con questo indirizzo email");
                }
            } catch (DAOException | MessagingException ex) {
                request.setAttribute("errorMessage", "Errore interno, riprovare più tardi");
            }
        } else {
            // Invalid emailAddress address
            request.setAttribute("errorMessage", "Indirizzo email non valido");
        }

        request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
    }

    private void sendConfirmationEmail(String recipient, String id) throws MessagingException {
        // TODO: HTML template for emailAddress content (inject ID)
        String html = "Clicca <a href=\"http://localhost:8084/BuyBuy/reset_password?id=" + id + "\">qui</a> per reimpostare la password del tuo account.";
        emailUtil.sendEmail(recipient, "Reimpostazione password", html);
    }

}
