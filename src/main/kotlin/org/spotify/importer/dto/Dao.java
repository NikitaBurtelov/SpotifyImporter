package org.spotify.importer.dto;

import org.burtelov.spotifyimporter.model.users.User;

/**
 * @author Nikita Burtelov
 */
public interface Dao {
    public void addUser(User user);

    public void removeUser(User user);

    public void clearUserAll();
}
