package spring.dao;

import spring.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    void addUser(User user);

    User getUser(int id);

    void updateUser(User user);

    void deleteUser(User user);
}
