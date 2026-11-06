
import javax.swing.*; //importa a biblioteca Swing, utilizada para criar interfaces gráficas em Java

public class splash {

    public static void main(String[] args) {

        JFrame tela = new JFrame(); //cria a janela que exibirá a Splash Screen

        JProgressBar progresso = new JProgressBar(); //cria uma barra de progresso para mostrar o carregamento
        JLabel texto = new JLabel("Carregando sistema..."); //cria um texto que será exibido na janela

        tela.setSize(400, 250); //define o tamanho da janela
        tela.getBackground();
        tela.setLayout(null); //desativa o posicionamento automático dos componentes, permitindo posicioná-los manualmente
        tela.setUndecorated(true); //remove a barra superior e as bordas padrão da janela
        tela.setLocationRelativeTo(null); //centraliza a janela na tela do computador

        texto.setBounds(120, 80, 200, 30); //define a posição (X e Y), largura e altura do texto
        progresso.setBounds(50, 140, 300, 20); //define a posição (X e Y), largura e altura da barra de progresso
        progresso.setStringPainted(true); //permite exibir o percentual numérico dentro da barra de progresso

        tela.add(texto); //adiciona o texto dentro da janela
        tela.add(progresso); //adiciona a barra de progresso dentro da janela.
        tela.setVisible(true); //torna a janela visível para o usuário.

        for (int i = 0; i <= 100; i++) { //cria uma repetição que começa em 0 e termina em 100.
            progresso.setValue(i); //atualiza o valor da barra de acordo com o número da variável i.
            try { //inicia um bloco para tratar possíveis interrupções durante a espera.
                Thread.sleep(30); //faz o programa esperar 30 milissegundos a cada repetição.
            } catch (InterruptedException e) { //captura uma possível interrupção durante o tempo de espera.
                Thread.currentThread().interrupt(); //restaura o estado de interrupção da thread atual.
                break; //interrompe o laço de repetição caso ocorra uma interrupção.
            }
        }
        tela.dispose();// Fecha a Splash Screen

        teste.abrir();// Abre a proxima tela
    }
}
