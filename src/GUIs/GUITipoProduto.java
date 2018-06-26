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
import java.io.IOException;
import java.util.Date;
import javax.swing.JDialog;
import java.io.FileNotFoundException;
import DAOs.DAOTipoproduto;
import Entidades.Tipoproduto;
import javax.swing.WindowConstants;
import GUIs.GUIMenu;

/**
 *
 * @author Leonardo
 */
public class GUITipoProduto extends JFrame {

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
    private JPanel pnCentro = new JPanel(new GridLayout(1, 1));
    private JPanel pnSul = new JPanel(new GridLayout(1, 1));
    DAOTipoproduto daoTipoproduto = new DAOTipoproduto();
    private JLabel lbIdTipoproduto = new JLabel("Id do Tipo de produto");
    private JLabel lbTipoproduto = new JLabel("Tipo de produto");
    private JTextField tfIdTipoproduto = new JTextField(20);
    private JTextField tfTipoproduto = new JTextField(20);
    private String qualAcao = "";
    private Tipoproduto objeto = new Tipoproduto();
    private Tipoproduto objetoOriginal = new Tipoproduto();
    SimpleDateFormat sdfSoData = new SimpleDateFormat("dd/MM/yyyy");
    private String ultimaAcao = null;

    public GUITipoProduto() throws IOException, FileNotFoundException, ParseException {
        setSize(700, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Crud");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        Date dtAtual = new Date();
        cp.add(pnNorte, BorderLayout.NORTH);

        pnNorte.add(lbIdTipoproduto);
        pnNorte.add(tfIdTipoproduto);

        pnNorte.add(btBuscar);
        pnNorte.add(btAdicionar);
        pnNorte.add(btSalvar);
        pnNorte.add(btEditar);
        pnNorte.add(btExcluir);
        pnNorte.add(btListar);
        pnNorte.add(btCancela);
        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.add(lbTipoproduto);
        pnCentro.add(tfTipoproduto);
        cp.add(pnSul, BorderLayout.SOUTH);
        pnSul.setBackground(Color.red);
        scroll.add(jTextArea);
        pnSul.add(scroll);

        ultimaAcao = "Buscar";
        liberaBloqueiaUsuario();
        tfIdTipoproduto.selectAll();

        setLocationRelativeTo(null);
        setVisible(true);
        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try { //caso aconteça algum erro
                    tfIdTipoproduto.setBackground(Color.white);//volta para cor branca se estiver de outra cor
                    jTextArea.setText("");//limpa o textArea
                    objeto = new Tipoproduto();
                    int chave = Integer.valueOf(tfIdTipoproduto.getText());//pega os dados que o usuario digitou e converte para int
                    objeto.setIdTipoProduto(chave); //adiciona a chave na variável p.id
                    objeto = daoTipoproduto.obter(objeto.getIdTipoProduto()); //busca na lista com a chave e se encontrar 
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
                        tfTipoproduto.setText(objeto.getTipoProduto());
                    }
                    //antes de escolher um botão o usuario não pode fazer nada
                    liberaBloqueiaUsuario();
                    tfIdTipoproduto.selectAll();//seleciona o texto todo

                } catch (Exception erro) {
                    //algo está errado. Por exemplo, campo chave vazio ou string no lugar de número inteiro, etc
                    pnNorte.setBackground(Color.yellow);
                    tfIdTipoproduto.requestFocus();
                    tfIdTipoproduto.setBackground(Color.red);
                    jTextArea.setText("Erro... \n");
                    jTextArea.append(erro.getMessage());
                }
            }
        });

        btAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfIdTipoproduto.requestFocus();
                qualAcao = "incluir";
                objeto = new Tipoproduto();
                //liberar para digitar dados nos atributos abaixo
                ultimaAcao = "Adicionar";
                liberaBloqueiaUsuario();
                limpaCampo();

            }
        });
        btSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                objeto.setIdTipoProduto(Integer.valueOf(tfIdTipoproduto.getText()));
                objeto.setTipoProduto(tfTipoproduto.getText());
                tfIdTipoproduto.setBackground(null);
                tfIdTipoproduto.requestFocus();
                tfIdTipoproduto.setBackground(Color.WHITE);
                ultimaAcao = "Salvar";
                liberaBloqueiaUsuario();
                if (qualAcao.equals("incluir")) {
                    daoTipoproduto.adicionar(objeto);
                } else {
                    daoTipoproduto.atualizar(objeto);
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
                tfIdTipoproduto.requestFocus();
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
                        "Confirma a exclusão do registro <ID = " + objeto.getTipoProduto() + ">?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    try {
                        daoTipoproduto.excluir(objeto);
                    } catch (Exception ex) {
                        Logger.getLogger(GUITipoProduto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tfIdTipoproduto.requestFocus();
                    limpaCampo();
                }
            }
        });
        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GUIListagemTipoProduto guiListagem = new GUIListagemTipoProduto(daoTipoproduto.listInOrderNome());
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
        btEditar.setVisible(ultimaAcao.equals("BuscarAchou"));
        tfIdTipoproduto.setEditable(ultimaAcao.equals("Buscar") || ultimaAcao.equals("Salvar") || ultimaAcao.equals("BuscarAchou") || ultimaAcao.equals("BuscarNaoAchou"));
        tfTipoproduto.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
    }

    public void limpaCampo() {
        tfTipoproduto.setText("");
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
