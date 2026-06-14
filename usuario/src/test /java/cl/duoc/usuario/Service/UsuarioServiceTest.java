package cl.duoc.usuario.Service;

import cl.duoc.usuario.DTO.UsuarioCreateDTO;
import cl.duoc.usuario.DTO.UsuarioDTO;
import cl.duoc.usuario.Exception.RecursoNoEncontradoException;
import cl.duoc.usuario.Model.Usuario;
import cl.duoc.usuario.Repository.UsuarioRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    //-------------------------------- FINDALL------------------------------------------------

    @Test
    @DisplayName("findAll - debe retornar lista de Usuario cuando existen registros")
    void debeRetornarListaDeUsuario(){
       //Given
    List<Usuario> usuariosSimulados = List.of(
        new Usuario(
            1L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        ),
        new Usuario(
            2L, "Felipe", "Acuña","Gonzalez","felipe123","felipe@gmail.com","abc123",30,"Masculino","Vendedor"
        )
    );
    when(usuarioRepository.findAll()).thenReturn(usuariosSimulados);
    //when
    List<UsuarioDTO> resultado = usuarioService.findAll();
    //then 
    assertNotNull(resultado);
    assertEquals(2, resultado.size());
    assertEquals("Maximo", resultado.get(0).getNombre());

    verify(usuarioRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("findAll - debe retornar lista vacia cuando no hay Usuario")
    void debeRetornarListaVaciaSiNoHayUsuario(){
        //Given
        when(usuarioRepository.findAll()).thenReturn(List.of());
        //when
        List<UsuarioDTO> resultado = usuarioService.findAll();
        //then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

     //-------------------------------- FINDBYID------------------------------------------------

    @Test
    @DisplayName("findById - debe retornar el DTO correcto cuando el usuario existe")
    void debeRetornarUsuarioPorId(){
        //Given
        Usuario usuario = new Usuario(
            1L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        // When
        UsuarioDTO resultado = usuarioService.findById(1L);
        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Maximo", resultado.getNombre());
        assertEquals("maximomango@gmail.com", resultado.getCorreo());
    }

    @Test
    @DisplayName("findById - debe lanzar RecursoNoEncontradoException cuando el ID no existe")
    void debeLanzarExcepcionCuandoUsuarioNoExiste(){
        // Given
         when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->usuarioService.findById(999L)
    );

    }

     // ── crear ──────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("crear - debe persistir y retornar el usuario con ID generado")
    void debeCrearUsuarioCorrectamente(){
        //Given
        UsuarioCreateDTO dto = new UsuarioCreateDTO(
            "Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );
        Usuario guardado = new Usuario(
            3L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);
        //when
        UsuarioDTO resultado = usuarioService.crear(dto);
        //then  
        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Maximo", resultado.getNombre());
        assertEquals("maximomango@gmail.com", resultado.getCorreo());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

        // ── eliminar ───────────────────────────────────────────────────────────────
    @Test
    @DisplayName("eliminar - debe lanzar excepción al intentar eliminar un ID inexistente")
    void debeLanzarExcepcionAlEliminarUsuarioInexistente() {
        // Given
        when(usuarioRepository.existsById(999L)).thenReturn(false);
        // When & Then
        assertThrows(RecursoNoEncontradoException.class, () ->usuarioService.eliminar(999L)
    );
    verify(usuarioRepository, never()).deleteById(any());
}

    @Test
    @DisplayName("eliminar - debe invocar deleteById cuando el usuario existe")
    void debeEliminarUsuarioExistente() {
        // Given
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        // When
        usuarioService.eliminar(1L);
        // Then
        verify(usuarioRepository, times(1)).deleteById(1L);
}


}
