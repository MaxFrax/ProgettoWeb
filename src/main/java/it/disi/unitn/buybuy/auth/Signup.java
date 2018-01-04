package it.disi.unitn.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

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
        // Test
        test();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("signup.html").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        /* TODO
            - email validation using JavaMail
            - username validation using regex
            - add setSalt() to User DAO (add column to DB schema)
         */
        // Parameters validation
        boolean validInput = true;
        String email = req.getParameter("email");
        validInput = email.contains("@");
        if (!validInput) {
            res.sendRedirect(req.getContextPath() + "/error.jsp");
        }

        // Create user
        User user = new User();
        user.setEmail(email);
        user.setName(req.getParameter("name"));
        user.setLastname(req.getParameter("surname"));
        user.setType(User.Type.REGISTRATION_PENDING);
        user.setUsername(req.getParameter("username"));

        // Generate password hash
        String pass = req.getParameter("pass");
        PasswordEncrypter enc = new PasswordEncrypter(pass);
        try {
            String hashPassword = enc.getEncryptedPassword();
            user.setHashPassword(hashPassword);
            user.setHashSalt(enc.getSaltString());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            res.sendRedirect(req.getContextPath() + "/error.jsp");
        }

        // Insert user into DB
        try {
            userDao.insert(user);
            res.sendRedirect(req.getContextPath() + "/success.jsp");
        } catch (DAOException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            res.sendRedirect(req.getContextPath() + "/error.jsp");
        }

    }

    private void test() {
        // Anteponi salt in byte[] (prendi stringa dal DB)
        String saltStr = "-z���N:���-5��O�@\n" +
"F)i���r�-�W                                 ";
        PasswordEncrypter enc = new PasswordEncrypter(saltStr, "pallonegonfiabile");
        String resultHash = null;
        try {
            resultHash = enc.getEncryptedPassword();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Confronta hash calcolato con quello nel DB
        System.err.println(resultHash);
        System.err.println(resultHash.equals("BE73883A5E52BC18418E18FE53B8C1E5560EE715E25BC8DB235614E17C669001"));
    }

}
