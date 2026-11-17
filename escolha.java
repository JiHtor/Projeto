import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class escolha {
    public static void main(String[] args){

        JFrame janela = new JFrame();
        JTextArea terminal = new JTextArea();
        JButton mago = new JButton("MAGO: Um mestre das artes arcanas.");
        JButton paladino = new JButton("PALADINO: Um combatente ágil e silencioso.");
        JButton ladino = new JButton("LADINO: Um guerreiro sagrado que combina força e resistência.");

        //texto da história
        String lore =
                "\n" +
                        "   ESCOLHA SUA CLASSE: ";

        janela.setSize(1300, 799);
        janela.getContentPane().setBackground(Color.BLACK);
        janela.setForeground(Color.WHITE);
        janela.setUndecorated(true); //remove a barra superior e as bordas padrão da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

        terminal.setText(lore);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.WHITE);
        terminal.setFont(new Font("Cinzel", Font.BOLD, 30));
        terminal.setEditable(false);
        terminal.setLineWrap(false);
        terminal.setBounds(70, 70, 1100, 650);

        mago.setBounds(170, 200, 1000, 130);
        mago.setFont(new Font("Cinzel", Font.BOLD, 30));
        mago.setOpaque(false);
        mago.setContentAreaFilled(false);
        mago.setBorderPainted(false);
        mago.setFocusPainted(false);
        mago.setForeground(Color.WHITE);
        mago.setHorizontalAlignment(SwingConstants.LEFT);
        mago.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                mago.setForeground(Color.GRAY);
                mago.setFont(new Font("Cinzel", Font.BOLD, 35));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mago.setForeground(Color.WHITE);
                mago.setFont(new Font("Cinzel", Font.BOLD, 30));
            }});

        paladino.setBounds(170, 350, 1000, 130);
        paladino.setFont(new Font("Cinzel", Font.BOLD, 30));
        paladino.setOpaque(false);
        paladino.setContentAreaFilled(false);
        paladino.setBorderPainted(false);
        paladino.setFocusPainted(false);
        paladino.setForeground(Color.WHITE);
        paladino.setHorizontalAlignment(SwingConstants.LEFT);
        paladino.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                paladino.setForeground(Color.GRAY);
                paladino.setFont(new Font("Cinzel", Font.BOLD, 35));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                paladino.setForeground(Color.WHITE);
                paladino.setFont(new Font("Cinzel", Font.BOLD, 30));
            }});

        ladino.setBounds(170, 500, 1000, 130);
        ladino.setFont(new Font("Cinzel", Font.BOLD, 30));
        ladino.setOpaque(false);
        ladino.setContentAreaFilled(false);
        ladino.setBorderPainted(false);
        ladino.setFocusPainted(false);
        ladino.setForeground(Color.WHITE);
        ladino.setHorizontalAlignment(SwingConstants.LEFT);
        ladino.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                ladino.setForeground(Color.GRAY);
                ladino.setFont(new Font("Cinzel", Font.BOLD, 35));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ladino.setForeground(Color.WHITE);
                ladino.setFont(new Font("Cinzel", Font.BOLD, 30));
            }});

        janela.add(mago);
        janela.add(paladino);
        janela.add(ladino);
        janela.add(terminal);
        janela.setVisible(true);
    }
}