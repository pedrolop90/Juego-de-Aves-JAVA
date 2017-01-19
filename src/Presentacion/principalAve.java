

package Presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class principalAve extends JFrame implements KeyListener,MouseListener{
    
    private juego juego;
    private boolean parar=false;
    
    
    public principalAve(){
        setBounds(200,0,500,500);
        setResizable(false);
        addKeyListener(this);
        addMouseListener(this);
        juego=new juego(this.getWidth(),this.getHeight());
        add(juego);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER&&!parar){
           juego.start();
           parar=true;
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER&&parar){
            juego.stop();
            parar=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            juego.reIniciar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        juego.pezY-=50;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
    private class juego extends JPanel{
        
        private javax.swing.Timer t;
        private int totalX;
        private int totalY;
        private ImageIcon imagenPez;
        private ImageIcon imagenTubo1;
        private ImageIcon imagenTubo2;
        private ImageIcon imagenPiso;
        private ImageIcon imagenFondo;
        private int pezX;
        private int pezY;
        private int tiempoCaida=0;
        private int tubo2X;
        private int tubo2Y;
        private int tubo1X;
        private int tubo1Y;
        private int espacioPez=60;
        private int tiempoMovimientoTubos=0;
        private int anchoTubo=50;
        private boolean crearNumeroAleatorioTubo1=true;
        private boolean crearNumeroAleatorioTubo2=true;
        private boolean perdio=false;
        private ImageIcon imagenPerdio;
        
        
        public juego(int tX,int tY){
            totalX=tX;
            totalY=tY;
            pezX=100;
            pezY=totalY/2;
            tubo2X=totalX/2;
            tubo2Y=0;
            tubo1X=totalX;
            imagenPez=new ImageIcon("src/imagenes/pez.png");
            imagenTubo1=new ImageIcon("src/imagenes/tubo1.png");
            imagenTubo2=new ImageIcon("src/imagenes/tubo2.png");
            imagenPiso=new ImageIcon("src/imagenes/piso.png");
            imagenFondo=new ImageIcon("src/imagenes/fondo.png");
            imagenPerdio=new ImageIcon("src/imagenes/gamerover.png");
            t=new  javax.swing.Timer(10,new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    tiempoCaida+=10;
                    tiempoMovimientoTubos+=10;
                    if(pezY+35>=350){
                        t.stop();
                    }
                    if(((pezX+35>=tubo1X)&&(pezX+35<=tubo1X+anchoTubo))){
                        if((pezY+35>=0&&pezY+35<=tubo1Y)||(pezY+35>=(tubo1Y+espacioPez))||(pezY<=tubo1Y)){
                            perdio=true;
                        }
                    }
                     if(((pezX+35>=tubo2X)&&(pezX+35<=tubo2X+anchoTubo))){
                        if((pezY+35>=0&&pezY+35<=tubo2Y)||(pezY+35>=(tubo2Y+espacioPez))||(pezY<=tubo2Y)){
                            perdio=true;
                        }
                    }
                    
                    if(tubo2X+anchoTubo<=0){
                        tubo2X=totalX+anchoTubo*2;
                        crearNumeroAleatorioTubo2=true;
                    }else if(tubo1X+anchoTubo<=0){
                        tubo1X=totalX+anchoTubo*2;
                        crearNumeroAleatorioTubo1=true;
                    }
                    if(tiempoCaida==200){
                        pezY+=10;
                        tiempoCaida=0;
                    }
                    if(tiempoMovimientoTubos==100){
                        tubo2X-=20;
                        tubo1X-=20;
                        tiempoMovimientoTubos=0;
                    }
                    update();
                }
            });
        }
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            dibujarFondo(g);
            dibujarPiso(g);
            dibujarPez(g);
                if (crearNumeroAleatorioTubo2) {
                    tubo2Y = (int) (Math.random() * 250) + 20;
                    crearNumeroAleatorioTubo2 = false;
                }
                g.drawImage(imagenTubo1.getImage(), tubo2X, 0, anchoTubo, tubo2Y, this);
                g.drawImage(imagenTubo2.getImage(), tubo2X, tubo2Y + espacioPez, anchoTubo, 350 - tubo2Y - 60, this);
                if (crearNumeroAleatorioTubo1) {
                    tubo1Y = (int) (Math.random() * 250) + 20;
                    crearNumeroAleatorioTubo1 = false;
                }
                g.drawImage(imagenTubo1.getImage(), tubo1X, 0, anchoTubo, tubo1Y, this);
                g.drawImage(imagenTubo2.getImage(), tubo1X, tubo1Y + espacioPez, anchoTubo, 350 - tubo1Y - 60, this);
            if (perdio) {
                dibujarPerdio(g);
                 t.stop(); 
            }
           
        }
        public void dibujarFondo(Graphics g){
            g.drawImage(imagenFondo.getImage(), 0, 0, imagenFondo.getIconWidth(), 350, this);
            g.drawImage(imagenFondo.getImage(), imagenFondo.getIconWidth(), 0, imagenFondo.getIconWidth(), 350, this);
        }
        public void dibujarPiso(Graphics g){
            g.drawImage(imagenPiso.getImage(), 0, 350, 500, 150, this);
        }
        public void dibujarPez(Graphics g){
             g.drawImage(imagenPez.getImage(), pezX, pezY, 35, 35, this);
        }
        
        public void dibujarPerdio(Graphics g){
             g.drawImage(imagenPerdio.getImage(), totalX/2, totalY/2, 100, 50, this);
        }
        
        public void reIniciar(){
            pezX=100;
            pezY=totalY/2;
            tubo2X=totalX/2;
            tubo2Y=0;
            tubo1X=totalX;
           crearNumeroAleatorioTubo1=true;
            crearNumeroAleatorioTubo2=true;
            perdio=false;
            t.start();
        }
        
        public void update(){
            this.repaint();
        }
        public void start(){
            t.start();
        }
        public void stop(){
            t.stop();
        }
    }
    
    public static void main(String[] args) {
        principalAve p=new principalAve();
        p.setVisible(true);
        p.setDefaultCloseOperation(3);
    }
    
}
