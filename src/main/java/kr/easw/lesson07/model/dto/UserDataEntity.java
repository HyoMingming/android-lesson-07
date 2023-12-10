package kr.easw.lesson07.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity

public class UserDataEntity {
    @Id
    @GeneratedValue
    private long id;
    private String userId;
    private String password;
    private boolean isAdmin;

    public UserDataEntity() {
    }

    public UserDataEntity(final long id, final String userId, final String password, final boolean isAdmin) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }
}

