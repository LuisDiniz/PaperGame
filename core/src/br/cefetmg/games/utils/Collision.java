/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.games.utils;

import com.badlogic.gdx.math.Rectangle;

/**

 @author Sergio
 */
public class Collision {
    
    private static boolean rangesIntersect(
            float min1, float max1,
            float min2, float max2) 
    {
        if ( ( (min1 >= min2) && (min1 <= max2) ) || ( (min2 >= min1) && (min2 <= max1) ) )
            return true;
        else
            return false;
    }

    /**
     * Verifica se dois retângulos em 2D estão colidindo.
     * Esta função pode verificar se o eixo X dos dois objetos está colidindo
     * e então se o mesmo ocorre com o eixo Y.
     * @param r1 retângulo 1
     * @param r2 retângulo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean rectsOverlap(Rectangle r1, Rectangle r2) {
        if (rangesIntersect(r1.x, r1.x + r1.width, r2.x, r2.x + r2.width) && rangesIntersect(r1.y, r1.y + r1.height, r2.y, r2.y + r2.height))
            return true;
        else
            return false;
    }
}
