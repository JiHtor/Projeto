
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// ============================================================
// JOGO: ESCURIDAO SANGRENTA
// Versao Swing do RPG original.
//
// Durante as historias:
// - O terminal ocupa a tela.
//
// Durante as batalhas:
// - O terminal fica a esquerda.
// - A arte ASCII do inimigo fica a direita.
// - Os botoes ficam na parte inferior.
// ============================================================

public class jogo {

    // ------------------------------------------------------------
    // CONSTANTES
    // ------------------------------------------------------------

    static final int VELOCIDADE_NORMAL = 30;
    static final int VELOCIDADE_RAPIDA = 5;

    static final char INICIO_ARTE = '\u0001';
    static final char FIM_ARTE = '\u0002';

    static final String[] PODERES = {
            "",
            "BOLA DE FOGO",
            "ATAQUE FURTIVO",
            "ESCUDO SAGRADO"
    };

    // ------------------------------------------------------------
    // ESTADO DO JOGO
    // ------------------------------------------------------------

    static JFrame janela;
    static JTextArea terminal;
    static JScrollPane painel;

    // Componentes exclusivos do inimigo
    static JTextArea arteInimigo;
    static JScrollPane painelInimigo;

    static JButton botaoContinuar;
    static JButton[] botoesAcao = new JButton[3];

    static Timer digitacao;
    static boolean rapido = false;

    // [0] Vida
    // [1] Ataque
    // [2] Defesa
    // [3] Critico minimo
    // [4] Critico maximo
    // [5] Esquiva minima
    // [6] Esquiva maxima

    static int[] personagem = new int[7];
    static int classe;
    static int vidaMaxima;

    // ------------------------------------------------------------
    // CLASSE INIMIGO
    // ------------------------------------------------------------

    static class Inimigo {

        String titulo;
        String nome;
        String sujeito;
        String msgEspecial;

        String[] derrota;

        int vida;
        int ataque;
        int defesa;
        int bonusMago;
        int danoEspecial;

        Inimigo(
                String titulo,
                String nome,
                String sujeito,
                int vida,
                int ataque,
                int defesa,
                int bonusMago,
                int danoEspecial,
                String msgEspecial,
                String[] derrota
        ) {

            this.titulo = titulo;
            this.nome = nome;
            this.sujeito = sujeito;
            this.vida = vida;
            this.ataque = ataque;
            this.defesa = defesa;
            this.bonusMago = bonusMago;
            this.danoEspecial = danoEspecial;
            this.msgEspecial = msgEspecial;
            this.derrota = derrota;
        }
    }

    // ============================================================
    // INICIO DO JOGO
    // ============================================================

    public static void iniciar(int classeEscolhida) {

        classe = classeEscolhida;

        personagem = new int[7];

        configurarPersonagem(classe, personagem);

        vidaMaxima = personagem[0];

        criarJanela();

        cenaCastelo();
    }

    // ------------------------------------------------------------
    // ATRIBUTOS DAS CLASSES
    // ------------------------------------------------------------

    public static void configurarPersonagem(int c, int[] p) {

        if (c == 1) {

            // MAGO
            p[0] = 50;
            p[1] = 50;
            p[2] = 0;
            p[3] = 15;
            p[4] = 20;
            p[5] = 17;
            p[6] = 20;

        } else if (c == 2) {

            // LADINO
            p[0] = 50;
            p[1] = 35;
            p[2] = 5;
            p[3] = 10;
            p[4] = 20;
            p[5] = 10;
            p[6] = 20;

        } else {

            // PALADINO
            p[0] = 100;
            p[1] = 20;
            p[2] = 20;
            p[3] = 19;
            p[4] = 20;
            p[5] = 19;
            p[6] = 20;
        }
    }

    // ------------------------------------------------------------
    // CALCULAR DANO
    // ------------------------------------------------------------

    public static int calcularDano(int ataque, int defesa) {

        int dano = ataque - defesa;

        if (dano < 0) {
            dano = 0;
        }

        return dano;
    }

    // ============================================================
    // CRIAR JANELA
    // ============================================================

    static void criarJanela() {

        janela = new JFrame();

        janela.setSize(1300, 799);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setUndecorated(true);
        janela.setLayout(null);

        janela.getContentPane().setBackground(Color.BLACK);

        // --------------------------------------------------------
        // TERMINAL PRINCIPAL
        // --------------------------------------------------------

        terminal = new JTextArea();

        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.WHITE);
        terminal.setCaretColor(Color.WHITE);
        terminal.setFont(new Font("Consolas", Font.PLAIN, 20));
        terminal.setEditable(false);
        terminal.setLineWrap(false);
        terminal.setBorder(null);

        painel = new JScrollPane(terminal);

        painel.setBounds(0, 0, 1300, 690);
        painel.setBorder(null);

        // --------------------------------------------------------
        // AREA DO ASCII DO INIMIGO
        // --------------------------------------------------------

        arteInimigo = new JTextArea();

        arteInimigo.setBackground(Color.BLACK);
        arteInimigo.setForeground(Color.WHITE);
        arteInimigo.setFont(new Font("Consolas", Font.PLAIN, 16));
        arteInimigo.setEditable(false);
        arteInimigo.setFocusable(false);
        arteInimigo.setLineWrap(false);
        arteInimigo.setWrapStyleWord(false);
        arteInimigo.setBorder(null);

        painelInimigo = new JScrollPane(arteInimigo);

        painelInimigo.setBounds(650, 0, 650, 690);
        painelInimigo.setBorder(null);

        painelInimigo.getViewport().setBackground(Color.BLACK);

        painelInimigo.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        painelInimigo.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        painelInimigo.setVisible(false);

        // --------------------------------------------------------
        // BOTAO CONTINUAR
        // --------------------------------------------------------

        botaoContinuar = new JButton("CONTINUAR");

        botaoContinuar.setBounds(1050, 700, 180, 50);

        estilizar(botaoContinuar);

        // --------------------------------------------------------
        // BOTOES DE ACAO
        // --------------------------------------------------------

        for (int i = 0; i < botoesAcao.length; i++) {

            botoesAcao[i] = new JButton();

            estilizar(botoesAcao[i]);

            botoesAcao[i].setVisible(false);
        }

        // --------------------------------------------------------
        // CAMADAS
        // --------------------------------------------------------

        JLayeredPane camadas = new JLayeredPane();

        camadas.setBounds(0, 0, 1300, 799);

        camadas.add(painel, JLayeredPane.DEFAULT_LAYER);

        camadas.add(painelInimigo, JLayeredPane.PALETTE_LAYER);

        camadas.add(botaoContinuar, JLayeredPane.MODAL_LAYER);

        for (JButton b : botoesAcao) {
            camadas.add(b, JLayeredPane.MODAL_LAYER);
        }

        janela.add(camadas);

        configurarAtalhos();

        janela.setVisible(true);
    }

    // ============================================================
    // ESTILO DOS BOTOES
    // ============================================================

    static void estilizar(JButton b) {

        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);

        b.setFont(new Font("Consolas", Font.BOLD, 16));

        b.setOpaque(true);
        b.setFocusPainted(false);
        b.setFocusable(false);

        b.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (b.isEnabled()) {

                    b.setBackground(Color.WHITE);
                    b.setForeground(Color.BLACK);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                b.setBackground(Color.BLACK);
                b.setForeground(Color.WHITE);
            }
        });
    }

    // ============================================================
    // ATALHO SPACE
    // ============================================================

    static void configurarAtalhos() {

        InputMap mapa = janela.getRootPane().getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        ActionMap acoes = janela.getRootPane().getActionMap();

        mapa.put(KeyStroke.getKeyStroke("SPACE"), "acelerar");

        acoes.put("acelerar", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                rapido = true;

                if (digitacao != null) {
                    digitacao.setDelay(VELOCIDADE_RAPIDA);
                }
            }
        });

        mapa.put(
                KeyStroke.getKeyStroke("released SPACE"),
                "normal"
        );

        acoes.put("normal", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                rapido = false;

                if (digitacao != null) {
                    digitacao.setDelay(VELOCIDADE_NORMAL);
                }
            }
        });
    }

    // ============================================================
    // FERRAMENTAS DE TELA
    // ============================================================

    // ------------------------------------------------------------
    // PREPARAR TELA NORMAL
    // ------------------------------------------------------------

    static void prepararTela() {

        if (digitacao != null) {
            digitacao.stop();
        }

        terminal.setText("");

        // O terminal volta a ocupar a tela.
        painel.setBounds(0, 0, 1300, 690);

        // Esconde e limpa a arte do inimigo.
        painelInimigo.setVisible(false);

        arteInimigo.setText("");

        // Esconde os botoes de acao.
        for (JButton b : botoesAcao) {

            b.setVisible(false);

            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
        }

        botaoContinuar.setVisible(true);
        botaoContinuar.setEnabled(false);

        botaoContinuar.setBackground(Color.BLACK);
        botaoContinuar.setForeground(Color.WHITE);
    }

    // ------------------------------------------------------------
    // PREPARAR TELA DE BATALHA
    // ------------------------------------------------------------

    static void configurarTelaBatalha() {

        // Divide a janela em duas partes.

        // Esquerda: mensagens do combate.
        painel.setBounds(0, 0, 650, 690);

        // Direita: desenho do inimigo.
        painelInimigo.setBounds(650, 0, 650, 690);

        painelInimigo.setVisible(true);

        arteInimigo.setCaretPosition(0);
    }

    // ------------------------------------------------------------
    // MOSTRAR ASCII DO INIMIGO
    // ------------------------------------------------------------

    static void mostrarInimigo(String arte) {

        arteInimigo.setText(arte);

        arteInimigo.setCaretPosition(0);
    }

    // ------------------------------------------------------------
    // ESCOLHER ASCII AUTOMATICAMENTE
    // ------------------------------------------------------------

    static void selecionarArteInimigo(Inimigo ini) {

        if (ini.nome.equalsIgnoreCase("NOSFERATU")) {

            mostrarInimigo(NOSFERATU);

        } else if (ini.nome.equalsIgnoreCase("DIABINHO")) {

            mostrarInimigo(DIABINHO);

        } else if (ini.nome.equalsIgnoreCase("DIABO")) {

            mostrarInimigo(DIABO);

        } else if (
                ini.nome.equalsIgnoreCase("GUARDIÃO")
                        || ini.nome.equalsIgnoreCase("GUARDIAO")
        ) {

            mostrarInimigo(GUARDIAO);

        } else {

            mostrarInimigo("");
        }
    }

    // ------------------------------------------------------------
    // DEFINIR ACAO DE UM BOTAO
    // ------------------------------------------------------------

    static void definirAcao(JButton b, Runnable acao) {

        for (ActionListener al : b.getActionListeners()) {
            b.removeActionListener(al);
        }

        b.addActionListener(e -> acao.run());
    }

    // ============================================================
    // EFEITO DE DIGITACAO
    // ============================================================

    static void digitar(String texto, Runnable aoTerminar) {

        final int[] indice = {0};

        digitacao = new Timer(
                rapido ? VELOCIDADE_RAPIDA : VELOCIDADE_NORMAL,
                e -> {

                    if (indice[0] < texto.length()) {

                        char c = texto.charAt(indice[0]);

                        // As artes entre os marcadores aparecem de uma vez.
                        if (c == INICIO_ARTE) {

                            int fim = texto.indexOf(
                                    FIM_ARTE,
                                    indice[0]
                            );

                            if (fim != -1) {

                                terminal.append(
                                        texto.substring(
                                                indice[0] + 1,
                                                fim
                                        )
                                );

                                indice[0] = fim + 1;

                            } else {

                                terminal.append(String.valueOf(c));

                                indice[0]++;
                            }

                        } else {

                            terminal.append(String.valueOf(c));

                            indice[0]++;
                        }

                        terminal.setCaretPosition(
                                terminal.getDocument().getLength()
                        );

                    } else {

                        ((Timer) e.getSource()).stop();

                        aoTerminar.run();
                    }
                }
        );

        digitacao.start();
    }

    // ============================================================
    // MOSTRAR TEXTO
    // ============================================================

    static void mostrarTexto(String texto, Runnable proximo) {

        prepararTela();

        definirAcao(botaoContinuar, proximo);

        digitar(texto, () -> botaoContinuar.setEnabled(true));
    }

    // ============================================================
    // MOSTRAR RESULTADO INSTANTANEO
    // ============================================================

    static void mostrarInstantaneo(String texto, Runnable proximo) {

        if (digitacao != null) {
            digitacao.stop();
        }

        terminal.setText(texto);

        terminal.setCaretPosition(0);

        for (JButton b : botoesAcao) {
            b.setVisible(false);
        }

        botaoContinuar.setVisible(true);
        botaoContinuar.setEnabled(true);

        definirAcao(botaoContinuar, proximo);
    }

    // ============================================================
    // CONFIGURAR BOTOES DE ACAO
    // ============================================================

    static void configurarBotoesAcao(
            String[] rotulos,
            Runnable[] acoes,
            boolean visivel
    ) {

        int largura = 240;
        int espaco = 90;

        int total = rotulos.length * largura
                + (rotulos.length - 1) * espaco;

        int inicio = 650 - total / 2;

        botaoContinuar.setVisible(false);

        for (int i = 0; i < botoesAcao.length; i++) {

            if (i < rotulos.length) {

                botoesAcao[i].setText(rotulos[i]);

                botoesAcao[i].setBounds(
                        inicio + i * (largura + espaco),
                        700,
                        largura,
                        50
                );

                botoesAcao[i].setEnabled(true);
                botoesAcao[i].setVisible(visivel);

                definirAcao(botoesAcao[i], acoes[i]);

            } else {

                botoesAcao[i].setVisible(false);
            }
        }
    }

    // ============================================================
    // MOSTRAR ESCOLHAS
    // ============================================================

    static void mostrarEscolhas(
            String texto,
            String[] rotulos,
            Runnable[] acoes
    ) {

        prepararTela();

        configurarBotoesAcao(rotulos, acoes, false);

        digitar(texto, () -> {

            for (int i = 0; i < rotulos.length; i++) {
                botoesAcao[i].setVisible(true);
            }
        });
    }

    // ============================================================
    // MOSTRAR ACOES DA BATALHA
    // ============================================================

    static void mostrarAcoes(
            String texto,
            String[] rotulos,
            Runnable[] acoes
    ) {

        // IMPORTANTE:
        // Aqui nao usamos prepararTela(), porque ele apagaria
        // o desenho do inimigo a cada turno.

        if (digitacao != null) {
            digitacao.stop();
        }

        terminal.setText("");

        // Mantem o painel do inimigo e divide a janela.
        configurarTelaBatalha();

        configurarBotoesAcao(rotulos, acoes, true);

        terminal.setText(texto);

        terminal.setCaretPosition(0);
    }

    // ============================================================
    // FORMATACAO DE TEXTO
    // ============================================================

    static String tit(String titulo) {

        return "        ===== " + titulo + " =====";
    }

    static String linhas(String... ls) {

        StringBuilder sb = new StringBuilder("\n");

        for (String l : ls) {

            if (l.isEmpty() || l.charAt(0) == INICIO_ARTE) {

                sb.append(l);

            } else {

                sb.append(" ").append(l);
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    static String arte(String a) {

        return INICIO_ARTE + a + FIM_ARTE;
    }

    static String barra(int atual, int max) {

        int tamanho = 20;
        int cheios = 0;

        if (max > 0) {

            cheios = atual * tamanho / max;
        }

        if (cheios < 0) {
            cheios = 0;
        }

        if (cheios > tamanho) {
            cheios = tamanho;
        }

        if (atual > 0 && cheios == 0) {
            cheios = 1;
        }

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < tamanho; i++) {

            sb.append(i < cheios ? "#" : "-");
        }

        sb.append("]");

        return sb.toString();
    }

    static String cabecalho(
            String titulo,
            String nome,
            int vidaIni,
            int vidaIniMax,
            int turno
    ) {

        String linha =
                "  ================================================\n";

        return "\n" + linha
                + "   " + titulo + "   (TURNO " + turno + ")\n"
                + linha + "\n"
                + String.format(
                "   %-10s %s %3d/%d\n",
                "VOCE",
                barra(personagem[0], vidaMaxima),
                personagem[0],
                vidaMaxima
        )
                + String.format(
                "   %-10s %s %3d/%d\n",
                nome,
                barra(vidaIni, vidaIniMax),
                vidaIni,
                vidaIniMax
        )
                + "\n";
    }

    // ============================================================
    // CHEGADA AO CASTELO
    // ============================================================

    static void cenaCastelo() {

        mostrarTexto(linhas(
                tit("AS MONTANHAS"),
                "",
                "Depois de uma longa jornada, voce finalmente",
                "alcanca as montanhas que cercam o castelo.",
                "O vento sopra violentamente.",
                "A neve cobre o caminho e o silencio e assustador.",
                "",
                "Ao longe, uma enorme fortaleza surge entre a nevoa.",
                "O lugar onde tudo comecou.",
                "",
                "O castelo de Nosferatu.",
                "",
                arte(TORRE)
        ), jogo::encontroNosferatu);
    }

    // ============================================================
    // PRIMEIRO ENCONTRO COM NOSFERATU
    // ============================================================

    static void encontroNosferatu() {

        mostrarTexto(linhas(
                tit("O SENHOR DO CASTELO"),
                "",
                "As enormes portas do castelo se abrem lentamente.",
                "O salao esta completamente escuro.",
                "Voce caminha alguns passos.",
                "",
                "Uma voz surge do fundo do salao.",
                "",
                "\"Mais um guerreiro...\"",
                "\"Mais uma alma que acredita poder me derrotar.\"",
                "",
                "Uma figura se levanta do trono.",
                "Nosferatu finalmente aparece.",
                "",
                arte(NOSFERATU),
                "",
                "\"Voce nao passa de mais um corpo para minha colecao.\"",
                "\"Vamos acabar logo com isso.\""
        ), jogo::batalhaInicial);
    }

    // ============================================================
    // PRIMEIRA BATALHA: NOSFERATU
    // ============================================================

    static void batalhaInicial() {

        mostrarInimigo(NOSFERATU);

        turnoInicial(
                1,
                10000,
                "   Nosferatu observa voce sem demonstrar preocupacao.\n"
        );
    }

    static void turnoInicial(
            int turno,
            int vidaNosferatu,
            String log
    ) {

        String texto = cabecalho(
                "BATALHA: NOSFERATU",
                "NOSFERATU",
                vidaNosferatu,
                10000,
                turno
        ) + log;

        mostrarAcoes(
                texto,
                new String[]{
                        "ATACAR",
                        PODERES[classe],
                        "BLOQUEAR"
                },
                new Runnable[]{
                        () -> executarInicial(1, turno, vidaNosferatu),
                        () -> executarInicial(2, turno, vidaNosferatu),
                        () -> executarInicial(3, turno, vidaNosferatu)
                }
        );
    }

    static void executarInicial(
            int opcao,
            int turno,
            int vidaNosferatu
    ) {

        StringBuilder sb = new StringBuilder();

        if (opcao == 1) {

            int dano = calcularDano(personagem[1], 100);

            vidaNosferatu -= dano;

            sb.append("   Voce ataca Nosferatu e causa ")
                    .append(dano)
                    .append(" de dano.\n");

            sb.append("   Ele sequer parece ter sentido o golpe.\n");

        } else if (opcao == 2) {

            if (classe == 1) {

                sb.append("   Voce dispara uma Bola de Fogo!\n");

            } else if (classe == 2) {

                sb.append("   Voce tenta um Ataque Furtivo!\n");

            } else {

                sb.append("   Voce ergue seu Escudo Sagrado!\n");
            }

            sb.append("   Nosferatu permanece completamente intacto.\n");

        } else {

            sb.append("   Voce assume uma postura defensiva.\n");
        }

        int danoRecebido;

        if (turno == 1) {

            danoRecebido = 15;

        } else if (turno == 2) {

            danoRecebido = 25;

        } else {

            danoRecebido = 100;
        }

        if (opcao == 3 || (opcao == 2 && classe == 3)) {

            danoRecebido = 0;

        } else {

            danoRecebido = calcularDano(
                    danoRecebido,
                    personagem[2]
            );
        }

        personagem[0] -= danoRecebido;

        if (personagem[0] < 0) {
            personagem[0] = 0;
        }

        sb.append("\n   Nosferatu ataca e causa ")
                .append(danoRecebido)
                .append(" de dano.\n");

        sb.append("   Sua vida restante: ")
                .append(personagem[0])
                .append("\n");

        if (turno >= 3 || personagem[0] <= 0) {

            personagem[0] = 0;

            mostrarInstantaneo(
                    cabecalho(
                            "BATALHA: NOSFERATU",
                            "NOSFERATU",
                            vidaNosferatu,
                            10000,
                            turno
                    ) + sb,
                    jogo::derrotaInicial
            );

        } else {

            turnoInicial(
                    turno + 1,
                    vidaNosferatu,
                    sb.toString()
            );
        }
    }

    // ============================================================
    // DERROTA INICIAL
    // ============================================================

    static void derrotaInicial() {

        mostrarTexto(linhas(
                tit("DERROTA"),
                "",
                "Nosferatu segura voce pelo pescoco.",
                "\"Eu avisei... voce nao tinha a menor chance.\"",
                "",
                "Sua visao comeca a escurecer.",
                "Seu corpo cai no chao do castelo."
        ), jogo::cenaMorte);
    }

    // ============================================================
    // MORTE
    // ============================================================

    static void cenaMorte() {

        mostrarTexto(linhas(
                tit("MORTE"),
                "",
                "O silencio toma conta do salao.",
                "Nosferatu retorna ao seu trono.",
                "\"Mais um corpo para minha colecao.\"",
                "",
                "Mas sua historia ainda nao terminou.",
                "Quando tudo parece desaparecer...",
                "Voce sente algo puxando sua alma.",
                "Uma queda interminavel comeca.",
                "",
                "Voce esta sendo levado para outro lugar."
        ), jogo::despertarSubmundo);
    }

    // ============================================================
    // SUBMUNDO
    // ============================================================

    static void despertarSubmundo() {

        if (classe == 3) {

            personagem[0] = 100;

        } else {

            personagem[0] = 50;
        }

        vidaMaxima = personagem[0];

        mostrarTexto(linhas(
                tit("SUBMUNDO"),
                "",
                "Voce abre os olhos.",
                "O chao e coberto por cinzas.",
                "Rios de lava iluminam um horizonte infinito.",
                "Ao redor, almas vagam sem rumo.",
                "",
                "Voce percebe que esta no inferno.",
                "Mas, de alguma forma, ainda esta vivo.",
                "",
                "Sua vida foi restaurada.",
                "Vida atual: " + personagem[0]
        ), jogo::encontroDiabinho);
    }

    // ============================================================
    // DIABINHO
    // ============================================================

    static void encontroDiabinho() {

        mostrarTexto(linhas(
                tit("UMA PRESENCA"),
                "",
                "Um barulho estranho surge atras de uma rocha.",
                "\"Ei! Voce ai!\"",
                "\"Nao deveria estar andando por aqui!\"",
                "",
                "Uma pequena criatura aparece.",
                "",
                arte(DIABINHO),
                "",
                "\"Sou um dos demonios mais importantes daqui!\"",
                "\"E nao, nao estou com medo de voce.\"",
                "A criatura parece estar tremendo."
        ), jogo::batalhaDiabinho);
    }

    static void batalhaDiabinho() {

        Inimigo diabinho = new Inimigo(
                "BATALHA: DIABINHO",
                "DIABINHO",
                "O Diabinho",
                60,
                15,
                5,
                15,
                0,
                "",
                new String[]{
                        "O Diabinho comemora sua vitoria.",
                        "\"Eu sabia! Quer dizer... foi uma luta dificil!\"",
                        "Sua jornada termina no submundo."
                }
        );

        iniciarBatalha(diabinho, jogo::vitoriaDiabinho);
    }

    static void vitoriaDiabinho() {

        mostrarTexto(linhas(
                tit("VITORIA"),
                "",
                "O Diabinho cai de joelhos.",
                "\"ESPERA! ESPERA! EU ME RENDO!\"",
                "",
                "\"Voce nao precisa me matar! Posso ser util!\"",
                "\"Conheco esse lugar melhor do que ninguem!\"",
                "\"Posso mostrar um caminho para voce sair daqui!\"",
                "",
                "O pequeno demonio parece desesperado.",
                "\"So precisamos atravessar o territorio do meu antigo mestre...\"",
                "\"O Diabo.\"",
                "",
                "O Diabinho agora sera seu guia."
        ), jogo::evolucao);
    }

    // ============================================================
    // EVOLUCAO
    // ============================================================

    static void evolucao() {

        personagem[0] += 10;
        personagem[1] += 5;
        personagem[2] += 2;

        vidaMaxima += 10;

        mostrarTexto(linhas(
                tit("EVOLUCAO"),
                "",
                "A energia do Diabinho comeca a se dissipar.",
                "Uma parte dessa energia e absorvida por voce.",
                "",
                "Seus atributos aumentaram!",
                "Vida: " + personagem[0],
                "Ataque: " + personagem[1],
                "Defesa: " + personagem[2],
                "",
                "O Diabinho observa, claramente arrependido.",
                "\"Eu devia ter ficado quieto...\""
        ), jogo::caminhoAteDiabo);
    }

    static void caminhoAteDiabo() {

        mostrarTexto(linhas(
                tit("VALE DAS CINZAS"),
                "",
                "O Diabinho comeca a caminhar.",
                "\"Venha por aqui. Conheco um caminho mais rapido.\"",
                "",
                "Voce atravessa um enorme vale coberto por cinzas.",
                "Criaturas observam sua passagem de longe.",
                "Nenhuma delas parece querer se aproximar.",
                "",
                "Depois de algum tempo, voce avista uma fortaleza.",
                "Suas torres se erguem acima de um mar de lava.",
                "",
                "\"E ali que meu antigo mestre vive...\"",
                "\"O Diabo.\"",
                "",
                "O Diabinho engole seco.",
                "\"So para deixar claro, nos somos amigos agora, certo?\"",
                "\"Porque eu realmente nao gostaria de morrer hoje.\""
        ), jogo::encontroDiabo);
    }

    // ============================================================
    // DIABO
    // ============================================================

    static void encontroDiabo() {

        mostrarTexto(linhas(
                tit("A FORTALEZA"),
                "",
                "As portas da fortaleza se abrem.",
                "Um calor insuportavel toma conta do ambiente.",
                "No fundo do salao, uma figura observa sua chegada.",
                "",
                "\"Diabinho...\"",
                "\"O que voce pensa que esta fazendo trazendo um humano ate aqui?\"",
                "",
                "O pequeno demonio se esconde atras de voce.",
                "\"Entao... sobre isso...\"",
                "\"Eu posso explicar!\"",
                "",
                "O Diabo se levanta de seu trono.",
                "\"Nao tenho interesse em suas explicacoes.\"",
                "\"Se deseja continuar, tera que passar por mim.\"",
                "",
                arte(DIABO)
        ), jogo::batalhaDiabo);
    }

    static void batalhaDiabo() {

        Inimigo diabo = new Inimigo(
                "BATALHA: DIABO",
                "DIABO",
                "O Diabo",
                150,
                18,
                5,
                15,
                30,
                "O DIABO PREPARA UM ATAQUE ESPECIAL!",
                new String[]{
                        "O Diabo permanece de pe.",
                        "\"Voce nunca deveria ter chegado ate aqui.\""
                }
        );

        iniciarBatalha(diabo, jogo::vitoriaDiabo);
    }

    static void vitoriaDiabo() {

        mostrarTexto(linhas(
                tit("VITORIA"),
                "",
                "O Diabo cai de joelhos.",
                "\"Impossivel... como um simples humano...\"",
                "",
                "O Diabinho aparece atras de voce.",
                "\"Eu sempre soube que voce conseguiria!\"",
                "\"Quer dizer... eu tinha certeza absoluta!\"",
                "",
                "O Diabo ergue a cabeca.",
                "\"Se realmente deseja retornar ao mundo dos vivos...\"",
                "\"Existe um caminho.\"",
                "",
                "\"Atras deste salao existe uma passagem.\"",
                "\"Ela leva ao Portao do Renascimento.\"",
                "\"Mas existe um guardiao protegendo sua entrada.\"",
                "\"Ele e muito mais antigo e poderoso do que eu.\""
        ), jogo::encontroDemonio);
    }

    // ============================================================
    // GUARDIAO
    // ============================================================

    static void encontroDemonio() {

        mostrarTexto(linhas(
                tit("PORTAO DO RENASCIMENTO"),
                "",
                "A passagem atras do trono comeca a se abrir.",
                "Uma corrente de ar frio atravessa o salao.",
                "O Diabinho caminha lentamente atras de voce.",
                "\"Eu nunca gostei desse lugar...\"",
                "",
                "A passagem termina em uma enorme camara.",
                "No centro dela existe um portao gigantesco.",
                "Uma criatura permanece diante da entrada.",
                "",
                "O Diabo abaixa a cabeca.",
                "\"O Guardiao...\"",
                "",
                "A criatura finalmente se move.",
                "",
                arte(GUARDIAO),
                "",
                "\"Mais uma alma tentando retornar ao mundo?\"",
                "\"Voce derrotou o Diabo...\"",
                "\"Mas isso nao significa absolutamente nada.\"",
                "",
                "\"Eu sou o guardiao deste portao.\"",
                "\"Enquanto eu existir, nenhuma alma abandonara este lugar.\"",
                "",
                "O Diabinho se esconde atras de uma coluna.",
                "\"Acho que agora estamos encrencados...\""
        ), jogo::batalhaDemonio);
    }

    static void batalhaDemonio() {

        Inimigo guardiao = new Inimigo(
                "BATALHA: GUARDIAO",
                "GUARDIAO",
                "O Guardiao",
                200,
                25,
                8,
                20,
                40,
                "O GUARDIAO LIBERA UMA ONDA DE ENERGIA!",
                new String[]{
                        "O Guardiao permanece diante do portao.",
                        "\"Sua alma permanecera aqui para sempre.\""
                }
        );

        iniciarBatalha(guardiao, jogo::vitoriaDemonio);
    }

    static void vitoriaDemonio() {

        mostrarTexto(linhas(
                tit("O GUARDIAO FOI DERROTADO!"),
                "",
                "A criatura cai diante do portao.",
                "Sua armadura se desfaz em fragmentos de luz.",
                "Pela primeira vez em eras, o caminho esta livre."
        ), jogo::cenaRenascimento);
    }

    // ============================================================
    // RENASCIMENTO
    // ============================================================

    static void cenaRenascimento() {

        if (classe == 3) {

            personagem[0] = 110;

        } else {

            personagem[0] = 60;
        }

        vidaMaxima = personagem[0];

        mostrarTexto(linhas(
                tit("RENASCIMENTO"),
                "",
                "O Guardiao cai lentamente.",
                "O enorme portao comeca a se abrir.",
                "Uma luz intensa atravessa a camara.",
                "",
                "O Diabinho se aproxima.",
                "\"Bom... parece que chegamos ao fim.\"",
                "\"Foi um prazer trabalhar com voce.\"",
                "\"Quer dizer, foi um prazer sobreviver ao seu lado.\"",
                "",
                "O pequeno demonio para diante do portao.",
                "\"Eu nao posso atravessar.\"",
                "\"Mas voce... voce ainda tem uma conta para resolver.\"",
                "",
                "Voce atravessa o portao.",
                "Seu corpo e envolvido por uma luz intensa.",
                "A energia do submundo percorre suas veias.",
                "",
                "Sua alma retorna ao mundo dos vivos.",
                "",
                "Seu corpo foi restaurado!",
                "Vida: " + personagem[0],
                "Ataque: " + personagem[1],
                "Defesa: " + personagem[2],
                "",
                "Mas sua jornada ainda nao terminou.",
                "Nosferatu continua no castelo."
        ), jogo::cenaRetornoCastelo);
    }

    static void cenaRetornoCastelo() {

        mostrarTexto(linhas(
                tit("DE VOLTA AO MUNDO"),
                "",
                "Voce desperta nas proximidades do castelo.",
                "O mesmo vento frio atravessa as montanhas.",
                "A fortaleza continua envolta pela escuridao.",
                "",
                "Mas alguma coisa mudou.",
                "Voce nao e mais o guerreiro que entrou naquele salao.",
                "Agora sabe o que existe alem da morte.",
                "E nao pretende morrer novamente.",
                "",
                "O castelo esta diante de voce.",
                "Desta vez, Nosferatu nao tera a mesma facilidade."
        ), jogo::encontroFinalNosferatu);
    }

    // ============================================================
    // NOSFERATU FINAL
    // ============================================================

    static void encontroFinalNosferatu() {

        mostrarTexto(linhas(
                tit("O RETORNO DE NOSFERATU"),
                "",
                "As portas do castelo se abrem novamente.",
                "O salao continua exatamente como antes.",
                "Nosferatu esta sentado em seu trono.",
                "",
                arte(NOSFERATU),
                "",
                "Ao perceber sua presenca, ele se levanta.",
                "\"Nao...\"",
                "\"Isso e impossivel.\"",
                "",
                "Ele observa seu corpo restaurado.",
                "\"Eu vi voce morrer.\"",
                "\"Como conseguiu retornar?\"",
                "",
                "Voce caminha em sua direcao.",
                "Desta vez, nao existe medo.",
                "",
                "Nosferatu se levanta completamente.",
                "\"Entao terei que acabar com voce novamente!\""
        ), jogo::batalhaFinalNosferatu);
    }

    static void batalhaFinalNosferatu() {

        Inimigo nosferatu = new Inimigo(
                "BATALHA FINAL: NOSFERATU",
                "NOSFERATU",
                "Nosferatu",
                120,
                15,
                5,
                20,
                25,
                "NOSFERATU LIBERA UMA EXPLOSAO DE ENERGIA SOMBRIA!",
                new String[]{
                        "Nosferatu permanece de pe.",
                        "\"Voce deveria ter permanecido morto.\""
                }
        );

        iniciarBatalha(nosferatu, jogo::vitoriaFinal);
    }

    static void vitoriaFinal() {

        mostrarTexto(linhas(
                tit("NOSFERATU FOI DERROTADO!"),
                "",
                "Nosferatu cai de joelhos.",
                "\"Nao... isso nao pode estar acontecendo...\"",
                "",
                "A criatura tenta se levantar, mas seu corpo comeca a se desfazer.",
                "A maldicao que cobria o reino finalmente se quebra."
        ), jogo::finalHistoria);
    }

    // ============================================================
    // FINAL DA HISTORIA
    // ============================================================

    static void finalHistoria() {

        mostrarEscolhas(
                linhas(
                        tit("O FIM DA MALDICAO..."),
                        "",
                        "Uma luz atravessa as janelas do castelo.",
                        "Pela primeira vez em muitos anos, o sol volta a nascer.",
                        "A escuridao abandona as montanhas.",
                        "As almas presas pela maldicao finalmente encontram paz.",
                        "",
                        "O reino esta livre.",
                        "E sua historia sera lembrada por muitas geracoes.",
                        "",
                        "",
                        tit("FIM DE JOGO")
                ),
                new String[]{
                        "VOLTAR AO MENU",
                        "SAIR"
                },
                new Runnable[]{
                        jogo::voltarAoMenu,
                        jogo::sair
                }
        );
    }

    // ============================================================
    // MENU E SAIDA
    // ============================================================

    static void voltarAoMenu() {

        janela.dispose();

        escolha.abrir();
    }

    static void sair() {

        System.exit(0);
    }

    // ============================================================
    // BATALHA GENERICA
    // ============================================================

    static void iniciarBatalha(
            Inimigo ini,
            Runnable aoVencer
    ) {

        // Escolhe a arte correspondente ao inimigo.
        selecionarArteInimigo(ini);

        // Guarda a vida do jogador no inicio da batalha.
        int vidaInicial = personagem[0];

        turnoBatalha(
                ini,
                ini.vida,
                1,
                "",
                vidaInicial,
                aoVencer
        );
    }

    // ------------------------------------------------------------
    // TURNO DA BATALHA
    // ------------------------------------------------------------

    static void turnoBatalha(
            Inimigo ini,
            int vidaIni,
            int turno,
            String log,
            int vidaInicial,
            Runnable aoVencer
    ) {

        String texto = cabecalho(
                ini.titulo,
                ini.nome,
                vidaIni,
                ini.vida,
                turno
        ) + log;

        mostrarAcoes(
                texto,
                new String[]{
                        "ATACAR",
                        PODERES[classe],
                        "BLOQUEAR"
                },
                new Runnable[]{
                        () -> executarTurno(
                                1,
                                ini,
                                vidaIni,
                                turno,
                                vidaInicial,
                                aoVencer
                        ),

                        () -> executarTurno(
                                2,
                                ini,
                                vidaIni,
                                turno,
                                vidaInicial,
                                aoVencer
                        ),

                        () -> executarTurno(
                                3,
                                ini,
                                vidaIni,
                                turno,
                                vidaInicial,
                                aoVencer
                        )
                }
        );
    }

    // ------------------------------------------------------------
    // EXECUTAR ACAO DO JOGADOR E DO INIMIGO
    // ------------------------------------------------------------

    static void executarTurno(
            int opcao,
            Inimigo ini,
            int vidaIni,
            int turno,
            int vidaInicial,
            Runnable aoVencer
    ) {

        StringBuilder sb = new StringBuilder();

        boolean bloqueou = false;

        int dano;

        // --------------------------------------------------------
        // ACAO DO JOGADOR
        // --------------------------------------------------------

        if (opcao == 1) {

            dano = calcularDano(
                    personagem[1],
                    ini.defesa
            );

            vidaIni -= dano;

            sb.append("   Voce ataca e causa ")
                    .append(dano)
                    .append(" de dano!\n");

        } else if (opcao == 2) {

            if (classe == 1) {

                // MAGO: BOLA DE FOGO

                dano = calcularDano(
                        personagem[1] + ini.bonusMago,
                        ini.defesa
                );

                vidaIni -= dano;

                sb.append("   BOLA DE FOGO!\n");

                sb.append("   Voce causa ")
                        .append(dano)
                        .append(" de dano!\n");

            } else if (classe == 2) {

                // LADINO: ATAQUE FURTIVO

                dano = calcularDano(
                        personagem[1] * 2,
                        ini.defesa
                );

                vidaIni -= dano;

                sb.append("   ATAQUE FURTIVO!\n");

                sb.append("   Voce causa ")
                        .append(dano)
                        .append(" de dano!\n");

            } else {

                // PALADINO: ESCUDO SAGRADO

                bloqueou = true;

                sb.append("   ESCUDO SAGRADO!\n");

                sb.append("   Voce bloqueia completamente o proximo ataque.\n");
            }

        } else {

            bloqueou = true;

            sb.append("   Voce assume uma postura defensiva.\n");
        }

        if (vidaIni < 0) {
            vidaIni = 0;
        }

        // --------------------------------------------------------
        // VERIFICAR SE O INIMIGO MORREU
        // --------------------------------------------------------

        if (vidaIni <= 0) {

            mostrarInstantaneo(
                    cabecalho(
                            ini.titulo,
                            ini.nome,
                            0,
                            ini.vida,
                            turno
                    ) + sb,
                    aoVencer
            );

            return;
        }

        // --------------------------------------------------------
        // ACAO DO INIMIGO
        // --------------------------------------------------------

        int danoRecebido;

        // Ataque especial a cada tres turnos.

        if (ini.danoEspecial > 0 && turno % 3 == 0) {

            danoRecebido = ini.danoEspecial;

            sb.append("\n   ")
                    .append(ini.msgEspecial)
                    .append("\n");

        } else {

            danoRecebido = ini.ataque;
        }

        // Bloqueio anula o dano recebido.

        if (bloqueou) {

            danoRecebido = 0;

        } else {

            danoRecebido = calcularDano(
                    danoRecebido,
                    personagem[2]
            );
        }

        personagem[0] -= danoRecebido;

        if (personagem[0] < 0) {
            personagem[0] = 0;
        }

        sb.append("\n   ")
                .append(ini.sujeito)
                .append(" ataca e causa ")
                .append(danoRecebido)
                .append(" de dano!\n");

        sb.append("   Sua vida restante: ")
                .append(personagem[0])
                .append("\n");

        // --------------------------------------------------------
        // RESULTADO DO TURNO
        // --------------------------------------------------------

        if (personagem[0] <= 0) {

            mostrarInstantaneo(
                    cabecalho(
                            ini.titulo,
                            ini.nome,
                            vidaIni,
                            ini.vida,
                            turno
                    ) + sb,
                    () -> telaDerrota(
                            ini,
                            vidaInicial,
                            aoVencer
                    )
            );

        } else {

            turnoBatalha(
                    ini,
                    vidaIni,
                    turno + 1,
                    sb.toString(),
                    vidaInicial,
                    aoVencer
            );
        }
    }

    // ============================================================
    // TELA DE DERROTA
    // ============================================================

    static void telaDerrota(
            Inimigo ini,
            int vidaInicial,
            Runnable aoVencer
    ) {

        String texto = linhas(
                tit("DERROTA"),
                ""
        ) + linhas(ini.derrota);

        mostrarEscolhas(
                texto,
                new String[]{
                        "TENTAR NOVAMENTE",
                        "VOLTAR AO MENU",
                        "SAIR"
                },
                new Runnable[]{

                        () -> {

                            personagem[0] = vidaInicial;

                            iniciarBatalha(ini, aoVencer);
                        },

                        jogo::voltarAoMenu,

                        jogo::sair
                }
        );
    }

    // ============================================================
    // ARTES ASCII
    // ============================================================

    static final String TORRE =
            "              _.._                        +                        ^v^           \n" +
                    "            .' .-'`                      / \\          ^v^                    \n" +
                    "            |  |              +         /   \\         +                        \n" +
                    "            \\  \\             / \\       /     \\       / \\                       \n" +
                    "             '._'-._        /   \\     /   ^   \\     /   \\            ^v^       \n" +
                    "          +     '```       /     \\   /   / \\   \\   /     \\            +        \n" +
                    "         / \\              /   ^   \\ /   /   \\   \\ /   ^   \\          / \\       \n" +
                    "        /   \\            /   / \\   /   |  _  |   \\   / \\   \\        /   \\      \n" +
                    "       /  ^  \\          /____|_|__/____|_|_|_|____\\__|_|____\\      /  ^  \\     \n" +
                    "      /__/_\\__\\         |  _   _  |  |  _   _   _  |  |  _   _  | /__/_\\__\\    \n" +
                    "      | | | ||_||_||| |_| |_| ||_|_| |_| |_| |_| |_|_|| |_| |_| ||_||_|| | | |\n" +
                    "       |  _  |  ||  | / \\ / \\ | || |   _______   | || | / \\ / \\ |  ||  |  _  | \n" +
                    "       | | | |  ||  | | | | | | || |  /#######\\  | || | | | | | |  ||  | | | | \n" +
                    "       | |_| |  ..  | |_| |_| | .. |  |#######|  | .. | |_| |_| |  ..  | |_| | \n" +
                    "       |  .  |      |  .   .  |    |  |#######|  |    |  .   .  |      |  .  | \n" +
                    "    __/_______\\____/___________\\__/____|#######|____\\__/___________\\____/_______\\__";

    static final String NOSFERATU =
            "       _.---._          \n" +
                    "    , /~~~ ~~~\\ ,       \n" +
                    "    |\\|(0)|(0)|/|       \n" +
                    "    | |  'V'  |,|      \n" +
                    "    |_/\\ /W\\ /\\_|       \n" +
                    "    __/|\\_`_/|\\__       \n" +
                    "   /     \\---/     \\    \n" +
                    "   \\  `   \\_/   ,  /    \n" +
                    "    |  |   /    |  |    \n" +
                    "    |  |  | o o |  |    \n" +
                    "    |  |  | o o |  |    ";

    static final String DIABINHO =
            "             ,      ,       \n" +
                    "          /(.-\"\"-.)\\           \n" +
                    "      |\\  \\/      \\/  /|       \n" +
                    "      | \\ / =.  .= \\ / |       \n" +
                    "      \\( \\   o\\/o   / )/       \n" +
                    "       \\_, '-/  \\-' ,_/        \n" +
                    "         /   \\__/   \\           \n" +
                    "         \\ \\__/\\__/ /           \n" +
                    "       ___\\ \\|--|/ /___          \n" +
                    "     /`    \\      /    `\\        \n" +
                    "    /       '----'       \\       ";

    static final String DIABO =
            "      __ _____________________     |\\         /|   _____________________ __\n" +
                    "         \\___             / | |\\ \\  .---.---.  / /| | \\             ___/\n" +
                    "             \\___    /|  /  | |   \\<>  / \\  <>/  | |  \\  |\\    ___/\n" +
                    "                 ____ | /   | |   \\ VvV___VvV /   | |   \\ | ____\n" +
                    "             ____   / |/    | |    \\ ^_____^ /    | |    \\| \\   ____\n" +
                    "         ____    /    | \\   | |     \\_____/     | |   / |    \\    ____\n" +
                    "     ____      /      |   \\ | | ___/ /|___|\\ \\___ | | /   |      \\      ____\n" +
                    " ____        /___     |__  \\| |___  | | | | |  ___| |/  __|     ___\\        ____\n" +
                    "     ____\\       /    |  /  | \\   | \\ |___| | /   | /  |  \\    \\       /____\n" +
                    "          \\     /     | /   |  \\ / | \\|___|/ | \\ /  |   \\ |     \\     /\n" +
                    "           \\   /      |/    |   \\| \\  \\___/  / |/   |    \\|      \\";

    static final String GUARDIAO =
            "   /\\                                                                   /\\   \n" +
                    "  /  \\                                                                 /  \\  \n" +
                    " /    '-._                        .-\"\"\"\"\"-.                        _.-'    \\ \n" +
                    "/         '-._                  /           \\                  _.-'         \\\n" +
                    "\\             '-._              |  \\_. ._/  |              _.-'             /\n" +
                    " \\      /\\         /\\       _.-'(_   /^\\   _)'-.       /\\         /\\      / \n" +
                    "  \\    /  \\       /  \\  _.-'      | |III| |      '-._  /  \\       /  \\    /  \n" +
                    "   \\  /    \\     /    \\/          '-.   .-'          \\/    \\     /    \\  /   \n" +
                    "    \\/      \\   /      V             '---'             V      \\   /      \\/    \n" +
                    "             \\ /                                               \\ /             \n" +
                    "              V                                                 V              ";

}