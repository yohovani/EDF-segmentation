/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segmentation;

/**
 *
 * @author yohovani
 */
public class test {

	public static void main(String[] argv) {
		EDF test = new EDF();
//		//	test.show_image();
		test.test();
		test.draw_Image();
		test.save_image("test.png");
		test.show_image();	
		for(int i=0;i<3;i++){
			test.test();
//			test.draw_Image();
			test.show_image();	
		}		
//		test.run();
	}
}
