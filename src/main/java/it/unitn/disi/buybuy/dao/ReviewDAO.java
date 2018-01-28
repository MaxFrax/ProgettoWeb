/*
 * ReviewDAO.java
 */
package it.unitn.disi.buybuy.dao;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.DAO;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.disi.buybuy.dao.entities.Review;
import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@code Review}.
 * 
 * @author apello96
 */
public interface ReviewDAO extends DAO<Review,Integer>{
    
    /**
     * Returns the number of {@link Review review} stored on the persistence system
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
     * Returns the {@link Review review} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code review} to get.
     * @return the {@code review} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Review getByPrimaryKey(Integer primaryKey) throws DAOException;

    /**
     * Returns the list of all the valid {@link Review review} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code review}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Review> getAll() throws DAOException;
    
    /**
     * Persists the new {@link Review review} passed as parameter to the storage
     * system.
     * @param review the new {@code review} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    public Long insert(Review review) throws DAOException;
    
    /**
     * Update the review passed as parameter and returns it.
     *
     * @param review the review used to update the persistence system.
     * @return the updated review.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Review update(Review review) throws DAOException;
    
    public List<Review> getByItemID(Integer id) throws DAOException;

    public List<Review> getByShopID(Integer id) throws DAOException;
    
    public Review getByItemIDAndUserID(Integer user_id, Integer item_id) throws DAOException;
    
}
