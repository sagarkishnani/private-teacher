package com.pt.privateteacher.repository;

import com.pt.privateteacher.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Optional<Tutor> findByEmail(String email);
    boolean existsByEmail(String email);
}