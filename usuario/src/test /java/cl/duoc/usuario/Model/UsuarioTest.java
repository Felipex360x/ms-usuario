package cl.duoc.usuario.Model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class UsuarioTest {

    @Test
    @DisplayName("Contructor vacio - debe crear una istancia no nula")
     void constructorVacioDebeCrearInstanciaNoNula() {
        Usuario usuario = new Usuario();
        assertNotNull(usuario);
    }

    @Test
    @DisplayName("Contructor completo - debe Asignar todos los campos correctomante ")
    void constructorCompletoDebeAsignarTodosLosCampos(){
        Usuario usuario = new  Usuario(
            1L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );

        assertEquals(1L, usuario.getId());
        assertEquals("Felipe", usuario.getNombre());
        assertEquals("Acuña", usuario.getApellidoP());
        assertEquals("Gonzalez", usuario.getApellidoM());
        assertEquals("felipe123", usuario.getNombreUser());
        assertEquals("felipe@gmail.com", usuario.getCorreo());
        assertEquals("123456", usuario.getPassword());
        assertEquals(25, usuario.getEdad());
        assertEquals("Masculino", usuario.getGenero());
        assertEquals("Comprador", usuario.getTipoUser());
    }
    @Test
    @DisplayName("Setters - debe permitir modicar cada compo individualmente")
    void settersDebenPermitirModificarCampos() {
        Usuario usuario = new Usuario();

        usuario.setId(2L);
        usuario.setNombre("Juan");
        usuario.setApellidoP("Perez");
        usuario.setApellidoM("Soto");
        usuario.setNombreUser("juan123");
        usuario.setCorreo("juan@gmail.com");
        usuario.setPassword("abc123");
        usuario.setEdad(30);
        usuario.setGenero("Masculino");
        usuario.setTipoUser("Vendedor");

        assertEquals(2L, usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Perez", usuario.getApellidoP());
        assertEquals("Soto", usuario.getApellidoM());
        assertEquals("juan123", usuario.getNombreUser());
        assertEquals("juan@gmail.com", usuario.getCorreo());
        assertEquals("abc123", usuario.getPassword());
        assertEquals(30, usuario.getEdad());
        assertEquals("Masculino", usuario.getGenero());
        assertEquals("Vendedor", usuario.getTipoUser());
    }

    @Test
    @DisplayName("equals y hashCode - dos Usuarios con los mismos datos deben ser iguales")
     void dosUsuariosConMismosDatosDebenSerIguales(){
        Usuario u1 = new Usuario(
            1L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );

        Usuario u2 = new Usuario(
            1L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
        );
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
     }


    @Test
    @DisplayName("ToString - debe contener el nombre del usuario en la representacion")
    void toStringDebeContenerNombreDelUsuario(){

        Usuario usuario = new Usuario(
             3L,"Maximo","peres","rojas","Maximo4312","maximomango@gmail.com","ColoColoheart",25,"masculino","comprador"
            
        );
        String texto = usuario.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("Maximo"));
    }




    
}
