package cl.duoc.usuario.Controller;

import cl.duoc.usuario.DTO.UsuarioCreateDTO;
import cl.duoc.usuario.DTO.UsuarioDTO;
import cl.duoc.usuario.Service.UsuarioService;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//* nuevos imports */
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name="Usuarios",description = "Operaciones de gestion de usuarios")
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Operation(
        summary = "Listar todas los Usuarios",
        description = "Retorna la lista completa de Usuarios registradas en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>>getAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(summary = "Buscar Usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrada"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@Parameter(description = "Id unico de la mascota",required = true)@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }





    @Operation(summary = "Registrar nueva Usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creada exitosamente")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(dto));
    }

    @Operation(summary = "Actualizar Usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@Parameter(description = "Ide de la mascota a actualizar")@PathVariable Long id, @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar Usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Ide de la mascota a eliminar")@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }






    @PostMapping("/manual")
    public ResponseEntity<?> crearConValidacionManual(@RequestBody UsuarioCreateDTO dto){
        List<String> errores = usuarioService.validarUsuarioManual(dto);
        if(!errores.isEmpty()){
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        UsuarioDTO nuevoAuto = usuarioService.crear(dto);
        return new ResponseEntity<>(nuevoAuto,HttpStatus.CREATED);
    }




}
