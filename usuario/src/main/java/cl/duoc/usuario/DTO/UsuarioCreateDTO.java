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

  
    @Schema(description = "Nombre del Usuario", example = "maximo")
    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @Schema(description = "apellido paterno del Usuario", example = "rojas")
    @NotBlank(message = "el Apellido Paterno no puede estar vacio")
    private String apellidoP;
    /*apellido materno */
    @Schema(description = "apellido Materno del Usuario", example = "villa")
    @NotBlank(message = "el Apellido Materno no puede estar vacio")
    private String apellidoM;
    /*nombre de usuario */
    @Schema(description = "Nombre de usuario", example = "maximopro")
    @NotBlank(message = "el Nombre de usuario no puede estar vacio")
    private String nombreUser;
    @Schema(description = "correo de usuario", example = "maixmo@gmail.com")
    @NotBlank(message = "el Correo  no puede estar vacio")
    private String correo;
    @Schema(description = "contraseña", example = "123124")
    @NotBlank(message = "la contraseña no puede estar vacio")
    private String password;
    /*sse cambio el tipo de dato de string a interger  */
    @Schema(description = "edad del usuario", example = "19")
    @Min(value = 18,message = "Eres menor de edad")
    @Max(value = 80,message = "Eres menor de edad")
    private Integer edad;
    @Schema(description = "Genero del usuario", example = "Masculino")
    @NotBlank(message = "el genero no puede estar vacio")
    private String genero;
    /*tipo usuario como si es vendedor o es el comrpador */
    @Schema(description = "Tipo de usuario", example = "Comprador")
    @NotBlank(message = "ingrese el tipo de usuario vendedor o comprador")
    private String tipoUser;

    
}
