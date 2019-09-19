/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Segmentation;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	private int width;
	private int height;
	
	public EDF(){
		this.image = ImageManager.toBufferedImage(ImageManager.openImage());
		this.imageTest = new BufferedImage(this.image.getWidth(),this.image.getHeight(),BufferedImage.TYPE_INT_RGB);
		this.imageList = new ArrayList();
		this.coordinatesList = new ArrayList();
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public void test(){
		for(int i=0;i<this.width;i++){
			for(int j=0;j<this.height;j++){
				//Rule 1
				if(rule1(i,j+1)){
					j+=1;
				}else{
					//Rule 2
					if(rule2(i+1,j+1)){
						i+=1;
						j+=1;
					}else{
						//Rule 3
						if(rule3(i-1,j+1)){
							i-=1;
							j+=1;
						}else{
							//rule 4
							if(rule4(i+1,j)){
								i+=1;
							}else{
								//Rule 5
								if(rule5(i-1,j)){
									i-=1;
								}else{
									//Rule 6
									//Validation
									if(j+1 < this.height){
										j+=1;
										this.image.setRGB(i, j, Color.YELLOW.getRGB());
									}
								}
							}
						}
					}
				}
				
			}
		}
	}
	
	public int getColor(int x, int y){
		Color aux = new Color(this.image.getRGB(x, y));
		return (aux.getRed()+aux.getGreen()+aux.getBlue())/3;
	}
	
	public boolean rule1(int x, int y){
		//Validation
		if(y+1 < this.height){
			//Rule 1
			if(getColor(x,y+1) == 0){
				//Move to down
				y+=1;
				this.image.setRGB(x, y, Color.RED.getRGB());
				return true;
			}
		}
		return false;
	}
	
	public boolean rule2(int x, int y){
		//Validation
		if(x+1 <  this.width && y+1 < this.height){
			//Rule 2
			if(getColor(x+1,y+1) == 0){
				//Move to down and right
				x+=1;
				y+=1;
				this.image.setRGB(x, y, Color.CYAN.getRGB());
				return true;
			}	
		}
		return false;
	}
	
	public boolean rule3(int x, int y){
		//Validation
		if(x-1 >= 0 && y+1 < this.height){
			//Rule 3
			if(getColor(x-1,y+1) == 0){
				//Move to down and left
				x-=1;
				y+=1;
				this.image.setRGB(x, y, Color.GREEN.getRGB());
				return true;
			}
		}
		return false;
	}
	
	public boolean rule4(int x, int y){
		//Validation
		if(x+1 < this.width){
			//Rule 4
			if(getColor(x+1,y) == 0){
				//Move to right
				x+=1;
				this.image.setRGB(x, y, Color.PINK.getRGB());
				return true;
			}
		}
		return false;
	}
	
	public boolean rule5(int x, int y){
		//Validation
		if(x-1 >= 0){
			//Rule 5
			if(getColor(x-1,y) == 0){
				//Move to Left
				x-=1;
				this.image.setRGB(x, y, Color.ORANGE.getRGB());
				return true;
			}
		}
		return false;
	}
	
	public void save_image(){
		try {
			File outputfile = new File("test1.png");
			ImageIO.write(this.image, "png", outputfile);
		} catch (IOException ex) {
			Logger.getLogger(EDF.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void draw_Image(){
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<this.width;i++){
			for(int j=0;j<this.height;j++){
				img.setRGB(i, j, Color.BLACK.getRGB());
			}
		}
		for(int i=0;i<this.width;i++){
			for(int j=0;j<this.height;j++){
				if(this.image.getRGB(i, j) != Color.RED.getRGB() && this.image.getRGB(i, j) != Color.CYAN.getRGB() && this.image.getRGB(i, j) != Color.GREEN.getRGB() && this.image.getRGB(i, j) != Color.PINK.getRGB() && this.image.getRGB(i, j) != Color.ORANGE.getRGB() && this.image.getRGB(i, j) != Color.BLACK.getRGB() && this.image.getRGB(i, j) != Color.YELLOW.getRGB()){
					img.setRGB(i, j, this.image.getRGB(i, j));
				}
			}
		}
		this.image = img;
		System.out.println("Draw");
	}
	
	public void show_image(){
		ImageGUI img = new ImageGUI(this.image);
	}
	
	public void doc(){
		try {
            String ruta = "Coordenadas.txt";
            String contenido = "";
			for(int i=0;i<this.coordinatesList.size();i++){
				contenido += "X: "+this.coordinatesList.get(i).getX()+" , Y: "+this.coordinatesList.get(i).getY()+"\n";
			}
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
		
}
