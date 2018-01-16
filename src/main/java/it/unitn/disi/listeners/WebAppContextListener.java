package it.unitn.disi.listeners;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.buybuy.dao.CategoryDAO;
import it.unitn.disi.buybuy.dao.entities.Category;
import it.unitn.disi.buybuy.shop.Main;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 */
public class WebAppContextListener implements ServletContextListener {
    
    DAOFactory daoFactory;

    /**
     * The serlvet container call this method when initializes the application
     * for the first time.
     *
     * @param sce the event fired by the servlet container when initializes the
     * application
     *
     * @author apello96
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        String dbURL = context.getInitParameter("dburl");

        try {
            // Initialize DAO factory
            JDBCDAOFactory.configure(dbURL);
            daoFactory = JDBCDAOFactory.getInstance();
            context.setAttribute("daoFactory", daoFactory);
            // Populate application scope with categories
            List<Category> categories = retrieveCategories();
            context.setAttribute("categories", categories);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(WebAppContextListener.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

    }

    /**
     * The servlet container call this method when destroyes the application.
     *
     * @param sce the event generated by the servlet container when destroyes
     * the application.
     *
     * @author apello96
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
    }

    private List<Category> retrieveCategories() throws DAOFactoryException {
        CategoryDAO categoryDAO = daoFactory.getDAO(CategoryDAO.class);
        List<Category> categories = null;
        try {
            categories = categoryDAO.getAll();
        } catch (DAOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOFactoryException(ex);
        }
        return categories;
    }
}
