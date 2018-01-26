package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.ReviewDAO;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Review;
import it.unitn.disi.buybuy.dao.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCReviewDAO extends JDBCDAO<Review, Integer> implements ReviewDAO {
    
    public JDBCReviewDAO(Connection con) {
        super(con);
        FRIEND_DAOS.put(ItemDAO.class, new JDBCItemDAO(CON));
        FRIEND_DAOS.put(UserDAO.class, new JDBCUserDAO(CON));
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Review getByPrimaryKey(Integer primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Review> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long insert(Review review) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Review update(Review review) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Review> getByItemID(Integer id) throws DAOException {
        
        // Get needed DAOs
        ItemDAO itemDAO;
        UserDAO userDAO;        
        try {
            itemDAO = getDAO(ItemDAO.class);
            userDAO = getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM REVIEW WHERE ITEM_ID=?";
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                // Add current result to list
                Review review = new Review();
                review.setId(resultSet.getInt("ID"));
                review.setDescription(resultSet.getString("DESCRIPTION"));
                review.setRating(resultSet.getInt("RATING"));
                // Get Item from ITEM_ID
                Item item = itemDAO.getByPrimaryKey(resultSet.getInt("ITEM_ID"));
                review.setItem(item);
                // Get User from USER_ID
                User user = userDAO.getByPrimaryKey(resultSet.getInt("USER_ID"));
                review.setUser(user);
                // Add to list
                reviews.add(review);
            }
        } catch (SQLException ex) {
            throw new DAOException("Failed to query reviews with ITEM_ID = " + id, ex);
        }
        return reviews;
    }
    
    
    @Override
    public List<Review> getByShopID(Integer id) throws DAOException {
        
        // Get needed DAOs
        ItemDAO itemDAO;
        UserDAO userDAO;        
        try {
            itemDAO = getDAO(ItemDAO.class);
            userDAO = getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM REVIEW WHERE ID=?";
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                // Add current result to list
                Review review = new Review();
                review.setId(resultSet.getInt("ID"));
                review.setDescription(resultSet.getString("DESCRIPTION"));
                review.setRating(resultSet.getInt("RATING"));
                // Get Item from ITEM_ID
                Item item = itemDAO.getByPrimaryKey(resultSet.getInt("ITEM_ID"));
                review.setItem(item);
                // Get User from USER_ID
                User user = userDAO.getByPrimaryKey(resultSet.getInt("USER_ID"));
                review.setUser(user);
                // Add to list
                reviews.add(review);
            }
        } catch (SQLException ex) {
            throw new DAOException("Failed to query reviews with ID = " + id, ex);
        }
        return reviews;
    }
}
