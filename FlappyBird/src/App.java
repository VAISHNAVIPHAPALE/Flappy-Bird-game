import javax.swing.*; // graphical user interface widget toolkit for java.
// provides gui for java programs;

public class App 
{
    public static void main(String[] args) throws Exception
    {
        //System.out.println("Hello, World!");
        int boardwidth=900;
        int boardheight=640;

        JFrame frame=new JFrame("Flappy Bird");
        frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird=new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);

    }
}
