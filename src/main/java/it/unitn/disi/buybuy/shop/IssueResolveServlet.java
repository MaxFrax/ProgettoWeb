
package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.IssueDAO;
import it.unitn.disi.buybuy.dao.entities.Issue;
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
public class IssueResolveServlet extends HttpServlet {
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
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            forwardToErrorPage(request, response);
            return;
        }
        if (user.getType() == User.Type.ADMINISTRATOR) {
            try {
                String id = request.getParameterNames().nextElement().substring(6);
                Issue issue = issueDAO.getByPrimaryKey(Integer.parseInt(id));
                Integer choice = Integer.parseInt(request.getParameter("choice" + id));
                if(choice != 0){
                    issue.setAdminChoice(Issue.AdminChoice.values()[choice]);
                    issueDAO.setAdminChoice(issue);
                    request.setAttribute("message", "Anomalia processata con successo");                    
                }
                else{
                    request.setAttribute("message", "Scegli un opzione");
                }
                
                request.getRequestDispatcher("/notification").forward(request, response);
                
            } catch (DAOException ex) {
                throw new ServletException(ex.getMessage(), ex);
            }
        } else {
            forwardToErrorPage(request, response);
        }
    }
    
    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", new Message(Message.Type.ERROR));
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
