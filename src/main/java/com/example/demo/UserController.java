package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController {
    @Autowired
    private JdbcTemplate shardingSphereJdbcTemplate;

    @GetMapping("/user")
    public String insert(@RequestParam String username) {
        Object[] params = new Object[] {
            username
        };

        shardingSphereJdbcTemplate.update("INSERT INTO users (username) VALUES (?)", params);
        return "User has been created";
    }
    
    @GetMapping("/users")
    public List<Map<String, Object>> getAll() {
        return shardingSphereJdbcTemplate.queryForList("SELECT * FROM users");
    }

    @GetMapping("/users/filter")
    public List<Map<String, Object>> filter(@RequestParam String username) {
        Object[] params = new Object[] {
            username 
        };
        return shardingSphereJdbcTemplate.queryForList("SELECT * FROM users WHERE username ILIKE ?", params);
    }
}
