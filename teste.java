import javax.swing.*;

public class teste {

    public static void abrir() {

        JFrame janela = new JFrame("teste");

        ImageIcon imagem = new ImageIcon("img/tste.jpg");
        JLabel fundo = new JLabel(imagem);

        janela.setSize(700, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        fundo.setBounds(0, 0, 700, 400);
        fundo.setLayout(null);

        janela.add(fundo);
        janela.setVisible(true);
    }
}