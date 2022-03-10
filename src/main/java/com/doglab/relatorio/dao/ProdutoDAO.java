package com.doglab.relatorio.dao;

import org.springframework.stereotype.Service;

import com.doglab.relatorio.orm.Produto;
import com.doglab.relatorio.repositories.ProdutoRepository;

@Service
public class ProdutoDAO {

	private final ProdutoRepository repo;
	
	public ProdutoDAO(ProdutoRepository repo)
	{
		this.repo = repo;
	}
	
	public void save(Produto produto)
	{
		repo.save(produto);
	}

	public void delete(Produto produto)
	{
		repo.delete(produto);
	}
	
	public void deleteById(Long id)
	{
		repo.deleteById(id);
	}
	
	public Iterable<Produto> listAll()
	{
		return repo.findAll();
	}
	
	public Integer countColumns()
	{
		return repo.countColumns();
	}
	
}
