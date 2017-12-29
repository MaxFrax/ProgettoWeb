/*
 * ItemDAO.java
*/
package it.unitn.disi.buybuy.dao;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.DAO;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.disi.buybuy.dao.entities.Item;
import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@code Item}.
 * 
 * @author apello96
 */
public interface ItemDAO extends DAO<Item,Integer>{
    
    /**
     * Returns the number of {@link Item item} stored on the persistence system
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
     * Returns the {@link Item item} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code item} to get.
     * @return the {@code item} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Item getByPrimaryKey(Integer primaryKey) throws DAOException;

    /**
     * Returns the list of all the valid {@link Item item} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code item}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Item> getAll() throws DAOException;
    
    /**
     * Persists the new {@link Item item} passed as parameter to the storage
     * system.
     * @param item the new {@code item} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    public Long insert(Item item) throws DAOException;
    
    /**
     * Update the item passed as parameter and returns it.
     *
     * @param item the item used to update the persistence system.
     * @return the updated retailer.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Item update(Item item) throws DAOException;
}
