package com.ajay.fundbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @NotNull
  @Column(name = "user_id")
  private UUID userId;

}
