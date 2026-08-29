package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class FotoFianzaService {

    private final FotoFianzaRepository fotoRepository;

    public FotoFianzaService(FotoFianzaRepository fotoRepository) {
        this.fotoRepository = fotoRepository;
    }

    public FotoFianza registrarYSellarFoto(Piso piso, Estudiante estudiante, String estancia, String descripcion, MultipartFile archivo) throws Exception {
        // 1. Calcular Hash SHA-256 en el backend
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(archivo.getBytes());
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        String sha256Hash = hexString.toString();

        // 2. Simulación de guardado de archivo físico / Cloud Storage
        String urlSimulada = "/uploads/fianza/" + System.currentTimeMillis() + "_" + archivo.getOriginalFilename();

        // 3. Crear entidad con sello de tiempo del servidor
        FotoFianza foto = new FotoFianza();
        foto.setPiso(piso);
        foto.setEstudiante(estudiante);
        foto.setEstancia(estancia);
        foto.setDescripcion(descripcion);
        foto.setUrlImagen(urlSimulada);
        foto.setCodigoHashSha256(sha256Hash);
        foto.setTimestampServidor(LocalDateTime.now());

        return fotoRepository.save(foto);
    }
}