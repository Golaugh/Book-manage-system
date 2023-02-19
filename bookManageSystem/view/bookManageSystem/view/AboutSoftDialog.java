package bookManageSystem.view;

import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutSoftDialog extends JDialog implements ActionListener {
    private ComponentTools componentTools = new ComponentTools();
    private JPanel aboutSoftPanel;
    private Box totalHBox, leftHBox, rightVBox;
    private JLabel iconLabel, systemLabel, editionTextField1, editionTextField2, editionTextField3, hyperlinkLabel;
    private JButton closeButton;

    AboutSoftDialog() {
        // 设置Dialog的相关属性
        this.setTitle("关于软件");
        this.setBounds(450, 250, 750, 500);

        this.setContentPane(this.createAboutSoftPanel());
        this.setVisible(false);

        // 为按钮注册事件监听器
        closeButton.addActionListener(this);
    }

    /**
     * 创建Dialog的内容面板
     *
     * @return 返回一个JPanel
     */
    private JPanel createAboutSoftPanel() {
        aboutSoftPanel = new JPanel();
        aboutSoftPanel.setLayout(new BorderLayout());

        totalHBox = Box.createHorizontalBox();

        leftHBox = Box.createHorizontalBox();
        iconLabel = new JLabel();
        iconLabel.setIcon(componentTools.iconSize(new ImageIcon("src/bookManageSystem/images/school.jpg"), 160, 160));
        leftHBox.add(iconLabel);
        totalHBox.add(Box.createHorizontalStrut(20));
        totalHBox.add(leftHBox);

        rightVBox = Box.createVerticalBox();
        systemLabel = new JLabel("图书管理系统--版本 1.0");
        systemLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        editionTextField1 = new JLabel("即便相距海边甚远，我依然时不时能够听见大海的声音。");
        editionTextField1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        editionTextField2 = new JLabel("那是种由内而外的，不断回响的声音...");
        editionTextField2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        editionTextField3 = new JLabel("现在我愈发觉得那是从我脑袋里出来的声音了。");
        editionTextField3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        hyperlinkLabel = new JLabel("<html></u>参考GitHub链接(极其有限的参考！)</u></html>");
        hyperlinkLabel.setForeground(new Color(0, 149, 200));
        hyperlinkLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        closeButton = new JButton("关闭");
        rightVBox.add(systemLabel);
        rightVBox.add(Box.createVerticalStrut(40));
        rightVBox.add(editionTextField1);
        rightVBox.add(Box.createVerticalStrut(10));
        rightVBox.add(editionTextField2);
        rightVBox.add(Box.createVerticalStrut(10));
        rightVBox.add(editionTextField3);
        rightVBox.add(Box.createVerticalStrut(40));
        rightVBox.add(hyperlinkLabel);
        rightVBox.add(Box.createVerticalStrut(40));
        rightVBox.add(closeButton);
        totalHBox.add(Box.createHorizontalStrut(40));
        totalHBox.add(rightVBox);

        aboutSoftPanel.add(totalHBox, BorderLayout.NORTH);
        return aboutSoftPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // “关闭”按钮的事件处理
        if (e.getSource() == closeButton) {
            // 设置该Dialog不显示即可
            this.setVisible(false);
        }
    }
}