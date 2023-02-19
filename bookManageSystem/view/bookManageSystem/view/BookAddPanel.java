package bookManageSystem.view;

import bookManageSystem.dao.BookTypeDao;
import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//按键“用户添加"响应界面
public class BookAddPanel extends JPanel implements ActionListener {

    private ComponentTools componentTools = new ComponentTools();
    private Box totalVBox, funcationHBox, nameandauthorHBox, publishandnumHBox, descriptionHBox, buttonHBox;
    private JButton alterButton;
    private JLabel bookTypeManageLabel, authorLabel, nameLabel, publishLabel, numLabel;
    private JTextField authorTextField, nameTextField, publishTextField, numTextField;
    private JTextArea descriptionTextArea;

    BookAddPanel() {
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
        bookTypeManageLabel = new JLabel("图书添加");
        bookTypeManageLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        funcationHBox.add(bookTypeManageLabel);
        totalVBox.add(funcationHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        nameTextField = new JTextField(10);
        authorTextField = new JTextField(10);

        nameandauthorHBox = Box.createHorizontalBox();
        nameLabel = new JLabel("书名：");
        authorLabel = new JLabel("作者：");
        nameandauthorHBox.add(nameLabel);
        nameandauthorHBox.add(Box.createHorizontalStrut(20));
        nameandauthorHBox.add(nameTextField);
        nameandauthorHBox.add(Box.createHorizontalStrut(20));
        nameandauthorHBox.add(authorLabel);
        nameandauthorHBox.add(Box.createHorizontalStrut(20));
        nameandauthorHBox.add(authorTextField);

        publishTextField = new JTextField(10);
        numTextField = new JTextField(10);

        publishandnumHBox = Box.createHorizontalBox();
        publishLabel = new JLabel("出版社");
        numLabel = new JLabel("数量");
        publishandnumHBox.add(publishLabel);
        publishandnumHBox.add(Box.createHorizontalStrut(20));
        publishandnumHBox.add(publishTextField);
        publishandnumHBox.add(Box.createHorizontalStrut(20));
        publishandnumHBox.add(numLabel);
        publishandnumHBox.add(Box.createHorizontalStrut(20));
        publishandnumHBox.add(numTextField);

        totalVBox.add(nameandauthorHBox);
        totalVBox.add(Box.createVerticalStrut(50));
        totalVBox.add(publishandnumHBox);
        totalVBox.add(Box.createVerticalStrut(50));

        descriptionHBox = Box.createHorizontalBox();
        numLabel = new JLabel("操作结果：");
        descriptionTextArea = new JTextArea(2, 20);
        descriptionTextArea.setLineWrap(true);
        descriptionHBox.add(numLabel);
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
        if (e.getSource() == alterButton) {
            String id = nameTextField.getText();
            //descriptionTextArea.setText(id);
            String zuozhe = authorTextField.getText();
            String chubanshe =publishTextField.getText();
            //String description = descriptionTextArea.getText();
            String sum=numTextField.getText();
            int num = Integer.parseInt(sum);
            // 组装修改的SQL语句（需要替换为数据库语句）
            //descriptionTextArea.setText(String.valueOf(num));
            //连接数据库*/

            try{
                boolean isok=false;
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=图书管理系统";
                Connection con;
                con = DriverManager.getConnection(url, "sa", "123456");
                Statement stmt = con.createStatement();
                ResultSet rs0=stmt.executeQuery("select *from bookshelf");
                String[] bookname=new String[1000];
                //String[] authername=new String[1000];
                int[] numss=new int[1000];
                int i=1;int shu=0;

                while (rs0.next()){
                    bookname[i]=rs0.getString(1);
                    numss[i]=rs0.getInt(5);
                    //authername[i]= rs0.getString(2);
                    i++;
                }
                //descriptionTextArea.setText(String.valueOf(numss[1]));
                for (int j=1;j<=i;j++){
                    if((nameTextField.getText()+"，"+authorTextField.getText()).equals(bookname[j])) {
                        shu=numss[j];
                        shu+=num;
                        String newid=(nameTextField.getText()+"，"+authorTextField.getText());
                        String sql1="update bookshelf set 数量='"+shu+"' where 书名_作者='"+newid+"'";
                        Statement stmt1=con.createStatement();
                        stmt1.executeUpdate(sql1);
                        isok=true;
                        break;
                    }
                }
                //descriptionTextArea.setText(String.valueOf(shu));
                if(isok==false){
                PreparedStatement sql2 = con.prepareStatement("insert into bookshelf"+" values(?,?,?,?,?)");
                sql2.setString(1,id+"，"+zuozhe);
                sql2.setString(2,id);
                sql2.setString(3,zuozhe);
                sql2.setString(4,chubanshe);
                sql2.setInt(5,num);
                sql2.executeUpdate();
                isok=true;
                }
                // 对修改结果进行判定
                if (isok) {
                    // 添加成功则输出“成功添加！”
                    descriptionTextArea.setText("成功添加！");
                    // 重置各输入框
                    //new ComponentTools().reset(nameTextField, authorTextField, descriptionTextArea);
                    // 弹出提示框
                    JOptionPane.showMessageDialog(null, "修改成功！");
                } else {
                    // 修改失败也弹出提示框
                    JOptionPane.showMessageDialog(null, "修改失败！");
                }

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }





        }
    }
}
