package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.types.CartItem;
import it.unitn.disi.buybuy.types.Message;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateCart extends HttpServlet {

    ItemDAO itemDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for item storage system");
        }
        try {
            itemDAO = daoFactory.getDAO(ItemDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for item storage system", ex);
        }
    }

    /**
     * Add, update quantity, or remove a single item to shopping cart.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Parse parameters
        Enumeration<String> paramNames = request.getParameterNames();
        Integer itemId = null;
        Integer itemQty = null;
        try {
            if (paramNames.hasMoreElements()) {
                String id = paramNames.nextElement();
                itemId = Integer.valueOf(id);
                itemQty = Integer.valueOf(request.getParameter(id));
                if (itemQty < 0) {
                    throw new NumberFormatException("Item quantity cannot be negative");
                }
            } else {
                throw new ServletException("No parameters");
            }
        } catch (NumberFormatException | ServletException ex) {
            Logger.getLogger(UpdateCart.class.getName()).log(Level.INFO, null, ex);
            forwardToErrorPage(request, response);
            return;
        }

        // Retrieve shopping cart or instantiate a new one
        HttpSession session = request.getSession();
        Map<Integer, CartItem> cart = (Map) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap();
            session.setAttribute("cart", cart);
        }

        // Check if item is already in cart        
        CartItem cartItem = cart.get(itemId);
        boolean newItem = false;
        if (cartItem == null) {
            newItem = true;
            try {
                // Retrieve item from DB
                Item item = itemDAO.getByPrimaryKey(itemId);
                if (item == null) {
                    throw new DAOException("ItemDAO.getByPrimaryKey() returned null");
                }
                // Add new item to shopping cart
                cart.put(itemId, new CartItem(item, itemQty));               
            } catch (DAOException ex) {
                Logger.getLogger(UpdateCart.class.getName()).log(Level.WARNING, null, ex);
                forwardToErrorPage(request, response);
                return;
            }
        } else if (itemQty == 0) {
            // Remove item from shopping cart
            cart.remove(itemId);
        } else {
            // Increment item quantity
            Integer newQty = cartItem.getQuantity() + itemQty;
            cartItem.setQuantity(newQty);
        }

        String path = request.getContextPath() + "/cart";
        // If item is new in cart
        if (newItem) {
            path += "?new_item=" + itemId;
        }
        // Redirect to shopping cart page
        response.sendRedirect(path);
    }

    /**
     * Bulk update item quantities from shopping cart form.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get request parameters
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.isEmpty()) {
            forwardToErrorPage(request, response);
            return;
        }

        // Retrieve cart
        HttpSession session = request.getSession();
        Map<Integer, CartItem> cart = (Map) session.getAttribute("cart");
        if (cart == null) {
            forwardToErrorPage(request, response);
            return;
        }

        // Update cart items with new quantities
        try {
            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                // Convert key and value to Integer
                Integer itemId = Integer.valueOf(param.getKey());
                Integer itemQty = Integer.valueOf(param.getValue()[0]);
                if (itemQty < 0) {
                    throw new NumberFormatException("Item quantity cannot be negative");
                } else if (itemQty == 0) {
                    cart.remove(itemId);
                } else {
                    cart.get(itemId).setQuantity(itemQty);
                }
            }
        } catch (NumberFormatException ex) {
            Logger.getLogger(UpdateCart.class.getName()).log(Level.INFO, null, ex);
            forwardToErrorPage(request, response);
            return;
        }

        // Redirect to shopping cart page
        response.sendRedirect(request.getContextPath() + "/cart");

    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", new Message(Message.Type.ERROR));
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

}
