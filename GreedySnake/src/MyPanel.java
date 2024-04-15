import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class  MyPanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon title=new ImageIcon("title.jpg");
    ImageIcon up=new ImageIcon("up.png");
    ImageIcon down=new ImageIcon("down.png");
    ImageIcon left=new ImageIcon("left.png");
    ImageIcon right=new ImageIcon("right.png");
    ImageIcon body=new ImageIcon("body.png");
    ImageIcon food=new ImageIcon("food.png");

    private int len=3;//蛇的初始长度（包括头）
    private int score=0;

    int[] snakex=new int[900];
    int[] snakey=new int[900];

    private char hdirect='D';

    private boolean isStarted=false;
    private boolean isFailed=false;
    private boolean wining=false;

    private int foodx;
    private int foody;
    Random random=new Random();
    Timer timer=new Timer(100,this);
    public MyPanel(){
        this.setBackground(Color.WHITE);

        initSnake();//一定要在构造器中调用，否则paintComponent方法读取不到蛇的坐标

        this.setFocusable(true);//可以使用键盘或鼠标交互组件的前提
        this.addKeyListener(this);//当前类实现了KeyListener接口后，可以通过自身对象来处理键盘事件

        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){//此方法用来画出各种组件，包括蛇
        super.paintComponent(g);
        title.paintIcon(this,g,25,11);//显示标题
        g.fillRect(25,75,850,600);//画出活动区域

        g.setColor(Color.WHITE);//将分数和长度的字体颜色设置为白色
        g.drawString("Score "+ score,750,35);
        g.drawString("Lenth "+ len,750,50);



        if(hdirect=='W'){
            up.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(hdirect=='S'){
            down.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(hdirect=='A'){
            left.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(hdirect=='D'){
            right.paintIcon(this,g,snakex[0],snakey[0]);
        }
        for(int i=1;i<len;i++) {
            body.paintIcon(this, g, snakex[i], snakey[i]);
        }

        if(!isStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.ITALIC, 30));
            g.drawString("Press Space To Start", 300, 300);
        }
        if(isFailed){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.ITALIC,30));
            g.drawString("You Failed",300,300);
        }
        if(wining) {
            g.setColor(Color.cyan);
            g.setFont(new Font("Arial",Font.ITALIC,30));
            g.drawString("You Win!!!",300,300);
        }

        food.paintIcon(this,g,foodx,foody);
    }

    public void initSnake(){
        len=3;
        score=0;
        hdirect='D';
        snakex[0]=100;
        snakey[0]=100;
        snakex[1]=75;
        snakey[1]=100;
        snakex[2]=50;
        snakey[2]=100;
        foodx=25+25*random.nextInt(34);
        foody=75+25*random.nextInt(24);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyreturn=e.getKeyCode();
        if(keyreturn==KeyEvent.VK_SPACE){
            if(isFailed){
                isFailed=!isFailed;
                initSnake();
            }
            if(wining){
                wining=!wining;
                initSnake();
            }
            if(!isFailed) {
                isStarted = !isStarted;
                repaint();//一定要重绘，否则即使已经有监听事件但不会有任何反应
            }
        }else if(keyreturn==KeyEvent.VK_UP){
            hdirect='W';
        } else if (keyreturn==KeyEvent.VK_DOWN) {
            hdirect='S';
        } else if (keyreturn==KeyEvent.VK_LEFT) {
            hdirect='A';
        } else if (keyreturn==KeyEvent.VK_RIGHT) {
            hdirect='D';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {//蛇运动是一个动作事件，所以要与Timer关联
        if(isStarted&&!isFailed&&!wining) {
            for (int i = len - 1; i > 0; i--) {
                snakex[i] = snakex[i - 1];//越小的下标越靠近蛇头
                snakey[i] = snakey[i - 1];
            }

            if(hdirect=='D'){
                snakex[0] = snakex[0] + 25;
            } else if (hdirect=='A') {
                snakex[0]=snakex[0]-25;
            } else if (hdirect=='W') {
                snakey[0]=snakey[0]-25;
            } else if (hdirect=='S') {
                snakey[0]=snakey[0]+25;
            }

            if(snakex[0]==foodx&&snakey[0]==foody){//吃到食物后，长度增加
                len++;
                score+=10;
                if(score==300){
                    wining=!wining;//分数达到300时游戏胜利
                }
                snakex[len-1]=snakex[len-2];//防止跳帧
                snakey[len-1]=snakey[len-2];
                foodx=25+25*random.nextInt(34);//重新生成食物的随机坐标
                foody=75+25*random.nextInt(24);
            }

            for(int i=1;i<len;i++){//检查身体和头是否碰撞，蛇头和墙壁是否碰撞
                if(snakex[0]==snakex[i]&&snakey[0]==snakey[i]||
                snakex[0]>=875||snakex[0]<=0||snakey[0]<=50||snakey[0]>=675){
                    isFailed=true;
                }
            }

        }

        repaint();
        timer.start();//定时器启动后会按照预定的时间间隔触发动作事件，并调用与之关联的ActionListener的actionPerformed方法。
    }
}
