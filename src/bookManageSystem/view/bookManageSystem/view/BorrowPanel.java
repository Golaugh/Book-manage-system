package bookManageSystem.view;

import bookManageSystem.dao.BookTypeDao;
import bookManageSystem.tools.ComponentTools;
import bookManageSystem.tools.SimpleTools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//按键“借书"响应界面
public class BorrowPanel extends JPanel implements ActionListener, ListSelectionListener {
    private ComponentTools componentTools = new ComponentTools();

    private Box totalVBox, funcationHBox, bookTypeCheckHBox, tableHBox,
            publishandnumHBox, nameandauthorLabel, descriptionHBox, buttonHBox;
    private JButton checkButton, borrowButton, resetButton;
    private JLabel bookTypeManageLabel, bookTypeNameLabel1, authorLabel,
            publishLabel, nameLabel, descriptionLabel, numLabel;
    private JTextField bookTypeNameTextField1, authorTextField, nameTextField, publishTextField, numTextField;
    private JTextArea descriptionTextArea;
    private JScrollPane tableScrollPanel;
    private JTable table;
    private DefaultTableModel tableModel;

    BorrowPanel() {
        // 添加控件到图书类别维护面板中
        this.add(createBookTypeManageVBox());
        // 为按钮批量设置图标
        componentTools.setIcons(new JButton[]{borrowButton, resetButton}, new String[]{"src/bookManageSystem/images/edit.png", "src/bookManageSystem/images/delete.png"});
        // 为按钮注册事件监听器
        checkButton.addActionListener(this);
        borrowButton.addActionListener(this);
        resetButton.addActionListener(this);
        // 为表格注册监听器
        table.getSelectionModel().addListSelectionListener(this);
    }

    /**
     * 图书类别维护面板中的控件内容
     *
     * @return 返回一个Box
     */
    private Box createBookTypeManageVBox() {
        totalVBox = Box.createVerticalBox();

        funcationHBox = Box.createHorizontalBox();
        bookTypeManageLabel = new JLabel("借书系统");
        bookTypeManageLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        funcationHBox.add(bookTypeManageLabel);
        totalVBox.add(funcationHBox);
        totalVBox.add(Box.createVerticalStrut(20));

        bookTypeCheckHBox = Box.createHorizontalBox();
        bookTypeNameLabel1 = new JLabel("输入书名：");
        bookTypeNameTextField1 = new JTextField(10);
        checkButton = new JButton("查询");
        bookTypeCheckHBox.add(bookTypeNameLabel1);
        bookTypeCheckHBox.add(Box.createHorizontalStrut(30));
        bookTypeCheckHBox.add(bookTypeNameTextField1);
        bookTypeCheckHBox.add(Box.createHorizontalStrut(30));
        bookTypeCheckHBox.add(checkButton);
        totalVBox.add(bookTypeCheckHBox);
        totalVBox.add(Box.createVerticalStrut(20));

        tableHBox = Box.createHorizontalBox();
        // 实例化一个滚动面板
        tableScrollPanel = new JScrollPane();
        // 将表格设置为滚动面板内容视图
        tableScrollPanel.setViewportView(this.createTable("select 书名_作者,书名,作者,出版社,数量 from bookshelf"));
        // 设置滚动面板预定义尺寸
        tableScrollPanel.setPreferredSize(new Dimension(700, 250));
        // 将滚动面板添加到HBox中
        tableHBox.add(tableScrollPanel);
        totalVBox.add(tableHBox);
        totalVBox.add(Box.createVerticalStrut(20));

        nameandauthorLabel = Box.createHorizontalBox();
        nameLabel = new JLabel("书名：");
        nameTextField = new JTextField(10);
        nameTextField.setEditable(false);
        authorLabel = new JLabel("作者：");
        authorTextField = new JTextField(10);
        authorTextField.setEditable(false);
        nameandauthorLabel.add(nameLabel);
        nameandauthorLabel.add(Box.createHorizontalStrut(20));
        nameandauthorLabel.add(nameTextField);
        nameandauthorLabel.add(Box.createHorizontalStrut(20));
        nameandauthorLabel.add(authorLabel);
        nameandauthorLabel.add(Box.createHorizontalStrut(20));
        nameandauthorLabel.add(authorTextField);
        totalVBox.add(nameandauthorLabel);
        totalVBox.add(Box.createVerticalStrut(20));

        publishandnumHBox = Box.createHorizontalBox();
        publishLabel = new JLabel("出版社");
        numLabel = new JLabel("数量");
        numTextField = new JTextField();
        numTextField.setEditable(false);
        publishTextField = new JTextField();
        publishTextField.setEditable(false);
        publishandnumHBox.add(publishLabel);
        publishandnumHBox.add(Box.createHorizontalStrut(20));
        publishandnumHBox.add(publishTextField);
        publishandnumHBox.add(Box.createHorizontalStrut(20));
        publishandnumHBox.add(numLabel);
        publishandnumHBox.add(Box.createHorizontalStrut(20));
        publishandnumHBox.add(numTextField);

        totalVBox.add(publishandnumHBox);
        totalVBox.add(Box.createVerticalStrut(20));

        descriptionHBox = Box.createHorizontalBox();
        descriptionLabel = new JLabel("操作结果：");
        descriptionTextArea = new JTextArea(2, 40);
        descriptionTextArea.setLineWrap(true);
        descriptionHBox.add(descriptionLabel);
        descriptionHBox.add(Box.createHorizontalStrut(20));
        descriptionHBox.add(descriptionTextArea);
        totalVBox.add(descriptionHBox);
        totalVBox.add(Box.createVerticalStrut(20));

        buttonHBox = Box.createHorizontalBox();
        borrowButton = new JButton("借书！");
        resetButton = new JButton("重置！");
        buttonHBox.add(borrowButton);
        buttonHBox.add(Box.createHorizontalStrut(80));
        buttonHBox.add(resetButton);
        totalVBox.add(buttonHBox);

        return totalVBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JsonCache sb=new JsonCache();
        String namess= (String) sb.READFLIE("name");
        // “查询”按钮的事件处理
        if (e.getSource() == checkButton) {
            // 组装借书的SQL语句
            String sql = "";
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=图书管理系统";
                Connection con;
                con = DriverManager.getConnection(url, "sa", "123456");
                Statement stmt = con.createStatement();
                // 判断图书类别输入框是否为空并做处理
                if (!new SimpleTools().isEmpty(bookTypeNameTextField1.getText())) {
                    // 为空即查询所有图书类别
                    sql = "select * from bookshelf;";
                    String[][] rowdatas = new BookTypeDao().ListTobookshelfArray(new BookTypeDao().getRecordsDataBySql(sql));
                    // 表头数据
                    String[] headers = {"书名_作者","书名", "作者", "出版社", "数量"};
                    // 填充表格模型，刷新表格
                    tableModel.setDataVector(rowdatas, headers);
                } else {
                    // 不为空即按条件查询图书类别
                    sql = "select * from bookshelf where 书名='" + bookTypeNameTextField1.getText() + "';";
                    String checkresult = "select count(*) from bookshelf where 书名='" + bookTypeNameTextField1.getText() + "';";
                    rs = stmt.executeQuery(checkresult);
                    rs.next();
                    int row = rs.getInt(1);
                        if(row != 0)
                        {
                            //如果查到书籍的数量不为空，显示数据库中该书籍所有信息
                            // 查询结果转换成二维数组
                            String[][] rowdatas = new BookTypeDao().ListTobookshelfArray(new BookTypeDao().getRecordsDataBySql(sql));
                            // 表头数据
                            String[] headers = {"书名_作者","书名", "作者", "出版社", "数量"};
                            // 填充表格模型，刷新表格
                            tableModel.setDataVector(rowdatas, headers);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"没有找到该书籍");
                        }

                }
                // 执行查询书籍数量操作并返回数据库中书籍数量


            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        }
        // “借书”按钮的事件处理
        if (e.getSource() == borrowButton) {
            // 获取各输入框的内容
            String bookname = nameTextField.getText();
            String bookauthor = authorTextField.getText();
            String aaaaaa=bookname+"，"+bookauthor;
            // 组装借书的SQL语句
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=图书管理系统";
                Connection con;
                con = DriverManager.getConnection(url, "sa", "123456");
                Statement stmt = con.createStatement();
                //判断用户可借书数量，被借书籍剩余数量
                String sql5="select 剩余可借数目 from users where 姓名='"+namess+"'";

                ResultSet rs5=stmt.executeQuery(sql5);
                int un=0;int bookn=0;
                while (rs5.next()){
                    un=rs5.getInt(1);
                }
                String sql6="select 数量 from bookshelf where 书名_作者='"+aaaaaa+"'";// 执行修改操作并返回结果
                ResultSet rs6=stmt.executeQuery(sql6);
                while (rs6.next()){
                bookn=rs6.getInt(1);
                }


                boolean isOK=false;
                if(un>=1&&bookn>=1) {
                    ResultSet rs7=stmt.executeQuery("select 书名 from imfo where 姓名='"+namess+"'");
                    String bookex[]=new String[6];
                    int i=1;
                    boolean isOK1=false;
                    while (rs7.next()){
                        if (rs7.getString(1).equals(bookname)) {
                                isOK1 = true;
                            }
                    }

                    if(isOK1){
                        isOK=false;
                    }else isOK=true;




                } else isOK=false;


                if (isOK) {

                    // 弹出提示框
                    JOptionPane.showMessageDialog(null, "借书成功！");
                    // 借书成功后更新数据库
                    System.out.println(aaaaaa);
                    String sql3 = "update bookshelf set 数量=数量-1 where 书名_作者='"+aaaaaa+"'" ;
                    String sql4 = "update users set 剩余可借数目=剩余可借数目-1 where 姓名='"+namess+"'";
                    Statement stmt1=con.createStatement();
                    stmt1.executeUpdate(sql3);
                    stmt1.executeUpdate(sql4);
                    //借书成功，更新imfo表
                    Date utilDate = new Date();//util.Date

                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  //JAVA获取时间，并且转化为sql形式
                    String sql2 = "insert into imfo values('"+bookname+"','"+sqlDate+"','2022-10-10','"+namess+"')";
                    isOK = new BookTypeDao().dataChange(sql2);
                    // 修改成功则刷新表格
                    refreshTable();
                    // 重置各输入框
                    new ComponentTools().reset(nameTextField, authorTextField, descriptionTextArea,
                            publishTextField, numTextField);
                } else {
                    // 修改失败也弹出提示框
                    JOptionPane.showMessageDialog(null, "借书失败！请确认自己借了多少书或者是否借了一样的书");
                }

            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        }

        // 重置按钮的事件处理
        if (e.getSource() == resetButton) {
            // 即清空输入框的内容
            componentTools.reset(bookTypeNameTextField1,nameTextField, authorTextField, descriptionTextArea,
                    publishTextField, numTextField);
        }
    }

    /**
     * 根据SQL查询得到的数据填充一个表格控件
     *
     * @param sql SQL语句
     * @return 返回一个JTable控件
     */
    private JTable createTable(String sql) {
        // 将表格数据转换成一个二维数组
        String[][] rowdatas = new BookTypeDao().ListTobookshelfArray(new BookTypeDao().getRecordsDataBySql(sql));
        // 表头内容
        String[] headers = {"书名_作者","书名", "作者", "出版社", "数量"};
        // 实例化一个表格
        table = new JTable();
        // 设置行高
        table.setRowHeight(30);
        // 将表头数据和表格内容数据填充到默认表格模型中，使用表格模型方便获取表格数据内容和刷新表格
        tableModel = new DefaultTableModel(rowdatas, headers);
        // 设置表格的模型
        table.setModel(tableModel);
        // 返回表格控件
        return table;
    }

    /**
     * 刷新表格数据
     */
    private void refreshTable() {
        // 查询所有的图书类别数据信息
        String sql = "select * from bookshelf;";
        // 将获取的表格数据转换成二维数组
        String[][] rowdatas = new BookTypeDao().ListTobookshelfArray(new BookTypeDao().getRecordsDataBySql(sql));
        // 表头数据
        String[] headers = {"书名_作者","书名", "作者", "出版社", "数量"};
        // 重新填充表格模型，即刷新表格数据
        tableModel.setDataVector(rowdatas, headers);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // 获取表格被选中行
        int getSelectedRowIndex = table.getSelectedRow();
        // 判断是否未选中行
        if (getSelectedRowIndex == -1) {
            refreshTable();
        } else {
            // 将选中的表格行数据依次填充到下面的控件中
            nameTextField.setText((String) table.getValueAt(getSelectedRowIndex, 1));
            authorTextField.setText((String) table.getValueAt(getSelectedRowIndex, 2));
            publishTextField.setText((String) table.getValueAt(getSelectedRowIndex, 3));
            numTextField.setText((String) table.getValueAt(getSelectedRowIndex, 4));
        }
    }
}
