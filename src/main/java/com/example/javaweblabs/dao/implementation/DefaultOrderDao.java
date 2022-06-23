package com.example.javaweblabs.dao.implementation;

import com.example.javaweblabs.dao.abstraction.OrderDao;
import com.example.javaweblabs.entity.Order;
import com.example.javaweblabs.entity.User;
import com.example.javaweblabs.enums.OrderStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultOrderDao implements OrderDao {
    private final Connection connection;

    public DefaultOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Order entity) {
        String sql = "INSERT INTO orders(user_id, master_id, status_id, price, description, start_date, end_date) VALUES (?, default, default, default, ?, ?, default)";
        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getUser().getId());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, Date.valueOf(entity.getStartDate()));
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
    public Optional<Order> findById(Integer id) {
        String sql = "SELECT orders.id, user_id, master_id, name, surname, price, description, start_date, end_date, status_id FROM orders LEFT OUTER JOIN users ON orders.master_id = users.id WHERE orders.id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                User user = new User();
                User master = new User();
                user.setId(resultSet.getInt("user_id"));
                master.setId(resultSet.getInt("master_id"));
                master.setName(resultSet.getString("name"));
                master.setSurname(resultSet.getString("surname"));
                order.setUser(user);
                order.setMaster(master);
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status_id")));
                Optional.ofNullable(resultSet.getString("price")).ifPresent(price -> order.setPrice(Integer.valueOf(price)));
                order.setDescription(resultSet.getString("description"));
                Optional<String> start_date = Optional.ofNullable(resultSet.getString("start_date"));
                Optional<String> end_date = Optional.ofNullable(resultSet.getString("end_date"));
                start_date.ifPresent((data -> order.setStartDate(LocalDate.parse(data))));
                end_date.ifPresent((data -> order.setEndDate(LocalDate.parse(data))));
                return Optional.of(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
//        String sql = "SELECT * FROM orders ORDER BY id ASC";
        String sql = "SELECT orders.id, user_id, master_id, name, surname, price, description, start_date, end_date, status_id FROM orders LEFT OUTER JOIN users ON orders.master_id = users.id ORDER BY orders.id ASC";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                User user = new User();
                User master = new User();
                user.setId(resultSet.getInt("user_id"));
                master.setId(resultSet.getInt("master_id"));
                master.setName(resultSet.getString("name"));
                master.setSurname(resultSet.getString("surname"));
                order.setUser(user);
                order.setMaster(master);
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status_id")));
                Optional.ofNullable(resultSet.getString("price")).ifPresent(price -> order.setPrice(Integer.valueOf(price)));
                order.setDescription(resultSet.getString("description"));
                Optional<String> start_date = Optional.ofNullable(resultSet.getString("start_date"));
                Optional<String> end_date = Optional.ofNullable(resultSet.getString("end_date"));
                start_date.ifPresent((data -> order.setStartDate(LocalDate.parse(data))));
                end_date.ifPresent((data -> order.setEndDate(LocalDate.parse(data))));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public boolean update(Order entity) {
        boolean updated = false;
        Optional<Order> storedOrder = findById(entity.getId());
        String sql = "UPDATE orders SET user_id=?, master_id=?, status_id=(?::order_statuses), price=?, description=?, start_date=?, end_date=? WHERE id=?";
        if (storedOrder.isPresent()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, entity.getUser().getId());
                if (entity.getMaster() != null) {
                    statement.setInt(2, entity.getMaster().getId());
                } else {
                    statement.setNull(2, Types.INTEGER);
                }
                statement.setString(3, entity.getOrderStatus().name());
                statement.setInt(4, entity.getPrice());
                statement.setString(5, entity.getDescription());
                statement.setDate(6, Date.valueOf(entity.getStartDate()));
                if (entity.getEndDate() != null) {
                    statement.setDate(7, Date.valueOf(entity.getEndDate()));
                } else {
                    statement.setNull(7, Types.DATE);
                }
                statement.setInt(8, entity.getId());
                updated = statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }

    @Override
    public List<Order> findUserOrders(Integer userId) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT orders.id, user_id, master_id, name, surname, price, description, start_date, end_date, status_id FROM orders LEFT OUTER JOIN users ON orders.master_id = users.id WHERE orders.user_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status_id")));
                Optional.ofNullable(resultSet.getString("price")).ifPresent(price -> order.setPrice(Integer.valueOf(price)));
                order.setDescription(resultSet.getString("description"));
                Optional<String> start_date = Optional.ofNullable(resultSet.getString("start_date"));
                Optional<String> end_date = Optional.ofNullable(resultSet.getString("end_date"));
                start_date.ifPresent((data -> {
                    order.setStartDate(LocalDate.parse(data));
                }));
                end_date.ifPresent((data -> {
                    order.setEndDate(LocalDate.parse(data));
                }));

                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                User master = new User();
                master.setId(resultSet.getInt("master_id"));
                master.setName(resultSet.getString("name"));
                master.setSurname(resultSet.getString("surname"));
                order.setUser(user);
                order.setMaster(master);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public List<Order> findMasterOrders(Integer masterId) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE master_id = ? ORDER BY id ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, masterId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status_id")));
                Optional.ofNullable(resultSet.getString("price")).ifPresent(price -> order.setPrice(Integer.valueOf(price)));
                order.setDescription(resultSet.getString("description"));
                Optional<String> start_date = Optional.ofNullable(resultSet.getString("start_date"));
                Optional<String> end_date = Optional.ofNullable(resultSet.getString("end_date"));
                start_date.ifPresent((data -> {
                    order.setStartDate(LocalDate.parse(data));
                }));
                end_date.ifPresent((data -> {
                    order.setEndDate(LocalDate.parse(data));
                }));

                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                User master = new User();
                master.setId(resultSet.getInt("master_id"));
                order.setUser(user);
                order.setMaster(master);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
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
