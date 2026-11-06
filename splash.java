import javax.swing.*;//chama extenção para criar as funções, o * da liberdade para usarmos todos os comandos do swing

public class splash {

    public static void main(String[] args) {

        JFrame tela = new JFrame();

        JProgressBar progresso = new JProgressBar();

        JLabel texto = new JLabel("Carregando sistema...");

        tela.setSize(400, 250);
        tela.setLayout(null);
        tela.setUndecorated(true);
        tela.setLocationRelativeTo(null);

        texto.setBounds(120, 80, 200, 30);
        progresso.setBounds(50, 140, 300, 20);

        progresso.setStringPainted(true);

        tela.add(texto);
        tela.add(progresso);

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

        JOptionPane.showMessageDialog(null, "Sistema iniciado!");
    }
}