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
import it.unitn.disi.buybuy.dao.entities.Retailer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
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
            final Float latitude = Float.parseFloat(request.getParameter("lat"));
            final Float longitude = Float.parseFloat(request.getParameter("lng"));
            List<Pair<Item, Retailer>> itemsWithPlace = itemDao.getWithRetailer();
            Collections.sort(itemsWithPlace, new Comparator<Pair<Item, Retailer>>() {
                @Override
                public int compare(Pair<Item, Retailer> o1, Pair<Item, Retailer> o2) {
                    // se distanza di o1 rispetto a me è maggiore di distanza da me di o2
                    Retailer r1 = o1.getValue();
                    Retailer r2 = o2.getValue();
                    Float distance1 = distance(latitude, longitude, r1.getLatitude(), r1.getLongitude());
                    Float distance2 = distance(latitude, longitude, r2.getLatitude(), r2.getLongitude());
                    return (int) Math.signum(distance1 - distance2);
                }
            }
            );
            res = new ArrayList<>();
            for (Pair<Item, Retailer> pair : itemsWithPlace) {
                res.add(pair.getKey());
            }
        } catch (NumberFormatException e) {
            // This catch executes when the place is not given by the user
            try {
                int category_id = Integer.parseInt(request.getParameter("category"));
                if (query == null || query.isEmpty()) {
                    res = itemDao.getByCategory(category_id);
                } else {
                    res = itemDao.getByCategoryAndQuery(category_id, query);
                }
            } catch (NumberFormatException ex) {
                // Numero di categoria non intero, per cui secondo la logica del frontend è vuoto = non settato
                res = itemDao.getByQuery(query);

            } catch (DAOException ex) {
                Logger.getLogger(Search.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (DAOException ex) {
            Logger.getLogger(Search.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("results", res);
        request.getRequestDispatcher("results.jsp").forward(request, response);
    }

    private Float distance(Float lat1, Float lon1, Float lat2, Float lon2) {
        final int R = 6371; // Radious of the earth
        Float latDistance = toRad(lat2 - lat1);
        Float lonDistance = toRad(lon2 - lon1);
        Float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(toRad(lat1)) * Math.cos(toRad(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
        Float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));

        return R * c;
    }

    private static Float toRad(Float value) {
        return (float) (value * Math.PI / 180);
    }
}
