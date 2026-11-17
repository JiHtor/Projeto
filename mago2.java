import javax.swing.*;

public class mago2 {

    public static void abrir() {

        JFrame janela = new JFrame();

        janela.setSize(1300, 799);
        janela.setUndecorated(true); //remove a barra superior e as bordas padrão da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

        janela.setVisible(true);
    }
}