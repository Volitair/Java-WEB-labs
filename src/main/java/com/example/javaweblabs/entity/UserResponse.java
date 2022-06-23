package com.example.javaweblabs.entity;

public class UserResponse {
    private Integer id;
    private User user;
    private User master;
    private String response;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", user=" + user +
                ", master=" + master +
                ", response='" + response + '\'' +
                '}';
    }
}
