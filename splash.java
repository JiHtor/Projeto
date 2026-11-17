import javax.swing.*;
import java.awt.*;

public class splash {
    public static void main(String[] args) {

        JFrame tela = new JFrame();
        JProgressBar progresso = new JProgressBar();
        JLabel texto = new JLabel("Carregando jogo...");
        ImageIcon imagem = new ImageIcon("img/san.png");

        // Pega o tamanho original da imagem
        int largura = imagem.getIconWidth();
        int altura = imagem.getIconHeight();

        // Cria o fundo
        JLabel fundo = new JLabel(imagem);
        fundo.setBounds(0, 0, largura, altura);

        // Configura a janela
        tela.setSize(largura, altura);
        tela.setLayout(null);
        tela.setUndecorated(true);
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centraliza o texto
        texto.setBounds(
                0,
                altura - 90,
                largura,
                30);

        texto.setHorizontalAlignment(SwingConstants.CENTER);
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Consolas", Font.BOLD, 20));

        // Centraliza a barra de progresso
        progresso.setBounds(
                (largura - 300) / 2,
                altura - 55,
                300,
                20);
        progresso.setStringPainted(true);
        progresso.setForeground(new Color(0, 0, 0));
        progresso.setBackground(Color.DARK_GRAY);
        progresso.setBorderPainted(false);

        tela.add(texto);
        tela.add(progresso);
        tela.add(fundo);
        tela.setVisible(true);

        for (int i = 0; i <= 100; i++) {
            progresso.setValue(i);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        tela.dispose();
        escolha.abrir();
    }
}