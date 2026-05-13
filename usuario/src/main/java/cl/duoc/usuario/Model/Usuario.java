package cl.duoc.usuario.Model;

import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import lombok.Data;



/*nos permite tener un orden de los atributos  */
@JsonPropertyOrder({
    "id",
    "nombre",
    "apellidoP",
    "apellidoM",
    "nombreUser",
    "correo",
    "edad",
    "genero",
    "tipoUser",
    "password"
})


@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    /*apellido paterno */
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
