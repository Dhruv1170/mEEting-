package com.api.frontendmeet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.frontendmeet.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findOneByEmailIgnoreCase(String invitee);

	Optional<UserEntity> findByEmailIgnoreCase(String email);

	@Query("SELECT email FROM UserEntity m WHERE m.id=:id")
	String getemailid(Long id);

}