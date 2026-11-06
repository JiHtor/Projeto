
import javax.swing.*; //importa a biblioteca Swing, utilizada para criar interfaces gráficas em Java

public class splash { //cria a classe principal da tela de carregamento

    public static void main(String[] args) { //método principal, onde a execução do programa começa

        JFrame tela = new JFrame(); // Cria a janela que exibirá a Splash Screen

        JProgressBar progresso = new JProgressBar(); // Cria uma barra de progresso para mostrar o carregamento
        JLabel texto = new JLabel("Carregando sistema..."); // Cria um texto que será exibido na janela

        tela.setSize(400, 250); // Define o tamanho da janela: 400 pixels de largura e 250 de altura
        tela.setLayout(null); // Desativa o posicionamento automático dos componentes, permitindo posicioná-los manualmente
        tela.setUndecorated(true); // Remove a barra superior e as bordas padrão da janela
        tela.setLocationRelativeTo(null); // Centraliza a janela na tela do computador

        texto.setBounds(120, 80, 200, 30); // Define a posição (X e Y), largura e altura do texto
        progresso.setBounds(50, 140, 300, 20); // Define a posição (X e Y), largura e altura da barra de progresso
        progresso.setStringPainted(true); // Permite exibir o percentual numérico dentro da barra de progresso

        tela.add(texto); // Adiciona o texto dentro da janela
        tela.add(progresso); // Adiciona a barra de progresso dentro da janela.
        tela.setVisible(true); // Torna a janela visível para o usuário.

        for (int i = 0; i <= 100; i++) { // Cria uma repetição que começa em 0 e termina em 100.
            progresso.setValue(i); // Atualiza o valor da barra de acordo com o número da variável i.
            try { // Inicia um bloco para tratar possíveis interrupções durante a espera.
                Thread.sleep(30); // Faz o programa esperar 30 milissegundos a cada repetição.
            } catch (InterruptedException e) { // Captura uma possível interrupção durante o tempo de espera.
                Thread.currentThread().interrupt(); // Restaura o estado de interrupção da thread atual.
                break; // Interrompe o laço de repetição caso ocorra uma interrupção.
            }
        }

// Fecha a Splash Screen
        tela.dispose();

// Abre a tela de login
        teste.abrir();

    } // Finaliza o método main.

} // Finaliza a classe SplashScreen.
