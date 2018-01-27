
package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.IssueDAO;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.PurchaseDAO;
import it.unitn.disi.buybuy.dao.entities.Issue;
import it.unitn.disi.buybuy.dao.entities.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apell
 */
public class JDBCIssueDAO extends JDBCDAO<Issue, Integer> implements IssueDAO {
    
    public JDBCIssueDAO(Connection con) {
        super(con);
        FRIEND_DAOS.put(PurchaseDAO.class, new JDBCPurchaseDAO(con));
        FRIEND_DAOS.put(ItemDAO.class, new JDBCItemDAO(con));
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Issue getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        Issue issue = new Issue();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM app.ISSUE WHERE id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()){
                    issue.setId(rs.getInt("ID"));
                    issue.setUserDescription(rs.getString("USER_DESCRIPTION"));
                    issue.setSellerRead(rs.getBoolean("SELLER_READ"));
                    issue.setAdminRead(rs.getBoolean("ADMIN_READ"));

                    PurchaseDAO purchaseDao = getDAO(PurchaseDAO.class);
                    issue.setPurchase(purchaseDao.getByPrimaryKey(rs.getInt("PURCHASE_ID")));
                }
            }
        } catch (SQLException | DAOFactoryException ex) {
            throw new DAOException("Impossible to get the Issue for the passed primary key", ex);
        }
        return issue;
    }

    @Override
    public List<Issue> getAll() throws DAOException {
        PurchaseDAO purchaseDAO;
        try {
            purchaseDAO = getDAO(PurchaseDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        List<Issue> issues = new ArrayList();         
        String query = "SELECT * FROM ISSUE ORDER BY ID DESC";            
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Issue issue = new Issue();
                issue.setId(resultSet.getInt("ID"));
                issue.setUserDescription(resultSet.getString("USER_DESCRIPTION"));
                issue.setSellerRead(resultSet.getBoolean("SELLER_READ"));
                issue.setAdminRead(resultSet.getBoolean("ADMIN_READ"));
                issue.setAdminChoice(Issue.AdminChoice.values()[resultSet.getInt("ADMIN_CHOICE")]);
                issue.setPurchase(purchaseDAO.getByPrimaryKey(resultSet.getInt("PURCHASE_ID")));
                issues.add(issue);
            }            
        } catch (SQLException ex) {
            throw new DAOException("Failed to query the count", ex);
        }
        return issues;
    }

    @Override
    public Issue update(Issue entity) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long insert(Issue issue) throws DAOException {
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO app.ISSUE(USER_DESCRIPTION,PURCHASE_ID) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, issue.getUserDescription());
            ps.setInt(2, issue.getPurchase().getId());

            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    try {
                        CON.rollback();
                    } catch (SQLException ex) {
                        //User: log the exception
                        Logger.getLogger(JDBCUserDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    throw new DAOException("Impossible to persist the new issue");
                }
            } else {
                try {
                    CON.rollback();
                } catch (SQLException ex) {
                    //User: log the exception
                    Logger.getLogger(JDBCUserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                throw new DAOException("Impossible to persist the new issue");
            }
        } catch (SQLException ex) {
            try {
                CON.rollback();
            } catch (SQLException ex1) {
                //User: log the exception
                Logger.getLogger(JDBCUserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            throw new DAOException("Impossible to persist the new issue", ex);
        }
    }
    
    @Override
    public Integer getCountNotReadBySellerId(Integer seller_id) throws DAOException {
        ItemDAO itemDAO;        
        try {
            itemDAO = getDAO(ItemDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        ArrayList<Item> items = (ArrayList<Item>) itemDAO.getBySellerId(seller_id);
        Integer count = 0;
        if(items.size() > 0){            
            String query = "SELECT COUNT(*) AS number FROM ISSUE iss, PURCHASE p, ITEM i WHERE iss.SELLER_READ IS NULL AND iss.PURCHASE_ID = p.ID AND i.ID = p.ITEM_ID";
            query += " AND ( i.ID = " + items.get(0).getId().toString();
            for (int i = 1; i < items.size(); i++) {
                query += " OR i.ID = " + items.get(i).getId().toString();

            }
            query += ")";
            
            try {
                PreparedStatement stmt = CON.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery();
                resultSet.next();
                count = resultSet.getInt("number");               
            } catch (SQLException ex) {
                throw new DAOException("Failed to query the count", ex);
            }
        }
        return count;
    }
    
    @Override
    public Integer getCountNotReadForAdmin() throws DAOException {        
        Integer count = 0;            
        String query = "SELECT COUNT(*) AS number FROM ISSUE WHERE ADMIN_READ IS NULL";            
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            count = resultSet.getInt("number");               
        } catch (SQLException ex) {
            throw new DAOException("Failed to query the count", ex);
        }
        return count;
    }
    
    @Override
    public List<Issue> getNotReadedBySellerId(Integer seller_id) throws DAOException {
        ItemDAO itemDAO;
        PurchaseDAO purchaseDAO;
        try {
            itemDAO = getDAO(ItemDAO.class);
            purchaseDAO = getDAO(PurchaseDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        
        ArrayList<Item> items = (ArrayList<Item>) itemDAO.getBySellerId(seller_id);
        List<Issue> issues = new ArrayList();
        if(items.size() > 0){            
            String query = "SELECT iss.ID, iss.USER_DESCRIPTION, iss.SELLER_READ, iss.ADMIN_READ, iss.ADMIN_CHOICE, iss.PURCHASE_ID FROM ISSUE iss, PURCHASE p, ITEM i WHERE iss.SELLER_READ IS NULL AND iss.PURCHASE_ID = p.ID AND i.ID = p.ITEM_ID";
            query += " AND ( i.ID = " + items.get(0).getId().toString();
            for (int i = 1; i < items.size(); i++) {
                query += " OR i.ID = " + items.get(i).getId().toString();

            }
            query += ")  ORDER BY iss.ID DESC";
            
            try {
                PreparedStatement stmt = CON.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    Issue issue = new Issue();
                    issue.setId(resultSet.getInt("ID"));
                    issue.setUserDescription(resultSet.getString("USER_DESCRIPTION"));
                    issue.setSellerRead(resultSet.getBoolean("SELLER_READ"));
                    issue.setAdminRead(resultSet.getBoolean("ADMIN_READ"));
                    issue.setAdminChoice(Issue.AdminChoice.values()[resultSet.getInt("ADMIN_CHOICE")]);
                    issue.setPurchase(purchaseDAO.getByPrimaryKey(resultSet.getInt("PURCHASE_ID")));
                    issues.add(issue);
                }
            } catch (SQLException ex) {
                throw new DAOException("Failed to query the list of issues", ex);
            }
        }
        return issues;
    }
    
    @Override
    public List<Issue> getBySellerId(Integer seller_id) throws DAOException {
        ItemDAO itemDAO;
        PurchaseDAO purchaseDAO;
        try {
            itemDAO = getDAO(ItemDAO.class);
            purchaseDAO = getDAO(PurchaseDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        ArrayList<Item> items = (ArrayList<Item>) itemDAO.getBySellerId(seller_id);
        List<Issue> issues = new ArrayList();
        if(items.size() > 0){            
            String query = "SELECT iss.ID, iss.USER_DESCRIPTION, iss.SELLER_READ, iss.ADMIN_READ, iss.ADMIN_CHOICE, iss.PURCHASE_ID FROM ISSUE iss, PURCHASE p, ITEM i WHERE iss.PURCHASE_ID = p.ID AND i.ID = p.ITEM_ID";
            query += " AND ( i.ID = " + items.get(0).getId().toString();
            for (int i = 1; i < items.size(); i++) {
                query += " OR i.ID = " + items.get(i).getId().toString();

            }
            query += ")  ORDER BY iss.ID DESC";
            
            try {
                PreparedStatement stmt = CON.prepareStatement(query);
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    Issue issue = new Issue();
                    issue.setId(resultSet.getInt("ID"));
                    issue.setUserDescription(resultSet.getString("USER_DESCRIPTION"));
                    issue.setSellerRead(resultSet.getBoolean("SELLER_READ"));
                    issue.setAdminRead(resultSet.getBoolean("ADMIN_READ"));
                    issue.setAdminChoice(Issue.AdminChoice.values()[resultSet.getInt("ADMIN_CHOICE")]);
                    issue.setPurchase(purchaseDAO.getByPrimaryKey(resultSet.getInt("PURCHASE_ID")));
                    issues.add(issue);
                }
            } catch (SQLException ex) {
                throw new DAOException("Failed to query the list of issues", ex);
            }
        }
        return issues;
    }
    
    @Override
    public Issue setSellerRead(Issue issue) throws DAOException {
        if (issue == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed issue is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE app.ISSUE SET SELLER_READ = TRUE WHERE id = ?")) {            
            std.setInt(1, issue.getId());
            if (std.executeUpdate() == 1) {
                issue.setSellerRead(Boolean.TRUE);
                return issue;
            } else {
                throw new DAOException("Impossible to update the issue");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the issue", ex);
        }
    }
    
    @Override
    public Issue setAdminRead(Issue issue) throws DAOException {
        if (issue == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed issue is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE app.ISSUE SET ADMIN_READ = TRUE WHERE id = ?")) {            
            std.setInt(1, issue.getId());
            if (std.executeUpdate() == 1) {
                issue.setAdminRead(Boolean.TRUE);
                return issue;
            } else {
                throw new DAOException("Impossible to update the issue");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the issue", ex);
        }
    }
    
    @Override
    public List<Issue> getAllOpened() throws DAOException{
        PurchaseDAO purchaseDAO;
        try {
            purchaseDAO = getDAO(PurchaseDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        List<Issue> issues = new ArrayList();         
        String query = "SELECT iss.ID, iss.USER_DESCRIPTION, iss.SELLER_READ, iss.ADMIN_READ, iss.ADMIN_CHOICE, iss.PURCHASE_ID FROM ISSUE iss WHERE ADMIN_CHOICE IS NULL ORDER BY iss.ID DESC";            
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Issue issue = new Issue();
                issue.setId(resultSet.getInt("ID"));
                issue.setUserDescription(resultSet.getString("USER_DESCRIPTION"));
                issue.setSellerRead(resultSet.getBoolean("SELLER_READ"));
                issue.setAdminRead(resultSet.getBoolean("ADMIN_READ"));
                issue.setAdminChoice(Issue.AdminChoice.values()[resultSet.getInt("ADMIN_CHOICE")]);
                issue.setPurchase(purchaseDAO.getByPrimaryKey(resultSet.getInt("PURCHASE_ID")));
                issues.add(issue);
            }            
        } catch (SQLException ex) {
            throw new DAOException("Failed to query the count", ex);
        }
        return issues;
    }
    
    @Override
    public Issue setAdminChoice(Issue issue) throws DAOException {
        if (issue == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed issue is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE app.ISSUE SET ADMIN_CHOICE = ?, SELLER_READ = NULL WHERE id = ?")) {
            std.setInt(1, issue.getAdminChoice().ordinal());
            std.setInt(2, issue.getId());
            if (std.executeUpdate() == 1) {
                return issue;
            } else {
                throw new DAOException("Impossible to update the issue");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the issue", ex);
        }
    }
}