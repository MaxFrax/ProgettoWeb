package it.unitn.disi.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends HttpServlet {

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
        req.getRequestDispatcher("signup.html").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        /* TODO
            - strip user input
            - validation feedback (redirect to form with error popup)
            - email confirmation using JavaMail
         */
        
        // Trim spaces from input
        String name = req.getParameter("name").trim();
        String lastname = req.getParameter("surname").trim();
        String username = req.getParameter("username").trim();
        String email = req.getParameter("email").trim();
        
        // Validate username and email
        boolean validInput = isValidUsername(username) && isValidEmail(email);
        if (validInput) {
            try {
                // Check that user is not duplicate
                boolean duplicateUser = isDuplicateUser(email, username);
                if (!duplicateUser) {
                    // Create user
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setLastname(lastname);
                    user.setType(User.Type.REGISTRATION_PENDING);
                    user.setUsername(username);
                    // Generate hash from password
                    String password = req.getParameter("pass");
                    String salt = passwordHashing.getSalt();
                    String hashedPassword = passwordHashing.hashPassword(password, salt);
                    user.setHashPassword(hashedPassword);
                    user.setHashSalt(salt);
                    // Insert user into DB
                    userDao.insert(user);
                    res.sendRedirect(req.getContextPath() + "/success.jsp");
                } else {
                    // TODO redirect to form w/ duplicate user error
                    System.out.println("Duplicate user");
                    res.sendRedirect(req.getContextPath() + "/error.jsp");
                }
            } catch (DAOException | NoSuchAlgorithmException ex) {
                // TODO redirect to form w/ generic error
                System.out.println("Failed to check if user is duplicate");
                res.sendRedirect(req.getContextPath() + "/error.jsp");
            }
        } else {
            // TODO redirect to form w/ invalid input error
            System.out.println("Invalid username and/or email");
            res.sendRedirect(req.getContextPath() + "/error.jsp");
        }

    }

    private boolean isValidEmail(String email) {
        boolean isValid = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidUsername(String username) {
        // Username between 3 and 10 characters long.
        // It contains characters, numbers and the ., -, _ symbols.
        String USERNAME_PATTERN = "^[a-z0-9._-]{3,10}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isDuplicateUser(String email, String username) throws DAOException {
        boolean isDuplicate = false;
        User duplEmail = userDao.getByEmail(email);
        User duplUsername = userDao.getByUsername(username);
        return !(duplEmail == null && duplUsername == null);
    }

}
