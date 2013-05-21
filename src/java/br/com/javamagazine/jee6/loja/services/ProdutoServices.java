package br.com.javamagazine.jee6.loja.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.javamagazine.jee6.loja.entity.Produto;

@Stateless
public class ProdutoServices {
  @PersistenceContext(name="LojaPU")
  private EntityManager em;

  @SuppressWarnings("unchecked")
  public List<Produto> findAll() {
    return em.createQuery("SELECT p FROM Produto p ORDER BY p.titulo")
        .getResultList();
  }
}