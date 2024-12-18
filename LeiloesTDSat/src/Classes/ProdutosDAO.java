package Classes;

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Classes.ProdutosDTO;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        Connection conn = null;
        PreparedStatement prep = null;

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement("INSERT INTO produtos(nome,valor,status) VALUES(?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            int status = prep.executeUpdate();

            if (status > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro" + ex.getMessage());

        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "erro ao fechar conexão" + e.getMessage());
            }
        }

    }

    public List<ProdutosDTO> listarProdutos() {
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;

        List<ProdutosDTO> produtos = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);

            rs = prep.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                produtos.add(produto);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "erro ao conectar" + ex.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }

        }
        return produtos;
    }

    public void venderProduto(ProdutosDTO produtos) {
        if (produtos.getId() == null) {
            JOptionPane.showMessageDialog(null, "Erro: ID nulo");
        }

        Connection conn = null;
        PreparedStatement prep = null;

        try {
            conn = new conectaDAO().connectDB();

            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(sql);

            prep.setInt(1, produtos.getId());

            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto Vendido");
            } else {
                JOptionPane.showMessageDialog(null, "ID não encontrado");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }

        }

    }
}
