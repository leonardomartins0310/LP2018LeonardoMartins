/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import Entidades.Tipoproduto;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Leonardo
 */
public class TipoProdutoDataModelProduto extends DefaultComboBoxModel<Tipoproduto> {
    public TipoProdutoDataModelProduto(Tipoproduto[] items) {
        super(items);
    }
 
    @Override
    public Tipoproduto getSelectedItem() {
        Tipoproduto selected = (Tipoproduto) super.getSelectedItem(); 
        return selected;
    }
}