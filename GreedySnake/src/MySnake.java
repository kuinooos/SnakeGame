import javax.swing.*;

public class MySnake {
    public static void main(String[] args) {
        JFrame f=new JFrame("贪吃蛇");
        f.setLocation(350,100);
        f.setSize(900,720);
        f.setResizable(false);//窗口大小恒定
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭即结束程序
        f.setVisible(true);

        f.add(new MyPanel());
    }
}
 