import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener,KeyListener
{
    int boardwidth=600;
    int boardheight=640;

    // Images;
     Image backgroundImg;
     Image birdImg;
     Image topPipImg;
     Image bottomPipeImg;

    //Bird;
    int birdx=boardwidth/8;
    int birdy=boardheight/2;
    int bwidth=34;
    int bheight=24;

    class Bird
    {
        int x=birdx;
        int y=birdy;
        int width=bwidth;
        int height=bheight;
        Image img;
        
        Bird(Image img)
        {
            this.img=img;
        }
    }

    //pipes;
    int pipex=boardwidth;
    int pipey=0;
    int pipew=64;
    int pipeh=512;
     
    class pipe
    {
       int x=pipex;
       int y=pipey;
       int width=pipew;
       int height=pipeh;
       Image img;
       Boolean passed=false;

       pipe(Image img)
       {
        this.img=img;
       }
    }

    //game logic;
    Bird bird;
    int velocityx=-4;
    int velocityY=0;
    int gravity=1;

    ArrayList<pipe> pipes;
    Random random=new Random();

    Timer gameloop;
    Timer placepipetimer;

    boolean gameover=false;
    double score=0;



    FlappyBird()
    {
        setPreferredSize(new Dimension(boardwidth,boardheight));
        //setBackground(Color.blue);

        setFocusable(true);
        addKeyListener(this);

        //load images;
        backgroundImg=new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        birdImg=new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        topPipImg=new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();
       
        //bird;
        bird=new Bird(birdImg);
        pipes=new ArrayList<pipe>();


        //place pipes timer;
        placepipetimer =new Timer(1500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                placepipes();
            }
        });

        placepipetimer.start();

        //game timer
        gameloop=new Timer(1000/60,this);
        gameloop.start();

    }

    public void placepipes()
    {
        //(0-1) * pipeheight/2 -> (0-256);
        // 128
        //0-128-(0-256)--> 1/4 pipeh -> 3/4 pipeh

        int randompipey=(int)(pipey- pipeh/4 - Math.random()*(pipeh/2));
        int openingspace=boardheight/4;

        pipe toppipe=new pipe(topPipImg);
        toppipe.y=randompipey;
        pipes.add(toppipe);
        pipe bottompipe=new pipe(bottomPipeImg);
        bottompipe.y=toppipe.y+ pipeh+openingspace;
        pipes.add(bottompipe);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        //System.out.println("draw");
        // backgrounud;
        g.drawImage(backgroundImg,0,0,boardwidth,boardheight,null);
        
        // bird;
        g.drawImage(bird.img, bird.x, bird.y,bird.width,bird.height,null);

        for(int i=0;i<pipes.size();i++)
        {
             pipe pip=pipes.get(i);
             g.drawImage(pip.img, pip.x, pip.y, pip.width, pip.height,null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameover)
        {
            g.drawString("Game Over :" + String.valueOf((int) score),10,35);
        }
        else
        {
            g.drawString(String.valueOf((int) score),10,35);
        }
    }

    public void move()
    {
        //bird;
        velocityY +=gravity;
        bird.y += velocityY;
        bird.y=Math.max(bird.y,0);

        //pipes;

        for(int i=0;i<pipes.size();i++)
        {
            pipe pip=pipes.get(i);
            pip.x +=velocityx;

            if(!pip.passed && bird.x > pip.x + pip.width)
            {
                pip.passed=true;
                score +=0.5;
            }

            if(collision(bird,pip))
            {
                gameover=true;
            }
        }

        if(bird.y > boardheight)
        {
            gameover=true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
       
        move();
        repaint();
        if(gameover)
        {
            placepipetimer.stop();
            gameloop.stop();
        }
        
    }


    public Boolean collision(Bird a,pipe b)
    {
          return a.x < b.x+b.width && 
          a.x+a.width >b.x && 
          a.y<b.y+b.height &&
          a.y+a.height>b.y;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
         {
              velocityY=-9;
              if(gameover)
              {
                //restart the game;
                bird.y=birdy;
                velocityY=0;
                pipes.clear();
                score=0;
                gameover=false;
                gameloop.start();
                placepipetimer.start();

              }
         }
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
       
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
      
    }


}
