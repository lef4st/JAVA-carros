package com.example.carros.api.carros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {

	List<Carro> findByTipo(String tipo);
	
	List<Carro> findByNomeContaining(String query);
}
