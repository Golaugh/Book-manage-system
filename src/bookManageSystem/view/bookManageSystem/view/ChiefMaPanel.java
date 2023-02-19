package bookManageSystem.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChiefMaPanel extends JFrame implements ActionListener {
    //创建一个容器
    JPanel jp;
    //创建背景面板。
    BackgroundPanel bgp;
    //创建一个按钮，用来证明我们的确是创建了背景图片，而不是一张图片。
    JButton jb;
    public static void main(String[] args){
        new ChiefMaPanel();
    }
    public ChiefMaPanel(){
//不采用任何布局方式。
        jp=(JPanel)this.getContentPane();
        this.setLayout(null);
//在这里随便找一张400*300的照片既可以看到测试结果。
        ImageIcon img=new ImageIcon("src/bookManageSystem/images/chief_ma.jpg");
        bgp=new BackgroundPanel(img.getImage());
        bgp.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
//创建按钮
        jb=new JButton("马科长说的对！");
        jb.addActionListener(this);
        bgp.add(jb);
        jp.add(bgp);
        this.setBounds(480, 320, 628, 478);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb){
            this.dispose();
        }
    }
}

class BackgroundPanel extends JPanel{
    Image im;
    public BackgroundPanel(Image im){
        this.im=im;
        this.setOpaque(true);
    }
    //Draw the back ground.
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
    }

}
