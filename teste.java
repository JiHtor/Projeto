import javax.swing.*;
import java.awt.Image;

public class teste {

    public static void abrir() {

        JFrame janela = new JFrame("teste");

        ImageIcon imagem = new ImageIcon("img/tste.jpg");
        JLabel fundo = new JLabel(imagem);

        janela.setSize(1200, 699);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        Image imagemRedimensionada = imagem.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);

        janela.add(fundo);
        janela.setVisible(true);
    }
}