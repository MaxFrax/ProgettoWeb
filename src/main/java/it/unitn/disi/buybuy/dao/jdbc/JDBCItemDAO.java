/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOFactoryException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.CategoryDAO;
import it.unitn.disi.buybuy.dao.ItemDAO;
import it.unitn.disi.buybuy.dao.ShopDAO;
import it.unitn.disi.buybuy.dao.entities.Category;
import it.unitn.disi.buybuy.dao.entities.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxfrax
 */
public class JDBCItemDAO extends JDBCDAO<Item, Integer> implements ItemDAO {

    public JDBCItemDAO(Connection con) {
        super(con);
        FRIEND_DAOS.put(ShopDAO.class, new JDBCShopDAO(con));
        FRIEND_DAOS.put(CategoryDAO.class, new JDBCCategoryDAO(con));
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
        // TODO ricerca non case sensitive e parole di ricerca che possono essere contenute in una parola del db
        List<Item> results = new ArrayList<>();
        // https://stackoverflow.com/questions/14290857/sql-select-where-field-contains-words
        String[] searchWords = userQuery.split("\\s+");
        StringBuilder query = new StringBuilder("SELECT * FROM item WHERE false ");
        for (String searchWord : searchWords) {
            //query.append("OR name LIKE '%?%' OR description LIKE '%?%' ");
            query.append("OR name LIKE ? OR description LIKE ? ");
        }
        System.out.println(query.toString());
        System.out.println(Arrays.toString(searchWords));
        try (PreparedStatement stm = CON.prepareStatement(query.toString())) {
            int i = 1;
            for (String searchWord : searchWords) {
                stm.setString(i, searchWord);
                i++;
                stm.setString(i, searchWord);
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
    public List<Item> getByCategoryAndQuery(Integer category_id, String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Item itemFactory(ResultSet rs) throws SQLException, DAOFactoryException, DAOException {
        Item i = new Item();
        i.setId(rs.getInt("ID"));
        i.setName(rs.getString("NAME"));
        i.setDescription(rs.getString("DESCRIPTION"));
        i.setPrice(rs.getFloat("PRICE"));
        i.setCategory(getDAO(CategoryDAO.class).getByPrimaryKey(rs.getInt("CATEGORY_ID")));
        i.setSeller(getDAO(ShopDAO.class).getByPrimaryKey(rs.getInt("SELLER_ID")));
        return i;
    }

}
