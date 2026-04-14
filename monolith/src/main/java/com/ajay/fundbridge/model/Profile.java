package com.ajay.fundbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @NotNull
  @Column(name = "user_id", nullable = false, unique = true)
  private UUID userId;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;
  @Column(name = "mobile_number")
  private String mobileNumber;
  @Column(name = "profile_img")
  private String profileImage;
  @Column(name = "image_public_id")
  private String imagePublicId;
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
