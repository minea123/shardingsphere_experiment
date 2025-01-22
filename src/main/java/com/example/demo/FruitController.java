package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.regex.qual.RegexBottom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    @Autowired
    JdbcTemplate shardingShperJdbcTemplate;

    @PostMapping
    public ResponseEntity<String> createFruit(@RequestBody Map<String, String> body) {
        try {
            Object[] parms = new Object[] {
                body.get("name")
            };
    
            shardingShperJdbcTemplate.update("INSERT INTO fruits (name) VALUES (?)", parms);
            return new ResponseEntity<>("You have created new fruit", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<Map<String, Object>> getFruits(@RequestParam(defaultValue = "") String search) {
        Object[] params = new Object[] {
            "%" + search + "%"
        };
        return shardingShperJdbcTemplate.queryForList("SELECT * FROM fruits WHERE LOWER(name) LIKE ?", params);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateFruit(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            Object[] parms = new Object[] {
                body.get("name"),
                id
            };
    
            shardingShperJdbcTemplate.update("UPDATE fruits SET name = ? WHERE id = ?", parms);

            return new ResponseEntity<String>("Data has been updated id=" + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<String> delete(@PathVariable String id) {
        try {
            Object[] params = new Object[] {
                id
            };
    
            shardingShperJdbcTemplate.update("DELETE FROM fruits WHERE id = ?", params);
            return new ResponseEntity<>("fruit has been deleted! id=" + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
