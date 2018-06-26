/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "tipoproduto")
@NamedQueries({
    @NamedQuery(name = "Tipoproduto.findAll", query = "SELECT t FROM Tipoproduto t")})
public class Tipoproduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_produto")
    private Integer idTipoProduto;
    @Column(name = "tipo_produto")
    private String tipoProduto;
    @ManyToMany(mappedBy = "tipoprodutoList")
    private List<Cliente> clienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProduto")
    private List<Produto> produtoList;

    public Tipoproduto() {
    }

    public Tipoproduto(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public Integer getIdTipoProduto() {
        return idTipoProduto;
    }

    public void setIdTipoProduto(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoProduto != null ? idTipoProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoproduto)) {
            return false;
        }
        Tipoproduto other = (Tipoproduto) object;
        if ((this.idTipoProduto == null && other.idTipoProduto != null) || (this.idTipoProduto != null && !this.idTipoProduto.equals(other.idTipoProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idTipoProduto+" - "+tipoProduto;
    }
    
}
