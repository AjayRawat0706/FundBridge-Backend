package com.fundbridge.users.repository;

import com.fundbridge.users.model.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserId(UUID userId);

    boolean existsByMobileNumber(@NotBlank(message = "Mobile number required") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number") String mobileNumber);
}
