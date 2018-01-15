/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.dao.jdbc;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.jdbc.JDBCDAO;
import it.unitn.disi.buybuy.dao.CategoryDAO;
import it.unitn.disi.buybuy.dao.entities.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxfrax
 */
public class JDBCCategoryDAO extends JDBCDAO<Category, Integer> implements CategoryDAO {

    public JDBCCategoryDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        try (PreparedStatement stmt = CON.prepareStatement("SELECT COUNT(*) FROM category");) {
            ResultSet counter = stmt.executeQuery();
            if (counter.next()) {
                return counter.getLong(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to count categories", ex);
        }

        return 0L;
    }

    @Override
    public Category getByPrimaryKey(Integer prmrk) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Category> getAll() throws DAOException {
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM category ORDER BY id")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Category c = categoryFactory(rs);
                    if (c != null) {
                        categories.add(c);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public Category update(Category entcls) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long insert(Category category) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Category categoryFactory(ResultSet rs) {
        Category c = new Category();
        try {
            c.setId(rs.getInt("ID"));
            c.setName(rs.getString("NAME"));
        } catch (SQLException ex) {
            Logger.getLogger(JDBCCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            c = null;
        }
        return c;
    }

}
