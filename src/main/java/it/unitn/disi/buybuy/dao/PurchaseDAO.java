/*
 * PurchaseDAO.java
 */
package it.unitn.disi.buybuy.dao;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.DAO;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.disi.buybuy.dao.entities.Purchase;
import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@code Purchase}.
 * 
 * @author apello96
 */
public interface PurchaseDAO extends DAO<Purchase,Integer>{
    
    /**
     * Returns the number of {@link Purchase purchase} stored on the persistence system
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
     * Returns the {@link Purchase purchase} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code purchase} to get.
     * @return the {@code purchase} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Purchase getByPrimaryKey(Integer primaryKey) throws DAOException;

    /**
     * Returns the list of all the valid {@link Purchase purchase} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code purchase}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Purchase> getAll() throws DAOException;
    
    /**
     * Persists the new {@link Purchase purchase} passed as parameter to the storage
     * system.
     * @param purchase the new {@code purchase} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    public Long insert(Purchase purchase) throws DAOException;
    
    /**
     * Update the purchase passed as parameter and returns it.
     *
     * @param purchase the purchase used to update the persistence system.
     * @return the updated purchase.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Purchase update(Purchase purchase) throws DAOException;
    
    public Purchase getByUserIdAndItemId(Integer userId, Integer ItemId) throws DAOException;
}
