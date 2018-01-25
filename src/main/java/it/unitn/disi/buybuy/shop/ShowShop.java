package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.RetailerDAO;
import it.unitn.disi.buybuy.dao.ReviewDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Retailer;
import it.unitn.disi.buybuy.dao.entities.Review;
import it.unitn.disi.buybuy.dao.entities.Shop;
import it.unitn.disi.buybuy.types.Message;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowShop extends HttpServlet {

    private ItemDAO itemDAO;
    private ReviewDAO reviewDAO;
    private RetailerDAO retailerDAO;
    private ShopDAO shopDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            retailerDAO = daoFactory.getDAO(RetailerDAO.class);
            shopDAO = daoFactory.getDAO(ShopDAO.class);
            itemDAO = daoFactory.getDAO(ItemDAO.class);
            reviewDAO = daoFactory.getDAO(ReviewDAO.class);
            
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String shopId = request.getParameter("id");
        if (shopId == null || shopId.isEmpty()) {
            forwardToErrorPage(request, response);
            return;
        }
        
        Retailer retailer = null;
        Shop shop = null;
        
        try {
            Integer id = Integer.valueOf(shopId);
            shop = shopDAO.getByPrimaryKey(id);
            retailer = retailerDAO.getByShopID(id);
            if (shop == null) {
                throw new DAOException();
            }
        } catch (DAOException | NumberFormatException ex) {
            forwardToErrorPage(request, response);
            return;
        }
        request.setAttribute("shop", shop);
        request.setAttribute("retailer", retailer);
        
        
        Item item = null;
        List<Review> reviews = null;
        try {
            Integer id = Integer.valueOf(shopId);
            item = itemDAO.getByShopID(id);
            Integer itemid = item.getId();
            reviews = reviewDAO.getByItemID(itemid);
            if (item == null) {
                throw new DAOException();
            }
        } catch (DAOException | NumberFormatException ex) {
            forwardToErrorPage(request, response);
            return;
        }
        request.setAttribute("item", item);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("/shop.jsp").forward(request, response);
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("message", new Message(Message.Type.ERROR));
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

}
