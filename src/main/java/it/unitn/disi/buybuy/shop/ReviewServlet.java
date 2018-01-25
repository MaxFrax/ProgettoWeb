/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.ReviewDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Review;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.Message;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author apell
 */
public class ReviewServlet extends HttpServlet {
    
    private ReviewDAO reviewDAO;
    private ItemDAO itemDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            reviewDAO = daoFactory.getDAO(ReviewDAO.class);
            itemDAO = daoFactory.getDAO(ItemDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        Integer item_id = Integer.parseInt(request.getParameter("id"));
        if (user == null) {            
            forwardToErrorPage(request, response);
            return;
        }
        try {
            Review review = reviewDAO.getByItemIDAndUserID(user.getId(),item_id);
            Item item = itemDAO.getByPrimaryKey(item_id);
            if(review.getId()!= null){
                request.setAttribute("message", new Message("Hai già recensito questo oggetto"));
                request.getRequestDispatcher("/OrderServlet").forward(request, response);
            }            
            request.setAttribute("item", item);
            request.getRequestDispatcher("/review.jsp").forward(request, response);
        } catch (DAOException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Integer item_id = Integer.parseInt(request.getParameter("id"));
        if (user == null) {
            forwardToErrorPage(request, response);
            return;
        }
        try {
            Review review = reviewDAO.getByItemIDAndUserID(user.getId(), item_id);
            if(review.getId()== null){
                Item item = itemDAO.getByPrimaryKey(item_id);
                review.setItem(item);
                review.setUser(user);
                Integer rating = 0;
                if("".equals(request.getParameter("user_rating"))){
                    request.setAttribute("message", new Message("Dai un voto all' oggetto"));
                    request.setAttribute("item", item);
                    request.getRequestDispatcher("review.jsp").forward(request, response);
                }
                else{
                    rating = Integer.parseInt(request.getParameter("user_rating"));
                    review.setRating(rating);
                    review.setDescription(request.getParameter("user_review"));
                    reviewDAO.insert(review);
                    request.setAttribute("message", new Message("Recensione inviata con successo! Grazie per il tuo contributo"));
                    request.getRequestDispatcher("/OrderServlet").forward(request, response);
                }
            }
            else{
                request.setAttribute("message", new Message("Hai già recensito questo oggetto"));
                request.getRequestDispatcher("/OrderServlet").forward(request, response);
            }
            
            
        } catch (DAOException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }
    
    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("message", new Message(Message.Type.ERROR));
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
