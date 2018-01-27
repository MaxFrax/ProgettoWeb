package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.auth.EmailUtil;
import it.unitn.disi.buybuy.auth.PasswordHashing;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.Message;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;
    private ShopDAO shopDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            userDAO = daoFactory.getDAO(UserDAO.class);
            shopDAO = daoFactory.getDAO(ShopDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        try {
            user = userDAO.getByPrimaryKey(user.getId());

            if (user == null) {
                forwardToErrorPage(request, response);
                return;
            }
            String name = request.getParameter("name");
            String lastname = request.getParameter("last_name");
            String username = request.getParameter("username");
            String mail = request.getParameter("mail");
            String password = request.getParameter("password");
            String confirm_password = request.getParameter("confirm_password");
            String new_password = request.getParameter("new_password");
            String new_confirm_password = request.getParameter("new_confirm_password");

            //null name
            if ("".equals(name)) {
                request.setAttribute("message", "il campo \"Nome\" non può essere vuoto");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //null lastname
            if ("".equals(lastname)) {
                request.setAttribute("message", "il campo \"Cognome\" non può essere vuoto");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //null username
            if ("".equals(username)) {
                request.setAttribute("message", "il campo \"Username\" non può essere vuoto");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //null mail
            if ("".equals(mail)) {
                request.setAttribute("message", "il campo \"Mail\" non può essere vuoto");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //invalid mail
            if (!EmailUtil.isValidEmail(mail)) {
                request.setAttribute("message", "mail non valida");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //differences beetween the two password
            if (!password.equals(confirm_password)) {
                request.setAttribute("message", "La due password inserite non corrispondono");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //new password differences
            if (!new_password.equals(new_confirm_password)) {
                request.setAttribute("message", "Le due nuove password non corrispondono");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //password invalid
            PasswordHashing hash = new PasswordHashing();
            String[] saltAndHash = userDAO.getSaltAndHashByEmail(user.getEmail());
            if (saltAndHash != null) {
                password = hash.hashPassword(password, saltAndHash[0]);
                if (!password.equals(saltAndHash[1])) {
                    request.setAttribute("message", "Password errata o mancante i cambiamenti non possono essere effettuati");
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                }
            }
            //mail già in uso
            if (!user.getEmail().equals(mail) && userDAO.getByUsername(mail) != null) {
                request.setAttribute("message", "Mail già in uso");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            //username già in uso
            if (!user.getUsername().equals(username) && userDAO.getByUsername(username) != null) {
                request.setAttribute("message", "Username già in uso");
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            }
            if (!new_password.equals("") && new_password.equals(new_confirm_password)) {
                String salt = hash.getSalt();
                user.setHashSalt(salt);
                String hashedPassword = hash.hashPassword(new_password, salt);
                user.setHashPassword(hashedPassword);
                user.setConfirmationID(null);
                userDAO.update(user);
            }

            user.setName(name);
            user.setLastname(lastname);
            user.setEmail(mail);
            user.setUsername(username);
            userDAO.updateProfile(user);
            request.setAttribute("message", "Profilo salvato con successo!");
            request.getSession().setAttribute("user", user);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        } catch (DAOException | NoSuchAlgorithmException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", new Message(Message.Type.ERROR));
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

}
