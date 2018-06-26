/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Leonardo
 */
import GUIs.GUIEmpresa;
import GUIs.GUIProduto;
import GUIs.GUITipoProduto;
import GUIs.GUIMenu;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
       new GUIMenu();
    }
    
}
