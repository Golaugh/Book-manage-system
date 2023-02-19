package bookManageSystem.view;

import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//按键“用户添加"响应界面
public class UserAddPanel extends JPanel implements ActionListener {

    private ComponentTools componentTools = new ComponentTools();
    private Box totalVBox, funcationHBox, nameandclassLabel, manegerandcodeHBox, descriptionHBox, buttonHBox, p_repHBox;
    private JButton alterButton;
    private JLabel bookTypeManageLabel, nameLabel, idLabel, managerLabel, adminLabel, pwLabel, re_pwLabel;
    private JTextField nameTextField, idTextField, classTextField, adminTextField, pwTextField, re_pwTextField;
    private JTextArea descriptionTextArea;

    UserAddPanel() {
        // 添加控件到图书类别维护面板中
        this.add(createBookTypeManageVBox());
        // 为按钮批量设置图标
        componentTools.setIcons(new JButton[]{alterButton}, new String[]{"src/bookManageSystem/images/edit.png"});
        // 为按钮注册事件监听器
        alterButton.addActionListener(this);
    }

    /**
     * 图书类别维护面板中的控件内容
     *
     * @return 返回一个Box
     */
    private Box createBookTypeManageVBox() {
        totalVBox = Box.createVerticalBox();
        totalVBox.add(Box.createVerticalStrut(40));

        funcationHBox = Box.createHorizontalBox();
        bookTypeManageLabel = new JLabel("用户添加");
        bookTypeManageLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        funcationHBox.add(bookTypeManageLabel);
        totalVBox.add(funcationHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        idTextField = new JTextField(10);
        nameTextField = new JTextField(10);

        nameandclassLabel = Box.createHorizontalBox();
        idLabel = new JLabel(" 学 号：");
        nameLabel = new JLabel(" 姓 名：");
        nameandclassLabel.add(idLabel);
        nameandclassLabel.add(Box.createHorizontalStrut(20));
        nameandclassLabel.add(idTextField);
        nameandclassLabel.add(Box.createHorizontalStrut(20));
        nameandclassLabel.add(nameLabel);
        nameandclassLabel.add(Box.createHorizontalStrut(20));
        nameandclassLabel.add(nameTextField);

        totalVBox.add(nameandclassLabel);
        totalVBox.add(Box.createVerticalStrut(50));

        classTextField = new JTextField(10);
        adminTextField = new JTextField(10);

        manegerandcodeHBox = Box.createHorizontalBox();
        managerLabel = new JLabel(" 班 级：");
        adminLabel = new JLabel(" 管理员：");
        manegerandcodeHBox.add(managerLabel);
        manegerandcodeHBox.add(Box.createHorizontalStrut(20));
        manegerandcodeHBox.add(classTextField);
        manegerandcodeHBox.add(Box.createHorizontalStrut(20));
        manegerandcodeHBox.add(adminLabel);
        manegerandcodeHBox.add(Box.createHorizontalStrut(20));
        manegerandcodeHBox.add(adminTextField);

        p_repHBox = Box.createHorizontalBox();
        pwLabel = new JLabel(" 密 码:");
        re_pwLabel = new JLabel("确认密码:");
        pwTextField = new JTextField(20);
        re_pwTextField = new JTextField(20);
        p_repHBox.add(pwLabel);
        p_repHBox.add(Box.createHorizontalStrut(20));
        p_repHBox.add(pwTextField);
        p_repHBox.add(Box.createHorizontalStrut(20));
        p_repHBox.add(re_pwLabel);
        p_repHBox.add(Box.createHorizontalStrut(20));
        p_repHBox.add(re_pwTextField);

        totalVBox.add(manegerandcodeHBox);
        totalVBox.add(Box.createVerticalStrut(50));
        totalVBox.add(p_repHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        descriptionHBox = Box.createHorizontalBox();
        adminLabel = new JLabel("操作结果：");
        descriptionTextArea = new JTextArea(2, 20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);
        descriptionHBox.add(adminLabel);
        descriptionHBox.add(Box.createHorizontalStrut(20));
        descriptionHBox.add(descriptionTextArea);
        totalVBox.add(descriptionHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        buttonHBox = Box.createHorizontalBox();
        alterButton = new JButton("添加！");
        buttonHBox.add(alterButton);
        totalVBox.add(buttonHBox);

        return totalVBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // “修改”按钮的事件处理
        try{
        if (e.getSource() == alterButton) {
            // 获取各输入框的内容
            String id =idTextField.getText();
            String name = nameTextField.getText();
            String banji=classTextField.getText();
            String password = pwTextField.getText();
            String repassword=re_pwTextField.getText();
            String AD=adminTextField.getText();
            boolean adj=false;
            boolean isok=false;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=图书管理系统";
            Connection con;
            con = DriverManager.getConnection(url, "sa", "123456");
            Statement stmt = con.createStatement();
            if(AD.equals("yes"))
                adj=true;
            else adj=false;
            if(adj){
                PreparedStatement sql2 = con.prepareStatement("insert into users"+" values(?,?,?,?,?,?)");
                sql2.setInt(1, Integer.parseInt(id));
                sql2.setString(2,name);
                sql2.setString(3,banji);
                sql2.setInt(4,5);
                sql2.setBoolean(5,adj);
                sql2.setString(6,password);
                sql2.executeUpdate();
                isok=true;
            }else {
                PreparedStatement sql2 = con.prepareStatement("insert into users"+" values(?,?,?,?,?,?)");
                sql2.setInt(1, Integer.parseInt(id));
                sql2.setString(2,name);
                sql2.setString(3,banji);
                sql2.setInt(4,5);
                sql2.setBoolean(5,adj);
                sql2.setString(6,password);
                sql2.executeUpdate();
                isok=true;
            }
            if (isok) {
                // 添加成功则输出“成功添加！”
                descriptionTextArea.setText("成功添加！");
                // 重置各输入框
                new ComponentTools().reset(idTextField, nameTextField, descriptionTextArea);
                // 弹出提示框
                JOptionPane.showMessageDialog(null, "修改成功！");
            } else {
                // 修改失败也弹出提示框
                JOptionPane.showMessageDialog(null, "修改失败！");
            }
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
