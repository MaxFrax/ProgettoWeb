package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.RetailerDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.entities.Retailer;
import it.unitn.disi.buybuy.dao.entities.Shop;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.validator.routines.UrlValidator;

public class MyShop extends HttpServlet {

    ShopDAO shopDAO;
    RetailerDAO retailerDAO;
    private final int MAX_LENGTH_DESC = 255;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory");
            }
            shopDAO = daoFactory.getDAO(ShopDAO.class);
            retailerDAO = daoFactory.getDAO(RetailerDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve user from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        // Check that user is a seller
        if (!(user.getType() == User.Type.SELLER)) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        // Retrieve shop
        Shop shop = (Shop) session.getAttribute("myShop");
        if (shop == null) {
            // Find in database
            try {
                shop = shopDAO.getByOwnerId(user.getId());
                if (shop == null) {
                    throw new DAOException("Shop not found");
                }
                session.setAttribute("myShop", shop);
            } catch (DAOException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        }

        // Retrieve retailer (if any)
        Retailer retailer = (Retailer) session.getAttribute("retailer");
        if (retailer == null) {
            // Find in database
            try {
                retailer = retailerDAO.getByShopId(shop.getId());
                if (retailer != null) {
                    session.setAttribute("retailer", retailer);
                }
            } catch (DAOException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        }

        // Forward to JSP
        request.getRequestDispatcher("/myshop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check that user is logged in
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        // Retrieve request parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String website = request.getParameter("website");

        // Errors to be shown in JSP
        ArrayList<String> errors = new ArrayList<>();
        request.setAttribute("errors", errors);

        // Validate name
        if (isEmptyParam(name)) {
            errors.add("Il nome del negozio non può essere vuoto");
        }

        // Validate website
        if (!isEmptyParam(website)) {
            UrlValidator urlValidator = new UrlValidator();
            website = website.trim();
            if (!urlValidator.isValid(website)) {
                errors.add("Sito web non valido");
            }
        }

        // Validate description
        if (!isEmptyParam(description)) {
            description = description.trim();
            if (description.length() > MAX_LENGTH_DESC) {
                errors.add("La descrizione può esser lunga al massimo " + MAX_LENGTH_DESC + " caratteri");
            }
        }

        // If there are validation errors, abort
        if (!errors.isEmpty()) {
            request.getRequestDispatcher("/myshop.jsp").forward(request, response);
            return;
        }

        // Retrieve shop
        Shop shop = null;
        try {
            shop = shopDAO.getByOwnerId(user.getId());
            if (shop == null) {
                throw new DAOException("Shop not found");
            }
            // Update shop
            shop.setName(name);
            shop.setWebsite(website);
            shop.setDescription(description);
            shopDAO.update(shop);
            // Update shop also in session
            session.setAttribute("myShop", shop);
            request.setAttribute("successMsg", new Message("Le modifiche sono state salvate"));
        } catch (DAOException ex) {
            Logger.getLogger(MyShop.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            errors.add("Errore interno, riprovare più tardi");
        }

        // Forward back to form
        request.getRequestDispatcher("/myshop.jsp").forward(request, response);

    }

    private boolean isEmptyParam(String param) {
        return (param == null || param.trim().length() == 0);
    }

}
