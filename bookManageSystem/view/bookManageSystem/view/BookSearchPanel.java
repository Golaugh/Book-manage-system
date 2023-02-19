package bookManageSystem.view;

import bookManageSystem.dao.BookTypeDao;
import bookManageSystem.tools.ComponentTools;
import bookManageSystem.tools.SimpleTools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//按键“图书查询"响应界面
public class BookSearchPanel extends JPanel implements ActionListener, ListSelectionListener {
    private ComponentTools componentTools = new ComponentTools();

    private Box totalVBox, funcationHBox, bookTypeCheckHBox, tableHBox;
    private JButton checkButton;
    private JLabel bookTypeManageLabel, bookTypeNameLabel1;
    private JTextField bookTypeNameTextField1;
    private JScrollPane tableScrollPanel;
    private JTable table;
    private DefaultTableModel tableModel;

    BookSearchPanel() {
        // 添加控件到图书类别维护面板中
        this.add(createBookTypeManageVBox());
        // 为按钮注册事件监听器
        checkButton.addActionListener(this);
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
        bookTypeManageLabel = new JLabel("图书查询");
        bookTypeManageLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        funcationHBox.add(bookTypeManageLabel);
        totalVBox.add(funcationHBox);
        totalVBox.add(Box.createVerticalStrut(20));

        bookTypeCheckHBox = Box.createHorizontalBox();
        bookTypeNameLabel1 = new JLabel("书名：");
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

        return totalVBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // “查询”按钮的事件处理
        if (e.getSource() == checkButton) {
            String sql = "";
            // 判断图书类别输入框是否为空并做处理
            if (!new SimpleTools().isEmpty(bookTypeNameTextField1.getText())) {
                // 为空即查询所有图书类别
                sql = "select * from bookshelf;";
            } else {
                // 不为空即按条件查询图书类别
                sql = "select * from bookshelf where 书名='" + bookTypeNameTextField1.getText() + "';";
            }
            // 查询结果转换成二维数组
            String[][] rowdatas = new BookTypeDao().ListTobookshelfArray(new BookTypeDao().getRecordsDataBySql(sql));
            // 表头数据
            String[] headers = {"书名_作者","书名", "作者", "出版社", "数量"};
            // 填充表格模型，刷新表格
            tableModel.setDataVector(rowdatas, headers);
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
        }
    }
}
