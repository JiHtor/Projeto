
import javax.swing.*;
import java.awt.*;

public class teste {

    public static void abrir() {

        JFrame janela = new JFrame("teste");
        JTextArea terminal = new JTextArea();
        JButton botao = new JButton("CONTINUAR");

        final int velocidadeNormal = 45;
        final int velocidadeRapida = 5;

        janela.setSize(1300, 799);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);
        janela.setUndecorated(true);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.WHITE);
        terminal.setCaretColor(Color.WHITE);
        terminal.setFont(new Font("Consolas", Font.PLAIN, 20));
        terminal.setEditable(false);
        terminal.setLineWrap(true);
        terminal.setWrapStyleWord(true);
        botao.setBounds(1050, 700, 180, 50);
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Consolas", Font.BOLD, 16));
        botao.setFocusPainted(false);

        String lore =
                "" +
                        "> INICIANDO ARQUIVO...\n\n" +
                        "> ANO 2045\n\n" +
                        "O mundo como conhecemos deixou de existir.\n" +
                        "Após um evento misterioso, a humanidade\n" +
                        "passou a viver entre ruínas e tecnologias\n" +
                        "abandonadas.\n\n" +
                        "> UM NOVO DESTINO AGUARDA...\n"+
                 "    / \\  //\\\n" +
                "     |\\___/|      \\\n" +
                "     /0  0  \\      |\n" +
                "    /     /  \\     |\n" +
                "   /  \\_/     \\    |\n" +
                "  /  /   \\     \\   |\n" +
                "  \\  \\___/     /   |\n" +
                "   \\__________/____|\n" +
                "      DRAGON";

        // Efeito da digitação
        final int[] indice = {0};
        Timer digitacao = new Timer(velocidadeNormal, e -> {
            if (indice[0] < lore.length()) {
                terminal.append(
                        String.valueOf(lore.charAt(indice[0])));
                indice[0]++;
            } else {
                ((Timer) e.getSource()).stop();
                botao.setEnabled(true);
            }
        });
        // Detecta quando a tecla espaço é apertado
        terminal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("SPACE"), "acelerar");
        terminal.getActionMap().put("acelerar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                digitacao.setDelay(velocidadeRapida);
            }});
        // Detecta quando para de apertar a tecla espaço
        terminal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released SPACE"), "normal");
        terminal.getActionMap().put("normal", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                digitacao.setDelay(velocidadeNormal);
            }
        });
        // Painel com rolagem
        JScrollPane painel = new JScrollPane(terminal);
        painel.setBounds(0, 0, 1300, 799);
        janela.add(painel);
        botao.setEnabled(false);
        janela.add(botao);
        digitacao.start();
        janela.setVisible(true);

    }
}