package com.example.carros.api.carros;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.carros.api.infra.exception.ObjectNotFoundException;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros(){
		
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
		
		/* Tradicional */
//		List<Carro> carros = rep.findAll(); 
//		List<CarroDTO> list = new ArrayList<>();
//		for(Carro c : carros) {
//			list.add(new CarroDTO(c));
//		}
//		return list;
	}
	
	public CarroDTO getCarroById(Long id){
		Optional<Carro> carro = rep.findById(id);
		
		return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
	}
	
	public List<CarroDTO> getCarrosByTipo(String tipo){
		
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	
	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
		
		return CarroDTO.create(rep.save(carro));
	}
	
	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Não foi possível atualizar o registro");
		
		Optional<Carro> optional = rep.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			rep.save(db);
			
			return CarroDTO.create(db);
		}else {
			return null;
			//throw new RuntimeException("Não foi possível atualizar o registro");
		}
	}
	
	public void delete(Long id) {
		rep.deleteById(id);
	}
	
    public List<CarroDTO> search(String query) {
        return rep.findByNomeContaining(query).stream().map(CarroDTO::create).collect(Collectors.toList());
    }
}
