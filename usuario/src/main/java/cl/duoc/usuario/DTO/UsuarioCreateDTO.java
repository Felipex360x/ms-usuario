package cl.duoc.usuario.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "el Apellido Paterno no puede estar vacio")
    private String apellidoP;
    /*apellido materno */
    @NotBlank(message = "el Apellido Materno no puede estar vacio")
    private String apellidoM;
    /*nombre de usuario */
    @NotBlank(message = "el Nombre de usuario no puede estar vacio")
    private String nombreUser;
    @NotBlank(message = "el Correo  no puede estar vacio")
    private String correo;
    @NotBlank(message = "la contraseña no puede estar vacio")
    private String password;
    /*sse cambio el tipo de dato de string a interger  */
    @Min(value = 18,message = "Eres menor de edad")
    @Max(value = 80,message = "Eres menor de edad")
    private Integer edad;
    @NotBlank(message = "el genero no puede estar vacio")
    private String genero;
    /*tipo usuario como si es vendedor o es el comrpador */
    @NotBlank(message = "ingrese el tipo de usuario vendedor o comprador")
    private String tipoUser;

    
}
