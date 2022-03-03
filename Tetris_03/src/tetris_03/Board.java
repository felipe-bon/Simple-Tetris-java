
package tetris_03;

public class Board {
    
    int[][] tabuleiro;
    int inicioY;
    int inicioX;  

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
    
    public int apaga_linha(){
        
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
        
    
    
    public void movimenta_bloco(Block bloco, int comando){
        
        switch (comando){
            
            case 1:
                
            break;
            
            case 2:
            if(colisao(bloco, comando)){
                inicioX--;
            }    
            break;
            
            case 3:
            if(colisao(bloco, comando))
                inicioX++;
            break;
            
            case 4:
            if(colisao(bloco, comando)){
                //rotaciona_bloco();
            }    
            break;
            
            case 5:
            if(!colisao(bloco, comando)){
                inicioY++;
            }   
            break;
        }
        
    }
    
    public void atualiza_tabuleiro(){       
         
        if(inicioY < 20)
            inicioY++;
        else
            inicioY = 0;
        

    }
    
    public boolean colisao(Block bloco, int direcao){
       
        switch (direcao){
            
            case 1:
                
            break;
            
            case 2:
            for(int i = 0; i < bloco.get_Max_T(); i++){
                for(int j = 0; j < bloco.get_Max_T(); j++){
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[inicioY+i+1][inicioX+j-1] != 0)
                        return false;
                }
            }
            return true;
  
            case 3:
            
            for(int i = 0; i < bloco.get_Max_T(); i++){
                for(int j = bloco.get_Max_T()-1; j >= 0; j--){
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[inicioY+i+1][inicioX+j+1] != 0)
                        return false;
                }
            }
                
            return true;
            
            case 4:
            int[][] pedaco = new int[4][4];
            for(int i = 0; i < 4; i++)
                for(int j = 0; j < 4; j++)
                    pedaco[i][j] = tabuleiro[i+inicioY][j+inicioX];
            bloco.rotaciona(pedaco, 1);
            break;
            
            case 5:
            for(int i = bloco.get_Max_T()-1; i >= 0; i--){
                for(int j = bloco.get_Max_T()-1; j >= 0; j--){
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[i+inicioY+1][j+inicioX] != 0){
                        return true;
                    }
                }
            }    
            
           
        }
        
        
        
        // testa a colisão para baixo;
        for(int i = bloco.get_Max_T()-1; i >= 0; i--){
            for(int j = bloco.get_Max_T()-1; j >= 0; j--){
                if(bloco.bloco_formato[i][j] != 0 && tabuleiro[i+inicioY+1][j+inicioX] != 0){
                    return true;
                }
            }
        }
        
        return false;   
    }
    
    public boolean insere_bloco(Block bloco){
              
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

//        if(inicioY == 16){
//            inicioY = 0;
//            inicioX = 4;
//            return true;
//        }
//        else
//            return false;
    }                  
}


