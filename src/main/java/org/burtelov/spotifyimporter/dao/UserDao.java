package org.burtelov.spotifyimporter.dao;

import org.burtelov.spotifyimporter.model.users.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Burtelov
 */
@Component
public class UserDao implements Dao{
    List<User> userList = new ArrayList();
    @Override
    public void addUser(@NonNull User user) {
        userList.add(user);
    }

    @Override
    public void removeUser(User user) {
        userList.remove(user);
    }

    @Override
    public void clearUserAll() {
        userList.clear();
    }
}
