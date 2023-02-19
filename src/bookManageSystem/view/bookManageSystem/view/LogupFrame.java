package bookManageSystem.view;

import bookManageSystem.tools.ComponentTools;
import bookManageSystem.tools.SimpleTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.sql.*;

//登陆界面
public class LogupFrame extends JFrame implements ActionListener {
    private SimpleTools simpleTools = new SimpleTools();
    private ComponentTools componentTools = new ComponentTools();
    private JPanel panel, PicturePanel;
    private Box totalVBox, systemLabelBox, usernameBox, passwordBox, testBox, buttonBox;
    private JLabel systemLabel, usernameLabel, passwordLabel, testBoxLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JPasswordField testBoxField;
    private JButton logupButton, resetButton, signupButton;

    public LogupFrame() {
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon
                ("src/bookManageSystem/images/signupbackground.png");
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

        this.setTitle("图书管理系统登陆界面");
        this.setBounds(400, 200, img.getIconWidth(), img.getIconHeight());

        this.setContentPane(this.createLogupPane());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);

        // 为按钮注册事件
        logupButton.addActionListener(this);
        resetButton.addActionListener(this);
        signupButton.addActionListener(this);
    }

    public JPanel createLogupPane() {
        panel = new JPanel();
        totalVBox = Box.createVerticalBox();

        PicturePanel = new JPanel();
        ImageIcon img = new ImageIcon
                ("src/bookManageSystem/images/testp2.jpg");
        JLabel background = new JLabel(img);
        ImageIcon img2 = new ImageIcon
                ("src/bookManageSystem/images/youshou.jpg");
        JLabel background2 = new JLabel(img2);
        PicturePanel.setLayout(new BorderLayout(0,0));
        PicturePanel.add(background2,"Center");
        PicturePanel.add(background,"East");
        PicturePanel.setOpaque(false);

        systemLabelBox = Box.createHorizontalBox();
        systemLabel = new JLabel("图书管理系统");
        systemLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
        systemLabelBox.add(systemLabel);

        usernameBox = Box.createHorizontalBox();
        usernameLabel = new JLabel("学号：");
        usernameTextField = new JTextField(20);
        usernameBox.add(usernameLabel);
        usernameBox.add(Box.createHorizontalStrut(20));
        usernameBox.add(usernameTextField);

        passwordBox = Box.createHorizontalBox();
        passwordLabel = new JLabel("密    码：");
        passwordField = new JPasswordField(20);
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(20));
        passwordBox.add(passwordField);

        testBox = Box.createHorizontalBox();
        testBoxLabel = new JLabel("验 证 码：");
        testBoxField = new JPasswordField(20);
        testBox.add(testBoxLabel);
        testBox.add(Box.createHorizontalStrut(15));
        testBox.add(testBoxField);

        buttonBox = Box.createHorizontalBox();
        logupButton = new JButton("登录账号！");
        resetButton = new JButton("忘记密码？");
        signupButton = new JButton("注册账号~");
        buttonBox.add(logupButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(resetButton);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(signupButton);

        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(systemLabelBox);
        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(usernameBox);
        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(passwordBox);
        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(testBox);
        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(PicturePanel);
        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(buttonBox);

        panel.add(totalVBox);
        panel.setOpaque(false);

        // 为标签设置图标
        new ComponentTools().setIcons(new JLabel[]{systemLabel, usernameLabel, passwordLabel, testBoxLabel},
                new String[]{"src/bookManageSystem/images/logo.png",
                        "src/bookManageSystem/images/userName.png",
                        "src/bookManageSystem/images/password.png",
                        "src/bookManageSystem/images/qualification.png"});
        // 为按钮设置图标
        new ComponentTools().setIcons(new JButton[]{logupButton, resetButton, signupButton},
                new String[]{"src/bookManageSystem/images/login.png",
                        "src/bookManageSystem/images/reset.png",
                        "src/bookManageSystem/images/reset.png"});
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 登录按钮的事件处理
        if (e.getSource() == logupButton) {
            try { //
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=图书管理系统";
                Connection con = null;
                con = DriverManager.getConnection(url, "sa", "123456");
                Statement stmt = con.createStatement();

                if (simpleTools.isEmpty(usernameTextField.getText()) && simpleTools.isEmpty(passwordField.getText())) {
                    //读取用户数据库，进行比对
                    String usersname=usernameTextField.getText();
                    BufferedWriter writer=null;
                    JsonCache chucun=new JsonCache();
                    chucun.saveDataToFile(String.valueOf(114514),usernameTextField.getText());
                    String[] account = new String[100];
                    String[] keycode = new String[100];
                    Boolean[] admin = new Boolean[100];
                    ResultSet rs1=stmt.executeQuery("select * from users");
                    int i=1;
                    boolean signal=false;
                    boolean admdinjudge=false;
                    while (rs1.next()){
                        account[i]=rs1.getString(1);
                        keycode[i]=rs1.getString(6);
                        i++;
                    }
                    ResultSet rs2=stmt.executeQuery("select 姓名 from users where 学号='"+usersname+"'");
                    while (rs2.next()){
                        chucun.saveDataToFile("name",rs2.getString(1));
                    }
                        int j=1;
                        for(j=1;j<=i;j++)
                        {
                            if(usernameTextField.getText().equals(account[j])&&passwordField.getText().equals(keycode[j])){
                                ResultSet rs3=stmt.executeQuery("select 管理员 from users where 学号='"+usersname+"'");
                                while (rs3.next()){
                                    if(rs3.getBoolean(1)==true) admdinjudge=true;

                                }
                                signal=true;

                            }
                        }
                    if (signal) { //账号密码管理员校对成功，就可以登录了
                        // 弹出提示框进行登录成功的提示
                        int isOK = JOptionPane.showConfirmDialog(null, "恭喜您，登录成功！");
                        // 如果用户点击了提示框中的确认按钮则进行下一步操作
                        if (isOK == JOptionPane.OK_OPTION) {
                            if(admdinjudge==true){
                                // 重置输入框内容
                                componentTools.reset(usernameTextField, passwordField);
                                // 当登录成功后，跳转到主界面
                                new UserFrameadmin().setVisible(true);
                                // 登录成功后同时使登录界面不可见（即消失）
                                this.setVisible(false);
                            }else if(admdinjudge==false){
                            // 重置输入框内容
                            componentTools.reset(usernameTextField, passwordField);
                            // 当登录成功后，跳转到主界面
                            new UserFrameNoadmin().setVisible(true);
                            // 登录成功后同时使登录界面不可见（即消失）
                            this.setVisible(false);
                            }
                        } else {
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "对不起，登录失败！");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "不能为空！");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            // 判断输入框是否为空并做处理
        }
        // 忘记密码按钮的事件处理
        if (e.getSource() == resetButton) {
            new ChiefMaPanel().setVisible(true);
        }

        if (e.getSource() == signupButton) {
            new SignUpFrame().setVisible(true);
        }
    }

}
