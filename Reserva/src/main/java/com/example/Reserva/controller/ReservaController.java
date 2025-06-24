    package com.example.Reserva.controller;

    import com.example.Reserva.model.Estado;
    import com.example.Reserva.model.Reserva;
    import com.example.Reserva.service.EstadoService;
    import com.example.Reserva.service.ReservaService;

    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.media.Content;
    import io.swagger.v3.oas.annotations.media.Schema;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
    import jakarta.validation.Valid;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.dao.EmptyResultDataAccessException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/v1/reserva")
    public class ReservaController {

        @Autowired
        private ReservaService ReservaService;

        @Autowired
        private EstadoService EstadoService;

        @Operation(summary="Permite obtener una lista con todas las Reservas")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Generó la lista con todas las Reservas",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })


        // llama a todas las reservas
        @GetMapping("/Total")
        public ResponseEntity<List<Reserva>> listarReservas() {
            return ResponseEntity.ok(ReservaService.listarReservas());
        }

        @Operation(summary="Permite obtener una Reserva por su id")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Se obtuvo la reerva realizada por su id",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })

        // los llama por id
        @GetMapping("/{id}")
        public ResponseEntity<Reserva> buscarReservaPorId(@PathVariable Long id) {
            Reserva reserva = ReservaService.buscarReservaPorId(id);

            if (reserva == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(reserva);
        }

        @Operation(summary="Permite crear una nueva reserva")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Generó la nueva reserva",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })

        // este es para crear una nueva reserva
        @PostMapping
        public ResponseEntity<?> crearReserva(@Valid @RequestBody Reserva reserva) {
            try {
                Reserva nueva = ReservaService.crearReserva(reserva);
                return ResponseEntity.ok(nueva);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @Operation(summary="Permite crear un nuevo estado")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Generó el nuevo estado de la reserva",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })

        // este es para crear un esatado
        @PostMapping("/estado")
        public ResponseEntity<Estado> crearEstado(@Valid @RequestBody Estado estado) {
            return ResponseEntity.status(HttpStatus.CREATED).body(EstadoService.crearEstado(estado));
        }


        @Operation(summary="Permite obtener una lista con todas los estados")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Generó la lista con todas los esatdos",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })
        // este es para listar los estados
        @GetMapping("/estado")
        public ResponseEntity<List<Estado>> listarEstados() {
            return ResponseEntity.ok(EstadoService.listarEstados());
        }

        @Operation(summary="Permite eliminar la reserva por su Id")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Se elimino la reserva por su id",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })

        // este es para eliminar por id
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteReservaPorId(@PathVariable Long id) {
            try {
                ReservaService.eliminarReserva(id);
                return ResponseEntity.noContent().build();
            } catch (EmptyResultDataAccessException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La reserva con ID " + id + " no existe.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la reserva.");
            }
        }

        @Operation(summary="Permite obtener las reservas por el usuario")
        @ApiResponses( value = {
            @ApiResponse(responseCode="200", description="Generó la lista con todas las Reservas del usuario",
            content=@Content(schema=@Schema(implementation=Reserva.class)))
        })

        @GetMapping("/usuario/{usuarioId}")
        public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
            List<Reserva> reservas = ReservaService.listarReservasPorUsuarioId(usuarioId);
            if (reservas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(reservas);
        }

    }
