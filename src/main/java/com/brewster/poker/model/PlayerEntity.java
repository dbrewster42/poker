package com.brewster.poker.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class PlayerEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     @Column(nullable = false, unique = true)
     private String username;
     private Integer money;
     @Column(updatable = false)
     @DateTimeFormat(pattern="yyyy-MM-dd")
     private Date createdAt;
     @DateTimeFormat(pattern="yyyy-MM-dd")
     private Date updatedAt;
}
