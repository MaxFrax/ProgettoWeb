package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.PurchaseDAO;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Purchase;
import it.unitn.disi.buybuy.dao.entities.User;
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
public class JDBCPurchaseDAO extends JDBCDAO<Purchase, Integer> implements PurchaseDAO {

    public JDBCPurchaseDAO(Connection con) {
        super(con);
        FRIEND_DAOS.put(ItemDAO.class, new JDBCItemDAO(con));
        FRIEND_DAOS.put(UserDAO.class, new JDBCUserDAO(con));
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Purchase getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM app.PURCHASE WHERE id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                Purchase purchase = new Purchase();
                purchase.setId(rs.getInt("ID"));

                UserDAO userDao = getDAO(UserDAO.class);
                purchase.setUser(userDao.getByPrimaryKey(rs.getInt("USER_ID")));

                ItemDAO itemDao = getDAO(ItemDAO.class);
                purchase.setItem(itemDao.getByPrimaryKey(rs.getInt("ITEM_ID")));

                return purchase;
            }
        } catch (SQLException | DAOFactoryException ex) {
            throw new DAOException("Impossible to get the purchase for the passed primary key", ex);
        }
    }

    @Override
    public List<Purchase> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Purchase update(Purchase entity) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long insert(Purchase purchase) throws DAOException {
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO app.PURCHASE(item_id, user_id) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, purchase.getItem().getId());
            ps.setInt(2, purchase.getUser().getId());

            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    try {
                        CON.rollback();
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCPurchaseDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    throw new DAOException("Impossible to persist the new purchase");
                }
            } else {
                try {
                    CON.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCPurchaseDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                throw new DAOException("Impossible to persist the new purchase");
            }
        } catch (SQLException ex) {
            try {
                CON.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(JDBCPurchaseDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            throw new DAOException("Impossible to persist the new retailer", ex);
        }
    }

    @Override
    public Purchase getByUserIdAndItemId(Integer user_id, Integer item_id) throws DAOException {
        // Get needed DAOs
        ItemDAO itemDAO;
        UserDAO userDAO;
        try {
            itemDAO = getDAO(ItemDAO.class);
            userDAO = getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }

        Purchase purchase = new Purchase();
        String query = "SELECT * FROM PURCHASE WHERE ITEM_ID=? AND USER_ID=?";
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            stmt.setInt(1, item_id);
            stmt.setInt(2, user_id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                purchase.setId(resultSet.getInt("ID"));
                // Get Item from ITEM_ID
                Item item = itemDAO.getByPrimaryKey(resultSet.getInt("ITEM_ID"));
                purchase.setItem(item);
                // Get User from USER_ID
                User user = userDAO.getByPrimaryKey(resultSet.getInt("USER_ID"));
                purchase.setUser(user);
            }
        } catch (SQLException ex) {
            throw new DAOException("Failed to query purchase with ITEM_ID = " + item_id + "and USER_ID= " + user_id, ex);
        }
        return purchase;
    }

}
