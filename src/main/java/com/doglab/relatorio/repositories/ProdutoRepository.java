package com.doglab.relatorio.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doglab.relatorio.orm.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long>
{

	@Query(value=
			"SELECT count(*) "
			+ "FROM information_schema.columns "
			+ "WHERE table_name = 'produtos';",
			nativeQuery = true)
	public Integer countColumns();
	
}
