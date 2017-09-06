package br.edu.ifsp.lab11.listapp.repository;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 */

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import br.edu.ifsp.lab11.listapp.domain.ParseEntity_TO;

/**
 * An interface that holds all common operations between all services
 *
 * @param <T> - The type of data which will be managed
 */
public interface IRepository<T extends ParseObject> {

    /**
     * Create a new register in ParseCloud
     *
     * @param object - The object who'll be saved
     *
     * @return - True if the operation has been successful
     */
    T save(T object) throws ParseException;

    /**
     * Retrieve a register in the ParseCloud
     *
     * @param objectID - The ID of the register
     *
     * @return - A instance T of the referred register
     */
    T loadById(String objectID) throws ParseException;

    /**
     * Retrieve all register of the type T in the ParseCloud
     *
     * @return - A list of all instance
     */
    List<T> loadAll() throws ParseException;

    T update(T object) throws ParseException;

    void delete(T object) throws ParseException;


}
