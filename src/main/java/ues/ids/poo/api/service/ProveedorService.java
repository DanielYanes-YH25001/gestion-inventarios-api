package ues.ids.poo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ues.ids.poo.api.dto.request.ProveedorRequestDTO;
import ues.ids.poo.api.dto.response.ProveedorResponseDTO;
import ues.ids.poo.api.mapper.ProveedorMapper;
import ues.ids.poo.api.model.Proveedor;
import ues.ids.poo.api.repository.ProveedorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    public List<ProveedorResponseDTO> obtenerTodos() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProveedorResponseDTO obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        return proveedorMapper.toResponseDTO(proveedor);
    }

    public ProveedorResponseDTO crear(ProveedorRequestDTO request) {
        Proveedor proveedor = proveedorMapper.toEntity(request);
        Proveedor guardado = proveedorRepository.save(proveedor);
        return proveedorMapper.toResponseDTO(guardado);
    }

    public ProveedorResponseDTO actualizar(Integer id, ProveedorRequestDTO request) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        proveedor.setNombre(request.getNombre());
        proveedor.setContacto(request.getContacto());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return proveedorMapper.toResponseDTO(actualizado);
    }

    public void eliminar(Integer id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }
}