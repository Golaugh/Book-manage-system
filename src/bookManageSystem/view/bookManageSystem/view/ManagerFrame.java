package bookManageSystem.view;

import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//登陆成功后管理员界面（应该先显示用户界面，再通过判断显示管理员
public class ManagerFrame extends JFrame implements ActionListener {
    private ComponentTools componentTools = new ComponentTools();

    private JMenuBar menuBar;
    private JMenu bookTypeManageMenu, bookManageMenu, otherMenu;
    private JMenuItem backMenuItem,exitMenuItem, aboutSoftMenuItem, userAddMenuItem, userDeleteMenuItem, userSearchMenuItem,
            userBookMenuItem, bookAddMenuItem, bookDeleteMenuItem, bookSearchMenuItem, bookChangeMenuItem, bookUserMenuItem;

    ManagerFrame() {
        this.setTitle("管理员系统");
        this.setBounds(400, 200, 950,700);
        this.setResizable(false);
        // 为主界面添加菜单条
        this.setJMenuBar(this.createMenuBar());
        // 设置主界面的内容面板
        this.setContentPane(createMainPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置默认不显示
        this.setVisible(false);

        // 为菜单条中的用户管理项注册事件
        userAddMenuItem.addActionListener(this);
        userDeleteMenuItem.addActionListener(this);
        userBookMenuItem.addActionListener(this);
        userSearchMenuItem.addActionListener(this);
        // 为菜单条中的书籍管理项注册事件
        bookAddMenuItem.addActionListener(this);
        bookDeleteMenuItem.addActionListener(this);
        bookSearchMenuItem.addActionListener(this);
        bookChangeMenuItem.addActionListener(this);
        bookUserMenuItem.addActionListener(this);
        // 为菜单条中其他项注册事件
        backMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        aboutSoftMenuItem.addActionListener(this);
    }

    /**
     * 为主界面创建一个菜单条
     *
     * @return 返回菜单条
     */
    private JMenuBar createMenuBar() {
        // 实例化一个菜单条对象
        menuBar = new JMenuBar();

        // 【图书类别管理】菜单
        bookTypeManageMenu = new JMenu("用户管理");
        userAddMenuItem = new JMenuItem("用户添加");
        userDeleteMenuItem = new JMenuItem("用户删除");
        userSearchMenuItem = new JMenuItem("用户查询");
        userBookMenuItem = new JMenuItem("查询用户借阅书籍");
        bookTypeManageMenu.add(userAddMenuItem);
        bookTypeManageMenu.add(userDeleteMenuItem);
        bookTypeManageMenu.add(userSearchMenuItem);
        bookTypeManageMenu.add(userBookMenuItem);
        menuBar.add(bookTypeManageMenu);

        // 【图书管理】菜单
        bookManageMenu = new JMenu("图书管理");
        bookAddMenuItem = new JMenuItem("图书添加");
        bookDeleteMenuItem = new JMenuItem("图书删除");
        bookSearchMenuItem = new JMenuItem("图书查询");
        bookChangeMenuItem = new JMenuItem("图书修改");
        bookUserMenuItem = new JMenuItem("查询图书借阅人员");
        bookManageMenu.add(bookAddMenuItem);
        bookManageMenu.add(bookDeleteMenuItem);
        bookManageMenu.add(bookSearchMenuItem);
        bookManageMenu.add(bookChangeMenuItem);
        bookManageMenu.add(bookUserMenuItem);
        menuBar.add(bookManageMenu);

        // 【其他】菜单
        otherMenu = new JMenu("其他");
        backMenuItem = new JMenuItem("返回");
        exitMenuItem = new JMenuItem("退出");
        aboutSoftMenuItem = new JMenuItem("关于软件");
        otherMenu.add(backMenuItem);
        otherMenu.add(exitMenuItem);
        otherMenu.add(aboutSoftMenuItem);
        menuBar.add(otherMenu);

        // 批量为菜单项设置图标
        componentTools.setIcons(
                new JMenuItem[]{backMenuItem,userAddMenuItem,userDeleteMenuItem, userBookMenuItem,
                        userSearchMenuItem,bookAddMenuItem,bookDeleteMenuItem,bookChangeMenuItem,bookSearchMenuItem,bookUserMenuItem,
                exitMenuItem,aboutSoftMenuItem},
                new String[]{"src/bookManageSystem/images/edit.png","src/bookManageSystem/images/edit.png", "src/bookManageSystem/images/edit.png", "src/bookManageSystem/images/edit.png",
                        "src/bookManageSystem/images/edit.png", "src/bookManageSystem/images/edit.png","src/bookManageSystem/images/edit.png",
                        "src/bookManageSystem/images/edit.png", "src/bookManageSystem/images/edit.png", "src/bookManageSystem/images/edit.png",
                        "src/bookManageSystem/images/exit.png", "src/bookManageSystem/images/about.png"}
        );

        return menuBar;
    }

    /**
     * 为主界面创建内容面板
     *
     * @return 返回一个面板
     */
    private JPanel createMainPanel() {
        // 实例化一个面板
        JPanel panel = new JPanel();
        // 设置面板中的布局方式
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        ImageIcon ii = new ComponentTools().iconSize(new ImageIcon("src/bookManageSystem/images/library_total.png"), 950,700);
        // 设置一个图片标签
        label.setIcon(ii);
        // 将标签添加到面板中
        panel.add(label);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // “用户添加”菜单项事件处理
        if (e.getSource() == userAddMenuItem) {
            // 将主界面的内容面板替换成用户添加面板
            this.setContentPane(new UserAddPanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “用户删除”菜单项事件处理
        if (e.getSource() == userDeleteMenuItem) {
            // 将主界面的内容面板替换成用户删除面板
            this.setContentPane(new UserDeletePanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “查询用户借阅书籍”菜单项事件处理
        if (e.getSource() == userBookMenuItem) {
            // 将主界面的内容面板替换成用户修改面板
            this.setContentPane(new UserBookPanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “用户查询”菜单项事件处理
        if (e.getSource() == userSearchMenuItem) {
            // 将主界面的内容面板替换成用户检索面板
            this.setContentPane(new UserSearchPanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “图书添加”菜单项事件处理
        if (e.getSource() == bookAddMenuItem) {
            // 将主界面的内容面板替换成图书添加面板
            this.setContentPane(new BookAddPanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “图书删除”菜单项事件处理
        if (e.getSource() == bookDeleteMenuItem) {
            // 将主界面的内容面板替换成图书删除面板
            this.setContentPane(new BookDeletePanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “图书查询”菜单项事件处理
        if (e.getSource() == bookSearchMenuItem) {
            // 将主界面的内容面板替换成图书检索面板
            this.setContentPane(new BookSearchPanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “图书修改”菜单项事件处理
        if (e.getSource() == bookChangeMenuItem) {
            // 将主界面的内容面板替换成图书修改面板
            this.setContentPane(new BookChangePanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “图书借阅人查询”菜单项事件处理
        if (e.getSource() == bookUserMenuItem) {
            // 将主界面的内容面板替换成图书借阅人查询面板
            this.setContentPane(new BookUserPanel());
            // 并设置该面板显示
            this.setVisible(true);
        }
        // “返回”菜单项事件处理
        if (e.getSource() == backMenuItem) {
            // 回退窗口
            this.dispose();
            new UserFrameadmin().setVisible(true);
        }
        // “退出”菜单项事件处理
        if (e.getSource() == exitMenuItem) {
            // 结束整个程序
            System.exit(0);
        }
        // “关于软件”菜单项事件处理
        if (e.getSource() == aboutSoftMenuItem) {
            // 设置关于软件提示框显示
            new AboutSoftDialog().setVisible(true);
        }
    }
}
