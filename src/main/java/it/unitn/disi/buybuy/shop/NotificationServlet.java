
package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.IssueDAO;
import it.unitn.disi.buybuy.dao.entities.Issue;
import it.unitn.disi.buybuy.dao.entities.User;
import it.unitn.disi.buybuy.types.Message;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author apell
 */
public class NotificationServlet extends HttpServlet {
    private IssueDAO issueDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            issueDAO = daoFactory.getDAO(IssueDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {            
            forwardToErrorPage(request, response);
            return;
        }
        if(user.getType() == User.Type.SELLER){
            try {
                List<Issue> issues = issueDAO.getNotReadedBySellerId(user.getId());
                for (Issue issue : issues) {
                    issue = issueDAO.setSellerRead(issue);
                }    
                request.setAttribute("issues", issues);
                request.setAttribute("title", "Nuove anomalie");
                request.getRequestDispatcher("/notifications.jsp").forward(request, response);
            } catch (DAOException ex) {
                throw new ServletException(ex.getMessage(), ex);
            }
        }
        else if(user.getType() == User.Type.ADMINISTRATOR){
            try {
                List<Issue> issues = issueDAO.getAllOpened();
                for (Issue issue : issues) {
                    issue = issueDAO.setAdminRead(issue);
                }            
                request.setAttribute("issues", issues);
                request.setAttribute("title", "Anomalie aperte");
                request.getRequestDispatcher("/notifications.jsp").forward(request, response);
            } catch (DAOException ex) {
                throw new ServletException(ex.getMessage(), ex);
            }
        }
        else
            forwardToErrorPage(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {            
            forwardToErrorPage(request, response);
            return;
        }
        if(user.getType() == User.Type.SELLER){
            try {
                List<Issue> issues = issueDAO.getBySellerId(user.getId());   
                request.setAttribute("issues", issues);
                request.setAttribute("title", "Tutte le anomalie");
                request.getRequestDispatcher("/notifications.jsp").forward(request, response);
            } catch (DAOException ex) {
                throw new ServletException(ex.getMessage(), ex);
            }
        }
        else if(user.getType() == User.Type.ADMINISTRATOR){
            try {
                List<Issue> issues = issueDAO.getAll();           
                request.setAttribute("issues", issues);
                request.setAttribute("title", "Tutte le anomalie");
                request.getRequestDispatcher("/notifications.jsp").forward(request, response);
            } catch (DAOException ex) {
                throw new ServletException(ex.getMessage(), ex);
            }
        }
        else
            forwardToErrorPage(request, response);
    }
    
    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("message", new Message(Message.Type.ERROR));
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
