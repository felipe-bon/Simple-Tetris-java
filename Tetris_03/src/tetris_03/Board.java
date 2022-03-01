
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
    
    // arrumar 
    public int apaga_linha(){
        
        int j, i, k = -1;
        // verifica qual linha está completa
        for(i = 20; i > 1; i--){
            for(j = 1; j < 11; j++){
                if(tabuleiro[i][j] == 0)
                    break;
            }
            if(j == 10 && tabuleiro[i][j] != 0){
                k = i;
                break;
            }
        }  
        if(k != -1){
            for(i = k; i > 1; i--){
                for(int l = 1; l < 11; l++){
                    tabuleiro[k][l] = tabuleiro[k-1][l];
                }
            }
            return 1;
        }
        return -1;
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
            if(colisao(bloco, comando)){
                inicioY++;
            }   
            break;
        }
        
    }
    
    public void atualiza_tabuleiro(){
        apaga_linha();
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
            for(int j = 0; j < bloco.get_Max_T(); j++){
                for(int i = 0; i < bloco.get_Max_T(); i++){
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[i+inicioY][j+inicioX] != 0){
                        return false;
                    }
                }
            }
            return true;
  
            case 3:
            for(int i = bloco.get_Max_T()-1; i >= 0; i--){
                for(int j = 0; j < bloco.get_Max_T();j++)
                {                    
                    if(bloco.bloco_formato[i][j] != 0 && tabuleiro[i+inicioY][j+inicioX+1] != 0){                
                        return false;
                    }    
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


