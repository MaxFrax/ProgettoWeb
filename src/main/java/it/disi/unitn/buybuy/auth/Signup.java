package it.disi.unitn.buybuy.auth;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import java.io.IOException;
import java.security.MessageDigest;
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
    private SecureRandom random;

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
        // Initialize RNG
        this.random = new SecureRandom();
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

        // Generate password hash
        byte[] salt = getSalt();
        byte[] password = req.getParameter("pass").getBytes();
        byte[] bytes = prependSalt(salt, password);
        String hashPassword = null;
        try {
            hashPassword = sha256(bytes);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            res.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        
        // Create user
        User user = new User();
        user.setEmail(email);
        user.setName(req.getParameter("name"));
        user.setLastname(req.getParameter("surname"));
        user.setType(User.Type.REGISTRATION_PENDING);
        user.setUsername(req.getParameter("username"));
        user.setHashPassword(hashPassword);
        user.setHashSalt(DatatypeConverter.printHexBinary(salt));
        
        // Insert user into DB
        try {
            userDao.insert(user);
            res.sendRedirect(req.getContextPath() + "/success.jsp");
        } catch (DAOException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            res.sendRedirect(req.getContextPath() + "/error.jsp");
        }

    }

    /**
     * Generate random 32-byte length salt to use with password hash.
     * @return an array of byte representing salt
     */
    private byte[] getSalt() {
        byte[] salt = new byte[32];
        this.random.nextBytes(salt);
        return salt;
    }

    /**
     * Prepend salt to password.
     * @param salt salt to prepend
     * @param password password
     * @return Password with salt prepended
     */
    private byte[] prependSalt(byte[] salt, byte[] password) {
        byte[] result = new byte[salt.length + password.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(password, 0, result, salt.length, password.length);
        return result;
    }

    /**
     * Generate SHA-256 string from an array of bytes.
     * @param bytes input array of the SHA-256 function
     * @return hash string calculated from input array
     * @throws NoSuchAlgorithmException 
     */
    private String sha256(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);
        String hash = DatatypeConverter.printHexBinary(md.digest());
        return hash;
    }
    
    private void test() {
        // Converti password in byte[] (prendi stringa locale)
        byte[] input = "salepepe".getBytes();
        // Anteponi salt in byte[] (prendi stringa dal DB)
        String saltStr = "619E41CBCC6D3A7E3E5EB89C718A0955F7007A5DEFB76FFAD4BCAF2AC54C781C";
        input = prependSalt(saltStr.getBytes(), input);
        String resultHash = null;
        try {
            // Calcola hash
            resultHash = sha256(input);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Confronta hash calcolato con quello nel DB
        System.err.println(resultHash.equals("939FBF9F1C38941BE9A80D30B2A5744372DBDFA2C09C8A60AE42F391EA04511B"));
    }

}
