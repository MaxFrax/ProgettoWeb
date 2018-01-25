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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO app.REVIEW(DESCRIPTION,RATING,USER_ID,ITEM_ID) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, review.getDescription());
            ps.setInt(2, review.getRating());
            ps.setInt(3, review.getUser().getId());
            ps.setInt(4, review.getItem().getId());

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
                    throw new DAOException("Impossible to persist the new review");
                }
            } else {
                try {
                    CON.rollback();
                } catch (SQLException ex) {
                    //User: log the exception
                    Logger.getLogger(JDBCUserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                throw new DAOException("Impossible to persist the new ureview");
            }
        } catch (SQLException ex) {
            try {
                CON.rollback();
            } catch (SQLException ex1) {
                //User: log the exception
                Logger.getLogger(JDBCUserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            throw new DAOException("Impossible to persist the new review", ex);
        }
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
    public Review getByItemIDAndUserID(Integer user_id, Integer item_id) throws DAOException {
        
        // Get needed DAOs
        ItemDAO itemDAO;
        UserDAO userDAO;        
        try {
            itemDAO = getDAO(ItemDAO.class);
            userDAO = getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            throw new DAOException("Failed to get DAOs", ex);
        }
        
        Review review = new Review();
        String query = "SELECT * FROM REVIEW WHERE ITEM_ID=? AND USER_ID=?";
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            stmt.setInt(1, item_id);
            stmt.setInt(2, user_id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {                
                review.setId(resultSet.getInt("ID"));
                review.setDescription(resultSet.getString("DESCRIPTION"));
                review.setRating(resultSet.getInt("RATING"));
                // Get Item from ITEM_ID
                Item item = itemDAO.getByPrimaryKey(resultSet.getInt("ITEM_ID"));
                review.setItem(item);
                // Get User from USER_ID
                User user = userDAO.getByPrimaryKey(resultSet.getInt("USER_ID"));
                review.setUser(user);                
            }
        } catch (SQLException ex) {
            throw new DAOException("Failed to query reviews with ITEM_ID = " + item_id + "and USER_ID= " + user_id, ex);
        }
        return review;
    }
    
}
