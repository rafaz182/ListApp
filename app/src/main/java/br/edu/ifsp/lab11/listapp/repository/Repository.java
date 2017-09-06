package br.edu.ifsp.lab11.listapp.repository;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bolts.Task;

/**
 * Created by r0xxFFFF-PC on 22/04/2017.
 */

public abstract class Repository<T extends ParseObject> implements IRepository<T> {

    private Class<T> returnedClass;

    public Repository() {
        this.returnedClass = (Class<T>) getTypeArguments(Repository.class, getClass()).get(0);
    }

    //public abstract ParseObject convertToParse(T object);

    //public abstract T convertToObject(ParseObject parse);

    @Override
    public T save(T object) throws ParseException {
        //Local Variables
        /*Task<Void> result;

        result = object.saveInBackground();

        return result.isCompleted();*/

        object.save();

        return object.fetch();
    }

    @Override
    public T loadById(String objectID) throws ParseException {
        //Local Variables
        T loadedObject = null;
        ParseQuery<T> query = null;

        query = ParseQuery.getQuery(this.returnedClass);
        query.whereEqualTo("objectId", objectID);

        loadedObject = query.getFirst();

        return loadedObject;
    }

    @Override
    public List<T> loadAll() throws ParseException {
        //Local Variables
        ParseQuery<T> query = null;
        //List<ParseObject> parserObjects;
        List<T> loadedObjects;

        query = ParseQuery.getQuery(this.returnedClass);
        loadedObjects = query.find();

        return loadedObjects;
    }

    @Override
    public T update(T object) throws ParseException {
        //Local Variables

        object.save();

        return object.fetch();
    }

    @Override
    public void delete(T object) throws ParseException {

        object.delete();
    }

    /**
     * Get the underlying class for a type, or null if the type is a variable
     * type.
     *
     * @param type the type
     * @return the underlying class
     */
    private static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic base class.
     *
     * @param baseClass the base class
     * @param childClass the child class
     * @return a list of the raw classes for the actual type arguments.
     */
    private <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (! getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            }
            else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        }
        else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
        // resolve types by chasing down type variables.
        for (Type baseType: actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

}
