/*
 * JDBCShopDAO.java
 */
package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.UserDAO;
import it.unitn.disi.buybuy.dao.entities.Shop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The JDBC implementation of the {@link ShopDAO} interface.
 *
 * @author apello96
 */
public class JDBCShopDAO extends JDBCDAO<Shop,Integer> implements ShopDAO{

    JDBCShopDAO(Connection CON) {
        super(CON);
        FRIEND_DAOS.put(UserDAO.class, new JDBCUserDAO(CON));
    }

    /**
     * Returns the number of {@link Shop shops} stored on the persistence system
     * of the application.
     *
     * @return the number of records present into the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Long getCount() throws DAOException {
        try (PreparedStatement stmt = CON.prepareStatement("SELECT COUNT(*) FROM shop");) {
            ResultSet counter = stmt.executeQuery();
            if (counter.next()) {
                return counter.getLong(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to count shops", ex);
        }

        return 0L;
    }

    /**
     * Returns the {@link Shop shop} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code shop} to get.
     * @return the {@code shop} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Shop getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM shop WHERE id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                Shop shop = new Shop();
                shop.setId(rs.getInt("id"));
                shop.setName(rs.getString("name"));
                shop.setDescription(rs.getString("description"));
                shop.setWebsite(rs.getString("website"));
                shop.setRating(rs.getInt("rating"));
                UserDAO userDAO = getDAO(UserDAO.class);
                shop.setOwner(userDAO.getByPrimaryKey(rs.getInt("owner_id")));

                return shop;
            }
        } catch (SQLException | DAOFactoryException ex) {
            throw new DAOException("Impossible to get the shop for the passed primary key", ex);
        }
    }

    /**
     * Returns the list of all the valid {@link Shop shop} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code shops}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Shop> getAll() throws DAOException {
        List<Shop> shops = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM shop ORDER BY name")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Shop shop = new Shop();
                    shop.setId(rs.getInt("id"));
                    shop.setName(rs.getString("name"));
                    shop.setDescription(rs.getString("shop"));
                    shop.setWebsite(rs.getString("website"));
                    shop.setRating(rs.getInt("rating"));
                    UserDAO userDAO = getDAO(UserDAO.class);
                    shop.setOwner(userDAO.getByPrimaryKey(rs.getInt("owner_id")));
                    
                    shops.add(shop);
                }
            }
        } catch (SQLException | DAOFactoryException ex) {
            throw new DAOException("Impossible to get the list of shops", ex);
        }

        return shops;
    }

    /**
     * Update the shop passed as parameter and returns it.
     *
     * @param shop the shop used to update the persistence system.
     * @return the updated shop.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Shop update(Shop shop) throws DAOException {
        if (shop == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed shop is null"));
        }

        try (PreparedStatement std = CON.prepareStatement("UPDATE app.shop SET name = ?, description = ?, website = ?, rating = ?, owner_id = ? WHERE id = ?")) {
            std.setString(1, shop.getName());
            std.setString(2, shop.getDescription());
            std.setString(3, shop.getWebsite());
            std.setInt(4, shop.getRating());
            std.setInt(5, shop.getOwner().getId());
            std.setInt(6, shop.getId());
            if (std.executeUpdate() == 1) {
                return shop;
            } else {
                throw new DAOException("Impossible to update the shop");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the shop", ex);
        }
    }

    /**
     * Persists the new {@link Shop shop} passed as parameter to the storage
     * system.
     * @param shop the new {@code shop} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    @Override
    public Long insert(Shop shop) throws DAOException {
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO app.SHOP(id,name,description,website,rating,owner_id) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, shop.getId());
            ps.setString(2, shop.getName());
            ps.setString(3, shop.getDescription());
            ps.setString(4, shop.getWebsite());
            ps.setInt(5, shop.getRating());
            ps.setInt(6, shop.getOwner().getId());

            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    try {
                        CON.rollback();
                    } catch (SQLException ex) {
                        //Shop: log the exception
                    }
                    throw new DAOException("Impossible to persist the new shop");
                }
            } else {
                try {
                    CON.rollback();
                } catch (SQLException ex) {
                    //Shop: log the exception
                }
                throw new DAOException("Impossible to persist the new shop");
            }
        } catch (SQLException ex) {
            try {
                CON.rollback();
            } catch (SQLException ex1) {
                //Shop: log the exception
            }
            throw new DAOException("Impossible to persist the new shop", ex);
        }
    }
}
