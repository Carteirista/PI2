/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mdio_top_alggen;

/**
 *
 * @author Artur
 */
public class AutoGen implements Runnable {
	
	MDIO_TOP_AlgGenView window;
	
	public AutoGen(MDIO_TOP_AlgGenView window){
		this.window = window;
	}
	
	public void run(){
		while(window.novaGerB());
	}
}
