package cl.duoc.usuario.Repository;


import cl.duoc.usuario.Model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("save - debe persistir el usuario y asignar un Id generado automaticamnte")
    void debePersistirUsuarioYAsignarIdGenerado(){
        //given
        Usuario usuario = new Usuario(
             null,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );
        //when
        Usuario guardado = usuarioRepository.save(usuario);
        //then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Maximo", guardado.getNombre());
    }

    @Test
    @DisplayName("findAll - debe retornar todos los Usuarios guardados en la BD")
    void debeRetornarTodosLosUsuarios(){
        //Given
        usuarioRepository.save(new Usuario(
            null,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        ));
        usuarioRepository.save(new Usuario(
            null,"Maria","Gomez","Diaz","maria123","maria@gmail.com","xyz789",25,"Femenino","Vendedor"
        ));
        //when
        List<Usuario> usuarios = usuarioRepository.findAll();
        //then
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());

    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeEncontrarUsuarioPorIdExistente(){
        //Give
        Usuario guardado = usuarioRepository.save(new Usuario(
             null,"Pedro","Lopez","Rojas", "pedro123","pedro@gmail.com", "pass123",28,"Masculino","Comprador"
        ));
        //when
        Optional<Usuario> resultado = usuarioRepository.findById(guardado.getId());
        //then
        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombre());
    } 

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Usuario> resultado = usuarioRepository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }
    @Test
    @DisplayName("deleteById - debe eliminar el Usuario de la base de datos")
    void debeEliminarUsuarioPorId(){
        //Given
        Usuario guardado = usuarioRepository.save(new Usuario(
            null,"Carlos","Mora","Silva","carlos123","carlos@gmail.com","pass123",35,"Masculino","Vendedor"
        ));
        Long id = guardado.getId();
        //when
        usuarioRepository.deleteById(id);
        //then
        assertFalse(usuarioRepository.findById(id).isPresent());

}






}