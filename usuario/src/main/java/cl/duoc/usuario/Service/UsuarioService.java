package cl.duoc.usuario.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.usuario.Model.Usuario;

import cl.duoc.usuario.Repository.UsuarioRepository;
import cl.duoc.usuario.DTO.UsuarioDTO;
import cl.duoc.usuario.Exception.RecursoNoEncontradoException;
import cl.duoc.usuario.DTO.UsuarioCreateDTO;



@Service
public class UsuarioService {

    
    private static final Logger Log =LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> findAll(){
        Log.info("Consultadondo a todos los usuarios");
        return usuarioRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UsuarioDTO findById(Long id){
        Log.info("Buscando Usuario id={}",id);
        Usuario u = usuarioRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Usuario no encontrado:"+id));
        Log.info("Usuario encotrado: username={},correo={}", u.getNombreUser(),u.getCorreo());
        return toDTO(u);
    }
    public UsuarioDTO crear(UsuarioCreateDTO dto){
        Log.info("creando al usuario correo={}",dto.getCorreo());
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setApellidoP(dto.getApellidoP());
        u.setApellidoM(dto.getApellidoM());
        u.setNombreUser(dto.getNombreUser());
        u.setCorreo(dto.getCorreo());
        u.setPassword(dto.getPassword());
        u.setEdad(dto.getEdad());
        u.setGenero(dto.getGenero());
        u.setTipoUser(dto.getTipoUser());
        Usuario guardar = usuarioRepository.save(u);
        Log.info("uusario creado id={}",guardar.getId());
        return toDTO(guardar);
    }

    public UsuarioDTO actualizar(Long id,UsuarioCreateDTO dto){
        Log.info("actualizando usuari id ={}",id);
        Usuario u = usuarioRepository.findById(id) .orElseThrow(()-> new RecursoNoEncontradoException("Usuaro no encotrado:" +id));
        u.setNombre(dto.getNombre());
        u.setApellidoP(dto.getApellidoP());
        u.setApellidoM(dto.getApellidoM());
        u.setNombreUser(dto.getNombreUser());
        u.setCorreo(dto.getCorreo());
        u.setPassword(dto.getPassword());
        u.setEdad(dto.getEdad());
        u.setGenero(dto.getGenero());
        u.setTipoUser(dto.getTipoUser());
        return toDTO(usuarioRepository.save(u));
    }

    public void eliminar(Long id) {
        Log.info("Eliminando Usuario id={}", id);
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
        Log.info("Usuario id={} eliminado", id);
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
            u.getId(),
            u.getNombre(),
            u.getApellidoP(),
            u.getApellidoM(),
            u.getNombreUser(),
            u.getCorreo(),
            u.getPassword(),
            u.getEdad(),
            u.getGenero(),
            u.getTipoUser()
        );
    
    }

    public List<String> validarUsuarioManual(UsuarioCreateDTO dto){
        List<String> errores = new ArrayList<>();
        if(usuarioRepository.existsByCorreoIgnoreCase(dto.getCorreo())){
            errores.add("el correo ya esta registrado");
        }
        return errores;
    }

}