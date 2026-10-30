package ues.ids.poo.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.request.ProductoRequestDTO;
import ues.ids.poo.api.dto.response.ProductoResponseDTO;
import ues.ids.poo.api.model.Categoria;
import ues.ids.poo.api.model.Producto;
import ues.ids.poo.api.model.Proveedor;
import ues.ids.poo.api.repository.CategoriaRepository;
import ues.ids.poo.api.repository.ProductoRepository;
import ues.ids.poo.api.repository.ProveedorRepository;

import java.util.List;

/**
 * @author Fran Recinos
 */
// @Service le dice a Spring Boot que esta clase contiene la lógica de negocio.
// La registra en la memoria para poder usarla desde el Controlador.
@Service
// @RequiredArgsConstructor (de Lombok) genera un constructor automático para todas
// las variables marcadas como "final". Así inyectamos los repositorios sin usar @Autowired.
@RequiredArgsConstructor
public class ProductoService {

    // Repositorios para interactuar con las tablas de la base de datos
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;

    /**
     * CREAR (POST): Recibe un DTO, valida que las llaves foráneas existan,
     * lo convierte a Entidad y lo guarda en la BD.
     */
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        // 1. Validar existencia de dependencias. orElseThrow lanza un error 404 si no encuentra el ID.
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría no existe"));

        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no existe"));

        // 2. Instanciar una nueva Entidad (el molde de la tabla BD) y llenarla con los datos del DTO
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioBase(dto.getPrecioBase());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setCategoria(categoria); // Asignamos el objeto completo ya validado
        producto.setProveedor(proveedor);

        // 3. Guardar en la BD
        Producto productoGuardado = productoRepository.save(producto);

        // 4. Convertir la entidad guardada de vuelta a DTO para responderle al usuario
        return mapearADTO(productoGuardado);
    }

    /**
     * LEER TODOS (GET): Obtiene todos los registros de la tabla y los transforma a DTOs.
     */
    public List<ProductoResponseDTO> obtenerTodos() {
        // findAll() trae la lista de Entidades. Usamos stream() y map() para
        // transformar cada Entidad Producto en un ProductoResponseDTO.
        return productoRepository.findAll().stream()
                .map(this::mapearADTO)
                .toList();
    }

    /**
     * LEER POR ID (GET): Busca un producto específico por su llave primaria.
     */
    public ProductoResponseDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        return mapearADTO(producto);
    }

    /**
     * ACTUALIZAR (PUT): Busca el producto existente, actualiza sus campos y lo vuelve a guardar.
     */
    public ProductoResponseDTO actualizarProducto(Integer id, ProductoRequestDTO dto) {
        // 1. Verificar que el producto que queremos editar realmente exista
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // 2. Verificar que las nuevas categorías o proveedores también existan
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoría no existe"));

        Proveedor proveedor = proveedorRepository.findById(dto.getIdProveedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no existe"));

        // 3. Sobrescribir los datos actuales con los nuevos
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioBase(dto.getPrecioBase());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);

        // 4. Al hacer save() con un ID que ya existe, Hibernate hace un UPDATE en lugar de un INSERT
        Producto productoActualizado = productoRepository.save(producto);
        return mapearADTO(productoActualizado);
    }

    /**
     * ELIMINAR (DELETE): Borra el registro de la BD.
     */
    public void eliminarProducto(Integer id) {
        // Validamos primero; si intentamos borrar algo que no existe, la BD daría error.
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    /**
     * MÉTODO AUXILIAR (DRY - Don't Repeat Yourself)
     * Toma una Entidad Producto (que viene de la BD) y la convierte a un DTO de respuesta.
     * Centralizamos esto aquí para no escribir las mismas 8 líneas en los métodos de crear, leer y actualizar.
     */
    private ProductoResponseDTO mapearADTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioBase(producto.getPrecioBase());
        dto.setPrecioVenta(producto.getPrecioVenta());

        // De los objetos completos, extraemos solo el ID para mandarlo en el JSON
        dto.setIdCategoria(producto.getCategoria().getIdCategoria());
        dto.setIdProveedor(producto.getProveedor().getIdProveedor());
        return dto;
    }
}
