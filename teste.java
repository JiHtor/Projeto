import javax.swing.*;

public class teste {

    public static void abrir() {

        JFrame janela = new JFrame("Minha primeira interface");

        janela.setSize(400, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        janela.setVisible(true);
    }
}