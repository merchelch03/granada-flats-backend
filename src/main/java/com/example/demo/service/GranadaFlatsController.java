package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/piso")
public class GranadaFlatsController {

    private final FotoFianzaService fianzaService;
    private final FotoFianzaRepository fianzaRepository;
    private final TareaLimpiezaRepository tareaRepository;
    private final PisoRepository pisoRepository;
    private final EstudianteRepository estudianteRepository;
    private final GastoService gastoService;
    private final GastoPisoRepository gastoPisoRepository;
    private final DeudaInquilinoRepository deudaInquilinoRepository;

    public GranadaFlatsController(FotoFianzaService fianzaService,
                                  FotoFianzaRepository fianzaRepository,
                                  TareaLimpiezaRepository tareaRepository,
                                  PisoRepository pisoRepository,
                                  EstudianteRepository estudianteRepository,
                                  GastoService gastoService,
                                  GastoPisoRepository gastoPisoRepository,
                                  DeudaInquilinoRepository deudaInquilinoRepository) {
        this.fianzaService = fianzaService;
        this.fianzaRepository = fianzaRepository;
        this.tareaRepository = tareaRepository;
        this.pisoRepository = pisoRepository;
        this.estudianteRepository = estudianteRepository;
        this.gastoService = gastoService;
        this.gastoPisoRepository = gastoPisoRepository;
        this.deudaInquilinoRepository = deudaInquilinoRepository;
    }

    @PostMapping("/estudiantes")
    public ResponseEntity<Estudiante> registrarEstudiante(@Valid @RequestBody Estudiante estudiante) {
        System.out.println("Registrando estudiante: " + estudiante.getEmail());
        // En un caso real, aquí cifraríamos la contraseña con BCrypt
        return ResponseEntity.ok(estudianteRepository.save(estudiante));
    }

    @GetMapping("/estudiantes/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        System.out.println("Login intento: " + email);
        return estudianteRepository.findByEmail(email)
                .map(estudiante -> {
                    if (estudiante.getPassword() != null && estudiante.getPassword().equals(password)) {
                        return ResponseEntity.ok(estudiante);
                    } else {
                        System.out.println("Contraseña incorrecta para: " + email);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                    }
                })
                .orElseGet(() -> {
                    System.out.println("Usuario no encontrado: " + email);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
                });
    }

    @PostMapping("/pisos")
    public ResponseEntity<?> crearPiso(@RequestBody Piso piso, @RequestParam Long estudianteId) {
        System.out.println("Creando piso para estudianteId: " + estudianteId);
        try {
            Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + estudianteId));
            
            Piso nuevoPiso = pisoRepository.save(piso);
            System.out.println("Piso guardado con ID: " + nuevoPiso.getId());
            
            estudiante.setPiso(nuevoPiso);
            estudianteRepository.save(estudiante);
            
            return ResponseEntity.ok(nuevoPiso);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear piso: " + e.getMessage());
        }
    }

    @PostMapping("/pisos/unirse")
    public ResponseEntity<?> unirseAPiso(@RequestParam String codigo, @RequestParam Long estudianteId) {
        System.out.println("Uniendo estudiante " + estudianteId + " al piso " + codigo);
        try {
            Piso piso = pisoRepository.findByCodigoInvitacion(codigo)
                    .orElseThrow(() -> new RuntimeException("Piso no encontrado"));
            Estudiante estudiante = estudianteRepository.findById(estudianteId).orElseThrow();

            estudiante.setPiso(piso);
            estudianteRepository.save(estudiante);

            return ResponseEntity.ok(piso);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al unirse: " + e.getMessage());
        }
    }

    @GetMapping("/pisos/buscar")
    public ResponseEntity<Piso> buscarPisoPorCodigo(@RequestParam String codigo) {
        return pisoRepository.findByCodigoInvitacion(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{pisoId}")
    public ResponseEntity<Piso> obtenerDetallesPiso(@PathVariable Long pisoId) {
        return pisoRepository.findById(pisoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MÓDULO 1: FIANZA ---

    @GetMapping("/{pisoId}/fianza")
    public ResponseEntity<List<FotoFianza>> obtenerGaleriaFianza(@PathVariable Long pisoId) {
        return ResponseEntity.ok(fianzaRepository.findByPisoId(pisoId));
    }

    @PostMapping("/{pisoId}/fianza/subir")
    public ResponseEntity<FotoFianza> subirFotoFianza(
            @PathVariable Long pisoId,
            @RequestParam("estudianteId") Long estudianteId,
            @RequestParam("estancia") String estancia,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("imagen") MultipartFile archivo) throws Exception {

        Piso piso = pisoRepository.findById(pisoId).orElseThrow();
        Estudiante estudiante = estudianteRepository.findById(estudianteId).orElseThrow();

        FotoFianza fotoSellada = fianzaService.registrarYSellarFoto(piso, estudiante, estancia, descripcion, archivo);
        return ResponseEntity.ok(fotoSellada);
    }

    // --- MÓDULO 2: LIMPIEZA ---

    @GetMapping("/{pisoId}/limpieza")
    public ResponseEntity<List<TareaLimpieza>> obtenerTareas(@PathVariable Long pisoId) {
        return ResponseEntity.ok(tareaRepository.findByPisoId(pisoId));
    }

    @PostMapping("/{pisoId}/limpieza")
    public ResponseEntity<TareaLimpieza> crearTarea(@PathVariable Long pisoId, @RequestBody TareaLimpieza tarea) {
        Piso piso = pisoRepository.findById(pisoId).orElseThrow();
        tarea.setPiso(piso);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        
        if (tarea.getEstudianteId() != null) {
            Estudiante e = estudianteRepository.findById(tarea.getEstudianteId()).orElse(null);
            tarea.setEstudiante(e);
        }
        
        return ResponseEntity.ok(tareaRepository.save(tarea));
    }

    @PutMapping("/limpieza/{tareaId}/estado")
    public ResponseEntity<TareaLimpieza> cambiarEstadoTarea(@PathVariable Long tareaId, @RequestParam EstadoTarea estado) {
        TareaLimpieza tarea = tareaRepository.findById(tareaId).orElseThrow();
        tarea.setEstado(estado);
        return ResponseEntity.ok(tareaRepository.save(tarea));
    }

    // --- MÓDULO 3: CUENTAS ---

    @GetMapping("/{pisoId}/gastos")
    public ResponseEntity<List<GastoPiso>> obtenerGastos(@PathVariable Long pisoId) {
        return ResponseEntity.ok(gastoPisoRepository.findByPisoId(pisoId));
    }

    @PostMapping("/{pisoId}/gastos")
    public ResponseEntity<GastoPiso> registrarGasto(@PathVariable Long pisoId, @RequestBody GastoPiso gasto, @RequestParam Long pagadorId) {
        Piso piso = pisoRepository.findById(pisoId).orElseThrow();
        Estudiante pagador = estudianteRepository.findById(pagadorId).orElseThrow();
        
        gasto.setPiso(piso);
        gasto.setPagador(pagador);
        gasto.setFecha(LocalDate.now());
        
        return ResponseEntity.ok(gastoService.registrarGastoYCalcularCuotas(gasto));
    }

    @GetMapping("/{pisoId}/saldos")
    public ResponseEntity<List<DeudaInquilino>> obtenerSaldos(@PathVariable Long pisoId) {
        return ResponseEntity.ok(deudaInquilinoRepository.findByGastoPisoPisoId(pisoId));
    }

    @PutMapping("/saldos/{deudaId}/saldar")
    public ResponseEntity<Void> saldarDeuda(@PathVariable Long deudaId) {
        gastoService.saldarDeuda(deudaId);
        return ResponseEntity.ok().build();
    }
}
