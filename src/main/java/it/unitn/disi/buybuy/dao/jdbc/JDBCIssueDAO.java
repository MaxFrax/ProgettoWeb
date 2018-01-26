
package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.IssueDAO;
import it.unitn.disi.buybuy.dao.PurchaseDAO;
import it.unitn.disi.buybuy.dao.entities.Issue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
