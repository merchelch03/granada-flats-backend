package com.example.demo.repository;

import com.example.demo.model.FotoFianza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FotoFianzaRepository extends JpaRepository<FotoFianza, Long> {
    List<FotoFianza> findByPisoId(Long pisoId);
}