/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import Entidades.Empresa;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Leonardo
 */
public class EmpresaDataModel extends DefaultComboBoxModel<Empresa> {
    public EmpresaDataModel(Empresa[] items) {
        super(items);
    }
 
    @Override
    public Empresa getSelectedItem() {
        Empresa selected = (Empresa) super.getSelectedItem(); 
        return selected;
    }
}
    

