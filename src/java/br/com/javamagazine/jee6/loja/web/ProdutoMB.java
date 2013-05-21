package br.com.javamagazine.jee6.loja.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.jee6.loja.entity.Produto;
import br.com.javamagazine.jee6.loja.services.ProdutoServices;

@Named
@RequestScoped
public class ProdutoMB {
  @Inject
  private ProdutoServices produtoServices;
  private List<Produto> produtos;

  @PostConstruct
  public void init() {
    produtos = produtoServices.findAll();
  }

  public List<Produto> getProdutos() {
    return produtos;
  }
}