
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class escolha {

    public static void abrir() {

        JFrame janela = new JFrame();
        janela.setSize(1300, 799);
        janela.getContentPane().setBackground(Color.BLACK);
        janela.setUndecorated(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

        // Título da tela
        JLabel titulo = new JLabel("ESCOLHA SEU PERSONAGEM");
        titulo.setBounds(350, 60, 600, 70);
        titulo.setForeground(new Color(218, 190, 130));
        titulo.setFont(new Font("Cinzel", Font.BOLD, 40));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        janela.add(titulo);

        // Desenho do MAGO
        String desenhoMago =
                "            .    _\n" +
                        "           /\\   | |\n" +
                        "         _/_ \\_ >_<\n" +
                        "        .-\\ -/.  |\n" +
                        "       /  | | \\_ |\n" +
                        "       \\ \\| |\\__(/\n" +
                        "       /(`---')  |\n" +
                        "      / /     \\  |\n" +
                        "   _.'  \\'-'  /  |\n" +
                        "   `----'`=-='   '";

        // Desenho do LADINO
        String desenhoLadino =
                "          .-.\n" +
                        "         /   \\\n" +
                        "        |\\-.-/|    /\\\n" +
                        "        /\\___/\\    ||\n" +
                        "       /  \\_/  \\  _||_\n" +
                        "       \\ \\| |\\____(/\n" +
                        "       /(`---')   '\n" +
                        "      / /     \\\n" +
                        "   _.'  \\'-'  /\n" +
                        "   `----'`=-=' ";

        // Desenho do PALADINO
        String desenhoPaladino =
                "          .-.\n" +
                        "         /___\\     /\\\n" +
                        "         |-=-|     ||\n" +
                        "         \\___/     ||\n" +
                        "        /=[_]=\\   _||_\n" +
                        "        \\ \\| |\\___(/\n" +
                        "        (`---')   ()\n" +
                        "        | | | |\n" +
                        "        |_| |_|\n" +
                        "       (__) (__)\n";

        // Criando os três painéis
        JPanel painelMago = criarPainel(
                janela,
                "MAGO",
                desenhoMago,
                "Um mestre das artes arcanas que utiliza magia para causar grandes danos.",
                () -> mago1.abrir());

        JPanel painelLadino = criarPainel(
                janela,
                "LADINO",
                desenhoLadino,
                "Um combatente ágil e silencioso, especializado em ataques furtivos.",
                () -> ladino1.abrir());

        JPanel painelPaladino = criarPainel(
                janela,
                "PALADINO",
                desenhoPaladino,
                "Um guerreiro sagrado que combina força e resistência.",
                () -> paladino1.abrir());

        // Posição dos painéis
        painelMago.setBounds(120, 200, 340, 450);
        painelLadino.setBounds(480, 200, 340, 450);
        painelPaladino.setBounds(840, 200, 340, 450);

        // Adicionando os painéis na janela
        janela.add(painelMago);
        janela.add(painelLadino);
        janela.add(painelPaladino);

        // Exibindo a janela
        janela.setVisible(true);
    }


    // Método responsável por criar cada classe
    public static JPanel criarPainel(
            JFrame janela,
            String nome,
            String desenho,
            String descricao,
            Runnable acao) {

        JPanel painel = new JPanel();
        painel.setBackground(Color.BLACK);
        painel.setLayout(null);

        // Título da classe
        JLabel tituloClasse = new JLabel(nome);
        tituloClasse.setBounds(10, 0, 320, 45);
        tituloClasse.setForeground(new Color(218, 190, 130));
        tituloClasse.setFont(new Font("Cinzel", Font.BOLD, 28));
        tituloClasse.setHorizontalAlignment(SwingConstants.CENTER);

        // Desenho do personagem
        JLabel personagem = new JLabel(
                "<html><pre>" + desenho + "</pre></html>");

        personagem.setBounds(10, 50, 320, 220);
        personagem.setForeground(Color.WHITE);
        personagem.setFont(new Font("Consolas", Font.PLAIN, 17));
        personagem.setHorizontalAlignment(SwingConstants.CENTER);

        // Botão da classe
        JButton botao = new JButton();
        botao.setBounds(10, 45, 320, 300);

        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito ao passar o mouse
        botao.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                personagem.setForeground(new Color(218, 190, 130));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                personagem.setForeground(Color.WHITE);
            }});

        // Ação ao clicar
        botao.addActionListener(e -> {
            janela.dispose();
            acao.run();
        });
        // Descrição fora do botão
        JLabel textoDescricao = new JLabel(
                "<html><div style='text-align:center;'>"
                        + descricao +
                        "</div></html>");

        textoDescricao.setBounds(10, 355, 320, 85);
        textoDescricao.setForeground(Color.LIGHT_GRAY);
        textoDescricao.setFont(new Font("Consolas", Font.PLAIN, 15));
        textoDescricao.setHorizontalAlignment(SwingConstants.CENTER);

        // Adicionando os componentes
        painel.add(tituloClasse);
        painel.add(personagem);
        painel.add(botao);
        painel.add(textoDescricao);

        // Garante que o botão fique atrás dos textos e do desenho
        painel.setComponentZOrder(botao, painel.getComponentCount() - 1);
        return painel;
    }
}