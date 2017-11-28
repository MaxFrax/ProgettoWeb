/*
 * JDBCRetailerDAO.java
 */
package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.RetailerDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.entities.Retailer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The JDBC implementation of the {@link RetailerDAO} interface.
 *
 * @author apello96
 */
public class JDBCRetailerDAO extends JDBCDAO<Retailer,Integer> implements RetailerDAO{
    
    public JDBCRetailerDAO(Connection con) {
        super(con);
        FRIEND_DAOS.put(ShopDAO.class, new JDBCShopDAO(CON));
    }
    
    /**
     * Returns the number of {@link Retailer retailers} stored on the persistence system
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
        try (PreparedStatement stmt = CON.prepareStatement("SELECT COUNT(*) FROM retailer");) {
            ResultSet counter = stmt.executeQuery();
            if (counter.next()) {
                return counter.getLong(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to count retailers", ex);
        }

        return 0L;
    }
    
    /**
     * Returns the {@link Retailer retailer} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code retailer} to get.
     * @return the {@code retailer} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Retailer getByPrimaryKey(Integer primaryKey) throws DAOException {
        if (primaryKey == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM retailer WHERE id = ?")) {
            stm.setInt(1, primaryKey);
            try (ResultSet rs = stm.executeQuery()) {

                rs.next();
                Retailer retailer = new Retailer();
                retailer.setId(rs.getInt("id"));
                retailer.setLatitude(rs.getFloat("latitude"));
                retailer.setLongitude(rs.getFloat("longitude"));
                retailer.setPostalCode(rs.getString("postal_code"));
                retailer.setCity(rs.getString("city"));
                retailer.setStreetName(rs.getString("street_name"));
                retailer.setStreetNumber(rs.getInt("street_number"));
                retailer.setProvince(rs.getString("province"));
                retailer.setOpenTimetable(rs.getString("open_timetable"));
                
                ShopDAO shopDAO = getDAO(ShopDAO.class);
                retailer.setShop(shopDAO.getByPrimaryKey(rs.getInt("shop_id")));

                return retailer;
            }
        } catch (SQLException | DAOFactoryException ex) {
            throw new DAOException("Impossible to get the retailer for the passed primary key", ex);
        }
    }

    /**
     * Returns the list of all the valid {@link Retailer retailers} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code retailers}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Retailer> getAll() throws DAOException {
        List<Retailer> retailers = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM retailer ORDER BY id")) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Retailer retailer = new Retailer();
                    retailer.setId(rs.getInt("id"));
                    retailer.setLatitude(rs.getFloat("latitude"));
                    retailer.setLongitude(rs.getFloat("longitude"));
                    retailer.setPostalCode(rs.getString("postal_code"));
                    retailer.setCity(rs.getString("city"));
                    retailer.setStreetName(rs.getString("street_name"));
                    retailer.setStreetNumber(rs.getInt("street_number"));
                    retailer.setProvince(rs.getString("province"));
                    retailer.setOpenTimetable(rs.getString("open_timetable"));
                
                    ShopDAO shopDAO = getDAO(ShopDAO.class);
                    retailer.setShop(shopDAO.getByPrimaryKey(rs.getInt("shop_id")));
                    
                    retailers.add(retailer);
                }
            }
        } catch (SQLException | DAOFactoryException ex) {
            throw new DAOException("Impossible to get the list of shops", ex);
        }

        return retailers;
    }

    /**
     * Update the retailer passed as parameter and returns it.
     *
     * @param retailer the retailer used to update the persistence system.
     * @return the updated retailer.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Retailer update(Retailer retailer) throws DAOException {
        if (retailer == null) {
            throw new DAOException("parameter not valid", new IllegalArgumentException("The passed retailer is null"));
        }

        try (PreparedStatement ps = CON.prepareStatement("UPDATE app.retailer SET latitude = ?, longitude = ?, postal_code = ?, city = ?, street_name = ?, street_number = ?, province = ?, open_timetable = ?, shop_id = ? WHERE id = ?")) {
            ps.setFloat(1, retailer.getLatitude());
            ps.setFloat(2, retailer.getLongitude());
            ps.setString(3, retailer.getPostalCode());
            ps.setString(4, retailer.getCity());
            ps.setString(5, retailer.getStreetName());
            ps.setInt(6, retailer.getStreetNumber());
            ps.setString(7, retailer.getProvince());
            ps.setString(8, retailer.getOpenTimetable());
            ps.setInt(9, retailer.getShop().getId());
            ps.setInt(19, retailer.getId());
            if (ps.executeUpdate() == 1) {
                return retailer;
            } else {
                throw new DAOException("Impossible to update the retailer");
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to update the retailer", ex);
        }
    }

    /**
     * Persists the new {@link Retailer retailers} passed as parameter to the storage
     * system.
     * @param retailer the new {@code retailer} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    @Override
    public Long insert(Retailer retailer) throws DAOException {
        try (PreparedStatement ps = CON.prepareStatement("INSERT INTO app.RETAILER(id,latitude,longitude,postal_code,city,street_name,street_number,province,open_timetable,shop_id,) VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, retailer.getId());
            ps.setFloat(2, retailer.getLatitude());
            ps.setFloat(3, retailer.getLongitude());
            ps.setString(4, retailer.getPostalCode());
            ps.setString(5, retailer.getCity());
            ps.setString(6, retailer.getStreetName());
            ps.setInt(7, retailer.getStreetNumber());
            ps.setString(8, retailer.getProvince());
            ps.setString(9, retailer.getOpenTimetable());
            ps.setInt(10, retailer.getShop().getId());
            
            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    try {
                        CON.rollback();
                    } catch (SQLException ex) {
                        //Retailer: log the exception
                    }
                    throw new DAOException("Impossible to persist the new retailer");
                }
            } else {
                try {
                    CON.rollback();
                } catch (SQLException ex) {
                    //Retailer: log the exception
                }
                throw new DAOException("Impossible to persist the new retailer");
            }
        } catch (SQLException ex) {
            try {
                CON.rollback();
            } catch (SQLException ex1) {
                //Retailer: log the exception
            }
            throw new DAOException("Impossible to persist the new retailer", ex);
        }
    }
}
