
package tetris_03;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class TetrisFrame extends JFrame implements ActionListener{
    
    JFrame frame;
    JPanel titulo;
    JPanel button_panel;
    JLabel mensagem; 
    JLabel inserirNome;
    JTextField Nomejogador;
    
    JButton jogar;
    JRadioButton dificil; 
    JRadioButton medio;
    JRadioButton facil;
    ButtonGroup dificuldades;
    
    int dificuldade;
    String nome;

    TetrisFrame(){ 
        
        dificuldade = 1;
        
        titulo = new JPanel();
        frame = new JFrame();
        mensagem = new JLabel();
        titulo = new JPanel();
        jogar = new JButton("jogar");
        inserirNome = new JLabel("Nome: ");
        Nomejogador = new JTextField();
        dificil = new JRadioButton("dificil");
        dificil.addActionListener(this);
        medio = new JRadioButton("medio");
        medio.addActionListener(this);
        facil = new JRadioButton("facil");
        facil.addActionListener(this);
        
        
        dificuldades = new ButtonGroup();
        dificuldades.add(dificil);
        dificuldades.add(medio);
        dificuldades.add(facil);
        
        Nomejogador.setBounds(125, 150, 200, 50);          
        inserirNome.setBounds(50, 100, 150, 150);
        dificil.setBounds(50,250,70,30);            
        medio.setBounds(120,250,70,30);
        facil.setBounds(190,250,70,30);
        dificil.setBackground(Color.BLACK);   
        medio.setBackground(Color.BLACK);       
        facil.setBackground(Color.BLACK);
        jogar.setBounds(125,200,100,25);
        
        jogar.addActionListener(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);       
        frame.setVisible(true);
        
        mensagem.setBackground(Color.BLACK);
        mensagem.setForeground(Color.WHITE);
        mensagem.setFont(new Font("Asai Analogue", Font.BOLD, 50));
        mensagem.setHorizontalAlignment(JLabel.CENTER);
        mensagem.setText("TETRIS");
        mensagem.setOpaque(true);
        titulo.setLayout(new BorderLayout());
        titulo.setBounds(0, 0, 800, 100);
        
        titulo.add(mensagem);
        frame.add(dificil);
        frame.add(medio);
        frame.add(facil);

        frame.add(titulo,BorderLayout.NORTH);
        frame.add(inserirNome);
        frame.add(Nomejogador);
        frame.add(jogar);   
        
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
             
        if(e.getSource() == facil){
            this.dificuldade = 1;
        }
        
        if(e.getSource() == medio){
            this.dificuldade = 20;
        }
        
        if(e.getSource() == dificil){
            this.dificuldade = 40;
        }
        
        if(e.getSource() == jogar){
            
            this.nome = Nomejogador.getText();
            
            this.add(new TetrisPanel(this.dificuldade, this.nome)); 
            this.setTitle("Tetris");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
        }
        
    }
       
}
