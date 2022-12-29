

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author boluyomi-adeyemi
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int Screen_Width = 600;
    static final int Screen_Height = 600;
    static final int unit_size = 25; //how big do we want the items
    static final int game_units = (Screen_Width * Screen_Height)/unit_size;
    static final int delay = 100; //pace of the game
    final int x[] = new int [game_units]; //holds bpdy of snake
    final int y[] = new int [game_units];
    int body_parts = 6;
    int obj_eaten;
    int objX; //x-coordinate of where the object is
    int objY; //y-coordinate of where object is
    String direction = "Right"; //the snake moves to the right on default
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame(){
        newObj(); //produces and obj for the snake to eat
        running = true;
        timer = new Timer(delay,this); //starts the timer
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < Screen_Height / unit_size; i++) {
                g.drawLine(i * unit_size, 0, i * unit_size, Screen_Height); //makes the grid lines
                g.drawLine(0, i * unit_size, Screen_Width, i * unit_size);//makes the grid lines
            }
            g.setColor(Color.GREEN);
            g.fillOval(objX, objY, unit_size, unit_size); //the apple is large and green

            for (int i = 0; i < body_parts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                  //  g.setColor(new Color((random.nextInt(255), random.nextInt(255), random.nextInt(255) ))); this is to make the snake change color
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }
            }
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + obj_eaten, (Screen_Width - metrics.stringWidth("Score: " + obj_eaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    public void newObj(){
        objX = random.nextInt((int)(Screen_Width/unit_size))*unit_size; //shows up at some points around x-axis
        objY = random.nextInt((int)(Screen_Height/unit_size))*unit_size; //SHOWS UP AT SOME POINT AROUND Y-AXIS



    }

    public void move(){ //shofts body parts of snake around
        for(int i = body_parts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        switch(direction){
            case "Up":
                y[0] = y[0] - unit_size;
                break;
            case "Down":
                y[0] = y[0] + unit_size;
                break;
            case "Left":
                x[0] = x[0] - unit_size;
                break;
            case "Right":
                x[0] = x[0] + unit_size;


        }

    }
    public void checkObject(){
        if((x[0] == objX ) && (y[0] == objY)){ //if snake eats, it will extend itself
            body_parts++;
            obj_eaten++; //this will be the score that shows how many was eaten
            newObj();
        }

        
    }
    public void checkHit(){ //checks if head of snake eats itself
        for (int i = body_parts; i>0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //checks if head hits left
        if(x[0] < 0){
            running = false;
        }
        //checks if head hits right
        if (x[0] > Screen_Width){
            running=false;
        }
        //checks if head hits top
        if (y[0] < 0){
            running=false;
        }
        //checks if head hits bottom
        if (y[0] > Screen_Height){
            running = false;
        }
        if(!running) { //when the snake stops, the timer will stop
            timer.stop();
        }


    }
    public void gameOver(Graphics g){
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics_1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + obj_eaten, (Screen_Width - metrics_1.stringWidth("Score: " + obj_eaten))/2, g.getFont().getSize());
        //displays text when game is over
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (Screen_Width - metrics.stringWidth("Game Over"))/2, Screen_Height/2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (running){
            move();
            checkObject();
            checkHit();
        }
        repaint();

    }


    public class MyKeyAdapter extends KeyAdapter{ //this is for directing the snake
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != "Right"){
                        direction = "Left";
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != "Left"){
                        direction = "Right";
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != "Down"){
                        direction = "Up";

                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != "Up"){
                        direction = "Down";
                    }
                    break;
            }

        }


    }    
    }


