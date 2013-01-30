/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdio_top_alggen;

import java.util.Comparator;

/**
 *
 * @author Artur
 */
public class CromossomaComparator implements Comparator {
	public int compare(Object o1, Object o2){
		Cromossoma c1 = (Cromossoma)o1, c2 = (Cromossoma)o2;
		if (c1.getFitness() > c2.getFitness()) {return 1;}
		else if(c1.getFitness() < c2.getFitness()) {return -1;}
		else {return 0;}
	}
}
