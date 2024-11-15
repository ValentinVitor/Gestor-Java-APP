package dao;

import model.MaxCategoria;
import util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void salvarCategoria(MaxCategoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nome, limite_itens) VALUES (?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getLimiteItens());
            stmt.executeUpdate();
        }
    }

    public List<MaxCategoria> listarCategorias() throws SQLException {
        String sql = "SELECT * FROM categorias";
        List<MaxCategoria> categorias = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new MaxCategoria(
                        rs.getString("nome"),
                        rs.getInt("limite_itens")
                ));
            }
        }
        return categorias;
    }

    public void atualizarCategoria(MaxCategoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET limite_itens = ? WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getLimiteItens()); // Usando getter para limite de itens
            stmt.setString(2, categoria.getNome()); // Usando getter para o nome da categoria
            stmt.executeUpdate();
            System.out.println("Categoria atualizada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a categoria: " + e.getMessage());
            return;
        }
    }

    public void deletarCategoria(String nomeCategoria) throws SQLException {
        String sql = "DELETE FROM categorias WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeCategoria); // Passando o nome como parâmetro
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar a categoria: " + e.getMessage());
            return;
        }
    }
    
    public MaxCategoria buscarCategoriaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MaxCategoria(rs.getString("nome"), rs.getInt("limite_itens"));
            } else {
                return null;  // Retorna null se não encontrar a categoria
            }
        }
    }

}