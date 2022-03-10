
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class TetrisPanel extends JPanel implements ActionListener{
    
    final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    final int COMPRIMENTO_TELA = (int)size.getWidth();
    final int ALTURA_TELA = (int)size.getHeight();    
    private final int UNIDADE_DE_MEDIDA = (ALTURA_TELA-(ALTURA_TELA/12))/22;//unidade de medida resposiva ao tamanho da tela
    
    
    private final int altura_quadro = UNIDADE_DE_MEDIDA*20;
    private final int largura_quadro = UNIDADE_DE_MEDIDA*10;

    private int pontos;
    private int linhas;
    private int level = 1;
    private final Board tabuleiro;
    private final Block bloco[]; 
    private final Block miniatura[];
    private final String nome;
    private Player jogador;
    private ArrayList<Player> recordes;
    private boolean rodando = false;
    private int atual;
    private int proximo;
    private final int contador[];
    private final int inicial_delay;
    private int delay;
    private Timer timer;
    private Random r;
    JFrame fr;
    private final JButton novo_jogo;    
        
    TetrisPanel(JFrame fr, int dificuldade, String Nome){
        
        this.setPreferredSize(size);        
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new adaptador_tecla());      
        
        this.fr = fr;
        this.nome = Nome;
        
        inicial_delay = 200 - dificuldade*2;
        this.level = dificuldade;
        this.pontos = 0;
        this.linhas = 0;
        
        novo_jogo = new JButton("NOVO JOGO");
                          
        
        novo_jogo.addActionListener(this);
        this.add(novo_jogo);
        novo_jogo.setVisible(false);
  
        
        r = new Random();
        
        atual = r.nextInt(7)+1;
        proximo = r.nextInt(7)+1;
           
        while(proximo == atual)
            proximo = r.nextInt(7)+1;

        tabuleiro = new Board();
 
        contador = new int[8];
        for(int i = 1; i < 8; i++)  
            contador[i] = 0;
        contador[atual]++;
        
        bloco = new Block[8];
        
        bloco[1] = new Block_I();
        bloco[2] = new Block_T();
        bloco[3] = new Block_L();
        bloco[4] = new Block_Li();
        bloco[5] = new Block_S();
        bloco[6] = new Block_Z();
        bloco[7] = new Block_Cubo();
        bloco[0] = bloco[proximo];
        
        miniatura = new Block[8];
        
        miniatura[1] = new Block_I();
        miniatura[2] = new Block_T();
        miniatura[3] = new Block_L();
        miniatura[4] = new Block_Li();
        miniatura[5] = new Block_S();
        miniatura[6] = new Block_Z();
        miniatura[7] = new Block_Cubo();       
        
        start_game();
    } 
    
    private void start_game(){
        rodando = true;
        timer = new Timer(delay,this);
        timer.start();     
    }
    
    @Override
    protected void paintComponent(Graphics g){
     
        super.paintComponent(g);
        desenha_tabuleiro(g);     
    }
    
    private void quadro_de_pontos(){
        
        jogador = new Player(this.nome, pontos);   
        String l;
        int i = 0;     
        recordes = new ArrayList();
        BufferedWriter escritor = null; 
        
        // escreve o jogador atual e seus pontos no arquivo recordes
        try{
            escritor = new BufferedWriter(new FileWriter(new File("recordes.txt"), true));
            escritor.write(jogador.get_nome()+";"+jogador.get_pontos());
            escritor.newLine();
            escritor.flush();
            escritor.close();// mudar
        }catch(IOException e){
            e.printStackTrace();
        }
        
        // le o arquivo recordes e salva no arrayList os jogadores em ordem decrescente de pontos
        BufferedReader arquivo = null;//Objetoleitor
        try{
            File f = new File("recordes.txt");
            FileReader fr = new FileReader(f);
            arquivo = new BufferedReader(fr);
            while((l = arquivo.readLine()) != null){
                String[] dado = l.split(";");                
                jogador = new Player(dado[0], Integer.parseInt(dado[1]));// sort aqui
                while(i < recordes.size() && recordes.get(i).get_pontos() > jogador.get_pontos())
                    i++;
                recordes.add(i, jogador);
                i = 0;
            }
            arquivo.close();
        
        }catch(java.io.IOException e){
            System.out.println("File error"+ e.toString());
        }
        
//        for(i = 0; i < recordes.size(); i++){
//            jogador = recordes.get(i);               
//        }
        
    }   
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        // esse jeito de contar os pontos bonificaa pontuação
        // quando se tem mais de uma linha completada em uma jogada        
        int temp = tabuleiro.apaga_linha();
        int level_ant = level;
        level = linhas/5;
        //passou de level
        if(level_ant != level){
            delay = inicial_delay - level*2;
            timer.setDelay(delay);
        }
            
        linhas = temp+linhas;        
        pontos = pontos + 100*temp*temp;// bonificação  
        
        if(rodando){
            if(tabuleiro.insere_bloco(bloco[atual])){
     
                atual = proximo;               
                proximo = r.nextInt(7)+1;
                
                // da new no bloco que caíra agora
                switch (atual){
                    
                    case 1:
                    bloco[atual] = new Block_I();    
                    break;
                    
                    case 2:
                    bloco[atual] = new Block_T();    
                    break;
                    
                    case 3:
                    bloco[atual] = new Block_L();    
                    break;
                    
                    case 4:
                    bloco[atual] = new Block_Li();    
                    break;
                    
                    case 5:
                    bloco[atual] = new Block_S();    
                    break;
                    
                    case 6:
                    bloco[atual] = new Block_Z();    
                    break;
                    
                    case 7:
                    bloco[atual] = new Block_Cubo();    
                    break;         
                   
                    
                }
                contador[atual]++;
                // da new no bloco que caíra em seguida do atual
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
            }            
        }
        else{   
      
            novo_jogo.setVisible(!rodando);
            if(e.getSource() == novo_jogo){  
                fr.setVisible(false);
                fr = new TetrisFrame();
            }
        }
        if(!tabuleiro.atualiza_tabuleiro()){
            rodando = false;           
        }   
        repaint();       
    }
    
    private void desenha_tabuleiro(Graphics g){
        
        // escreve as informações na tela
        g.setColor(Color.LIGHT_GRAY);
        g.setFont( new Font("Asai Analogue", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("NEXT: ",COMPRIMENTO_TELA/2+largura_quadro, UNIDADE_DE_MEDIDA);
        g.drawString("SCORE: "+pontos,COMPRIMENTO_TELA/3-(metrics.stringWidth("SCORE"+pontos)/2)-5*UNIDADE_DE_MEDIDA, ALTURA_TELA-2*UNIDADE_DE_MEDIDA);
        g.drawString("LINES: "+linhas,COMPRIMENTO_TELA/3-(metrics.stringWidth("LINES: "+linhas)/2)-5*UNIDADE_DE_MEDIDA, ALTURA_TELA-3*UNIDADE_DE_MEDIDA);
        g.drawString("LEVEL: "+level,COMPRIMENTO_TELA/3-(metrics.stringWidth("LEVEL: "+level)/2)-5*UNIDADE_DE_MEDIDA, ALTURA_TELA-4*UNIDADE_DE_MEDIDA);
        
        int nUnidade = UNIDADE_DE_MEDIDA/3;//nova unidade de medida para desenhar as miniaturas dos blocos;

        // desenha a caixa de proximo bloco        
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(i == 0 || i == 6 || j == 0 || j == 6){
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*nUnidade+1)-3*nUnidade, 4*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*nUnidade+1)-3*nUnidade, 4*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    

                    g.setColor(new Color(79,79,79));
                    g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*nUnidade+5)-3*nUnidade, 4*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                }
            }
        }
        
        // desenha as caixas de cada tipo de bloco   
        for(int k = 0; k < 7; k++){
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 7; j++){
                    if(i == 0 || i == 5 || j == 0 || j == 6){
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro+(j*nUnidade+1)-3*nUnidade, k*8*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro+(j*nUnidade+1)-3*nUnidade, k*8*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    

                        g.setColor(new Color(79,79,79));
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro+(j*nUnidade+5)-3*nUnidade, k*8*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                    }
                }
            }            
        }
        
        //desenha as miniaturas de cada bloco para fazer a contagem
        for(int k = 1; k < 8; k++){
            for(int i = 1; i < miniatura[k].get_Max_T()+1; i++){
                for(int j = 1; j < miniatura[k].get_Max_T()+1; j++){
                    if(miniatura[k].get_bloco_formato()[i-1][j-1] != 0){
                        g.setColor(miniatura[k].get_Color_E());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro+(j*nUnidade+1)-2*nUnidade, (k-1)*8*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                        g.setColor(miniatura[k].get_Color_C());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro+(j*nUnidade+1)-2*nUnidade, (k-1)*8*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    
                        
                        g.setColor(miniatura[k].get_Color_P());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro+(j*nUnidade+1)-2*nUnidade, (k-1)*8*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                    }  
                }
            }
            g.drawString(""+contador[k],COMPRIMENTO_TELA/2-largura_quadro, k*8*nUnidade);
        }
        
        if(rodando){ 
            
            //desenha o proximo bloco na caixa de proximo bloco
            for(int i = 1; i < bloco[0].get_Max_T()+1; i++){
                for(int j = 1; j < bloco[0].get_Max_T()+1; j++){
                    if(bloco[0].get_bloco_formato()[i-1][j-1] != 0){
                        g.setColor(bloco[0].get_Color_E());
                        g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*nUnidade+1+nUnidade)-3*nUnidade, 5*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                        g.setColor(bloco[0].get_Color_C());
                        g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*nUnidade+1+nUnidade)-3*nUnidade, 5*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    
                        
                        g.setColor(bloco[0].get_Color_P());
                        g.fillRect(COMPRIMENTO_TELA/2+largura_quadro+(j*nUnidade+5+nUnidade)-3*nUnidade, 5*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
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
            for(int ib = 0; ib < bloco[atual].get_Max_T(); ib++){
                for(int jb = 0; jb < bloco[atual].get_Max_T(); jb++){
                    if(bloco[atual].get_bloco_formato()[ib][jb] != 0){
                       
                        g.setColor(bloco[atual].get_Color_E());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+((jb+tabuleiro.get_inicioX())*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA+(ib+tabuleiro.get_inicioY()-1)*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
        
                        g.setColor(bloco[atual].get_Color_C());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+((jb+tabuleiro.get_inicioX())*UNIDADE_DE_MEDIDA+1-UNIDADE_DE_MEDIDA), UNIDADE_DE_MEDIDA+(ib+tabuleiro.get_inicioY()-1)*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(bloco[atual].get_Color_P());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+((jb+tabuleiro.get_inicioX())*UNIDADE_DE_MEDIDA+5-UNIDADE_DE_MEDIDA), UNIDADE_DE_MEDIDA+(ib+tabuleiro.get_inicioY()-1)*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                                            
                    }            
                }
            }
            
            // ver se ta POO correto
            // desenha o tabuleiro com as peças em que estão inseridas nele 
            for(int i = 0; i < 22; i++){
                for(int j = 0; j < 12; j++){
                    if(tabuleiro.get_tabuleiro()[i][j] > 0){                        
                        g.setColor(bloco[tabuleiro.get_tabuleiro()[i][j]].get_Color_E());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
                        
                        g.setColor(bloco[tabuleiro.get_tabuleiro()[i][j]].get_Color_C());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(bloco[tabuleiro.get_tabuleiro()[i][j]].get_Color_P());
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
     
                        
                    }
                    else if(tabuleiro.get_tabuleiro()[i][j] < 0){
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
                        
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(new Color(79, 79, 79));
                        g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
                    }
                }
            }
        }
        else
            fim_jogo(g);
    }
    
    private void fim_jogo(Graphics g){               
        
        if(recordes == null){
            quadro_de_pontos();
        }
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,COMPRIMENTO_TELA,ALTURA_TELA);
  
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.setFont( new Font("Asai Analogue", Font.BOLD, UNIDADE_DE_MEDIDA));
        g.setColor(Color.WHITE);  
        
        g.drawString("RECORDES",COMPRIMENTO_TELA/2+largura_quadro-(metrics.stringWidth(jogador.get_nome())/2)-UNIDADE_DE_MEDIDA*2, 2*UNIDADE_DE_MEDIDA);
        
        for(int i = 0; i < 10 && i < recordes.size(); i++){
            jogador = recordes.get(i); 
            g.drawString(jogador.get_nome(),COMPRIMENTO_TELA/2+largura_quadro-UNIDADE_DE_MEDIDA*3, (i+3)*UNIDADE_DE_MEDIDA);
            g.drawString(""+jogador.get_pontos(),COMPRIMENTO_TELA/2+largura_quadro+UNIDADE_DE_MEDIDA*8-(metrics.stringWidth(""+jogador.get_pontos())/2)-UNIDADE_DE_MEDIDA*5, (i+3)*UNIDADE_DE_MEDIDA);
        
        }
        
        
        for(int i = 0; i < 22; i++){
            for(int j = 0; j < 12; j++){               
                g.setColor(Color.DARK_GRAY);
                g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    

                g.setColor(new Color(79, 79, 79));
                g.fillRect(COMPRIMENTO_TELA/2-largura_quadro/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
            }
        }
        
        g.setFont( new Font("Asai Analogue", Font.BOLD, UNIDADE_DE_MEDIDA));
        g.setColor(Color.WHITE);
        g.setColor(Color.BLACK);         
        g.drawString("SCORE: "+pontos,COMPRIMENTO_TELA/2- (metrics.stringWidth("SCORE"+pontos)/2), ALTURA_TELA-3*UNIDADE_DE_MEDIDA);
        g.drawString("LINES: "+linhas,COMPRIMENTO_TELA/2- (metrics.stringWidth("LINES: "+linhas)/2), ALTURA_TELA-4*UNIDADE_DE_MEDIDA);
        g.drawString("LEVEL: "+level,COMPRIMENTO_TELA/2- (metrics.stringWidth("LEVEL: "+level)/2), ALTURA_TELA-5*UNIDADE_DE_MEDIDA);                     
        g.setFont( new Font("Asai Analogue", Font.BOLD, UNIDADE_DE_MEDIDA*2));
        g.drawString("GAME OVER",COMPRIMENTO_TELA/2- (metrics.stringWidth("GAME OVER")), ALTURA_TELA/2);
        
    }
    
    public class adaptador_tecla extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){

            
            case KeyEvent.VK_SPACE:                              
                tabuleiro.movimenta_bloco(bloco[atual], 1);                
                
            break;

            
            case KeyEvent.VK_LEFT:
                tabuleiro.movimenta_bloco(bloco[atual], 2);
               
            break;

            
            case KeyEvent.VK_RIGHT:
                tabuleiro.movimenta_bloco(bloco[atual],3);
                
            break; 

            
            case KeyEvent.VK_UP:
                tabuleiro.movimenta_bloco(bloco[atual], 4);
                
            break;

            
            case KeyEvent.VK_DOWN:                                           
                tabuleiro.movimenta_bloco(bloco[atual], 5);
            break;
            }
        }
    }    
}