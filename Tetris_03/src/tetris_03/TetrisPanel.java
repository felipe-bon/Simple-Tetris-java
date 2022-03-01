
package tetris_03;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class TetrisPanel extends JPanel implements ActionListener{
    
    final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    final int COMPRIMENTO_TELA = (int)size.getWidth();
    final int ALTURA_TELA = (int)size.getHeight();
    int UNIDADE_DE_MEDIDA = (ALTURA_TELA-(ALTURA_TELA/12))/22;//unidade de medida resposiva ao tamanho da tela
    int altura_quadro = UNIDADE_DE_MEDIDA*20;
    int largura_quadro = UNIDADE_DE_MEDIDA*10;

    int pontos;
    int linhas;
    int level;
    Board tabuleiro;
    Block bloco[];
    
    boolean rodando = false;
    int atual;
    int proximo;
    int contador[];
    int delay = 150;
    Timer timer;
    Random r;
    
    TetrisPanel(){
        this.setPreferredSize(new Dimension(COMPRIMENTO_TELA, ALTURA_TELA));        
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new adaptador_tecla());
        
        this.level = 0;
        this.pontos = 0;
        this.linhas = 0;
       
        r = new Random();
        atual = r.nextInt(1,8);
        proximo = r.nextInt(1,8);
        tabuleiro = new Board();
        
        contador = new int[8];
        
        for(int i = 1; i < 8; i++)  
            contador[i] = 0;
        
        bloco = new Block[8];
        
        bloco[1] = new Block_I();
        bloco[2] = new Block_T();
        bloco[3] = new Block_L();
        bloco[4] = new Block_Li();
        bloco[5] = new Block_S();
        bloco[6] = new Block_Z();
        bloco[7] = new Block_Cubo();
        bloco[0] = bloco[proximo];
        r = new Random();        
        start_game();
    }

    void start_game(){
        rodando = true;
        timer = new Timer(delay,this);
        timer.start();     
    }
    
    @Override
    public void paintComponent(Graphics g){
     
        super.paintComponent(g);
        desenha_tabuleiro(g);
    }
    
    void desenha_tabuleiro(Graphics g){
        
        g.setColor(Color.LIGHT_GRAY);
        g.setFont( new Font("Serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("PROXIMA: ",COMPRIMENTO_TELA/2+largura_quadro, 4*UNIDADE_DE_MEDIDA);
           
        // desenha a caixa de proximo bloco uma vez
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(i == 0 || i == 6 || j == 0 || j == 6){
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*UNIDADE_DE_MEDIDA+1), 4*UNIDADE_DE_MEDIDA+i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    

                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*UNIDADE_DE_MEDIDA+1), 4*UNIDADE_DE_MEDIDA+i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    

                    g.setColor(new Color(79,79,79));
                    g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*UNIDADE_DE_MEDIDA+5), 4*UNIDADE_DE_MEDIDA+i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                                 
                }
            }
        }
        
        if(rodando){ 
            
            //desenha o proximo bloco na caixa de proximo bloco
            for(int i = 1; i < bloco[0].get_Max_T()+1; i++){
                for(int j = 1; j < bloco[0].get_Max_T()+1; j++){
                    if(bloco[0].bloco_formato[i-1][j-1] != 0){
                        g.setColor(bloco[0].get_Color_E());
                        g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*UNIDADE_DE_MEDIDA+1+UNIDADE_DE_MEDIDA), 5*UNIDADE_DE_MEDIDA+i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    

                        g.setColor(bloco[0].get_Color_P());
                        g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*UNIDADE_DE_MEDIDA+1+UNIDADE_DE_MEDIDA), 5*UNIDADE_DE_MEDIDA+i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    

                        g.setColor(bloco[0].get_Color_C());
                        g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*UNIDADE_DE_MEDIDA+5+UNIDADE_DE_MEDIDA), 5*UNIDADE_DE_MEDIDA+i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                                 
                    }
                }
            }    
            
            // desenha a malha quadriculada do fundo
            g.setColor(Color.darkGray);
            for(int i = 0; i <= 22; i++){
                if(i <= 10)
                    g.drawLine(COMPRIMENTO_TELA/2-largura_quadro/2+i*UNIDADE_DE_MEDIDA,UNIDADE_DE_MEDIDA , COMPRIMENTO_TELA/2+i*UNIDADE_DE_MEDIDA-largura_quadro/2, altura_quadro+UNIDADE_DE_MEDIDA);
                g.drawLine(COMPRIMENTO_TELA/2+largura_quadro/2, i*UNIDADE_DE_MEDIDA, COMPRIMENTO_TELA/2-largura_quadro/2, i*UNIDADE_DE_MEDIDA);   
            }     
            
            // desenha o bloco atual caindo
            for(int ib = 0; ib < bloco[atual].Max_T; ib++){
                for(int jb = 0; jb < bloco[atual].Max_T; jb++){
                    if(bloco[atual].variacoes[bloco[atual].variacao][ib][jb] != 0){
                       
                        g.setColor(bloco[atual].cor_E);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+((jb+tabuleiro.inicioX)*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA+(ib+tabuleiro.inicioY)*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
        
                        g.setColor(bloco[atual].cor_C);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+((jb+tabuleiro.inicioX)*UNIDADE_DE_MEDIDA+1-UNIDADE_DE_MEDIDA), UNIDADE_DE_MEDIDA+(ib+tabuleiro.inicioY)*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(bloco[atual].cor_P);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+((jb+tabuleiro.inicioX)*UNIDADE_DE_MEDIDA+5-UNIDADE_DE_MEDIDA), UNIDADE_DE_MEDIDA+(ib+tabuleiro.inicioY)*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                                            
                    }            
                }
            }
            
            // desenha o tabuleiro com as peças em que estão inseridas nele 
            for(int i = 0; i < 22; i++){
                for(int j = 0; j < 12; j++){
                    if(tabuleiro.tabuleiro[i][j] > 0){                        
                        g.setColor(bloco[tabuleiro.tabuleiro[i][j]].cor_E);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
                        //System.out.println(bloco[tabuleiro.tabuleiro[i][j]].cor_C);
                        
                        g.setColor(bloco[tabuleiro.tabuleiro[i][j]].cor_C);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        //System.out.println(bloco[tabuleiro.tabuleiro[i][j]].cor_E);
                        
                        g.setColor(bloco[tabuleiro.tabuleiro[i][j]].cor_P);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
                        //System.out.println(bloco[tabuleiro.tabuleiro[i][j]].cor_P);
     
                        
                    }
                    // desenha as bordas do tabuleiro
                    else if(tabuleiro.tabuleiro[i][j] < 0){
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
                        //System.out.println(bloco[tabuleiro.tabuleiro[i][j]].cor_C);
                        
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        //System.out.println(bloco[tabuleiro.tabuleiro[i][j]].cor_E);
                        
                        g.setColor(new Color(79, 79, 79));
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
                        //System.out.println(bloco[tabuleiro.tabuleiro[i][j]].cor_P);
     
                    }
                    System.out.print(tabuleiro.tabuleiro[i][j]+" ");
                }
                System.out.print("\n");
            }
             System.out.print("\n\n\n");   
        }
        else
            fim_jogo(g);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        tabuleiro.atualiza_tabuleiro();
        if(rodando) {
            if(tabuleiro.insere_bloco(bloco[atual])){
                
                atual = proximo;
                proximo = r.nextInt(1,8);
                switch (proximo){
                    
                    case 1:
                    bloco[0] = new Block_I();    
                    break;
                    
                    case 2:
                    bloco[0] = new Block_T();    
                    break;
                    
                    case 3:
                    bloco[0] = new Block_L();    
                    break;
                    
                    case 4:
                    bloco[0] = new Block_Li();    
                    break;
                    
                    case 5:
                    bloco[0] = new Block_S();    
                    break;
                    
                    case 6:
                    bloco[0] = new Block_Z();    
                    break;
                    
                    case 7:
                    bloco[0] = new Block_Cubo();    
                    break;         
                    
                }              
                contador[atual]++;                               
            }
            //checkApple();
            //checkCollision();
        } 
        repaint();
    }
    
    public void fim_jogo(Graphics g){
        
    }
    
    public class adaptador_tecla extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){

            
            case KeyEvent.VK_SPACE:
                tabuleiro.movimenta_bloco(bloco[atual], 1);
                
            break;

            
            case KeyEvent.VK_LEFT:
                System.out.println("ESQUERDA");
                tabuleiro.movimenta_bloco(bloco[atual], 2);
               
            break;

            
            case KeyEvent.VK_RIGHT:
                System.out.println("DIREITA");               
                tabuleiro.movimenta_bloco(bloco[atual],3);
                
            break; 

            
            case KeyEvent.VK_UP:
                System.out.println("CIMA");                
                tabuleiro.movimenta_bloco(bloco[atual], 4);
                
            break;

            
            case KeyEvent.VK_DOWN:
                System.out.println("BAIXO");
                tabuleiro.movimenta_bloco(bloco[atual], 5);
                      
            break;
            }
        }
    }    
}