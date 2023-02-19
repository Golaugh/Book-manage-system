package bookManageSystem.view;

import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserInfoDialog extends JDialog implements ActionListener {
    private ComponentTools componentTools = new ComponentTools();
    private JPanel aboutSoftPanel;
    private Box totalHBox, leftHBox, rightVBox;
    private JLabel iconLabel, systemLabel, editionTextField1, editionTextField2, editionTextField3, hyperlinkLabel;
    private JButton closeButton;

    UserInfoDialog() {
        // 设置Dialog的相关属性
        String id,name = null,banji = null;
        JsonCache sb=new JsonCache();
        id= (String) sb.READFLIE(String.valueOf(114514));
        System.out.println(id);
        this.setTitle("用户信息");
        this.setBounds(450, 250, 750, 500);
        try { //
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=图书管理系统";
            Connection con = null;
            con = DriverManager.getConnection(url, "sa", "123456");
            Statement stmt = con.createStatement();
            ResultSet rs1=stmt.executeQuery("select 学号,姓名,班级 from users where 学号='"+id+"'");
            while (rs1.next()){
                name=rs1.getString(2);
                banji=rs1.getString(3);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        this.setContentPane(this.createAboutSoftPanel(id,name,banji));
        this.setVisible(false);

        // 为按钮注册事件监听器
        closeButton.addActionListener(this);
    }

    /**
     * 创建Dialog的内容面板
     *
     * @return 返回一个JPanel
     */
    private JPanel createAboutSoftPanel(String id,String name,String banji) {
        aboutSoftPanel = new JPanel();
        aboutSoftPanel.setLayout(new BorderLayout());

        totalHBox = Box.createHorizontalBox();

        leftHBox = Box.createHorizontalBox();
        iconLabel = new JLabel();
        iconLabel.setIcon(componentTools.iconSize(new ImageIcon("src/bookManageSystem/images/school.jpg"), 160, 160));
        leftHBox.add(iconLabel);
        totalHBox.add(Box.createHorizontalStrut(20));
        totalHBox.add(leftHBox);

        //需要通过sql调取相关信息
        rightVBox = Box.createVerticalBox();
        systemLabel = new JLabel("这是你的用户信息：");
        systemLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        editionTextField1 = new JLabel("学号："+id);
        editionTextField1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        editionTextField2 = new JLabel("姓名："+name);
        editionTextField2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        editionTextField3 = new JLabel("班级："+banji);
        editionTextField3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        hyperlinkLabel = new JLabel("用户名：");
        hyperlinkLabel.setForeground(new Color(0, 149, 200));
        hyperlinkLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        hyperlinkLabel.setVisible(false);
        rightVBox.add(systemLabel);
        rightVBox.add(Box.createVerticalStrut(40));
        rightVBox.add(editionTextField1);
        rightVBox.add(Box.createVerticalStrut(10));
        rightVBox.add(editionTextField2);
        rightVBox.add(Box.createVerticalStrut(10));
        rightVBox.add(editionTextField3);
        rightVBox.add(Box.createVerticalStrut(40));
        rightVBox.add(hyperlinkLabel);

        closeButton = new JButton("关闭");
        rightVBox.add(Box.createVerticalStrut(40));
        rightVBox.add(closeButton);
        totalHBox.add(Box.createHorizontalStrut(40));
        totalHBox.add(rightVBox);

        aboutSoftPanel.add(totalHBox);

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