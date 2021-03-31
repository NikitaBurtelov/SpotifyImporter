package org.burtelov.spotifyimporter.model.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Nikita Burtelov
 */
@Component
public class Users {
    Map<String, User> userHashMap = new HashMap<>();

    public void setUserHashMap(Map<String, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    public Map<String, User> getUserHashMap() {
        return userHashMap;
    }

    public User getUser(String key) {
        return userHashMap.get(key);
    }

    public void addUser(User user) {
        userHashMap.put(user.getId(), user);
    }
    public void removeUser(User user){
        userHashMap.remove(user.getId());
    }
    public void clearUserList(){
        userHashMap.clear();
    }
}
