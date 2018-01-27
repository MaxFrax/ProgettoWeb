/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maxfrax
 */
public class Login extends HttpServlet {

    private UserDAO userDao;
    private PasswordHashing passwordHashing;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
        passwordHashing = new PasswordHashing();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, res);
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
        String password = request.getParameter("pass");
        String email = request.getParameter("email");

        try {
            // Get salt and hash from db to recalculate hash and check if password is correct
            String[] saltAndHash = userDao.getSaltAndHashByEmail(email);
            if (saltAndHash != null) {
                String hashedPassword = passwordHashing.hashPassword(password, saltAndHash[0]);
                if (hashedPassword.equals(saltAndHash[1])) {
                    User user = userDao.getByEmailAndPassword(email, hashedPassword);
                    if (user.getType() != User.Type.REGISTRATION_PENDING) {
                        // Logged in
                        request.getSession().setAttribute("user", user);
                        response.sendRedirect("/BuyBuy");
                    } else {
                        // Error, registration still pending
                        request.setAttribute("error_message", "Prima di effettuare l'accesso è necessario confermare l'account cliccando sul link che ti è stato inviato via email.");
                        doGet(request, response);
                    }
                } else {
                    // Error in login credentials
                    request.setAttribute("error_message", "Password non corretta.");
                    doGet(request, response);
                }
            } else {
                // Error in login
                request.setAttribute("error_message", "Non esistono utenti registrati con questa email.");
                doGet(request, response);
            }

        } catch (DAOException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
