package ues.ids.poo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ues.ids.poo.api.model.Producto;

/**
 * @author Fran Recinos
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}