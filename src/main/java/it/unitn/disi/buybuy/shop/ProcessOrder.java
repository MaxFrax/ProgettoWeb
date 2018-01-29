package it.unitn.disi.buybuy.shop;

import java.util.logging.Level;
import java.util.logging.Logger;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.PurchaseDAO;
import it.unitn.disi.buybuy.dao.entities.Purchase;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.CartItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessOrder extends HttpServlet {

    PurchaseDAO purchaseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            purchaseDAO = daoFactory.getDAO(PurchaseDAO.class);

        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            clientRedirect("/error", response);
            return;
        }

        // Get cart
        Map<Integer, CartItem> cart = (Map) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            clientRedirect("/error", response);
            return;
        }

        String shipping = request.getParameter("shipping");
        if (!isEmptyParam(shipping)) {
            // Validate address form
            ArrayList<String> params = new ArrayList<>();
            String streetName = request.getParameter("street_name");
            params.add(streetName);
            String streetNumber = request.getParameter("street_number");
            params.add(streetNumber);
            String city = request.getParameter("city");
            params.add(city);
            String province = request.getParameter("province");
            params.add(province);
            String postalCode = request.getParameter("postal_code");
            params.add(postalCode);
            boolean emptyInput = false;
            for (String param : params) {
                param = param.trim();
                if (isEmptyParam(param)) {
                    emptyInput = true;
                    break;
                }
            }
            if (emptyInput) {
                clientRedirect("/error", response);
                return;
            }
            // Parse street number and postal code as numbers
            Integer postalNum = null;
            Integer streetNum = null;
            try {
                streetNum = Integer.valueOf(streetNumber);
                postalNum = Integer.valueOf(postalCode);
            } catch (NumberFormatException ex) {
                clientRedirect("/error", response);
                return;
            }

            // TODO: Create address object to add to Purchase in DB
        }

        // Basic payment form validation, since there isn't a payment engine
        // Validate payment information
        ArrayList<String> params = new ArrayList<>();
        String cardHolder = request.getParameter("card_holder");
        params.add(cardHolder);
        String cardNumber = request.getParameter("card_number");
        params.add(cardNumber);
        String expMonth = request.getParameter("exp_month");
        params.add(expMonth);
        String expYear = request.getParameter("exp_year");
        params.add(expYear);
        String securityCode = request.getParameter("security_code");
        params.add(securityCode);
        boolean emptyInput = false;
        for (String param : params) {
            param = param.trim();
            if (isEmptyParam(param)) {
                emptyInput = true;
                break;
            }
        }
        
        if (emptyInput) {
            clientRedirect("/error", response);
            return;
        }
        
        // Parse integer data types
        Long cardNum = null;
        Integer expMonthNum = null;
        Integer expYearNum = null;
        Integer securityCodeNum = null;
        try {
            cardNum = Long.valueOf(cardNumber);
            expMonthNum = Integer.valueOf(expMonth);
            expYearNum = Integer.valueOf(expYear);
            securityCodeNum = Integer.valueOf(securityCode);
        } catch (NumberFormatException ex) {
            clientRedirect("/error", response);
            return;
        }

        if (dummyPaymentSuccess()) {
            // Add purchase to DB
            try {
                for (Entry<Integer, CartItem> entry : cart.entrySet()) {
                    // Create purchase fpr each item in cart
                    Purchase purchase = new Purchase();
                    purchase.setItem(entry.getValue().getItem());
                    purchase.setUser(user);
                    purchaseDAO.insert(purchase);
                }
            } catch (DAOException ex) {
                Logger.getLogger(ProcessOrder.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                clientRedirect("/error", response);
                return;
            }

            // Client redirect to success page
            clientRedirect("/order_success", response);
            return;

        }

    }

    private boolean isEmptyParam(String param) {
        return (param == null || param.trim().length() == 0);
    }

    private void clientRedirect(String path, HttpServletResponse response) throws IOException {
        String contextPath = this.getServletContext().getContextPath();
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(contextPath + path);
        writer.close();
    }

    private boolean dummyPaymentSuccess() {
        return true;
    }

}
