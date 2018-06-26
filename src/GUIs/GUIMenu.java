/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.io.IOException;
import java.util.Date;
import javax.swing.JDialog;
import java.io.FileNotFoundException;
import javax.swing.WindowConstants;
import GUIs.GUIEmpresa;
import GUIs.GUIProduto;
import GUIs.GUITipoProduto;
import GUIs.GUICliente;

/**
 *
 * @author Leonardo
 */
public class GUIMenu extends JFrame {

    private Container cp;
    private JButton btProduto = new JButton("Produto");
    private JButton btEmpresa = new JButton("Empresa");
    private JButton btCliente = new JButton("Cliente");
    private JButton btTipoProduto = new JButton("Tipo de Produtos");
    private JPanel pn = new JPanel(new GridLayout(4, 1));

    public GUIMenu() throws IOException, FileNotFoundException, ParseException {
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Menu");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(pn,BorderLayout.CENTER);
        pn.add(btEmpresa);
        pn.add(btProduto);
        pn.add(btCliente);
        pn.add(btTipoProduto);
        setLocationRelativeTo(null);
        setVisible(true);
        btEmpresa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GUIEmpresa();
                    dispose();
                } catch (IOException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        btProduto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GUIProduto();
                    dispose();
                } catch (IOException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        btTipoProduto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GUITipoProduto();
                    dispose();
                } catch (IOException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        btCliente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GUICliente();
                    dispose();
                } catch (IOException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(GUIMenu.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
    }}
