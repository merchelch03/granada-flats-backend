package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class GastoService {

    private final GastoPisoRepository gastoRepository;
    private final DeudaInquilinoRepository deudaRepository;

    public GastoService(GastoPisoRepository gastoRepository, DeudaInquilinoRepository deudaRepository) {
        this.gastoRepository = gastoRepository;
        this.deudaRepository = deudaRepository;
    }

    @Transactional
    public GastoPiso registrarGastoYCalcularCuotas(GastoPiso gasto) {
        GastoPiso gastoGuardado = gastoRepository.save(gasto);
        List<Estudiante> inquilinos = gasto.getPiso().getInquilinos();

        int totalInquilinos = inquilinos.size();
        if (totalInquilinos <= 1) return gastoGuardado;

        // División equitativa
        BigDecimal cuota = gasto.getImporteTotal().divide(
            new BigDecimal(totalInquilinos), 2, RoundingMode.HALF_UP
        );

        // Crear registro de deuda para cada compañero salvo el pagador
        for (Estudiante inquilino : inquilinos) {
            if (!inquilino.getId().equals(gasto.getPagador().getId())) {
                DeudaInquilino deuda = new DeudaInquilino();
                deuda.setGastoPiso(gastoGuardado);
                deuda.setAcreedor(gasto.getPagador());
                deuda.setDeudor(inquilino);
                deuda.setCuotaIndividual(cuota);
                deuda.setEstado(EstadoDeuda.PENDIENTE);
                deudaRepository.save(deuda);
            }
        }

        return gastoGuardado;
    }

    @Transactional
    public void saldarDeuda(Long deudaId) {
        DeudaInquilino deuda = deudaRepository.findById(deudaId)
            .orElseThrow(() -> new IllegalArgumentException("Deuda no encontrada"));
        deuda.setEstado(EstadoDeuda.SALDADO);
        deudaRepository.save(deuda);
    }
}