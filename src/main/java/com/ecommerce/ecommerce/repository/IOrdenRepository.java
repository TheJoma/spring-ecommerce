package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden,Long> {
}
