package cl.duoc.usuario.Controller;

import cl.duoc.usuario.DTO.UsuarioDTO;
import cl.duoc.usuario.Exception.GlobalExceptionHandler;
import cl.duoc.usuario.Exception.RecursoNoEncontradoException;
import cl.duoc.usuario.Service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // ── GET /api/v2/usuarios ──────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/v2/usuarios - debe retornar 200 con la lista de usuarios")
    void debeRetornar200CuandoSePidenUsuarios() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of(
            new UsuarioDTO(
                1L,"Maximo","Perez","Rojas","maximo123","maximo@gmail.com","123456",25,"Masculino","Comprador"
                ),
            new UsuarioDTO(
                2L, "Felipe","Acuña","Gonzalez","felipe123","felipe@gmail.com","abc123",30,"Masculino","Vendedor"
                )
        ));
        // When & Then
        mockMvc.perform(get("/api/v2/usuarios")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].nombre").value("Maximo"));
    }

    @Test
    @DisplayName("GET /api/v2/usuarios - debe retornar 200 con lista vacía")
    void debeRetornar200ConListaVacia() throws Exception {
        // Given
        when(service.findAll()).thenReturn(List.of());
        // When & Then
        mockMvc.perform(get("/api/v2/usuarios")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/v2/usuarios/{id} ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/v2/usuarios/{id} - debe retornar usuario existente")
    void debeRetornarUsuarioPorId() throws Exception {
        // Given
        when(service.findById(1L)).thenReturn(
            new UsuarioDTO(
                1L, "Maximo", "Perez","Rojas","maximo123","maximo@gmail.com","123456",25,"Masculino","Comprador"
            )     
        );

        // When & Then
        mockMvc.perform(get("/api/v2/usuarios/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.nombre").value("Maximo"));
    }

    @Test
    @DisplayName("GET /api/v2/usuarios/{id} - debe retornar 404 cuando el usuario no existe")
    void debeRetornar404CuandoUsuarioNoExiste() throws Exception {
        // Given
        when(service.findById(999L)).thenThrow(new RecursoNoEncontradoException("Usuario no encontrado: 999"));
        // When & Then
        mockMvc.perform(get("/api/v2/usuarios/999")).andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Usuario no encontrado: 999"));
    }

    // ── POST /api/v2/usuarios ─────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/v2/usuarios - debe retornar 201 al crear un usuario válido")
    void debeRetornar201AlCrearUsuario() throws Exception {
        // Given
        String json = """
                {
                    "nombre":"Maximo",
                    "apellidoP":"Perez",
                    "apellidoM":"Rojas",
                    "nombreUser":"maximo123",
                    "correo":"maximo@gmail.com",
                    "password":"123456",
                    "edad":25,
                    "genero":"Masculino",
                    "tipoUser":"Comprador"
                }
                """;
        when(service.crear(any())).thenReturn(
                new UsuarioDTO(
                        1L,
                        "Maximo",
                        "Perez",
                        "Rojas",
                        "maximo123",
                        "maximo@gmail.com",
                        "123456",
                        25,
                        "Masculino",
                        "Comprador"
                )
        );
        // When & Then
        mockMvc.perform(post("/api/v2/usuarios").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.nombre").value("Maximo"));
    }
    @Test
    @DisplayName("POST /api/v2/usuarios - debe retornar 400 cuando el nombre está vacío")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {

        // Given
        String json = """
                {
                    "nombre":"",
                    "apellidoP":"Perez",
                    "apellidoM":"Rojas",
                    "nombreUser":"maximo123",
                    "correo":"maximo@gmail.com",
                    "password":"123456",
                    "edad":25,
                    "genero":"Masculino",
                    "tipoUser":"Comprador"
                }
                """;
        // When & Then
        mockMvc.perform(post("/api/v2/usuarios").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
    }

    // ── PUT /api/v2/usuarios/{id} ─────────────────────────────────────────────
    @Test
    @DisplayName("PUT /api/v2/usuarios/{id} - debe actualizar usuario existente")
    void debeActualizarUsuario() throws Exception {

        String json = """
                {
                    "nombre":"Maximo Actualizado",
                    "apellidoP":"Perez",
                    "apellidoM":"Rojas",
                    "nombreUser":"maximo123",
                    "correo":"maximo@gmail.com",
                    "password":"123456",
                    "edad":26,
                    "genero":"Masculino",
                    "tipoUser":"Comprador"
                }
                """;

        when(service.actualizar(any(Long.class), any()))
                .thenReturn(
                        new UsuarioDTO(
                                1L,
                                "Maximo Actualizado",
                                "Perez",
                                "Rojas",
                                "maximo123",
                                "maximo@gmail.com",
                                "123456",
                                26,
                                "Masculino",
                                "Comprador"
                        )
                );

        mockMvc.perform(put("/api/v2/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre")
                        .value("Maximo Actualizado"));
    }

    // ── DELETE /api/v2/usuarios/{id} ──────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/v2/usuarios/{id} - debe eliminar usuario existente")
    void debeEliminarUsuario() throws Exception {
        doNothing().when(service).eliminar(1L);
        mockMvc.perform(delete("/api/v2/usuarios/1")).andExpect(status().isNoContent());
    }
}