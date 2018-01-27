package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.CategoryDAO;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.RetailerDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Retailer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 *
 * @author maxfrax
 */
public class JDBCItemDAO extends JDBCDAO<Item, Integer> implements ItemDAO {

    public JDBCItemDAO(Connection con) {
        super(con);
        FRIEND_DAOS.put(ShopDAO.class, new JDBCShopDAO(con));
        FRIEND_DAOS.put(CategoryDAO.class, new JDBCCategoryDAO(con));
        FRIEND_DAOS.put(RetailerDAO.class, new JDBCRetailerDAO(con));
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Item getByPrimaryKey(Integer prmrk) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Item update(Item entcls) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long insert(Item item) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Item> getByQuery(String userQuery) {
        List<Item> results = new ArrayList<>();
        // Make user query lowercase
        userQuery = userQuery.toLowerCase();
        String[] searchWords = userQuery.split("\\s+");
        StringBuilder query = new StringBuilder("SELECT * FROM item WHERE false ");
        for (String searchWord : searchWords) {
            query.append("OR LOWER(name) LIKE ? OR LOWER(description) LIKE ? ");
        }
        try (PreparedStatement stm = CON.prepareStatement(query.toString())) {
            int i = 1;
            for (String searchWord : searchWords) {
                stm.setString(i, "%" + searchWord + "%");
                i++;
                stm.setString(i, "%" + searchWord + "%");
                i++;
            }
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Item it = itemFactory(rs);
                    results.add(it);
                }
            } catch (DAOFactoryException | DAOException ex) {
                Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return results;
    }

    @Override
    public List<Item> getByCategory(Integer category_id) throws DAOException {
        List<Item> results = new ArrayList<>();
        if (category_id == null) {
            throw new DAOException("category_id is null");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM item WHERE category_id = ?")) {
            stm.setInt(1, category_id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Item i = itemFactory(rs);
                    results.add(i);
                }
            } catch (SQLException | DAOFactoryException ex) {
                Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    @Override
    public List<Item> getByCategoryAndQuery(Integer categoryId, String userQuery) {
        List<Item> results = new ArrayList<>();
        // Make user query lowercase
        userQuery = userQuery.toLowerCase();
        String[] searchWords = userQuery.split("\\s+");
        StringBuilder query = new StringBuilder("SELECT * FROM item WHERE category_id = ? AND (false ");
        for (String searchWord : searchWords) {
            query.append("OR LOWER(name) LIKE ? OR LOWER(description) LIKE ? ");
        }
        query.append(")");
        try (PreparedStatement stm = CON.prepareStatement(query.toString())) {
            int i = 1;
            stm.setInt(i, categoryId);
            for (String searchWord : searchWords) {
                i++;
                stm.setString(i, "%" + searchWord + "%");
                i++;
                stm.setString(i, "%" + searchWord + "%");
            }
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Item it = itemFactory(rs);
                    results.add(it);
                }
            } catch (DAOFactoryException | DAOException ex) {
                Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return results;
    }

    private Item itemFactory(ResultSet rs) throws SQLException, DAOFactoryException, DAOException {
        Item i = new Item();
        i.setId(rs.getInt("ID"));
        i.setName(rs.getString("NAME"));
        i.setDescription(rs.getString("DESCRIPTION"));
        i.setPrice(rs.getFloat("PRICE"));
        i.setCategory(getDAO(CategoryDAO.class).getByPrimaryKey(rs.getInt("CATEGORY_ID")));
        i.setSeller(getDAO(ShopDAO.class).getByPrimaryKey(rs.getInt("SELLER_ID")));
        i.setRating(getRatingByItemId(rs.getInt("ID")));
        i.setReviewCount(getReviewCountByItemId(rs.getInt("ID")));
        return i;
    }

    @Override
    public Integer getRatingByItemId(Integer itemId) throws DAOException {
        String query = "SELECT AVG(RATING) AS AVG_RATING FROM REVIEW WHERE ITEM_ID=?";
        Integer average = 0;
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            stmt.setInt(1, itemId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                average = resultSet.getInt("AVG_RATING");
            }
        } catch (SQLException ex) {
            throw new DAOException("Failed to query reviews with ITEM_ID = " + itemId, ex);
        }
        return average;
    }

    @Override
    public Integer getReviewCountByItemId(Integer itemId) throws DAOException {
        String query = "SELECT COUNT(RATING) AS TOTAL FROM REVIEW WHERE ITEM_ID=?";
        Integer count = 0;
        try {
            PreparedStatement stmt = CON.prepareStatement(query);
            stmt.setInt(1, itemId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("TOTAL");
            }
        } catch (SQLException ex) {
            throw new DAOException("Failed to query reviews with ITEM_ID = " + itemId, ex);
        }
        return count;
    }

    @Override
    public List<Pair<Item, Retailer>> getWithRetailer(Integer category, String userQuery) throws DAOException {
        // Split query string
        String[] searchWords = userQuery.split("\\s+");
        // Attach to sql query user query words
        StringBuilder query = new StringBuilder("SELECT ITEM.*, RETAILER.ID as RET_ID FROM ITEM INNER JOIN SHOP on SELLER_ID = SHOP.ID INNER JOIN RETAILER ON RETAILER.SHOP_ID = SHOP.ID WHERE (false ");
        for (String searchWord : searchWords) {
            query.append("OR LOWER(ITEM.NAME) LIKE ? OR LOWER(ITEM.DESCRIPTION) LIKE ? ");
        }
        query.append(")");
        // attach category if given
        if (category != null) {
            query.append("AND category_id = ?");
        }
        List<Pair<Item, Retailer>> results = new ArrayList<>();
        List<Integer> itemsAlreadyAdded = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement(query.toString())) {
            // Fill query with values
            int i = 0;
            for (String searchWord : searchWords) {
                i++;
                stm.setString(i, "%" + searchWord + "%");
                i++;
                stm.setString(i, "%" + searchWord + "%");
            }
            if (category != null) {
                i++;
                stm.setInt(i, category);
            }
            // iterate through the values
            System.out.println(stm);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Pair<Item, Retailer> p = new Pair<>(itemFactory(rs), getDAO(RetailerDAO.class).getByPrimaryKey(rs.getInt("RET_ID")));
                    
                    if(!itemsAlreadyAdded.contains(p.getKey().getId())) {
                        results.add(p);
                        itemsAlreadyAdded.add(p.getKey().getId());
                    }
                    
                }
            } catch (DAOFactoryException ex) {
                Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
}
