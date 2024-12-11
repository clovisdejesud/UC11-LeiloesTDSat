/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

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

           /* if (status > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado!");
            }*/

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

    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }

}
