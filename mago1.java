import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class mago1 {
    public static void abrir() {

        JFrame janela = new JFrame("teste");
        JTextArea terminal = new JTextArea();
        JButton botao = new JButton("CONTINUAR");
        final int velocidadeNormal = 20;
        final int velocidadeRapida = 1;

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
                        " - Há anos, o sol não nasce sobre o reino. O que antes era uma terra próspera e iluminada hoje sufoca sob o véu \n" +
                        " de uma maldição imposta por uma criatura ancestral — um Nosferatu de poder incomensurável. Do alto de seu \n" +
                        " castelo isolado nas montanhas cinzentas, ele roubou a luz do dia e estendeu sua vontade sombria sobre a terra.\n" +
                        " - Aos poucos, a escuridão corrompeu não apenas os céus, mas as mentes. O vampiro controla a vontade dos habitantes \n" +
                        " amaldiçoados, transformando antigos cidadãos em marionetes silenciosas de seu domínio. Nos primeiros anos, \n" +
                        " o reino reuniu seus melhores soldados, mercenários e aventureiros em expedições desesperadas rumo à fortaleza\n " +
                        "sombria. Nenhum deles retornou.Hoje, a esperança secou nas ruas vazias. Ninguém mais ousa olhar para o horizonte\n" +
                        " onde o castelo se ergue. Resta apenas uma última centelha contra a noite absoluta — e ela carrega o seu nome.\n\n" +
                        "           _.._                        +                        ^v^           \n" +
                        "         .' .-'`                      / \\          ^v^                    \n" +
                        "         |  |              +         /   \\         +                        \n" +
                        "         \\  \\             / \\       /     \\       / \\                       \n" +
                        "          '._'-._        /   \\     /   ^   \\     /   \\            ^v^       \n" +
                        "       +     '```       /     \\   /   / \\   \\   /     \\            +        \n" +
                        "      / \\              /   ^   \\ /   /   \\   \\ /   ^   \\          / \\       \n" +
                        "     /   \\            /   / \\   /   |  _  |   \\   / \\   \\        /   \\      \n" +
                        "    /  ^  \\          /____|_|__/____|_|_|_|____\\__|_|____\\      /  ^  \\     \n" +
                        "   /__/_\\__\\         |  _   _  |  |  _   _   _  |  |  _   _  | /__/_\\__\\    \n" +
                        "   | | | ||_||_||| |_| |_| ||_|_| |_| |_| |_| |_|_|| |_| |_| ||_||_|| | | |\n" +
                        "    |  _  |  ||  | / \\ / \\ | || |   _______   | || | / \\ / \\ |  ||  |  _  | \n" +
                        "    | | | |  ||  | | | | | | || |  /#######\\  | || | | | | | |  ||  | | | | \n" +
                        "    | |_| |  ..  | |_| |_| | .. |  |#######|  | .. | |_| |_| |  ..  | |_| | \n" +
                        "    |  .  |      |  .   .  |    |  |#######|  |    |  .   .  |      |  .  | \n" +
                        " __/_______\\____/___________\\__/___|#######|____\\__/___________\\____/_______\\__\n\n" +
                        " Criado nas sombras e acostumado a sobreviver sozinho, você é imune à maldição do Nosferatu por razões desconhecidas.\n" +
                        " Após a queda dos heróis e exércitos do reino, você parte sozinho rumo ao castelo do vampiro, usando furtividade,\n " +
                        " venenos e lâminas silenciosas para enfrentar o monstro que mergulhou o mundo na escuridão.";

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
            mago2.abrir();
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