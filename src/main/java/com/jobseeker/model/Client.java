package com.jobseeker.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Client {

    @Id
    @GeneratedValue()
    private UUID uuid;
    private String name;
    private String email;

    public Client() {

    }

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
