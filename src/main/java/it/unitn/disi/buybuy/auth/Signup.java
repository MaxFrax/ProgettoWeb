package it.unitn.disi.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
        req.getRequestDispatcher("signup.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        /* TODO
            - validation feedback (redirect to signup.jsp with errors)
            - email confirmation using JavaMail
            - input length constraints (?)
         */
        ArrayList<String> errors = new ArrayList<>();
        req.setAttribute("errors", errors); // Now it's available in JSP by ${errors}

        // Name
        String name = req.getParameter("name").trim();
        if (name == null || name.isEmpty()) {
            errors.add("Inserisci il tuo nome.");
        }

        // Lastname
        String lastname = req.getParameter("lastname").trim();
        if (lastname == null || lastname.isEmpty()) {
            errors.add("Inserisci il tuo cognome.");
        }

        // Username
        String username = req.getParameter("username").trim();
        if (username == null || username.isEmpty()) {
            errors.add("Inserisci uno username.");
        } else if (!isValidUsername(username)) {
            errors.add("Lo username può essere lungo tra i 3 e i 10 caratteri e può contenere solo lettere, numeri, e i simboli . - _.");
        } else {
            try {
                if (isDuplicateUsername(username)) {
                    errors.add("Lo username inserito è già in uso.");
                }
            } catch (DAOException ex) {
                errors.clear();
                errors.add("Errore interno. Riprovare più tardi.");
                req.getRequestDispatcher("signup.jsp").forward(req, res);
                return; // Stop executing this servlet.
            }
        }

        // Email
        String email = req.getParameter("email").trim();
        if (email == null || email.isEmpty()) {
            errors.add("Inserisci il tuo indirizzo email.");
        } else if (!isValidEmail(email)) {
            errors.add("Indirizzo email non corretto.");
        } else {
            try {
                if (isDuplicateEmail(email)) {
                    errors.add("L'indirizzo email inserito è già in uso.");
                }
            } catch (DAOException ex) {
                errors.clear();
                errors.add("Errore interno. Riprovare più tardi.");
                req.getRequestDispatcher("signup.jsp").forward(req, res);
                return; // Stop executing this servlet.
            }
        }
        
        // Password
        String password = req.getParameter("password");
        if (password == null || password.isEmpty()) {
            errors.add("Inserisci una password.");
        }

        // Privacy agreement (checkbox)
        String privacy = req.getParameter("privacy");
        if (privacy == null) {
            errors.add("L'accettazione della normativa sulla privacy è obbligatoria.");
        }

        // If there are errors, forward to JSP with same request
        // JSP will use "errors" attribute to print <span> with error strings
        if (!errors.isEmpty()) {
            req.getRequestDispatcher("signup.jsp").forward(req, res);
            return; // Stop executing this servlet.
        }

        // Create user
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setType(User.Type.REGISTRATION_PENDING); // Default user type

        // Add user to DB
        try {
            // Calculate password hash
            String salt = passwordHashing.getSalt();
            String hashedPassword = passwordHashing.hashPassword(password, salt);
            user.setHashPassword(hashedPassword);
            user.setHashSalt(salt);
            // Insert user into DB
            userDao.insert(user);
            req.getRequestDispatcher("success.jsp").forward(req, res);
        } catch (NoSuchAlgorithmException | DAOException ex) {
            errors.add("Errore interno. Riprovare più tardi.");
            req.getRequestDispatcher("signup.jsp").forward(req, res);
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

    private boolean isDuplicateEmail(String email) throws DAOException {
        User user = userDao.getByEmail(email);
        return (user != null);
    }

    private boolean isDuplicateUsername(String username) throws DAOException {
        User user = userDao.getByUsername(username);
        return (user != null);
    }

}
