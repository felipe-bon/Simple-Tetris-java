
package tetris_03;

public class Board {
    
    private int[][] tabuleiro;
    private int inicioY;
    private int inicioX;  

    Board(){
        tabuleiro = new int[22][12];
        for(int i = 0; i < 22; i++){
            for(int j = 0; j < 12; j++){
                if(i == 0 || ((j == 0 || j == 11) && i != 21))
                    tabuleiro[i][j] = -1;      
                else if(i == 21)
                    tabuleiro[i][j] = -2;
                else
                    tabuleiro[i][j] = 0;
            }
        }                    
        inicioY = 0;
        inicioX = 4;       
    }
    
    protected int apaga_linha(){
        
        int i, j, verifica = 0, r = 4, apagadas = 0;
        int[] linhas = new int[4];
        
        for(i = 20; i > 0; i--){
            for(j = 1; j < 11; j++){
                if(tabuleiro[i][j] != 0)    
                    verifica++;               
            }
            if(verifica == 10){
                apagadas++;
                r--;
                linhas[r] = i;    
            }            
            verifica = 0;
        }       
        while(r != 4){
            for(i = linhas[r]; i > 1; i--){
                for(j = 1; j < 11; j++){
                    tabuleiro[i][j] = tabuleiro[i-1][j];
                }
            }
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
            if(!colisao(bloco, comando)){
                inicioX--;
            }    
            break;
            
            case 3:
            if(!colisao(bloco, comando))
                inicioX++;
            break;
            
            case 4:
            // rotaciona bloco para a direita-> comando = 1
            colisao(bloco, comando);   
            break;
            
            case 5:
            if(!colisao(bloco, comando) && inicioY+bloco.Max_T < 20){
                inicioY++;
            }   
            break;
        }
        
    }
    
    protected boolean atualiza_tabuleiro(){       
         
        for(int i = 1; i < 11; i++)
            if(tabuleiro[1][i] != 0)
                return false;
        
        if(inicioY < 20)
            inicioY++;
        else
            inicioY = 0;
        
        return true;

    }
    
    private boolean colisao(Block bloco, int direcao){
       
        switch (direcao){
            
            // rotaciona para a esquerda           
            case 1:  
            int t_max = bloco.get_Max_T();   
            int[][] pedaco = new int[t_max][t_max];
            for(int i = 0; i < bloco.get_Max_T(); i++){
                for(int j = 0; j < bloco.get_Max_T(); j++){
                    pedaco [i][j] = tabuleiro[i+inicioY][j+inicioX];
                }
            }
            bloco.rotaciona(pedaco, -1);
            
            break;
            
            case 2:
            for(int i = 0; i < bloco.get_Max_T(); i++){
                for(int j = 0; j < bloco.get_Max_T(); j++){
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[inicioY+i+1][inicioX+j-1] != 0)
                        return true;
                }
            }
            return false;
  
            case 3:
            
            for(int i = 0; i < bloco.get_Max_T(); i++){
                for(int j = bloco.get_Max_T()-1; j >= 0; j--){
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[inicioY+i+1][inicioX+j+1] != 0)
                        return true;
                }
            }
                
            return false;
            
            case 4:
            t_max = bloco.get_Max_T();
            pedaco = new int[t_max][t_max];
            for(int i = 0; i < t_max; i++){
                for(int j = 0; j < t_max; j++){
                    pedaco[i][j] = tabuleiro[i+inicioY][j+inicioX];
                }
            }
            bloco.rotaciona(pedaco, 1);
            break;
            
            case 5:
            for(int i = bloco.get_Max_T()-1; i >= 0; i--){
                for(int j = 0; j < bloco.get_Max_T(); j++){                   
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[i+inicioY+1][j+inicioX] != 0){
                        return true;
                    }
                }
            } 
            return false;
        }           
        return false; 
    }
    
    protected boolean insere_bloco(Block bloco){
                
        if(colisao(bloco, 5)){
            for(int ib = 0; ib < bloco.get_Max_T(); ib++){
                for(int jb = 0; jb < bloco.get_Max_T(); jb++){
                    if(bloco.variacoes[bloco.variacao][ib][jb] > 0){
                        tabuleiro[ib+inicioY][jb+inicioX] = bloco.variacoes[bloco.variacao][ib][jb];
                    }
                }
            }
            inicioY = 0;
            inicioX = 4;
            return true;        
        }
        return false;  
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
    
}


