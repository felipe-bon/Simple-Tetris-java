package Jogo;

public class Board {
    private final int INICIOY;
    private final int INICIOX;
    private final int LARGURA;
    private final int ALTURA;
    
    private int[][] tabuleiro;
    private int inicioY;
    private int inicioX;
    
    Board(){
       this(10,20);        
    }
  
    // board personalizado
    Board(int largura, int altura){
          
        this.ALTURA = altura;   //altura jogavel
        this.LARGURA = largura; //largura jogavel
        
        this.INICIOY = 0;
        this.INICIOX = largura % 2 == 0 ? (largura / 2) - 1 : (largura / 2);
        
        int altura_total = altura+2;
        int largura_total = largura+2;
        tabuleiro = new int[altura_total][largura_total];
        
        for(int i = 0; i < altura_total; i++){
            for(int j = 0; j < largura_total; j++){
                if(i == 0 || j == 0 || j == largura_total-1 || i == altura_total-1)
                    tabuleiro[i][j] = -1;      
                else
                    tabuleiro[i][j] = 0;
            }
        }                    
        inicioY = INICIOY;
        inicioX = INICIOX; 
    }

 
    protected int apaga_linha(){
        
        int i, j, verifica = 0, r = 4, apagadas = 0;
        int[] linhas = new int[4];
        
        for(i = ALTURA; i > 0; i--){
            for(j = 1; j < LARGURA+1; j++)
                if(tabuleiro[i][j] > 0)    
                    verifica++;               
            
            if(verifica == LARGURA){
                apagadas++;
                r--;
                linhas[r] = i;    
            }            
            verifica = 0;
        }       
        while(r != 4){
            for(i = linhas[r]; i > 1; i--)
                for(j = 1; j < LARGURA+1; j++)
                    tabuleiro[i][j] = tabuleiro[i-1][j];               
            r++;
        }
        
        return apagadas;
            
    }        
    
    protected void movimenta_bloco(Block bloco, int comando){
        
        switch (comando){
            
            case 1:
            colisao(bloco, comando);    
            break;
            
            case 2:
            if(!colisao(bloco, comando))//  anda pra esquerda
                inicioX--;           
            break;
            
            case 3:
            if(!colisao(bloco, comando))// anda pra direita
                inicioX++;
            break;
            
            case 4:
            colisao(bloco, comando); // rotaciona bloco para a direita-> comando = 1  
            break;
            
            case 5:
            if(!colisao(bloco, comando) && inicioY+bloco.get_max_tamanho() < ALTURA)
                inicioY++;           
            break;
        }  
    }
    
    protected boolean atualiza_tabuleiro(){       
         
        for(int i = 1; i < LARGURA+1; i++)
            if(tabuleiro[1][i] != 0)
                return false;
        
        inicioY++; // cai o bloco
        
        return true;
    }
    
    private boolean colisao(Block bloco, int direcao){
       
        switch (direcao){
            
            // rotaciona para a esquerda           
            case 1:             
            int[][] pedaco = new int[bloco.get_max_tamanho()][bloco.get_max_tamanho()];
            
            for(int i = 0; i < bloco.get_max_tamanho(); i++)
                for(int j = 0; j < bloco.get_max_tamanho(); j++)
                    pedaco [i][j] = tabuleiro[i+inicioY][j+inicioX];
                    
            return(!bloco.rotaciona(pedaco, -1));
            
            case 2:
            for(int i = 0; i < bloco.get_max_tamanho(); i++)
                for(int j = 0; j < bloco.get_max_tamanho(); j++)
                    if(bloco.get_bloco_formato()[i][j] != 0 && tabuleiro[inicioY+i+1][inicioX+j-1] != 0)
                        return true;
            
            return false;
  
            case 3:
            
            for(int i = 0; i < bloco.get_max_tamanho(); i++)
                for(int j = bloco.get_max_tamanho()-1; j >= 0; j--)
                    if(bloco.get_bloco_formato()[i][j] != 0 && tabuleiro[inicioY+i+1][inicioX+j+1] != 0)
                        return true;          
                
            return false;
            
            case 4:          
            pedaco = new int[bloco.get_max_tamanho()][bloco.get_max_tamanho()];
            for(int i = 0; i < bloco.get_max_tamanho(); i++)
                for(int j = 0; j < bloco.get_max_tamanho(); j++)
                    pedaco[i][j] = tabuleiro[i+inicioY][j+inicioX];
            
            return(!bloco.rotaciona(pedaco, 1));
            
            case 5:
            for(int i = bloco.get_max_tamanho()-1; i >= 0; i--)
                for(int j = 0; j < bloco.get_max_tamanho(); j++)                 
                    if(bloco.get_bloco_formato()[i][j] != 0 && tabuleiro[i+inicioY+1][j+inicioX] != 0)
                        return true; 
            
            return false;
         
            default:
                return false;
        }           
    }
    
    protected boolean insere_bloco(Block bloco){
                
        if(colisao(bloco, 5)){
            
            for(int ib = 0; ib < bloco.get_max_tamanho(); ib++)
                for(int jb = 0; jb < bloco.get_max_tamanho(); jb++)
                    if(bloco.get_bloco_formato()[ib][jb] > 0)
                        tabuleiro[ib+inicioY][jb+inicioX] = bloco.get_bloco_formato()[ib][jb];
            
            inicioY = INICIOY;
            inicioX = INICIOX;
            return true;        
        }
        return false;  
    }  
    
    protected void petrifica(){
        
        int i;
        for(i = ALTURA; i > 0; i--){
            if(tabuleiro[i][LARGURA/2] != -1)
                break;            
        }   
        for(int j = 1; j < LARGURA+1; j++)     
            tabuleiro[i][j] = -1;
                       
    }
    
    protected void despetrifica(){
        int linha = -1;
        for(int i = ALTURA; i > 0; i--){
            if(tabuleiro[i][LARGURA/2] == -1){
                linha = i;
                break;            
            }
        }
        if(linha != -1){
            for(int i = linha; i > 1; i--)
                System.arraycopy(tabuleiro[i-1], 1, tabuleiro[i], 1, LARGURA+1 - 1);  
        }
    }
    
    public int[][] get_tabuleiro(){
        return tabuleiro;
    }
    
    public int get_inicioY(){
        return inicioY;
    }
    
    public int get_inicioX(){
        return inicioX;
    }
    
    public int get_altura(){
        return ALTURA;
    }
    
    public int get_largura(){
        return LARGURA;
    }
    
}


