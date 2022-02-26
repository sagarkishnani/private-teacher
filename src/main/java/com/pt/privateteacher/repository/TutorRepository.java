package com.pt.privateteacher.repository;

import com.pt.privateteacher.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {
    Optional<Tutor> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("FROM Tutor t WHERE t.titulo IN :titulo ORDER BY costo DESC")
    List<Tutor> findByTituloInDesc(@Param("titulo") List<String> titulo);
    @Query("FROM Tutor t WHERE t.titulo IN :titulo ORDER BY costo ASC")
    List<Tutor> findByTituloInAsc(@Param("titulo") List<String> titulo);
    @Query("FROM Tutor t WHERE t.titulo IN :titulo ORDER BY calificacion DESC")
    List<Tutor> findByTituloInRecom(@Param("titulo") List<String> titulo);
}