package com.abbtech.task.model.repository;

import com.abbtech.task.model.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertPayment(Long userId, BigDecimal amount, String status) {
        Long id = jdbcTemplate.queryForObject("SELECT nextval('payments_seq')", Long.class);

        jdbcTemplate.update(
                "INSERT INTO payments (id, user_id, amount, status, created_at) VALUES (?,?,?,?,?)",
                id, userId, amount, status, Timestamp.valueOf(LocalDateTime.now())
        );
        return id;
    }

    public void updateStatus(Long paymentId, String status) {
        jdbcTemplate.update(
                "UPDATE payments SET status = ? WHERE id = ?",
                status, paymentId
        );
    }
 /// hamisini getir
    public List<Payment> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM payments ORDER BY created_at DESC",
                paymentRowMapper
        );
    }

    public List<Payment> findByUserId(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM payments WHERE user_id = ? ORDER BY created_at DESC",
                paymentRowMapper,
                userId
        );
    }

    public Payment findById(Long paymentId) {
        List<Payment> list = jdbcTemplate.query(
                "SELECT * FROM payments WHERE id = ?",
                paymentRowMapper,
                paymentId
        );
        return list.isEmpty() ? null : list.get(0);
    }

    private final RowMapper<Payment> paymentRowMapper = (rs, rowNum) -> {
        Payment p = new Payment();
        p.setId(rs.getLong("id"));
        p.setUserId(rs.getLong("user_id"));
        p.setAmount(rs.getBigDecimal("amount"));
        p.setStatus(rs.getString("status"));
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return p;
    };
}

