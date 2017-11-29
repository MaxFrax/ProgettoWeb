/*
 * PhotoDAO.java
 */
package it.unitn.disi.buybuy.dao;

import it.unitn.aa1617.webprogramming.persistence.utils.dao.DAO;
import it.unitn.aa1617.webprogramming.persistence.utils.dao.exceptions.DAOException;
import it.unitn.disi.buybuy.dao.entities.Photo;
import java.util.List;

/**
 * All concrete DAOs must implement this interface to handle the persistence 
 * system that interact with {@code Photo}.
 * 
 * @author apello96
 */
public interface PhotoDAO extends DAO<Photo,Integer>{
    
    /**
     * Returns the number of {@link Photo photo} stored on the persistence system
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
     * Returns the {@link Photo photo} with the primary key equals to the one
     * passed as parameter.
     *
     * @param primaryKey the {@code id} of the {@code photo} to get.
     * @return the {@code photo} with the id equals to the one passed as
     * parameter or {@code null} if no entities with that id is not present into
     * the storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public Photo getByPrimaryKey(Integer primaryKey) throws DAOException;

    /**
     * Returns the list of all the valid {@link Photo photo} stored by the
     * storage system.
     *
     * @return the list of all the valid {@code photo}.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author apello96
     */
    @Override
    public List<Photo> getAll() throws DAOException;
    
    /**
     * Persists the new {@link Photo photo} passed as parameter to the storage
     * system.
     * @param photo the new {@code photo} to persist.
     * @return the id of the new persisted record. 
     * @throws DAOException if an error occurred during the persist action.
     * 
     * @author apello96
     */
    public Long insert(Photo photo) throws DAOException;
    
    /**
     * Update the photo passed as parameter and returns it.
     *
     * @param photo the photo used to update the persistence system.
     * @return the updated photo.
     * @throws DAOException if an error occurred during the action.
     *
     * @author apello96
     */
    @Override
    public Photo update(Photo photo) throws DAOException;
}
