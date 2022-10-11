package ru.springBoot311.SpringBoot.sources.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.springBoot311.SpringBoot.sources.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    /**пришлось убрать коструктор с параметром EntityManager потому что после перехода на SpringBoot
     * от нас ушёл файл конфигурации с определением бина EntityManager, и сейчас
     * в реализованном кострукторе с этим параметром идет мерзкое красное подчёркивание
     * бесит
     * */
   @PersistenceContext
    private   EntityManager entityManager;

    @Override
    public void addUserToDatabase(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsersFromDatabase() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();

    }

    @Override
    public void deleteUserFromDatabase(long id) {
        entityManager.remove(getUserByIdFromDatabase(id));
    }

    @Override
    public User getUserByIdFromDatabase(long id) {
        return entityManager.find(User.class, id);
    }

    @Override

    public void updateUserInDatabase(User user) {
        entityManager.merge(user);
    }

    @Override
    public Optional<User> loadUserByUserName(String name) {
        TypedQuery<User> query = entityManager.createQuery("FROM User where name = ?1", User.class)
                .setParameter(1,name);
        Optional<User> user = Optional.of(query.getSingleResult());
        return  user;
    }
}
