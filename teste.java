import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class teste {
    public static void abrir() {

        JFrame janela = new JFrame("teste");
        JTextArea terminal = new JTextArea();
        JButton botao = new JButton("CONTINUAR");
        final int velocidadeNormal = 45;
        final int velocidadeRapida = 5;

        //configurações da janela
        janela.setSize(1300, 799);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setUndecorated(true);
        janela.setLayout(null);
        janela.getContentPane().setBackground(Color.BLACK);

        //configurações do terminal
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.WHITE);
        terminal.setCaretColor(Color.WHITE);
        terminal.setFont(new Font("Consolas", Font.PLAIN, 20));
        terminal.setEditable(false);
        terminal.setLineWrap(false);
        terminal.setBorder(null);

        //texto da história
        String lore =
                "\n" +
                        "   > INICIANDO ARQUIVO...\n\n" +
                        "   > ANO 2045\n\n" +
                        "   O mundo como conhecemos deixou de existir.\n" +
                        "   Após um evento misterioso, a humanidade\n" +
                        "   passou a viver entre ruínas e tecnologias\n" +
                        "   abandonadas.\n\n" +
                        "   > UM NOVO DESTINO AGUARDA...\n\n\n\n\n\n" +
                        "    / \\  //\\\n" +
                        "     |\\___/|      \\\n" +
                        "     /0  0  \\      |\n" +
                        "    /     /  \\     |\n" +
                        "   /  \\_/     \\    |\n" +
                        "  /  /   \\     \\   |\n" +
                        "  \\  \\___/     /   |\n" +
                        "   \\__________/____|\n" +
                        "      DRAGON";

        //configurações do botão
        botao.setBounds(1050, 700, 180, 50);
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Consolas", Font.BOLD, 16));
        botao.setOpaque(true);
        botao.setFocusPainted(false);
        botao.setEnabled(false);

        //efeito da digitação
        final int[] indice = {0};
        Timer digitacao = new Timer(velocidadeNormal, e -> {
            if (indice[0] < lore.length()) {
                terminal.append(String.valueOf(lore.charAt(indice[0])));
                indice[0]++;
            } else {
                ((Timer) e.getSource()).stop();
                botao.setEnabled(true);
            }});

        //acelerar digitação ao pressionar espaço
        terminal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("SPACE"), "acelerar");
        terminal.getActionMap().put("acelerar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                digitacao.setDelay(velocidadeRapida);
            }});

        //voltar à velocidade normal ao soltar espaço
        terminal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released SPACE"), "normal");
        terminal.getActionMap().put("normal", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                digitacao.setDelay(velocidadeNormal);
            }});

        //efeito de passar o mouse no botão
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(Color.WHITE);
                botao.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(Color.BLACK);
                botao.setForeground(Color.WHITE);
            }});

        //ação do botão
        botao.addActionListener(e -> {
            segunda.abrir();
            janela.dispose();
        });

        //painel de rolagem do terminal
        JScrollPane painel = new JScrollPane(terminal);
        painel.setBounds(0, 0, 1300, 799);
        painel.setBorder(null);

        //sistema de camadas
        JLayeredPane camadas = new JLayeredPane();
        camadas.setBounds(0, 0, 1300, 799);

        //terminal na camada de fundo
        camadas.add(painel, JLayeredPane.DEFAULT_LAYER);

        //botão na camada superior
        camadas.add(botao, JLayeredPane.PALETTE_LAYER);

        //adiciona as camadas à janela
        janela.add(camadas);

        //inicia a tela
        janela.setVisible(true);
        digitacao.start();
    }
}