package com.example.javaweblabs.dao.implementation;

import com.example.javaweblabs.dao.abstraction.UserResponseDao;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.entity.UserResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultUserResponseDao implements UserResponseDao {
    private final Connection connection;

    public DefaultUserResponseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(UserResponse entity) {
        String sql = "INSERT INTO user_responses(user_id, master_id, response) VALUES (?, ?, ?)";
        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getUser().getId());
            statement.setInt(2, entity.getMaster().getId());
            statement.setString(3, entity.getResponse());
            if (statement.executeUpdate() > 0) {
                created = true;
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return created;
    }

    @Override
    public Optional<UserResponse> findById(Integer id) {
        String sql = "SELECT * FROM user_resposes WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(resultSet.getInt("id"));
                userResponse.setResponse(resultSet.getString("response"));
                User user = new User();
                User master = new User();
                user.setId(resultSet.getInt("user_id"));
                master.setId(resultSet.getInt("master_id"));
                userResponse.setUser(user);
                userResponse.setMaster(master);
                return Optional.of(userResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserResponse> findAll() {
        List<UserResponse> userResponsesList = new ArrayList<>();
        String sql = "select user_responses.id as id, user_id, master_id, name as master_name, surname as master_surname, response from user_responses inner join users on master_id=users.id";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(resultSet.getInt("id"));
                userResponse.setResponse(resultSet.getString("response"));
                User user = new User();
                User master = new User();
                user.setId(resultSet.getInt("user_id"));
                master.setId(resultSet.getInt("master_id"));
                master.setName(resultSet.getString("master_name"));
                master.setSurname(resultSet.getString("master_surname"));
                userResponse.setUser(user);
                userResponse.setMaster(master);
                userResponsesList.add(userResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userResponsesList;
    }

    @Override
    public boolean update(UserResponse entity) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
