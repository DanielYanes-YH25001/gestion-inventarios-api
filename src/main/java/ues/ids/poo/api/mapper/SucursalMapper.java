package ues.ids.poo.api.mapper;

import org.springframework.stereotype.Component;
import ues.ids.poo.api.dto.request.SucursalRequestDTO;
import ues.ids.poo.api.dto.response.SucursalResponseDTO;
import ues.ids.poo.api.model.Sucursal;

@Component
public class SucursalMapper {

    public Sucursal toEntity(SucursalRequestDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setActiva(dto.getActiva());
        return sucursal;
    }

    public void updateEntity(Sucursal sucursal, SucursalRequestDTO dto) {
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setActiva(dto.getActiva());
    }

    public SucursalResponseDTO toResponseDTO(Sucursal sucursal) {
        return new SucursalResponseDTO(
                sucursal.getIdSucursal(),
                sucursal.getNombre(),
                sucursal.getDireccion(),
                sucursal.getActiva()
        );
    }
}
