
package it.unitn.disi.buybuy.shop;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.factories.DAOFactory;
import it.unitn.disi.buybuy.dao.IssueDAO;
import it.unitn.disi.buybuy.dao.PurchaseDAO;
import it.unitn.disi.buybuy.dao.entities.Issue;
import it.unitn.disi.buybuy.dao.entities.Purchase;
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
public class IssueServlet extends HttpServlet {

    private IssueDAO issueDAO;
    private PurchaseDAO purchaseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new DAOFactoryException("Failed to get DAO factory.");
            }
            issueDAO = daoFactory.getDAO(IssueDAO.class);
            purchaseDAO = daoFactory.getDAO(PurchaseDAO.class);
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
            Purchase purchase = purchaseDAO.getByUserIdAndItemId(user.getId(),item_id);
            if(purchase.getId() != null){
                Issue issue = issueDAO.getByPrimaryKey(purchase.getId());
                if(issue.getId()!= null){
                    request.setAttribute("message", new Message("Hai già segnalato un' anomalia relativa a questo oggetto"));
                    request.getRequestDispatcher("/orders").forward(request, response);
                }            
                request.setAttribute("purchase", purchase);
                request.getRequestDispatcher("/issue.jsp").forward(request, response);
            }
            else{
                forwardToErrorPage(request, response);
            }
        } catch (DAOException ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Integer purchase_id = Integer.parseInt(request.getParameter("id"));
        if (user == null) {
            forwardToErrorPage(request, response);
            return;
        }
        try {
            Issue issue = issueDAO.getByPrimaryKey(purchase_id);
            if(issue.getId()== null){
                Purchase purchase = purchaseDAO.getByPrimaryKey(purchase_id);
                issue.setPurchase(purchase);
                if("".equals(request.getParameter("description"))){
                    request.setAttribute("message", new Message("La descrizione non può essere vuota"));
                    request.setAttribute("purchase", purchase);
                    request.getRequestDispatcher("issue.jsp").forward(request, response);
                }
                else{
                    issue.setUserDescription(request.getParameter("description"));
                    issueDAO.insert(issue);
                    request.setAttribute("message", new Message("Il tuo problema è stato segnalato, attendi che se ne occupi un amministratore"));
                    request.getRequestDispatcher("/orders").forward(request, response);
                }
            }
            else{
                request.setAttribute("message", new Message("Hai già segnalato un' anomalia relativa a questo oggetto"));
                request.getRequestDispatcher("/orders").forward(request, response);
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
