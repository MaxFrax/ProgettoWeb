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
import it.unitn.disi.buybuy.dao.entities.Item;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maxfrax
 */
public class Search extends HttpServlet {

    ItemDAO itemDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for item storage system");
        }
        try {
            itemDao = daoFactory.getDAO(ItemDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for item storage system", ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Item> res = null;
        String query = request.getParameter("query");
        try {
            int category_id = Integer.parseInt(request.getParameter("category"));
            if (query.isEmpty()) {
                res = itemDao.getByCategory(category_id);
            } else {
                res = itemDao.getByCategoryAndQuery(category_id, query);
            }
        } catch (NumberFormatException ex) {
            try {
                // Numero di categoria non intero, per cui secondo la logica del frontend è vuoto = non settato
                res = itemDao.getAll();
            } catch (DAOException ex1) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (DAOException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("results", res);
        request.getRequestDispatcher("results.jsp").forward(request, response);
    }

}
