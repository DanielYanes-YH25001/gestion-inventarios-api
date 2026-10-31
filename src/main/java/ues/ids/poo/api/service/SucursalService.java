package ues.ids.poo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.request.SucursalRequestDTO;
import ues.ids.poo.api.dto.response.SucursalResponseDTO;
import ues.ids.poo.api.mapper.SucursalMapper;
import ues.ids.poo.api.model.Sucursal;
import ues.ids.poo.api.repository.StockSucursalRepository;
import ues.ids.poo.api.repository.SucursalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final StockSucursalRepository stockSucursalRepository;
    private final SucursalMapper sucursalMapper;

    public SucursalResponseDTO crearSucursal(SucursalRequestDTO request) {
        Sucursal sucursal = sucursalRepository.save(sucursalMapper.toEntity(request));
        return sucursalMapper.toResponseDTO(sucursal);
    }

    public List<SucursalResponseDTO> obtenerTodas() {
        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::toResponseDTO)
                .toList();
    }

    public SucursalResponseDTO obtenerPorId(Integer id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con el ID: " + id));

        return sucursalMapper.toResponseDTO(sucursal);
    }


    public SucursalResponseDTO actualizarSucursal(Integer id, SucursalRequestDTO request) {
        Sucursal sucursal = obtenerSucursal(id);
        sucursalMapper.updateEntity(sucursal, request);
        return sucursalMapper.toResponseDTO(sucursalRepository.save(sucursal));
    }

    @Transactional
    public void eliminarSucursal(Integer id) {
        Sucursal sucursal = obtenerSucursal(id);
        if (stockSucursalRepository.existsBySucursal_IdSucursal(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la sucursal porque tiene stock asociado"
            );
        }

        try {
            sucursalRepository.delete(sucursal);
            sucursalRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la sucursal porque tiene registros asociados",
                    exception
            );
        }
    }

    private Sucursal obtenerSucursal(Integer id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada"));
    }
}