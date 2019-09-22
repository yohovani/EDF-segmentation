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
//		//	segmentation.show_image();
		test.show_image();		
		test.segmentation();
		test.draw_Image();
		test.save_image("test.png");
		test.show_image();
		test.draw_Image();
		test.average();
		test.show_image();	
		test.save_image("test4.png");
	}
}
