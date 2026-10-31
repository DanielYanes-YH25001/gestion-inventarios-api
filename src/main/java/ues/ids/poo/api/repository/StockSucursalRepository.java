package ues.ids.poo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ues.ids.poo.api.model.StockSucursal;

import java.util.List;

@Repository
public interface StockSucursalRepository extends JpaRepository<StockSucursal, Long> {

    List<StockSucursal> findBySucursal_IdSucursal(Integer idSucursal);

    boolean existsBySucursal_IdSucursal(Integer idSucursal);
}
