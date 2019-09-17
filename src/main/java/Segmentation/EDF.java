/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segmentation;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author yohovani
 */
public class EDF {
	private BufferedImage image;
	private BufferedImage imageTest;
	private ArrayList<BufferedImage> imageList;
	private ArrayList<Coordinates> coordinatesList;
	
	public EDF(){
		this.image = ImageManager.toBufferedImage(ImageManager.openImage());
		this.imageTest = new BufferedImage(this.image.getWidth(),this.image.getHeight(),BufferedImage.TYPE_INT_RGB);
		this.imageList = new ArrayList();
		this.coordinatesList = new ArrayList();

	}
	
	public void get_coordinates(){
		int x = this.image.getWidth();
		int y = this.image.getHeight();
		Color pixel;
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				//Rule 1
				if(j+1 < x){
					pixel = new Color(this.image.getRGB(i, j+1));
					int aux =  (pixel.getBlue() + pixel.getGreen() + pixel.getRed())/3;
					if(aux <= 5){
						if(j+1 < x-5){							
							j+=1;
							this.coordinatesList.add(new Coordinates(i, j));
						}
					}else{
						this.imageTest.setRGB(i, j, pixel.getRGB());
					}
				}else{
					//Rule 2
					if(i+1 < x && j+1 < y){
						pixel = new Color(this.image.getRGB(i+1, j+1));
						int aux =  (pixel.getBlue() + pixel.getGreen() + pixel.getRed())/3;
						if(aux <= 5){
							if(i + 1 < (x - 5) && j+1 < (y - 5)){
								i+=1;
								j+=1;
								this.coordinatesList.add(new Coordinates(i, j));
							}
						}else{
							this.imageTest.setRGB(i, j, pixel.getRGB());
						}
					}else{
						//Rule 3
						if(i+1 < x && j-1 < y){
							pixel = new Color(this.image.getRGB(i-1, j+1));
							int aux =  (pixel.getBlue() + pixel.getGreen() + pixel.getRed())/3;
							if(aux <= 5){
								if(i + 1 < (x - 5) && j-1 < (y - 5)){
									i -= 1;
									j += 1;
									this.coordinatesList.add(new Coordinates(i, j));
								}else{
									this.imageTest.setRGB(i, j, pixel.getRGB());
								}
							}
						}else{
							//Rule 4
							if(j+1 < y){
								pixel = new Color(this.image.getRGB(i, j+1));
								int aux =  (pixel.getBlue() + pixel.getGreen() + pixel.getRed())/3;
								if(aux <= 5){
									if(j + 1 < (x - 5)){
										i+=1;
										this.coordinatesList.add(new Coordinates(i, j));
									}else{
										this.imageTest.setRGB(i, j, pixel.getRGB());
									}
								}
							}else{
								//Rule 5
								if(j-1 < y){
									pixel = new Color(this.image.getRGB(i-1, j));
									int aux =  (pixel.getBlue() + pixel.getGreen() + pixel.getRed())/3;
									if(aux <= 5){
										if(i+1 < x-5){
											this.coordinatesList.add(new Coordinates(i-1, j));
											j-=1;
										}else{
											this.imageTest.setRGB(i, j, pixel.getRGB());
										}
									}
								}else{
									this.coordinatesList.add(new Coordinates(i, j+1));
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void segmentation(){
		int x = this.image.getWidth();
		int y = this.image.getHeight();
		this.imageList.add(new BufferedImage(x/2,y,BufferedImage.TYPE_INT_RGB));
		this.imageList.add(new BufferedImage(x/2,y,BufferedImage.TYPE_INT_RGB));
		
		for(int i=0;i<x/2;i++){
			for(int j=0;j<y;j++){
				this.imageList.get(0).setRGB(i, j, Color.YELLOW.getRGB());
				this.imageList.get(1).setRGB(i, j, Color.YELLOW.getRGB());
			}
		}
		
		for(int i=0;i<this.coordinatesList.size();i++){
//			for(int j=0;j<x/2;j++){
//				for(int k=0;k<y;k++){
//					this.imageList.get(0).setRGB(j, k, this.image.getRGB(this.coordinatesList.get(i).getX(), this.coordinatesList.get(i).getY()));
//				}
//			}
			this.image.setRGB(this.coordinatesList.get(i).getX(), this.coordinatesList.get(i).getY(), Color.RED.getRGB());
			if(this.coordinatesList.get(i).getX() < x/2)
				this.imageList.get(0).setRGB(this.coordinatesList.get(i).getX(), this.coordinatesList.get(i).getY(), this.image.getRGB(this.coordinatesList.get(i).getX(), this.coordinatesList.get(i).getY()));
			else
				this.imageList.get(1).setRGB(this.coordinatesList.get(i).getX()-x/2, this.coordinatesList.get(i).getY(), this.image.getRGB(this.coordinatesList.get(i).getX(), this.coordinatesList.get(i).getY()));
		}
//		for(int i=0;i<this.coordinatesList.size();i++){
//			for(int j=0;j<x/2;j++){
//				for(int k=0;k<y;k++){
//					this.imageList.get(0).setRGB(j, k, this.image.getRGB(this.coordinatesList.get(i).getX(), this.coordinatesList.get(i).getY()));
//				}
//			}
//		}
	}
	
	public void show_image(){
		ImageGUI img = new ImageGUI(this.imageList.get(0));
		ImageGUI img2 = new ImageGUI(this.imageList.get(1));
		ImageGUI img3 = new ImageGUI(this.image);
		ImageGUI img4 = new ImageGUI(this.imageTest);
	}
	
	public void run(){
		get_coordinates();
		segmentation();
		show_image();
	}
	
}
