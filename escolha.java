import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class escolha {
    public static void main(String[] args){

        JFrame janela = new JFrame();
        JTextArea terminal = new JTextArea();
        JButton botao = new JButton("MAGO");


        //texto da história
        /*String lore =
                "\n" +
                        "   Escolha qual classe você quer jogar:\n\n\n" +
                        "     Mago:\n" +
                        "       Um mestre das artes arcanas. \n\n\n"+
                        "     Paladino:\n" +
                        "       Um combatente ágil e silencioso\n\n\n"+
                        "     Ladino:\n"+
                        "       Um guerreiro sagrado que combina força e resistência. ";
*/
        janela.setSize(1300, 799);
        janela.getContentPane().setBackground(Color.BLACK);
        janela.setForeground(Color.WHITE);
        janela.setUndecorated(true); //remove a barra superior e as bordas padrão da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

        //terminal.setText(lore);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.WHITE);
        terminal.setFont(new Font("Consolas", Font.PLAIN, 30));
        terminal.setEditable(false);
        terminal.setLineWrap(false);
        terminal.setBounds(100, 70, 1100, 650);

        botao.setBounds(170, 200, 1000, 130);
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setForeground(Color.WHITE);
        botao.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setForeground(Color.YELLOW);
                botao.setFont(new Font("Cinzel", Font.BOLD, 35));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setForeground(Color.WHITE);
                botao.setFont(new Font("Cinzel", Font.BOLD, 30));
            }});

        janela.add(botao);
        janela.add(terminal);
        janela.setVisible(true);
    }
}