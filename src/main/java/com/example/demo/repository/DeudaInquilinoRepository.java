package com.example.demo.repository;

import com.example.demo.model.DeudaInquilino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeudaInquilinoRepository extends JpaRepository<DeudaInquilino, Long> {
    List<DeudaInquilino> findByGastoPisoPisoId(Long pisoId);
}
