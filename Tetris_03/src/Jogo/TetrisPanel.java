package Jogo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
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
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TetrisPanel extends JPanel implements ActionListener{
    private final Dimension SIZE;
    private final int COMPRIMENTO_TELA;
    private final int ALTURA_TELA;    
    private final int UNIDADE_DE_MEDIDA;//unidade de medida resposiva ao tamanho da tela    
    private final int ALTURA_QUADRO;
    private final int LARGURA_QUADRO;

    private Font fonte;
    private int pontos;
    private int linhas;
    private int level;
    private int conta_bloco;
    private final Board tabuleiro;
    private final Block miniatura[];
    private final HashMap<Block, Integer> contagem;
    private final String nome;
    private int opcao;
    private Player jogador;
    private ArrayList<Player> recordes;
    private boolean rodando;
    private final boolean modo_cegas, modo_petrifica;
    private static Block atual;
    private static Block proximo;
    private final int inicial_delay;
    private int delay;
    private Timer timer;
    private Random r;
    JFrame fr;
    private final JButton novo_jogo;    
        
    TetrisPanel(JFrame frame, int opcao, boolean cegas, boolean petrifica, int dificuldade, String Nome){
        
        this.opcao = opcao;
        switch (opcao){
            case 1:
                this.tabuleiro = new Board();// padrão
                break;
            
            case 2:
                this.tabuleiro = new Board(10,15);// reduzido
                break;
            
            case 3:
                this.tabuleiro = new Board(15,15);// quadrado    
                break;
            
            default:
                this.tabuleiro = new Board();// padrão   
        }
        
        this.SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        this.COMPRIMENTO_TELA  = (int)SIZE.getWidth();
        this.ALTURA_TELA = (int)SIZE.getHeight();
        this.UNIDADE_DE_MEDIDA = (ALTURA_TELA-(ALTURA_TELA/(tabuleiro.get_largura()+2)))/(tabuleiro.get_altura()+2);
        this.ALTURA_QUADRO = UNIDADE_DE_MEDIDA*tabuleiro.get_altura();
        this.LARGURA_QUADRO = UNIDADE_DE_MEDIDA*tabuleiro.get_largura();       
        this.setPreferredSize(SIZE);        
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new adaptador_tecla()); 
        this.fonte = null;
        this.modo_cegas = cegas;
        this.modo_petrifica = petrifica;
        this.rodando = false;
        this.fr = frame;
        this.nome = Nome;        
        this.inicial_delay = 200 - dificuldade*2;
        this.level = dificuldade;
        this.pontos = 0;
        this.linhas = 0; 
        this.conta_bloco = 0;
        this.novo_jogo = new JButton("NOVO JOGO");
        this.novo_jogo.addActionListener(this);
        this.add(novo_jogo);
        this.novo_jogo.setVisible(false);   
        this.r = new Random();
        this.atual = Block.get_aleatorio();
        this.proximo = Block.get_aleatorio();
        this.contagem = new HashMap<Block, Integer>();
        this.miniatura = Block.get_representantes(); 
        
        try {
            fonte = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("Jogo/Asai-Analogue.ttf"));
        } catch (FontFormatException | IOException ex) { 
            Logger.getLogger(TetrisPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fonte = fonte.deriveFont(Font.BOLD, UNIDADE_DE_MEDIDA*2);
        
        for(int i = 0; i < miniatura.length; i++)
            contagem.put(miniatura[i], 0);
        
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
            
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                escritor.close();
            }
            catch(Exception e){
                
            }
        }
        
        // le o arquivo recordes e salva no arrayList os jogadores em ordem decrescente de pontos
        BufferedReader arquivo = null;//Objeto leitor
        try{
            File f = new File("recordes.txt");
            FileReader fr = new FileReader(f);
            arquivo = new BufferedReader(fr);
            while((l = arquivo.readLine()) != null){
                String[] dado = l.split(";");                
                jogador = new Player(dado[0], Integer.parseInt(dado[1]));
                while(i < recordes.size() && recordes.get(i).get_pontos() > jogador.get_pontos())
                    i++;
                recordes.add(i, jogador);
                i = 0;
            }
            arquivo.close();
        
        }catch(java.io.IOException e){
            System.out.println("File error"+ e.toString());
        }     
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
            this.setBackground(new Color(r.nextInt(50),r.nextInt(50),r.nextInt(50)));
            delay = inicial_delay - level*2;
            timer.setDelay(delay);
            if(modo_petrifica)
                tabuleiro.despetrifica();
        }
        linhas = temp+linhas;        
        pontos = pontos + 100*temp*temp;// bonificação  
        
        if(rodando){
            if(tabuleiro.insere_bloco(atual)){
                
            if(modo_petrifica){
                conta_bloco++;
                if(conta_bloco == tabuleiro.get_largura()){
                    tabuleiro.petrifica();
                    conta_bloco = 0;
                }
            }
                int v = contagem.get(atual);
                contagem.put(atual, v + 1);

                atual = proximo;
                proximo = Block.get_aleatorio();                                                   
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
        // desenha as informações na tela
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(this.fonte);
        
        int nUnidade = (ALTURA_TELA-(ALTURA_TELA/(tabuleiro.get_largura()+2)))/(52);//nova unidade de medida para desenhar as miniaturas dos blocos;

        int lugar = 7*UNIDADE_DE_MEDIDA;
        int lugar2 = 3*nUnidade;
        if(this.opcao == 3){
            lugar = 5*UNIDADE_DE_MEDIDA;
            lugar2 = 12*nUnidade;
            fonte = fonte.deriveFont(Font.BOLD, UNIDADE_DE_MEDIDA*3/2);
        }
        
        FontMetrics metrics = getFontMetrics(g.getFont());
        if(!modo_cegas)
            g.drawString("NEXT: ",COMPRIMENTO_TELA/2+LARGURA_QUADRO-lugar2, UNIDADE_DE_MEDIDA);
        
        g.drawString("SCORE:"+pontos,COMPRIMENTO_TELA-(metrics.stringWidth("SCORE"+pontos)/3)-lugar, ALTURA_TELA-4*UNIDADE_DE_MEDIDA);
        g.drawString("LINES:"+linhas,COMPRIMENTO_TELA-(metrics.stringWidth("LINES:"+linhas)/3)-lugar, ALTURA_TELA-6*UNIDADE_DE_MEDIDA);
        g.drawString(" LEVEL:"+level,COMPRIMENTO_TELA-(metrics.stringWidth("LEVEL:"+level)/3)-lugar, ALTURA_TELA-8*UNIDADE_DE_MEDIDA);
        
        
        // desenha a caixa de proximo bloco  
        if(!modo_cegas){
        
            for(int i = 0; i < 7; i++){
                for(int j = 0; j < 7; j++){
                    if(i == 0 || i == 6 || j == 0 || j == 6){
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2+LARGURA_QUADRO+(j*nUnidade+1)-lugar2, 4*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2+LARGURA_QUADRO+(j*nUnidade+1)-lugar2, 4*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    

                        g.setColor(new Color(79,79,79));
                        g.fillRect(COMPRIMENTO_TELA/2+LARGURA_QUADRO+(j*nUnidade+5)-lugar2, 4*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                    }
                }
            }
        }
        // desenha as caixas de cada tipo de bloco   
        for(int k = 0; k < 7; k++)
            for(int i = 0; i < 6; i++)
                for(int j = 0; j < 7; j++)
                    if(i == 0 || i == 5 || j == 0 || j == 6){
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/3-LARGURA_QUADRO/2+(j*nUnidade+1)-3*nUnidade, 3*nUnidade+k*7*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/3-LARGURA_QUADRO/2+(j*nUnidade+1)-3*nUnidade, 3*nUnidade+k*7*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    

                        g.setColor(new Color(79,79,79));
                        g.fillRect(COMPRIMENTO_TELA/3-LARGURA_QUADRO/2+(j*nUnidade+5)-3*nUnidade, 3*nUnidade+k*7*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                    }
                
        //desenha as miniaturas de cada bloco para fazer a contagem
        for(int k = 0; k < 7; k++){
            for(int i = 1; i < miniatura[k].get_max_tamanho()+1; i++)
                for(int j = 1; j < miniatura[k].get_max_tamanho()+1; j++)
                    if(miniatura[k].get_bloco_formato()[i-1][j-1] != 0){
                        g.setColor(miniatura[k].get_cor_escura());
                        g.fillRect(COMPRIMENTO_TELA/3-LARGURA_QUADRO/2+(j*nUnidade+1)-2*nUnidade, 3*nUnidade+k*7*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                        g.setColor(miniatura[k].get_cor_clara());
                        g.fillRect(COMPRIMENTO_TELA/3-LARGURA_QUADRO/2+(j*nUnidade+1)-2*nUnidade, 3*nUnidade+k*7*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    
                        
                        g.setColor(miniatura[k].get_cor_principal());
                        g.fillRect(COMPRIMENTO_TELA/3-LARGURA_QUADRO/2+(j*nUnidade+5)-2*nUnidade, 3*nUnidade+k*7*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                    }  
            
            //COMPRIMENTO_TELA/2-LARGURA_QUADRO+nUnidade*2
            g.setFont(this.fonte);
            this.fonte.deriveFont(UNIDADE_DE_MEDIDA);
            if(this.opcao == 3)
                lugar2 = nUnidade;
            g.drawString(""+contagem.get(miniatura[k]),4*lugar2, (k+1)*7*nUnidade); 
        }
        
        if(opcao == 3)
            lugar2 = 12*nUnidade;
        if(rodando){ 
            if(!modo_cegas){
                //desenha o proximo bloco na caixa de proximo bloco
                for(int i = 1; i < proximo.get_max_tamanho()+1; i++)
                    for(int j = 1; j < proximo.get_max_tamanho()+1; j++){
                        if(proximo.get_bloco_formato()[i-1][j-1] != 0){
                            g.setColor(proximo.get_cor_escura());
                            g.fillRect(COMPRIMENTO_TELA/2+LARGURA_QUADRO+(j*nUnidade+1+nUnidade)-lugar2, 5*nUnidade+i*nUnidade, nUnidade, nUnidade);    

                            g.setColor(proximo.get_cor_clara());
                            g.fillRect(COMPRIMENTO_TELA/2+LARGURA_QUADRO+(j*nUnidade+1+nUnidade)-lugar2, 5*nUnidade+i*nUnidade, nUnidade-5, nUnidade-5);    

                            g.setColor(proximo.get_cor_principal());
                            g.fillRect(COMPRIMENTO_TELA/2+LARGURA_QUADRO+(j*nUnidade+5+nUnidade)-lugar2, 5*nUnidade+i*nUnidade+5, nUnidade-9, nUnidade-10);                                 
                        }
                    }    
            }
            
            // desenha a malha quadriculada do fundo
            g.setColor(Color.darkGray);
            for(int i = 0; i <= tabuleiro.get_altura(); i++){
                if(i <= tabuleiro.get_largura())
                    g.drawLine(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+i*UNIDADE_DE_MEDIDA,UNIDADE_DE_MEDIDA , COMPRIMENTO_TELA/2+i*UNIDADE_DE_MEDIDA-LARGURA_QUADRO/2, ALTURA_QUADRO+UNIDADE_DE_MEDIDA);
                g.drawLine(COMPRIMENTO_TELA/2+LARGURA_QUADRO/2, i*UNIDADE_DE_MEDIDA, COMPRIMENTO_TELA/2-LARGURA_QUADRO/2, i*UNIDADE_DE_MEDIDA);   
            }     
            
            // desenha o bloco atual caindo
            for(int ib = 0; ib < atual.get_max_tamanho(); ib++)
                for(int jb = 0; jb < atual.get_max_tamanho(); jb++)
                    if(atual.get_bloco_formato()[ib][jb] != 0){
                       
                        g.setColor(atual.get_cor_escura());
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+((jb+tabuleiro.get_inicioX())*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA+(ib+tabuleiro.get_inicioY()-1)*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
        
                        g.setColor(atual.get_cor_clara());
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+((jb+tabuleiro.get_inicioX())*UNIDADE_DE_MEDIDA+1-UNIDADE_DE_MEDIDA), UNIDADE_DE_MEDIDA+(ib+tabuleiro.get_inicioY()-1)*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(atual.get_cor_principal());
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+((jb+tabuleiro.get_inicioX())*UNIDADE_DE_MEDIDA+5-UNIDADE_DE_MEDIDA), UNIDADE_DE_MEDIDA+(ib+tabuleiro.get_inicioY()-1)*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                                            
                    }            
            
            // desenha o tabuleiro com as peças em que estão inseridas nele 
            for(int i = 0; i < tabuleiro.get_altura()+2; i++){
                for(int j = 0; j < tabuleiro.get_largura()+2; j++){
                    if(tabuleiro.get_tabuleiro()[i][j] > 0){                        
                        g.setColor(Block.get_instancia(tabuleiro.get_tabuleiro()[i][j]).get_cor_escura());
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
                        
                        g.setColor(Block.get_instancia(tabuleiro.get_tabuleiro()[i][j]).get_cor_clara());
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(Block.get_instancia(tabuleiro.get_tabuleiro()[i][j]).get_cor_principal());
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                         
                    }
                    else if(tabuleiro.get_tabuleiro()[i][j] < 0){
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);    
                        
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    
                        
                        g.setColor(new Color(79, 79, 79));
                        g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
                    }
                }
            }
        }
        else
            fim_jogo(g);
    }
    
    private void fim_jogo(Graphics g){               
        if(recordes == null)
            quadro_de_pontos();
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,COMPRIMENTO_TELA,ALTURA_TELA);
  
        //nte = fonte.deriveFont(Font.BOLD, UNIDADE_DE_MEDIDA*2);
        g.setFont(new Font("SansSerif", Font.BOLD,UNIDADE_DE_MEDIDA));
        this.fonte.deriveFont(5);
        g.setColor(Color.WHITE);  
        FontMetrics metrics = getFontMetrics(g.getFont());

        g.drawString("RECORDES",COMPRIMENTO_TELA*3/4+UNIDADE_DE_MEDIDA, 2*UNIDADE_DE_MEDIDA);
        g.setFont(new Font("",Font.BOLD,UNIDADE_DE_MEDIDA/2));
        for(int i = 0; i < 10 && i < recordes.size(); i++){
            jogador = recordes.get(i); 
            g.drawString(jogador.get_nome(),COMPRIMENTO_TELA*3/4+UNIDADE_DE_MEDIDA-(metrics.stringWidth(jogador.get_nome())/2)+2*UNIDADE_DE_MEDIDA, (i+3)*UNIDADE_DE_MEDIDA);
            g.drawString(""+jogador.get_pontos(),COMPRIMENTO_TELA*3/4+UNIDADE_DE_MEDIDA-(metrics.stringWidth(""+jogador.get_pontos())/2)+5*UNIDADE_DE_MEDIDA, (i+3)*UNIDADE_DE_MEDIDA);
        
        }
        
        for(int i = 0; i < tabuleiro.get_altura()+2; i++)
            for(int j = 0; j < tabuleiro.get_largura()+2; j++){               
                g.setColor(Color.DARK_GRAY);
                g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA);
                
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+1)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA, UNIDADE_DE_MEDIDA-5, UNIDADE_DE_MEDIDA-5);    

                g.setColor(new Color(79, 79, 79));
                g.fillRect(COMPRIMENTO_TELA/2-LARGURA_QUADRO/2+(j*UNIDADE_DE_MEDIDA+5)-UNIDADE_DE_MEDIDA, i*UNIDADE_DE_MEDIDA+5, UNIDADE_DE_MEDIDA-9, UNIDADE_DE_MEDIDA-10);                        
            }
        
        g.setFont(this.fonte);
        this.fonte.deriveFont(UNIDADE_DE_MEDIDA);
        g.setColor(Color.BLACK);         
        g.drawString("SCORE: "+pontos,COMPRIMENTO_TELA/2- (metrics.stringWidth("SCORE"+pontos)/2), ALTURA_TELA-4*UNIDADE_DE_MEDIDA);
        g.drawString("LINES: "+linhas,COMPRIMENTO_TELA/2- (metrics.stringWidth("LINES: "+linhas)/2), ALTURA_TELA-5*UNIDADE_DE_MEDIDA);
        g.drawString(" LEVEL: "+level,COMPRIMENTO_TELA/2- (metrics.stringWidth("LEVEL: "+level)/2), ALTURA_TELA-6*UNIDADE_DE_MEDIDA);                     
        this.fonte.deriveFont(UNIDADE_DE_MEDIDA*2);
        g.drawString("GAME OVER",COMPRIMENTO_TELA/2-(metrics.stringWidth("GAME OVER")/2), ALTURA_TELA/3);   
    }
    
    public class adaptador_tecla extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_SPACE:                              
                    tabuleiro.movimenta_bloco(atual, 1);                
                    break;

                case KeyEvent.VK_LEFT:
                    tabuleiro.movimenta_bloco(atual, 2);
                    break;

                case KeyEvent.VK_RIGHT:
                    tabuleiro.movimenta_bloco(atual,3);
                    break; 

                case KeyEvent.VK_UP:
                    tabuleiro.movimenta_bloco(atual, 4); 
                    break;

                case KeyEvent.VK_DOWN:                                           
                    tabuleiro.movimenta_bloco(atual, 5);
                    break;

                case KeyEvent.VK_P:
                    if(timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                    break;
            }
        }
    }
}