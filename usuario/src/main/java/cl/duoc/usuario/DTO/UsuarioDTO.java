package cl.duoc.usuario.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellidoP;
    /*apellido materno */
    private String apellidoM;
    /*nombre de usuario */
    private String nombreUser;
    private String correo;
    private String password;
    /*sse cambio el tipo de dato de string a interger  */
    private Integer edad;
    private String genero;
    /*tipo usuario como si es vendedor o es el comrpador */
    private String tipoUser;

}
