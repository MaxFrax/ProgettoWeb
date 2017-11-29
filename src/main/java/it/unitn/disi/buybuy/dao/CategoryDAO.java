/*
 * CategoryDAO.java
 */
package it.unitn.disi.buybuy.dao;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.DAO;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.disi.buybuy.dao.entities.Category;
import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@code Category}.
 * 
 * @author apello96
 */
public interface CategoryDAO extends DAO<Category,Integer>{
    
    /**
     * Returns the number of {@link Category category} stored on the persistence system
     * of the application.
     *
     * @return the number of records present into the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Long getCount() throws DAOException;

    /**
     * Returns the {@link Category category} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code category} to get.
     * @return the {@code category} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Category getByPrimaryKey(Integer primaryKey) throws DAOException;

    /**
     * Returns the list of all the valid {@link Category categories} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code categories}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Category> getAll() throws DAOException;
    
    /**
     * Persists the new {@link Category category} passed as parameter to the storage
     * system.
     * @param category the new {@code category} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    public Long insert(Category category) throws DAOException;
    
    /**
     * Update the category passed as parameter and returns it.
     *
     * @param category the category used to update the persistence system.
     * @return the updated category.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Category update(Category category) throws DAOException;
    
}
