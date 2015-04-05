package com.wynd.grapher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sarxos.webcam.Webcam;

public class GrapherServlet extends HttpServlet {
	
	private boolean RUNNING = false;
	private ArrayDeque<byte[]> list;
	private static int LIM = 30;
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		String action = req.getParameter("action");
		if( action != null){
			if(action.equals("start")){
				resp.setContentType("text/plain");
				synchronized(this){
					if(!RUNNING){
						final Webcam webcam = Webcam.getDefault();
						list = new ArrayDeque<byte[]>();
				        if(webcam == null){
				        	out.println("false");
				        }else{
				            webcam.open();
							RUNNING = true;
							new Thread(new Runnable(){
								public void run(){
									while(true){
										synchronized(GrapherServlet.this){
											if(!RUNNING){
												break;
											}
										}

							            BufferedImage image = webcam.getImage();
							            // save image to PNG file
							            //ImageIO.write(image, "PNG", new File("test2.png"));
							            try{
							            	ByteArrayOutputStream b = new ByteArrayOutputStream();
											ImageIO.write(image, "JPG", b);

								            synchronized(GrapherServlet.this){
								            	list.add(b.toByteArray());
								            	if(list.size() > 30){
								            		list.removeFirst();
								            	}
								            	System.out.println(list.size() + " " + RUNNING);
								            }
							            	Thread.sleep(25);
							            }catch(Exception e){
							            	
							            }
									}
									webcam.close();
									Webcam.resetDriver();
								}
							}).start();
				        }
					}
				}
			}else if(action.equals("stop")){
				RUNNING = false;
			}else if(action.equals("get")){
				resp.setContentType("image/jpg");
				try{
					if(list != null && !list.isEmpty()){
						resp.getOutputStream().write(list.getLast());
					}else{
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	
}
