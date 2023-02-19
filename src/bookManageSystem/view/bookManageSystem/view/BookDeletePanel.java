package bookManageSystem.view;

import bookManageSystem.dao.BookTypeDao;
import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//按键“用户添加"响应界面
public class BookDeletePanel extends JPanel implements ActionListener {

    private ComponentTools componentTools = new ComponentTools();
    private Box totalVBox, funcationHBox, nameHBox, descriptionHBox, buttonHBox;
    private JButton alterButton;
    private JLabel bookTypeManageLabel, nameLabel, descriptionLabel;
    private JTextField nameTextField, numTextField;
    private JTextArea descriptionTextArea;

    BookDeletePanel() {
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
        bookTypeManageLabel = new JLabel("图书删除");
        bookTypeManageLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        funcationHBox.add(bookTypeManageLabel);
        totalVBox.add(funcationHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        nameTextField = new JTextField(10);

        nameHBox = Box.createHorizontalBox();
        nameLabel = new JLabel("书名：");
        nameHBox.add(nameLabel);
        nameHBox.add(Box.createHorizontalStrut(20));
        nameHBox.add(nameTextField);

        numTextField = new JTextField(10);

        totalVBox.add(nameHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        descriptionLabel = new JLabel("操作结果：");
        descriptionHBox = Box.createHorizontalBox();
        descriptionTextArea = new JTextArea(2, 20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);
        descriptionHBox.add(descriptionLabel);
        descriptionHBox.add(Box.createHorizontalStrut(20));
        descriptionHBox.add(descriptionTextArea);
        totalVBox.add(descriptionHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        buttonHBox = Box.createHorizontalBox();
        alterButton = new JButton("删除！（警告：随意删除图书信息可能导致未还书信息丢失！）");
        buttonHBox.add(alterButton);
        totalVBox.add(buttonHBox);

        return totalVBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // “删除”按钮的事件处理
        if (e.getSource() == alterButton) {
            // 获取各输入框的内容
            String id = nameTextField.getText();
            String description = descriptionTextArea.getText();
            // 组装修改的SQL语句（需要替换为数据库语句）
            // 执行修改操作并返回结果
            boolean isok=false;
            try{

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=图书馆系统";
                Connection con;
                con = DriverManager.getConnection(url, "sa", "123456");
                Statement stmt = con.createStatement();
                ResultSet rs0=stmt.executeQuery("select *from bookshelf");
                String[] bookname=new String[1000];
                int i=1;
                while (rs0.next()){
                    bookname[i]=rs0.getString(2);
                    i++;

                }

                for(int j=1;j<=i;j++){
                    if(id.equals(bookname[j])){
                        String sql11="delete from bookshelf where 书名='"+id+"'";
                        Statement stmt1=con.createStatement();
                        stmt1.executeUpdate(sql11);
                        isok=true;
                        break;
                    }else isok=false;
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

           // boolean isOK = new BookTypeDao().dataChange(alterSQL);
            // 对修改结果进行判定
            if (isok) {
                // 添加成功则输出“成功添加！”
                descriptionTextArea.setText("成功添加！");
                // 重置各输入框
                new ComponentTools().reset(nameTextField, descriptionTextArea);
                // 弹出提示框
                JOptionPane.showMessageDialog(null, "删除成功！");
            } else {
                // 修改失败也弹出提示框
                JOptionPane.showMessageDialog(null, "删除失败！");
            }
        }
    }
}
