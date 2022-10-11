package ru.springBoot311.SpringBoot.sources.dao;

import ru.springBoot311.SpringBoot.sources.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    public void addUserToDatabase(User user);

    public List<User> getAllUsersFromDatabase();

    public void deleteUserFromDatabase(long id);

    public User getUserByIdFromDatabase(long id);

     public void updateUserInDatabase(User user);

     public Optional<User> loadUserByUserName(String name);
}

