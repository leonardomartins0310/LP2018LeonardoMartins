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
import java.io.IOException;
import java.util.Date;
import java.io.FileNotFoundException;
import DAOs.DAOEmpresa;
import Entidades.Empresa;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;

/**
 *
 * @author Leonardo
 */
public class GUIEmpresa extends JFrame {

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
    private JLabel lbIdempresa = new JLabel("Id Empresa");
    private JLabel lbNomeempresa = new JLabel("Nome da Empresa");
    private JLabel lbRamoempresa = new JLabel("Ramo da Empresa");
    private JLabel lbLogoempresa = new JLabel("Logo da Empresa");
    private JLabel lbEnderecoempresa = new JLabel("Endereco da Empresa");
    private JLabel lbDatacadastroempresa = new JLabel("Data de Cadastro da Empresa");
    private JLabel lbImage = new JLabel("");
    private JTextField tfIdempresa = new JTextField(20);
    private JTextField tfNomeempresa = new JTextField(20);
    private JTextField tfRamoempresa = new JTextField(20);
    private JTextField tfLogoempresa = new JTextField(20);
    private JTextField tfEnderecoempresa = new JTextField(20);
    private JTextField tfDatacadastroempresa = new JTextField(20);
    private String qualAcao = "";
    private Empresa objeto = new Empresa();
    private Empresa objetoOriginal = new Empresa();
    SimpleDateFormat sdfSoData = new SimpleDateFormat("dd/MM/yyyy");
    private String ultimaAcao = null;

    public GUIEmpresa() throws IOException, FileNotFoundException, ParseException {
        setSize(1000, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Crud");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        Date dtAtual = new Date();
        cp.add(pnNorte, BorderLayout.NORTH);

        pnNorte.add(lbIdempresa);
        pnNorte.add(tfIdempresa);

        pnNorte.add(btBuscar);
        pnNorte.add(btAdicionar);
        pnNorte.add(btSalvar);
        pnNorte.add(btEditar);
        pnNorte.add(btExcluir);
        pnNorte.add(btListar);
        pnNorte.add(btCancela);
        cp.add(pnCentro, BorderLayout.CENTER);
        pnCentro.add(lbNomeempresa);
        pnCentro.add(tfNomeempresa);
        pnCentro.add(lbRamoempresa);
        pnCentro.add(tfRamoempresa);
        pnCentro.add(lbLogoempresa);
        pnCentro.add(tfLogoempresa);
        pnCentro.add(lbEnderecoempresa);
        pnCentro.add(tfEnderecoempresa);
        pnCentro.add(lbDatacadastroempresa);
        pnCentro.add(tfDatacadastroempresa);
        pnCentro.add(lbImage);
        cp.add(pnSul, BorderLayout.SOUTH);
        pnSul.setBackground(Color.red);
        scroll.add(jTextArea);
        pnSul.add(scroll);

        ultimaAcao = "Buscar";
        liberaBloqueiaUsuario();
        tfIdempresa.selectAll();

        setLocationRelativeTo(null);
        setVisible(true);
        btBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try { //caso aconteça algum erro
                    tfIdempresa.setBackground(Color.white);//volta para cor branca se estiver de outra cor
                    jTextArea.setText("");//limpa o textArea
                    objeto = new Empresa();
                    int chave = Integer.valueOf(tfIdempresa.getText());//pega os dados que o usuario digitou e converte para int
                    objeto.setIdEmpresa(chave); //adiciona a chave na variável p.id
                    objeto = daoEmpresa.obter(objeto.getIdEmpresa()); //busca na lista com a chave e se encontrar 
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
                        tfNomeempresa.setText(objeto.getNomeEmpresa());
                        tfRamoempresa.setText(objeto.getRamoEmpresa());
                        tfLogoempresa.setText(objeto.getLogoEmpresa());
                        tfEnderecoempresa.setText(objeto.getEnderecoEmpresa());
                        tfDatacadastroempresa.setText(sdfSoData.format(objeto.getDataCadastroEmpresa()));
                        try {
                            ImageIcon imageIcon = new ImageIcon(new ImageIcon(ImageIO.read(new File(objeto.getLogoEmpresa()))).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                            lbImage.setIcon(imageIcon);
                        } catch (Exception erro) {
                            lbImage.setIcon(null);
                            jTextArea.setText("Erro... \n");
                            jTextArea.append(erro.getMessage());
                        }
                    }
                    //antes de escolher um botão o usuario não pode fazer nada
                    liberaBloqueiaUsuario();
                    tfIdempresa.selectAll();//seleciona o texto todo

                } catch (Exception erro) {
                    //algo está errado. Por exemplo, campo chave vazio ou string no lugar de número inteiro, etc
                    pnNorte.setBackground(Color.yellow);
                    tfIdempresa.requestFocus();
                    tfIdempresa.setBackground(Color.red);
                    jTextArea.setText("Erro... \n");
                    jTextArea.append(erro.getMessage());
                }
            }
        });

        btAdicionar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfIdempresa.requestFocus();
                qualAcao = "incluir";
                objeto = new Empresa();
                //liberar para digitar dados nos atributos abaixo
                ultimaAcao = "Adicionar";
                liberaBloqueiaUsuario();
                limpaCampo();

            }
        });
        btSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                objeto.setIdEmpresa(Integer.valueOf(tfIdempresa.getText()));
                String data = tfDatacadastroempresa.getText();
                if (validaData(data)) {
                    try {
                        Date dataDigitada = (sdfSoData.parse(data));
                        if (dataDigitada.before(dtAtual)) {
                            objeto.setDataCadastroEmpresa(dataDigitada);
                            objeto.setEnderecoEmpresa(tfEnderecoempresa.getText());
                            objeto.setLogoEmpresa(tfLogoempresa.getText());
                            objeto.setNomeEmpresa(tfNomeempresa.getText());
                            objeto.setRamoEmpresa(tfRamoempresa.getText());
                            tfDatacadastroempresa.setBackground(null);
                            tfIdempresa.setBackground(null);
                            tfIdempresa.requestFocus();
                            tfIdempresa.setBackground(Color.WHITE);
                            ultimaAcao = "Salvar";
                            liberaBloqueiaUsuario();
                            if (qualAcao.equals("incluir")) {
                                daoEmpresa.adicionar(objeto);
                            } else {
                                daoEmpresa.atualizar(objeto);
                            }
                            limpaCampo();
                        } else {
                            tfDatacadastroempresa.requestFocus();
                            tfDatacadastroempresa.setBackground(Color.RED);
                            jTextArea.setText("Erro... \n");
                            jTextArea.append("Data Invalida");

                        }
                    } catch (ParseException erro) {
                        tfDatacadastroempresa.requestFocus();
                        tfDatacadastroempresa.setBackground(Color.RED);
                        jTextArea.setText("Erro... \n");
                        jTextArea.append(erro.getMessage());
                    }
                } else {
                    tfDatacadastroempresa.requestFocus();
                    tfDatacadastroempresa.setBackground(Color.RED);
                    jTextArea.setText("Erro... \n");
                    jTextArea.append("Data Invalida");
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
                tfIdempresa.requestFocus();
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
                        "Confirma a exclusão do registro <ID = " + objeto.getNomeEmpresa() + ">?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    try {
                        daoEmpresa.excluir(objeto);
                    } catch (Exception ex) {
                        Logger.getLogger(GUIEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tfIdempresa.requestFocus();
                    limpaCampo();
                }
            }
        });
        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                GUIListagemEmpresa guiListagem = new GUIListagemEmpresa(daoEmpresa.listInOrderNome());
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
        tfIdempresa.setEditable(ultimaAcao.equals("Buscar") || ultimaAcao.equals("Salvar") || ultimaAcao.equals("BuscarAchou") || ultimaAcao.equals("BuscarNaoAchou"));
        tfNomeempresa.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfRamoempresa.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfLogoempresa.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfEnderecoempresa.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
        tfDatacadastroempresa.setEditable(ultimaAcao.equals("Editar") || ultimaAcao.equals("Atualizar") || ultimaAcao.equals("Adicionar"));
    }

    public void limpaCampo() {
        tfNomeempresa.setText("");
        tfRamoempresa.setText("");
        tfLogoempresa.setText("");
        tfEnderecoempresa.setText("");
        tfDatacadastroempresa.setText("");
        lbImage.setText("");
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
