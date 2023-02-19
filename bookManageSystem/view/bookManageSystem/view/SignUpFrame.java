package bookManageSystem.view;

import bookManageSystem.dao.BookTypeDao;
import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//按键“注册账户"响应界面
public class SignUpFrame extends JFrame implements ActionListener {

    private ComponentTools componentTools = new ComponentTools();
    private Box totalVBox, funcationHBox, nameHBox, authorHBox, renumHBox,
            publishHBox, numHBox, descriptionHBox, buttonHBox;
    private JButton alterButton, backButton;
    private JLabel bookTypeManageLabel, authorLabel, nameLabel, publishLabel, numLabel, re_numLabel;
    private JTextField authorTextField, nameTextField, publishTextField;
    private TextField numTextField, re_numTextField;
    private JTextArea descriptionTextArea;

    SignUpFrame() {

        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon
                ("src/bookManageSystem/images/signupbackground.png");
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

        this.setContentPane(this.createBookTypeManageVBox());
        this.setTitle("用户注册界面");
        this.setBounds(400, 200, img.getIconWidth(), img.getIconHeight());


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);

        // 为按钮批量设置图标
        componentTools.setIcons(new JButton[]{alterButton, backButton}, new String[]{"src/bookManageSystem/images/edit.png","src/bookManageSystem/images/edit.png"});
        // 为按钮注册事件监听器
        alterButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    /**
     * 图书类别维护面板中的控件内容
     *
     * @return 返回一个Box
     */
    private JPanel createBookTypeManageVBox() {
        JPanel panel = new JPanel();
        totalVBox = Box.createVerticalBox();


        funcationHBox = Box.createHorizontalBox();
        bookTypeManageLabel = new JLabel("用户注册界面");
        bookTypeManageLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        funcationHBox.add(bookTypeManageLabel);

        nameTextField = new JTextField(20);
        authorTextField = new JTextField(20);

        nameHBox = Box.createHorizontalBox();
        nameLabel = new JLabel(" 姓 名 ：");
        authorLabel = new JLabel(" 班 级 ：");
        nameHBox.add(nameLabel);
        nameHBox.add(Box.createHorizontalStrut(20));
        nameHBox.add(nameTextField);
        authorHBox = Box.createHorizontalBox();
        authorHBox.add(authorLabel);
        authorHBox.add(Box.createHorizontalStrut(20));
        authorHBox.add(authorTextField);

        publishTextField = new JTextField(20);
        numTextField = new TextField(20);
        re_numTextField = new TextField(20);
        numTextField.setEchoChar('*');
        re_numTextField.setEchoChar('*');

        publishHBox = Box.createHorizontalBox();
        publishLabel = new JLabel("用户名：");
        numLabel = new JLabel(" 密 码 ：");
        re_numLabel = new JLabel("确认密码：");
        publishHBox.add(publishLabel);
        publishHBox.add(Box.createHorizontalStrut(20));
        publishHBox.add(publishTextField);
        numHBox = Box.createHorizontalBox();
        numHBox.add(numLabel);
        numHBox.add(Box.createHorizontalStrut(20));
        numHBox.add(numTextField);

        renumHBox = Box.createHorizontalBox();
        renumHBox.add(re_numLabel);
        renumHBox.add(Box.createHorizontalStrut(20));
        renumHBox.add(re_numTextField);

        descriptionHBox = Box.createHorizontalBox();
        numLabel = new JLabel("操作结果：");
        descriptionTextArea = new JTextArea(2, 10);
        descriptionTextArea.setEditable(false);
        descriptionHBox.add(numLabel);
        descriptionHBox.add(Box.createHorizontalStrut(20));
        descriptionHBox.add(descriptionTextArea);

        buttonHBox = Box.createHorizontalBox();
        alterButton = new JButton("添加！");
        backButton = new JButton("返回！");

        buttonHBox.add(alterButton);
        buttonHBox.add(Box.createHorizontalStrut(20));
        buttonHBox.add(backButton);

        totalVBox.add(Box.createVerticalStrut(40));
        totalVBox.add(funcationHBox);
        totalVBox.add(Box.createVerticalStrut(30));
        totalVBox.add(nameHBox);
        totalVBox.add(Box.createVerticalStrut(30));
        totalVBox.add(authorHBox);
        totalVBox.add(Box.createVerticalStrut(30));
        totalVBox.add(publishHBox);
        totalVBox.add(Box.createVerticalStrut(30));
        totalVBox.add(numHBox);
        totalVBox.add(Box.createVerticalStrut(30));
        totalVBox.add(renumHBox);
        totalVBox.add(Box.createVerticalStrut(30));
        totalVBox.add(descriptionHBox);
        totalVBox.add(Box.createVerticalStrut(20));
        totalVBox.add(buttonHBox);

        panel.add(totalVBox);
        panel.setOpaque(false);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // “修改”按钮的事件处理
        if (e.getSource() == alterButton) {
            //判断两次密码输入相同
            if (!numTextField.getText().equals(re_numTextField.getText()))
            {descriptionTextArea.setText("密码确认错误！");}
            // 获取各输入框的内容
            String id = nameTextField.getText();
            String name = authorTextField.getText();
            String description = descriptionTextArea.getText();
            // 组装修改的SQL语句（需要替换为数据库语句）
            String alterSQL = "update tb_booktype set btName='" + name + "',btDescription='" + description + "' where" +
                    " btId=" + id + ";";
            // 执行修改操作并返回结果
            boolean isOK = new BookTypeDao().dataChange(alterSQL);
            // 对修改结果进行判定
            if (isOK) {
                // 添加成功则输出“成功添加！”
                descriptionTextArea.setText("成功添加！");
                // 重置各输入框
                new ComponentTools().reset(nameTextField, authorTextField, descriptionTextArea);
                // 弹出提示框
                JOptionPane.showMessageDialog(null, "添加成功！");
            } else {
                // 修改失败也弹出提示框
                JOptionPane.showMessageDialog(null, "添加失败！");
            }
        }

        if (e.getSource() == backButton) {
            this.dispose();
            new LogupFrame().setVisible(true);
        }
    }
}
