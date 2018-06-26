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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.io.IOException;
import java.util.Date;
import javax.swing.JDialog;
import java.io.FileNotFoundException;
import DAOs.DAOCliente;
import DAOs.DAOTipoproduto;
import Entidades.Cliente;
import Entidades.Tipoproduto;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

/**
 *
 * @author Leonardo
 */
public class GUICliente extends JFrame {

    private Container cp;
    private JTextArea jTextArea = new JTextArea();
    private ScrollPane scroll = new ScrollPane();
    private JButton btBuscar = new JButton("Procurar");
    private JButton btSalvar = new JButton("Salvar");
    private JButton btAdicionar = new JButton("Adicionar");
    private JButton btEditar = new JButton("Editar");
    private JButton btExcluir = new JButton("Excluir");
    private JButton btListar = new JButton("Listar");
    private JButton btCancela = new JButton("Cancela");
    private JPanel pnNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel pnCentro = new JPanel(new GridLayout(3, 2));
    private JPanel pnSul = new JPanel(new GridLayout(1, 1));
    DAOCliente daoCliente = new DAOCliente();
    private JLabel lbIdcliente = new JLabel("Id do Cliente");
    private JLabel lbNomecliente = new JLabel("Nome do Cliente");
    private JLabel lbEmailcliente = new JLabel("Email do Cliente");
    private JLabel lbTiposDeProduto = new JLabel("Tipos de Produto");
    private JTextField tfIdcliente = new JTextField(20);
    private JTextField tfNomecliente = new JTextField(20);
    private JTextField tfEmailcliente = new JTextField(20);
    private JList<Tipoproduto> listaTipos;
    private String qualAcao = "";
    private Cliente objeto = new Cliente();
    private Cliente objetoOriginal = new Cliente();
    SimpleDateFormat sdfSoData = new SimpleDateFormat("dd/MM/yyyy");
    private String ultimaAcao = null;
    DAOTipoproduto daoTipoProduto = new DAOTipoproduto();
    TipoProdutoDataModel tipoprodutoDataModel = new TipoProdutoDataModel(daoTipoProduto.listInOrderNome());

    public GUICliente() throws IOException, FileNotFoundException, ParseException {
        setSize(1000, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Crud");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        Date dtAtual = new Date();
        cp.add(pnNorte, BorderLayout.NORTH);
        listaTipos = new JList(tipoprodutoDataModel);
        listaTipos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        pnNorte.add(lbIdcliente);
        pnNorte.add(tfIdcliente);

        pnNorte.add(btBuscar);
        pnNorte.add(btAdicionar);
        pnNorte.add(btSalvar);
        pnNorte.add(btEditar);
        pnNorte.add(btExcluir);
        pnNorte.add(btListar);
        pnNorte.add(btCancela);
        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.add(lbNomecliente);
        pnCentro.add(tfNomecliente);
        pnCentro.add(lbEmailcliente);
        pnCentro.add(tfEmailcliente);
        pnCentro.add(lbTiposDeProduto);
        pnCentro.add(listaTipos);
        cp.add(pnSul, BorderLayout.SOUTH);
        pnSul.setBackground(Color.red);
        scroll.add(jTextArea);
        pnSul.add(scroll);

        ultimaAcao = "Buscar";
        liberaBloqueiaUsuario();
        tfIdcliente.selectAll();

        setLocationRelativeTo(null);
        setVisible(true);
        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try { //caso aconteça algum erro
                    tfIdcliente.setBackground(Color.white);//volta para cor branca se estiver de outra cor
                    jTextArea.setText("");//limpa o textArea
                    objeto = new Cliente();
                    int chave = Integer.valueOf(tfIdcliente.getText());//pega os dados que o usuario digitou e converte para int
                    objeto.setIdCliente(chave); //adiciona a chave na variável p.id
                    objeto = daoCliente.obter(objeto.getIdCliente()); //busca na lista com a chave e se encontrar 

                    //devolve todos os dados da pessoa encontrada
                    if (objeto == null) {//Não encontrou - se não achar a pessoa na lista, devolve null
                        pnNorte.setBackground(Color.red);
                        limpaCampo();
                        ultimaAcao = "BuscarNaoAchou";
                        liberaBloqueiaUsuario();
                    } else {//encontrou
                        ultimaAcao = "BuscarAchou";
                        liberaBloqueiaUsuario();
                        pnNorte.setBackground(Color.green);
                        tfNomecliente.setText(objeto.getNomeCliente());
                        tfEmailcliente.setText(objeto.getEmailCliente());
                        listaTipos.setSelectedIndices(tipoprodutoDataModel.getSelectedIndexes(objeto.getTipoprodutoList()));
                    }
                    //antes de escolher um botão o usuario não pode fazer nada
                    liberaBloqueiaUsuario();
                    tfIdcliente.selectAll();//seleciona o texto todo

                } catch (Exception erro) {
                    //algo está errado. Por exemplo, campo chave vazio ou string no lugar de número inteiro, etc
                    pnNorte.setBackground(Color.yellow);
                    tfIdcliente.requestFocus();
                    tfIdcliente.setBackground(Color.red);
                    jTextArea.setText("Erro... \n");
                    jTextArea.append(erro.getMessage());
                }
            }
        });

        btAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfIdcliente.requestFocus();
                qualAcao = "incluir";
                objeto = new Cliente();
                //liberar para digitar dados nos atributos abaixo
                ultimaAcao = "Adicionar";
                liberaBloqueiaUsuario();
                limpaCampo();

            }
        });
        btSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                objeto.setIdCliente(Integer.valueOf(tfIdcliente.getText()));
                objeto.setNomeCliente(tfNomecliente.getText());
                tfNomecliente.setBackground(null);
                tfNomecliente.requestFocus();
                tfNomecliente.setBackground(null);
                objeto.setEmailCliente(tfEmailcliente.getText());
                tfEmailcliente.setBackground(null);
                tfEmailcliente.requestFocus();
                tfEmailcliente.setBackground(null);
                objeto.setTipoprodutoList(tipoprodutoDataModel.getSelectedItems(listaTipos.getSelectedIndices()));
                tfIdcliente.setBackground(null);
                tfIdcliente.requestFocus();
                tfIdcliente.setBackground(Color.WHITE);
                ultimaAcao = "Salvar";
                liberaBloqueiaUsuario();
                if (qualAcao.equals("incluir")) {
                    daoCliente.adicionar(objeto);
                } else {
                    daoCliente.atualizar(objeto);
                }
                limpaCampo();
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //antes de sair do sistema, grava os dados da lista em disco
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                // Sai do sistema  
                try {
                    new GUIMenu();
                } catch (IOException ex) {
                    Logger.getLogger(GUIProduto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(GUIProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });

        btEditar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfIdcliente.requestFocus();
                qualAcao = "editar";
                //liberar para digitar dados nos atributos abaixo
                ultimaAcao = "Editar";
                liberaBloqueiaUsuario();
            }
        });
        btExcluir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //vamos usar um jOptionPane para solicitar a confirmação
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                        "Confirma a exclusão do registro <ID = " + objeto.getNomeCliente() + ">?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    try {
                        daoCliente.excluir(objeto);
                    } catch (Exception ex) {
                        Logger.getLogger(GUICliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tfIdcliente.requestFocus();
                    limpaCampo();
                }
            }
        });
        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GUIListagemCliente guiListagem = new GUIListagemCliente(daoCliente.listInOrderNome());
            }
        });
        btCancela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ultimaAcao = "Buscar";
                liberaBloqueiaUsuario();
                limpaCampo();
            }
        });
    }

    public void liberaBloqueiaUsuario() {
        btSalvar.setVisible(ultimaAcao.equals("Adicionar") || ultimaAcao.equals("Editar"));
        btExcluir.setVisible(ultimaAcao.equals("BuscarAchou"));
        btAdicionar.setVisible(ultimaAcao.equals("BuscarNaoAchou"));
        btCancela.setVisible(ultimaAcao.equals("Editar") || ultimaAcao.equals("Adicionar"));
        listaTipos.setEnabled(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfIdcliente.setEditable(ultimaAcao.equals("Buscar") || ultimaAcao.equals("Salvar") || ultimaAcao.equals("BuscarAchou") || ultimaAcao.equals("BuscarNaoAchou"));
        tfNomecliente.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfEmailcliente.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        btEditar.setVisible(ultimaAcao.equals("BuscarAchou"));
    }

    public void limpaCampo() {
        tfNomecliente.setText("");
        tfEmailcliente.setText("");
    }

    public Boolean validaData(String data) {
        String dataSemBarra = data.replace("/", "");
        boolean retorno = true;
        try {
            double teste = Double.valueOf(dataSemBarra);
            retorno = true;
        } catch (NumberFormatException e) {
            retorno = false;
        }
        return retorno;
    }

}
