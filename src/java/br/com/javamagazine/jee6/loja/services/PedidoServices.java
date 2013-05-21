package br.com.javamagazine.jee6.loja.services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.javamagazine.jee6.loja.entity.Cliente;
import br.com.javamagazine.jee6.loja.entity.Pedido;
import br.com.javamagazine.jee6.loja.exception.ClienteNaoEncontradoException;

@Stateless
public class PedidoServices {
  @PersistenceContext(name="LojaPU")
  private EntityManager em;
  @Inject
  private ClienteServices clienteServices;

  public Pedido criarPedido(Pedido pedido, String emailCliente,
      String senhaCliente) throws ClienteNaoEncontradoException
  {
    Cliente cliente = clienteServices.findByEmailAndSenha(emailCliente,
        senhaCliente);
    if (cliente == null) { throw new ClienteNaoEncontradoException(); }
    pedido.setData(new Date());
    pedido.setCliente(cliente);
    em.persist(pedido);
    return pedido;
  }
}
