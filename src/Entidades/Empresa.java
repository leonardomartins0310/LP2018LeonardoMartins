/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @Column(name = "nome_empresa")
    private String nomeEmpresa;
    @Column(name = "ramo_empresa")
    private String ramoEmpresa;
    @Column(name = "logo_empresa")
    private String logoEmpresa;
    @Column(name = "endereco_empresa")
    private String enderecoEmpresa;
    @Column(name = "data_cadastro_empresa")
    @Temporal(TemporalType.DATE)
    private Date dataCadastroEmpresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa")
    private List<Produto> produtoList;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getRamoEmpresa() {
        return ramoEmpresa;
    }

    public void setRamoEmpresa(String ramoEmpresa) {
        this.ramoEmpresa = ramoEmpresa;
    }

    public String getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(String logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public String getEnderecoEmpresa() {
        return enderecoEmpresa;
    }

    public void setEnderecoEmpresa(String enderecoEmpresa) {
        this.enderecoEmpresa = enderecoEmpresa;
    }

    public Date getDataCadastroEmpresa() {
        return dataCadastroEmpresa;
    }

    public void setDataCadastroEmpresa(Date dataCadastroEmpresa) {
        this.dataCadastroEmpresa = dataCadastroEmpresa;
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
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idEmpresa+" - "+nomeEmpresa;
    }
    public String toStringListar(){
        return idEmpresa+" - "+nomeEmpresa+" - "+enderecoEmpresa+" - "+ramoEmpresa+" - "+logoEmpresa+" - "+dataCadastroEmpresa;
    }
    
}
