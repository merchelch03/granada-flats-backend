package com.example.demo.repository;

import com.example.demo.model.TareaLimpieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaLimpiezaRepository extends JpaRepository<TareaLimpieza, Long> {
    List<TareaLimpieza> findByPisoId(Long pisoId);
}