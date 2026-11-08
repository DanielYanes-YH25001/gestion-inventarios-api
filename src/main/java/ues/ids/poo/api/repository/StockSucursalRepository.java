package ues.ids.poo.api.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ues.ids.poo.api.model.StockSucursal;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockSucursalRepository extends JpaRepository<StockSucursal, Long> {

    List<StockSucursal> findBySucursal_IdSucursal(Integer idSucursal);

    boolean existsBySucursal_IdSucursal(Integer idSucursal);

    boolean existsByProducto_IdProducto(Integer idProducto);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT stock
        FROM StockSucursal stock
        WHERE stock.producto.idProducto = :idProducto
          AND stock.sucursal.idSucursal = :idSucursal
        """)
    Optional<StockSucursal> buscarConBloqueo(
        @Param("idProducto") Integer idProducto,
        @Param("idSucursal") Integer idSucursal
    );
}
