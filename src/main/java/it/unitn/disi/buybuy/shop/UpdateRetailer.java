package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.RetailerDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.entities.Retailer;
import it.unitn.disi.buybuy.dao.entities.Shop;
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

public class UpdateRetailer extends HttpServlet {

    private final int MAX_STREET_LENGTH = 100;
    private final int MAX_CITY_LENGTH = 50;
    private final int MAX_PROVINCE_LENGTH = 50;
    RetailerDAO retailerDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory");
            }
            retailerDAO = daoFactory.getDAO(RetailerDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Errors to be shown in JSP
        ArrayList<String> errors = new ArrayList<>();
        request.setAttribute("errors", errors);

        // Get shop from session
        HttpSession session = request.getSession();
        Shop shop = (Shop) session.getAttribute("myShop");
        if (shop == null) {
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        // If retailer does not exist, it means that the seller has not added
        // one yet.
        Retailer retailer = (Retailer) session.getAttribute("retailer");
        boolean isNewRetailer = (retailer == null);
        if (isNewRetailer) {
            retailer = new Retailer();
            retailer.setShop(shop);
        }

        // Street name
        String streetName = request.getParameter("route");
        if (isEmptyParam(streetName)) {
            errors.add("Inserisci la via");
        } else {
            streetName = streetName.trim();
            if (streetName.length() > MAX_STREET_LENGTH) {
                errors.add("Il nome della via può essere lungo al massimo " + MAX_STREET_LENGTH + "caratteri");
            } else {
                retailer.setStreetName(streetName);
            }
        }

        // Street number
        String streetNumber = request.getParameter("street_number");
        if (isEmptyParam(streetNumber)) {
            errors.add("Inserisci il numero civico");
        } else {
            try {
                Integer number = Integer.valueOf(streetNumber);
                retailer.setStreetNumber(number);
            } catch (NumberFormatException ex) {
                errors.add("Numero civico non valido");
            }
        }

        // City
        String city = request.getParameter("administrative_area_level_3");
        if (isEmptyParam(city)) {
            errors.add("Inserisci la città");
        } else {
            city = city.trim();
            if (city.length() > MAX_CITY_LENGTH) {
                errors.add("Il nome della città può essere lungo al massimo " + MAX_CITY_LENGTH + "caratteri");
            } else {
                retailer.setCity(city);
            }
        }

        // Province 
        String province = request.getParameter("administrative_area_level_2");
        if (isEmptyParam(province)) {
            errors.add("Inserisci la provincia");
        } else {
            province = province.trim();
            if (province.length() > MAX_PROVINCE_LENGTH) {
                errors.add("Il nome della provincia può essere lungo al massimo " + MAX_PROVINCE_LENGTH + "caratteri");
            } else {
                retailer.setProvince(province);
            }
        }

        // Postal code
        String postalCode = request.getParameter("postal_code");
        if (isEmptyParam(postalCode)) {
            errors.add("Inserisci il codice postale");
        } else {
            try {
                Integer code = Integer.valueOf(postalCode);
                retailer.setPostalCode(code);
            } catch (NumberFormatException ex) {
                errors.add("Codice postale non valido");
            }
        }

        // Forward back to form with errors
        if (!errors.isEmpty()) {
            request.getRequestDispatcher("/myshop.jsp").forward(request, response);
            return;
        }

        // Latitude and longitude
        String latitude = request.getParameter("lat");
        String longitude = request.getParameter("lng");
        try {
            if (isEmptyParam(latitude) || isEmptyParam(longitude)) {
                throw new NumberFormatException("Coordinates are empty");
            }
            Float lat = Float.valueOf(latitude);
            Float lng = Float.valueOf(longitude);
            retailer.setLatitude(lat);
            retailer.setLongitude(lng);
        } catch (NumberFormatException ex) {
            Logger.getLogger(UpdateRetailer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        // Insert or update retailer
        try {
            if (isNewRetailer) {
                retailer.setOpenTimetable(null);
                Long retailerId = retailerDAO.insert(retailer);
                retailer.setId(retailerId);
            } else {
                retailerDAO.update(retailer);
            }
            // Add retailer to session
            session.setAttribute("retailer", retailer);
            // Forward back to form with success message
            request.setAttribute("successMsg", new Message("Le modifiche sono state salvate"));
            request.getRequestDispatcher("/myshop.jsp").forward(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(UpdateRetailer.class.getName()).log(Level.SEVERE, "Failed to update or insert retailer in DB", ex);
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

    private boolean isEmptyParam(String param) {
        return (param == null || param.trim().length() == 0);
    }

}
