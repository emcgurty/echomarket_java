package echomarket.service;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
//
//http://www.tutorialspoint.com/java_dip/downloading_uploading_images.htm
// */
//package echo_market.service;
//
//import java.net.*;
//import java.io.*;
//import java.awt.image.*;
//
//import javax.imageio.*; 
//import javax.swing.*; 
//
//class UpLoadImage {
//   public static void main(String  args[]) throws Exception{
//      ServerSocket server=null;
//      Socket socket;
//      server = new ServerSocket(4000);
//      System.out.println("Server Waiting for image");
//
//      socket = server.accept();
//      System.out.println("Client connected.");
//      
//      InputStream in = socket.getInputStream();
//      DataInputStream dis = new DataInputStream(in);
//
//      int len = dis.readInt();
//      System.out.println("Image Size: " + len/1024 + "KB");
//      
//      byte[] data = new byte[len];
//      dis.readFully(data);
//      dis.close();
//      in.close();
//
//      InputStream ian = new ByteArrayInputStream(data);
//      BufferedImage bImage = ImageIO.read(ian);
// 
//      JFrame f = new JFrame("UpLoadImage");
//      ImageIcon icon = new ImageIcon(bImage);
//      JLabel l = new JLabel();
//      
//      l.setIcon(icon);
//      f.add(l);
//      f.pack();
//      f.setVisible(true);
//   }
//}
