package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden,Long> {

}
