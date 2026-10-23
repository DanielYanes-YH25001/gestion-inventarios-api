package ues.ids.poo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ues.ids.poo.api.model.Proveedor;

/**
 * @author Fran Recinos
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}