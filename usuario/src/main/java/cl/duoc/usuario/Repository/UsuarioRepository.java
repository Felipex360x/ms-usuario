package cl.duoc.usuario.Repository;
import cl.duoc.usuario.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    
    boolean existsByCorreoIgnoreCase(String correo);

}
