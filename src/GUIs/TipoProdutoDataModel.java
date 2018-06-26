/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import Entidades.Empresa;
import Entidades.Tipoproduto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author Leonardo
 */
public class TipoProdutoDataModel extends DefaultListModel<Tipoproduto> {
    
    private List<Tipoproduto> tipos;
    
    public TipoProdutoDataModel(List<Tipoproduto> tipos) {
        
       for (Tipoproduto t : tipos){
           addElement(t);
       }
       
       this.tipos = tipos;
    }
    
    public int getIndexOfItem(Tipoproduto tipo){
        int index = 0;
        for (Tipoproduto t : this.tipos){
            if (t.equals(tipo)){
                return index;
            }
            index++;
        }
        return -1;
    }
    
    
    List<Tipoproduto> getSelectedItems(int[] selectedIds){
        List<Tipoproduto> result = new ArrayList<>();

        for (int i = 0; i < selectedIds.length; i++) {
          Tipoproduto sel = getElementAt(selectedIds[i]);
          result.add(sel);
        }
    
        return result;
    }
    
    public int[] getSelectedIndexes(List<Tipoproduto> items){
         List<Integer> result = new ArrayList<>();
        
        for (Tipoproduto t : items){
            int index = getIndexOfItem(t);
            if (index >= 0){
                result.add(index);
            }
        }
        
        int[] res = new int[result.size()];
        int index = 0;
        for (Integer in : result){
            res[index] = in;
            index++;
        }
        
        return res;
    }
    
}
    
