package com.abbtech.task.model.repository;

import com.abbtech.task.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class UserRepository {


    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Long id) {
        List<User> users = jdbcTemplate.query("SELECT id, full_name, balance FROM users WHERE id = ?", (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setFullName(rs.getString("full_name"));
            u.setBalance(rs.getBigDecimal("balance"));
            return u;
        }, id);
        return users.isEmpty() ? null : users.get(0);
    }

    public void updateBalance(Long userId, BigDecimal newBalance) {
        jdbcTemplate.update("UPDATE users SET balance = ? WHERE id = ?", newBalance, userId);
    }
}



