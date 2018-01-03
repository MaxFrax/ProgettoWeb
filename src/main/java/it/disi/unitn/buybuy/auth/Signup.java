/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.disi.unitn.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.dao.jdbc.JDBCUserDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maxfrax
 */
public class Signup extends HttpServlet {

    private UserDAO userDao;

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
        request.getRequestDispatcher("signup.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean validInput = true;
        // Fields validation
        String email = req.getParameter("email");
        validInput = validInput && email.contains("@");
        // Config of User object 
        if (validInput) {
            User user = new User();
            user.setEmail(email);
            user.setName(req.getParameter("name"));
            user.setLastname(req.getParameter("surname"));
            user.setType(User.Type.REGISTRATION_PENDING);
            user.setUsername(req.getParameter("username"));
            // TODO encrypt password
            user.setHashPassword(req.getParameter("pass"));
            // Check if insert gone okay            
            try {
                userDao.insert(user);
                resp.sendRedirect("/BuyBuy");
            } catch (DAOException ex) {
                Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                // TODO parametro errore
                doGet(req, resp);
            }
        }
    }

}
