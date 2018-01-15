package it.unitn.disi.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.Message;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetPassword extends HttpServlet {

    private UserDAO userDAO;
    private PasswordHashing passwordHashing;

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
        passwordHashing = new PasswordHashing();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/reset_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check confirmation ID parameter
        String confirmationID = request.getParameter("id");
        if (confirmationID == null || confirmationID.isEmpty()) {
            request.setAttribute("message", new Message(Message.Type.ERROR));
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }
        
        // Check password parameter
        String password = request.getParameter("password");
        if (password == null || password.isEmpty()) {
            request.setAttribute("message", new Message("Inserisci una password valida", Message.Type.ERROR));
            request.getRequestDispatcher("/reset_password.jsp").forward(request, response);
            return;
        }

        // Update user's password
        try {
            // Check that an active user with provided confirmation ID exists
            User user = userDAO.getByConfirmationID(confirmationID);
            if (user == null || user.getType() == User.Type.REGISTRATION_PENDING) {
                throw new DAOException();
            }
            // Update user's password
            String salt = passwordHashing.getSalt();
            user.setHashSalt(salt);
            String hashedPassword = passwordHashing.hashPassword(password, salt);
            user.setHashPassword(hashedPassword);
            user.setConfirmationID(null);
            userDAO.update(user);
            // Success
            request.setAttribute("message", new Message("Password reimpostata con successo"));
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        } catch (DAOException | NoSuchAlgorithmException ex) {
            request.setAttribute("message", new Message(Message.Type.ERROR));
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }
        
    }

}
