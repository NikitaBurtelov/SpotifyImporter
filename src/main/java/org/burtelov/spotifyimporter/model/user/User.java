package org.burtelov.spotifyimporter.model.user;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Nikita Burtelov
 */

public class User {
    @NotEmpty(message = "Id id must not be empty")
    String id;

    public User(String id) {
        this.id = id;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
