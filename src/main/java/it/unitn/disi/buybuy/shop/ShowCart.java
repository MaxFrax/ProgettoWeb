package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ShowCart", urlPatterns = {"/ShowCart"})
public class ShowCart extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check for new items added to cart
        String newItemId = request.getParameter("new_item");
        if (newItemId != null && !newItemId.isEmpty()) {
            Integer id = null;
            try {
                id = Integer.valueOf(newItemId);
                Item item = itemDAO.getByPrimaryKey(id);
                if (item == null) {
                    throw new DAOException("getByPrimaryKey() returned null");
                }
                request.setAttribute("newItem", item);
            } catch (NumberFormatException ex) {
                Logger.getLogger(UpdateCart.class.getName()).log(Level.SEVERE, "Failed to parse item id", ex);
            } catch (DAOException ex) {
                Logger.getLogger(UpdateCart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Show cart
        request.getRequestDispatcher("/cart.jsp").forward(request, response);

    }

}
