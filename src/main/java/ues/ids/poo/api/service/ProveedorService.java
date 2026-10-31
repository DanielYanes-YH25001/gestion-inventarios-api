package ues.ids.poo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.request.ProveedorRequestDTO;
import ues.ids.poo.api.dto.response.ProveedorResponseDTO;
import ues.ids.poo.api.mapper.ProveedorMapper;
import ues.ids.poo.api.model.Proveedor;
import ues.ids.poo.api.repository.ProductoRepository;
import ues.ids.poo.api.repository.ProveedorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;
    private final ProductoRepository productoRepository;

    public List<ProveedorResponseDTO> obtenerTodos() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toResponseDTO)
                .toList();
    }

    public ProveedorResponseDTO obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id));
        return proveedorMapper.toResponseDTO(proveedor);
    }

    public ProveedorResponseDTO crear(ProveedorRequestDTO request) {
        Proveedor proveedor = proveedorMapper.toEntity(request);
        Proveedor guardado = proveedorRepository.save(proveedor);
        return proveedorMapper.toResponseDTO(guardado);
    }

    public ProveedorResponseDTO actualizar(Integer id, ProveedorRequestDTO request) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id));

        proveedor.setNombre(request.getNombre());
        proveedor.setContacto(request.getContacto());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return proveedorMapper.toResponseDTO(actualizado);
    }

    @Transactional
    public void eliminar(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado con ID: " + id));

        if (productoRepository.existsByProveedor_IdProveedor(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el proveedor porque tiene productos asociados"
            );
        }

        try {
            proveedorRepository.delete(proveedor);
            proveedorRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el proveedor porque tiene productos asociados",
                    exception
            );
        }
    }
}
