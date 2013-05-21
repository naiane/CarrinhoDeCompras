package br.com.javamagazine.jee6.loja.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
  private static final long serialVersionUID = 813440582621834761L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "data_pedido")
  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  private Date data;
  @ManyToOne
  @JoinColumn(name = "id_cliente")
  @NotNull
  private Cliente cliente;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "item_pedido", joinColumns = @JoinColumn(name = "id_pedido"))
  private Set<ItemPedido> itens;
  @Column(name = "total")
  @NotNull
  private Double valorTotal;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Set<ItemPedido> getItens() {
    if (itens == null) {
      itens = new HashSet<ItemPedido>();
    }
    return itens;
  }

  public void setItens(Set<ItemPedido> itens) {
    this.itens = itens;
  }

  public Double getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(Double valorTotal) {
    this.valorTotal = valorTotal;
  }

  public List<ItemPedido> getItensOrdenadosEmLista() {
    return new ArrayList<ItemPedido>(getItens());
  }

  public void adicionarItem(Produto produto, Integer quantidade) {
    ItemPedido itemExistente = getItem(produto);
    if (itemExistente != null) {
      atualizarQuantidade(produto, itemExistente.getQuantidade() + quantidade);
    }
    else {
      getItens().add(new ItemPedido(produto, quantidade));
      calcularTotal();
    }
  }

  public void removerItem(Produto produto) {
    getItens().remove(new ItemPedido(produto));
    calcularTotal();
  }

  public ItemPedido getItem(Produto produto) {
    ItemPedido itemAProcurar = new ItemPedido(produto);
    for (ItemPedido item : getItens()) {
      if (item.equals(itemAProcurar)) { return item; }
    }
    return null;
  }

  public void atualizarQuantidade(Produto produto, Integer novaQuantidade) {
    ItemPedido item = getItem(produto);
    if (item == null) { throw new IllegalArgumentException(
        "Item nao encontrado para produto " + produto); }
    item.atualizarQuantidade(novaQuantidade);
    calcularTotal();
  }

  public void calcularTotal() {
    valorTotal = 0D;
    for (ItemPedido item : getItens()) {
      valorTotal += item.getPrecoTotal();
    }
  }

  @Override
  public String toString() {
    return "Pedido [id=" + id + ", data=" + data + ", cliente=" + cliente
        + ", itens=" + itens + ", valorTotal=" + valorTotal + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Pedido other = (Pedido) obj;
    if (id == null) {
      if (other.id != null) return false;
    }
    else if (!id.equals(other.id)) return false;
    return true;
  }
}