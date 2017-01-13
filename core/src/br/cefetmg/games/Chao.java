/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.games;

/**

 @author mefew
 */
class Chao {
    
    //fazer um array com comeco, fim e altura de cada parte do terreno
    
    public static int getFloorHeight (float posX)
    {
        if (posX <= 5000 && posX >= 0)
            return 21;
        else return 21;
    }
    public static int getFloorHeightBelowCharacter (float posX, int width)
    {
        //implementar esta porra
        return -1;
    }
}
