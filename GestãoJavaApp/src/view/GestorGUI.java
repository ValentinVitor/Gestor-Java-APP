package view;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.MaxCategoria;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GestorGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gerenciador de Estoque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(770, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel tituloLabel = new JLabel("Gerenciamento de Estoque");
        tituloLabel.setBounds(300, 20, 200, 25);
        panel.add(tituloLabel);

        JButton criarCategoriaButton = new JButton("Criar Categoria");
        criarCategoriaButton.setBounds(50, 60, 150, 25);
        panel.add(criarCategoriaButton);

        JButton criarProdutoButton = new JButton("Criar Produto");
        criarProdutoButton.setBounds(50, 100, 150, 25);
        panel.add(criarProdutoButton);

        JButton listarCategoriasButton = new JButton("Listar Categorias");
        listarCategoriasButton.setBounds(220, 60, 150, 25);
        panel.add(listarCategoriasButton);

        JButton listarProdutosButton = new JButton("Listar Produtos");
        listarProdutosButton.setBounds(220, 100, 150, 25);
        panel.add(listarProdutosButton);
        
        JButton attCategoriaButton = new JButton("Att. Categoria");
        attCategoriaButton.setBounds(390, 60, 150, 25);
        panel.add(attCategoriaButton);
        
        JButton attProdutoButton = new JButton("Att. Produto");
        attProdutoButton.setBounds(390, 100, 150, 25);
        panel.add(attProdutoButton);
        
        JButton deletarCategoriaButton = new JButton("Deletar Categoria");
        deletarCategoriaButton.setBounds(560, 60, 150, 25);
        panel.add(deletarCategoriaButton);

        JButton deletarProdutoButton = new JButton("Deletar Produto");
        deletarProdutoButton.setBounds(560, 100, 150, 25);
        panel.add(deletarProdutoButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 150, 660, 200);
        panel.add(scrollPane);
        
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        
        criarCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = JOptionPane.showInputDialog("Digite o nome da categoria:");
                int limite = Integer.parseInt(JOptionPane.showInputDialog("Digite o limite de itens:"));

                try {
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    categoriaDAO.salvarCategoria(new MaxCategoria(nome, limite));
                    JOptionPane.showMessageDialog(panel, "Categoria salva com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao salvar categoria: " + ex.getMessage());
                }
            }
        });

        criarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    ProdutoDAO produtoDAO = new ProdutoDAO();

                    String nomeProduto = JOptionPane.showInputDialog("Digite o nome do produto:");
                    int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade:"));
                    double preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço:"));
                    String dataValidade = JOptionPane.showInputDialog("Digite a data de validade (YYYY-MM-DD):");
                    int categoriaId = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da categoria:"));

                    MaxCategoria categoria = categoriaDAO.buscarCategoriaPorId(categoriaId);
                    if (categoria == null) {
                        JOptionPane.showMessageDialog(panel, "Categoria não encontrada. Crie a categoria primeiro!");
                        return;
                    }

                    Produto novoProduto = new Produto(nomeProduto, quantidade, preco, dataValidade, categoriaId, categoria);
            	    produtoDAO.salvarProduto(novoProduto);
                    JOptionPane.showMessageDialog(panel, "Produto salvo com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao salvar produto: " + ex.getMessage());
                }
            }
        });

        listarCategoriasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    List<MaxCategoria> categorias = categoriaDAO.listarCategorias();

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID");
                    model.addColumn("Nome");
                    model.addColumn("Limite de Itens");

                    for (MaxCategoria categoria : categorias) {
                        model.addRow(new Object[]{categoria.getId(), categoria.getNome(), categoria.getLimiteItens()});
                    }

                    table.setModel(model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao listar categorias: " + ex.getMessage());
                }
            }
        });

        listarProdutosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    List<Produto> produtos = produtoDAO.listarProdutos();

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID");
                    model.addColumn("Nome");
                    model.addColumn("Quantidade");
                    model.addColumn("Preço");
                    model.addColumn("Data de Validade");
                    model.addColumn("Categoria ID");

                    for (Produto produto : produtos) {
                        model.addRow(new Object[]{produto.getId(), produto.getNome(), produto.getQuantidade(),
                                produto.getPreco(), produto.getDataValidade(), produto.getCategoriaId()});
                    }

                    table.setModel(model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao listar produtos: " + ex.getMessage());
                }
            }
        });
        
        deletarCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    String nomeCategoria = JOptionPane.showInputDialog("Digite o nome da categoria que deseja deletar:");

                    categoriaDAO.deletarCategoria(nomeCategoria);
                    JOptionPane.showMessageDialog(panel, "Categoria deletada com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao deletar categoria: " + ex.getMessage());
                }
            }
        });

        deletarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    String nomeProduto = JOptionPane.showInputDialog("Digite o nome do produto que deseja deletar:");

                    produtoDAO.deletarProduto(nomeProduto);
                    JOptionPane.showMessageDialog(panel, "Produto deletado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao deletar produto: " + ex.getMessage());
                }
            }
        });
        
        attCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da categoria que deseja atualizar:"));
            	String nome = JOptionPane.showInputDialog("Digite o novo nome de categoria (caso deseje atualizar):");
                int limite = Integer.parseInt(JOptionPane.showInputDialog("Digite o novo limite de itens (caso deseje atualizar):"));
                
                try {
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    categoriaDAO.atualizarCategoria(new MaxCategoria(id, nome, limite));
                    JOptionPane.showMessageDialog(panel, "Categoria atualizada com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao atualizar categoria: " + ex.getMessage());
                }
            }
        });
        
        attProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto que deseja atualizar:"));
            	String nome = JOptionPane.showInputDialog("Digite o novo nome de produto (caso deseje atualizar):");
                int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade de produto (caso deseje atualizar):"));
                double preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o novo preço (caso deseje atualizar):"));
                String dataValidade = JOptionPane.showInputDialog("Digite a nova data de validade caso deseje atualizar (YYYY-MM-DD):");
                int categoriaId = Integer.parseInt(JOptionPane.showInputDialog("Digite o novo ID da categoria (caso deseje atualizar):")); 
                
                try {
                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    produtoDAO.atualizarProduto(new Produto(id, nome, quantidade, preco, dataValidade, categoriaId));
                    JOptionPane.showMessageDialog(panel, "Produto atualizado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao atualizar produto: " + ex.getMessage());
                }
            }
        });
    }
}
