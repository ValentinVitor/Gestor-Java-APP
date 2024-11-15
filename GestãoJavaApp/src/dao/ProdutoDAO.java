package dao;

import model.Produto;
import util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

	public void salvarProduto(Produto produto) throws SQLException {
	    String sql = "INSERT INTO produtos (nome, quantidade, preco, data_validade, categoria_id) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = DataBaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, produto.getNome());
	        stmt.setInt(2, produto.getQuantidade());
	        stmt.setDouble(3, produto.getPreco());
	        stmt.setString(4, produto.getDataValidade());
	        stmt.setInt(5, produto.getCategoriaId());  // Agora estamos passando o ID da categoria
	        stmt.executeUpdate();
	    }
	}


    public List<Produto> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM produtos";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco"),
                        rs.getString("data_validade"),
                        rs.getInt("categoria_id")
                ));
            }
        }
        return produtos;
    }

    public void atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET quantidade = ?, preco = ?, data_validade = ?, categoria_id = ? WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produto.getQuantidade());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getDataValidade());
            stmt.setInt(4, produto.getCategoriaId());
            stmt.setString(5, produto.getNome()); // Usando getter para obter o nome
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o produto: " + e.getMessage());
            return;
        }
    }

    public void deletarProduto(String nomeProduto) throws SQLException {
        String sql = "DELETE FROM produtos WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeProduto); // Recebendo o nome do produto como parâmetro
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar o produto: " + e.getMessage());
            return;
        }
    }
}
    