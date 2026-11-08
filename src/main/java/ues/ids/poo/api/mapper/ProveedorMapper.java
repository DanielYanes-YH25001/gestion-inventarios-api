package ues.ids.poo.api.mapper;

import org.springframework.stereotype.Component;
import ues.ids.poo.api.dto.request.ProveedorRequestDTO;
import ues.ids.poo.api.dto.response.ProveedorResponseDTO;
import ues.ids.poo.api.model.Proveedor;

@Component
public class ProveedorMapper {

    public Proveedor toEntity(ProveedorRequestDTO dto) {
        Proveedor entity = new Proveedor();
        entity.setNombre(dto.getNombre());
        entity.setContacto(dto.getContacto());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        return entity;
    }

    public ProveedorResponseDTO toResponseDTO(Proveedor entity) {
        return new ProveedorResponseDTO(
            entity.getIdProveedor(),
            entity.getNombre(),
            entity.getContacto(),
            entity.getTelefono(),
            entity.getDireccion()
        );
    }
}
