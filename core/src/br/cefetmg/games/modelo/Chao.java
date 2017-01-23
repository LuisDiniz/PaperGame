package br.cefetmg.games.modelo;

import br.cefetmg.games.modelo.inimigos.BaseInimigo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**

 @author mefew
 */
public class Chao {        
    
    //fazer um array com comeco, fim e altura de cada parte do terreno
    private static List<Integer> posInicialCaixas;
    
    public Chao(){
        posInicialCaixas = new ArrayList<Integer>();
    }
    
    public static int getFloorHeight (float posX)
    {
        if (posX <= 5000 && posX >= 0){
            //altura da caixa
            for(Iterator<Integer> i = posInicialCaixas.iterator(); i.hasNext();) {
                Integer temp = i.next();            
                if (posX >= temp && (posX <= (temp + 100)))
                    return 100;
            }        
            return 21;
        }
        else 
            return 21;
    }
    public static int getFloorHeightBelowCharacter (float posX, float width)
    {
        int floorHeight;

        floorHeight = getFloorHeight(posX) > getFloorHeight(posX + width/2) ? getFloorHeight(posX) : getFloorHeight(posX + width/2);
        if ( floorHeight < getFloorHeight(posX+width))
            floorHeight = getFloorHeight(posX+width);

        return floorHeight;
    }
    
    public void addNovaCaixa(Integer posicaoInicial){
        posInicialCaixas.add(posicaoInicial);
    }
}
