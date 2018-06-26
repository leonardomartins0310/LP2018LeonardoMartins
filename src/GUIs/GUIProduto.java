package GUIs;

import DAOs.DAOEmpresa;
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
import DAOs.DAOProduto;
import DAOs.DAOTipoproduto;
import Entidades.Produto;
import Entidades.Empresa;
import Entidades.Tipoproduto;
import javax.swing.WindowConstants;
import GUIs.GUIMenu;

/**
 *
 * @author Leonardo
 */
public class GUIProduto extends JFrame {

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
    private JPanel pnCentro = new JPanel(new GridLayout(6, 2));
    private JPanel pnSul = new JPanel(new GridLayout(1, 1));
    DAOEmpresa daoEmpresa = new DAOEmpresa();
    DAOProduto daoProduto = new DAOProduto();
    DAOTipoproduto daoTipoProduto = new DAOTipoproduto();
    private JLabel lbIdproduto = new JLabel("Id do Produto");
    private JLabel lbNomedoproduto = new JLabel("Nome do Produto");
    private JLabel lbImagemdoproduto = new JLabel("Imagem do Produto");
    private JLabel lbDescricaoproduto = new JLabel("Descricao do Produto");
    private JLabel lbPrecoproduto = new JLabel("Preco do produto");
    private JLabel lbEmpresaproduto = new JLabel("Empresa");
    private JLabel lbTipoproduto = new JLabel("Tipo de Produto");
    private JTextField tfIdproduto = new JTextField(20);
    private JTextField tfNomedoproduto = new JTextField(20);
    private JTextField tfImagemdoproduto = new JTextField(20);
    private JTextField tfDescricaoproduto = new JTextField(20);
    private JTextField tfPrecoproduto = new JTextField(20);
    private JComboBox<Empresa> cbEmpresaproduto = new JComboBox<Empresa>();
    private JComboBox<Tipoproduto> cbTipoproduto = new JComboBox<Tipoproduto>();
    private String qualAcao = "";
    private Produto objeto = new Produto();
    private Produto objetoOriginal = new Produto();
    SimpleDateFormat sdfSoData = new SimpleDateFormat("dd/MM/yyyy");
    private String ultimaAcao = null;
    TipoProdutoDataModelProduto tipoprodutoDataModel = new TipoProdutoDataModelProduto(daoTipoProduto.listInOrderNome().toArray(new Tipoproduto[]{}));
    EmpresaDataModel empresaDataModel = new EmpresaDataModel(daoEmpresa.listInOrderNome().toArray(new Empresa[]{}));

    public GUIProduto() throws IOException, FileNotFoundException, ParseException {
        setSize(1000, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Crud");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        Date dtAtual = new Date();
        cp.add(pnNorte, BorderLayout.NORTH);

        pnNorte.add(lbIdproduto);
        pnNorte.add(tfIdproduto);

        pnNorte.add(btBuscar);
        pnNorte.add(btAdicionar);
        pnNorte.add(btSalvar);
        pnNorte.add(btEditar);
        pnNorte.add(btExcluir);
        pnNorte.add(btListar);
        pnNorte.add(btCancela);
        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.add(lbNomedoproduto);
        pnCentro.add(tfNomedoproduto);
        pnCentro.add(lbImagemdoproduto);
        pnCentro.add(tfImagemdoproduto);
        pnCentro.add(lbDescricaoproduto);
        pnCentro.add(tfDescricaoproduto);
        pnCentro.add(lbPrecoproduto);
        pnCentro.add(tfPrecoproduto);
        pnCentro.add(lbEmpresaproduto);
        pnCentro.add(cbEmpresaproduto);
        pnCentro.add(lbTipoproduto);
        pnCentro.add(cbTipoproduto);
        cp.add(pnSul, BorderLayout.SOUTH);
        pnSul.setBackground(Color.red);
        scroll.add(jTextArea);
        pnSul.add(scroll);

        ultimaAcao = "Buscar";
        liberaBloqueiaUsuario();
        tfIdproduto.selectAll();

        setLocationRelativeTo(null);
        setVisible(true);
        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try { //caso aconteça algum erro
                    tfIdproduto.setBackground(Color.white);//volta para cor branca se estiver de outra cor
                    jTextArea.setText("");//limpa o textArea
                    objeto = new Produto();
                    int chave = Integer.valueOf(tfIdproduto.getText());//pega os dados que o usuario digitou e converte para int
                    objeto.setIdProduto(chave); //adiciona a chave na variável p.id
                    objeto = daoProduto.obter(objeto.getIdProduto()); //busca na lista com a chave e se encontrar 
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
                        tfNomedoproduto.setText(objeto.getNomeDoProduto());
                        tfImagemdoproduto.setText(objeto.getImagemDoProduto());
                        tfDescricaoproduto.setText(objeto.getDescricaoProduto());
                        tfPrecoproduto.setText(String.valueOf(objeto.getPrecoProduto()));
                        empresaDataModel.setSelectedItem(objeto.getEmpresa());
                        tipoprodutoDataModel.setSelectedItem(objeto.getTipoProduto());
                    }
                    //antes de escolher um botão o usuario não pode fazer nada
                    liberaBloqueiaUsuario();
                    tfIdproduto.selectAll();//seleciona o texto todo

                } catch (Exception erro) {
                    //algo está errado. Por exemplo, campo chave vazio ou string no lugar de número inteiro, etc
                    pnNorte.setBackground(Color.yellow);
                    tfIdproduto.requestFocus();
                    tfIdproduto.setBackground(Color.red);
                    jTextArea.setText("Erro... \n");
                    jTextArea.append(erro.getMessage());
                }
            }
        });

        btAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfIdproduto.requestFocus();
                qualAcao = "incluir";
                objeto = new Produto();
                //liberar para digitar dados nos atributos abaixo
                ultimaAcao = "Adicionar";
                liberaBloqueiaUsuario();
                limpaCampo();

            }
        });
        btSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                objeto.setIdProduto(Integer.valueOf(tfIdproduto.getText()));
                try {
                    objeto.setPrecoProduto(Double.valueOf(tfPrecoproduto.getText()));
                    objeto.setEmpresa(empresaDataModel.getSelectedItem());
                    objeto.setTipoProduto(tipoprodutoDataModel.getSelectedItem());
                    objeto.setImagemDoProduto(tfImagemdoproduto.getText());
                    objeto.setNomeDoProduto(tfNomedoproduto.getText());
                    objeto.setDescricaoProduto(tfDescricaoproduto.getText());
                    tfPrecoproduto.setBackground(null);
                    tfPrecoproduto.requestFocus();
                    tfPrecoproduto.setBackground(null);
                    tfIdproduto.setBackground(null);
                    tfIdproduto.requestFocus();
                    tfIdproduto.setBackground(Color.WHITE);
                    ultimaAcao = "Salvar";
                    liberaBloqueiaUsuario();
                    if (qualAcao.equals("incluir")) {
                        daoProduto.adicionar(objeto);
                    } else {
                        daoProduto.atualizar(objeto);
                    }
                    limpaCampo();
                } catch (Exception erro) {
                    tfPrecoproduto.requestFocus();
                    tfPrecoproduto.setBackground(Color.RED);
                    jTextArea.setText("Erro... \n");
                    jTextArea.append(erro.getMessage());
                }
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
                tfIdproduto.requestFocus();
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
                        "Confirma a exclusão do registro <ID = " + objeto.getNomeDoProduto() + ">?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    try {
                        daoProduto.excluir(objeto);
                    } catch (Exception ex) {
                        Logger.getLogger(GUIProduto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tfIdproduto.requestFocus();
                    limpaCampo();
                }
            }
        });
        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GUIListagemProduto guiListagem = new GUIListagemProduto(daoProduto.listInOrderNome());
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

        cbEmpresaproduto.setModel(empresaDataModel);

        cbTipoproduto.setModel(tipoprodutoDataModel);
    }

    public void liberaBloqueiaUsuario() {
        btSalvar.setVisible(ultimaAcao.equals("Adicionar") || ultimaAcao.equals("Editar"));
        btExcluir.setVisible(ultimaAcao.equals("BuscarAchou"));
        btAdicionar.setVisible(ultimaAcao.equals("BuscarNaoAchou"));
        btCancela.setVisible(ultimaAcao.equals("Editar") || ultimaAcao.equals("Adicionar"));
        tfIdproduto.setEditable(ultimaAcao.equals("Buscar") || ultimaAcao.equals("Salvar") || ultimaAcao.equals("BuscarAchou") || ultimaAcao.equals("BuscarNaoAchou"));
        tfNomedoproduto.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfImagemdoproduto.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfDescricaoproduto.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfPrecoproduto.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        cbTipoproduto.setEnabled(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        cbEmpresaproduto.setEnabled(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        btEditar.setVisible(ultimaAcao.equals("BuscarAchou"));
    }

    public void limpaCampo() {
        tfNomedoproduto.setText("");
        tfImagemdoproduto.setText("");
        tfDescricaoproduto.setText("");
        tfPrecoproduto.setText("");
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
