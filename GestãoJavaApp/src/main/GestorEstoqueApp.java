package main;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import model.MaxCategoria;
import model.Produto;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GestorEstoqueApp {
    public static void main(String[] args) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Usando try-with-resources para garantir que o scanner seja fechado
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Criar Categoria");
                System.out.println("2. Criar Produto");
                System.out.println("3. Atualizar Categoria");
                System.out.println("4. Atualizar Produto");
                System.out.println("5. Deletar Categoria");
                System.out.println("6. Deletar Produto");
                System.out.println("7. Listar Categorias");
                System.out.println("8. Listar Produtos");
                System.out.println("9. Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer

                switch (opcao) {
                    case 1:
                        // Criar Categoria
                        System.out.println("=== Criar Categoria ===");
                        System.out.print("Nome da Categoria: ");
                        String nomeCategoria = scanner.nextLine();
                        System.out.print("Limite de Itens da Categoria: ");
                        int limiteItens = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        MaxCategoria novaCategoria = new MaxCategoria(nomeCategoria, limiteItens);
                        try {
                            categoriaDAO.salvarCategoria(novaCategoria);
                            System.out.println("Categoria criada com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao salvar a categoria: " + e.getMessage());
                        }
                        break;

                    case 2:
                        // Criar Produto
                        System.out.println("=== Criar Produto ===");
                        System.out.print("Nome do Produto: ");
                        String nomeProduto = scanner.nextLine();
                        System.out.print("Quantidade: ");
                        int quantidade = scanner.nextInt();
                        System.out.print("Preço: ");
                        double preco = scanner.nextDouble();
                        scanner.nextLine(); // Limpar o buffer
                        System.out.print("Data de Validade: ");
                        String dataValidade = scanner.nextLine();
                        System.out.println("Informe o ID da categoria para o produto (use um ID existente): ");
                        int categoriaId = scanner.nextInt();  // O usuário vai inserir o ID da categoria existente

                        // Verificar se a categoria existe
                    	MaxCategoria categoriaExistente = categoriaDAO.buscarCategoriaPorId(categoriaId);
                        try {
                        	if (categoriaExistente != null) {
                        	    System.out.println("Criando produto...");
                        	    Produto novoProduto = new Produto(nomeProduto, quantidade, preco, dataValidade, categoriaId);
                        	    produtoDAO.salvarProduto(novoProduto);
                        	    System.out.println("Produto salvo com sucesso!");
                        	} else {
                        	    System.out.println("Categoria não encontrada! Crie a categoria primeiro.");
                        	}
                        } catch (SQLException e) {
                            System.out.println("Erro ao salvar o produto: " + e.getMessage());
                        }
                        break;

                    case 3:
                        // Atualizar Categoria
                        System.out.println("=== Atualizar Categoria ===");
                        System.out.print("Nome da Categoria a ser atualizada: ");
                        String nomeAtualizarCategoria = scanner.nextLine();
                        System.out.print("Novo limite de itens: ");
                        int novoLimite = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        MaxCategoria categoriaAtualizada = new MaxCategoria(nomeAtualizarCategoria, novoLimite);
                        try {
                            categoriaDAO.atualizarCategoria(categoriaAtualizada);
                            System.out.println("Categoria atualizada com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar a categoria: " + e.getMessage());
                        }
                        break;

                    case 4:
                        // Atualizar Produto
                        System.out.println("=== Atualizar Produto ===");
                        System.out.print("Nome do Produto a ser atualizado: ");
                        String nomeAtualizarProduto = scanner.nextLine();
                        System.out.print("Nova quantidade: ");
                        int novaQuantidade = scanner.nextInt();
                        System.out.print("Novo preço: ");
                        double novoPreco = scanner.nextDouble();
                        scanner.nextLine(); // Limpar o buffer
                        System.out.print("Nova data de validade: ");
                        String novaDataValidade = scanner.nextLine();
                        System.out.print("Nova categoria: ");
                        int novaCategoriaProduto = scanner.nextInt();

                        Produto produtoAtualizado = new Produto(nomeAtualizarProduto, novaQuantidade, novoPreco, novaDataValidade, novaCategoriaProduto);
                        try {
                            produtoDAO.atualizarProduto(produtoAtualizado);
                            System.out.println("Produto atualizado com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao atualizar o produto: " + e.getMessage());
                        }
                        break;

                    case 5:
                        // Deletar Categoria
                        System.out.println("=== Deletar Categoria ===");
                        System.out.print("Nome da Categoria a ser deletada: ");
                        String nomeDeletarCategoria = scanner.nextLine();
                        try {
                            categoriaDAO.deletarCategoria(nomeDeletarCategoria);
                            System.out.println("Categoria deletada com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao deletar a categoria: " + e.getMessage());
                        }
                        break;

                    case 6:
                        // Deletar Produto
                        System.out.println("=== Deletar Produto ===");
                        System.out.print("Nome do Produto a ser deletado: ");
                        String nomeDeletarProduto = scanner.nextLine();
                        try {
                            produtoDAO.deletarProduto(nomeDeletarProduto);
                            System.out.println("Produto deletado com sucesso!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao deletar o produto: " + e.getMessage());
                        }
                        break;

                    case 7:
                        // Listar Categorias
                        System.out.println("=== Listar Categorias ===");
                        try {
                            List<MaxCategoria> categorias = categoriaDAO.listarCategorias();
                            if (categorias.isEmpty()) {
                                System.out.println("Nenhuma categoria encontrada.");
                            } else {
                                for (MaxCategoria cat : categorias) {
                                    System.out.println("Nome: " + cat.getNome() + " | Limite de Itens: " + cat.getLimiteItens());
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao listar categorias: " + e.getMessage());
                        }
                        break;

                    case 8:
                        // Listar Produtos
                        System.out.println("=== Listar Produtos ===");
                        try {
                            List<Produto> produtos = produtoDAO.listarProdutos();
                            if (produtos.isEmpty()) {
                                System.out.println("Nenhum produto encontrado.");
                            } else {
                                for (Produto prod : produtos) {
                                    System.out.println("Nome: " + prod.getNome() + " | Quantidade: " + prod.getQuantidade() +
                                            " | Preço: " + prod.getPreco() + " | Data de Validade: " + prod.getDataValidade() +
                                            " | Categoria: " + prod.getCategoriaId());
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao listar produtos: " + e.getMessage());
                        }
                        break;

                    case 9:
                        // Sair
                        System.out.println("Saindo do programa...");
                        return; // Finaliza o programa

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro no programa: " + e.getMessage());
        }
    }
}
