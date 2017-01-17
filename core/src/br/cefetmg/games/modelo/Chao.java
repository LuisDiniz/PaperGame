package br.cefetmg.games.modelo;

/**

 @author mefew
 */
class Chao {
    
    //fazer um array com comeco, fim e altura de cada parte do terreno
    
    public static int getFloorHeight (float posX)
    {
        if (posX <= 5000 && posX >= 0)
            //altura da caixa
            if (posX >= 500 && posX <= 600)
                return 100;
            else return 21;
        else return 21;
    }
    public static int getFloorHeightBelowCharacter (float posX, float width)
    {
        int floorHeight;

        floorHeight = getFloorHeight(posX) > getFloorHeight(posX + width/2) ? getFloorHeight(posX) : getFloorHeight(posX + width/2);
        if ( floorHeight < getFloorHeight(posX+width))
            floorHeight = getFloorHeight(posX+width);

        return floorHeight;
    }
}
