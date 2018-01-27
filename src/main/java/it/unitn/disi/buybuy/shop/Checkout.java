package it.unitn.disi.buybuy.shop;

import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.CartItem;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If user is not logged in, redirect to login form
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // If cart is empty, redirect to shopping cart page
        Map<Integer, CartItem> cart = null;
        cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        
        // Number of items to pickup
        String pickupsCount = request.getParameter("pickups_count");
        if (isEmptyParam(pickupsCount)) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }
        
        // Parse pickups count as int
        Integer count = null;
        try {
            count = Integer.valueOf(pickupsCount);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }
        
        // If checked pickups is  less than the number of items in cart, show
        // shipping information during checkout
        if (count < cart.size()) {
            request.setAttribute("shipping", true);
        }

        // Forward to checkout
        request.setAttribute("checkoutOk", true);
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);

    }
    
    private boolean isEmptyParam(String param) {
        return (param == null || param.trim().length() == 0);
    }

}
