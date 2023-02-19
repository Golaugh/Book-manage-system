package bookManageSystem.view;

import bookManageSystem.tools.ComponentTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//登陆成功后管理员界面（应该先显示用户界面，再通过判断显示管理员
public class UserFrameadmin extends JFrame implements ActionListener {
    private ComponentTools componentTools = new ComponentTools();

    private JMenuBar menuBar;
    private JMenu aboutMenu, otherMenu;
    private JMenuItem backMenuItem, userMenuItem, exitMenuItem, aboutSoftMenuItem;
    private JButton borrow,withdraw,search,manager;

    UserFrameadmin() {
        this.setResizable(false);
        this.setTitle("欢迎！");
        this.setBounds(400, 200, 950, 700);

        // 为主界面添加菜单条
        this.setJMenuBar(this.createMenuBar());
        // 设置主界面的内容面板
        this.setContentPane(createMainPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置默认不显示
        this.setVisible(false);

        // 为菜单条中其他项注册事件
        backMenuItem.addActionListener(this);
        userMenuItem.addActionListener(this);
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

        // 【其他】菜单
        aboutMenu = new JMenu("相关信息");
        otherMenu = new JMenu("其它");
        backMenuItem = new JMenuItem("返回");
        userMenuItem = new JMenuItem("用户信息");
        exitMenuItem = new JMenuItem("退出");
        aboutSoftMenuItem = new JMenuItem("关于软件");
        aboutMenu.add(backMenuItem);
        aboutMenu.add(userMenuItem);
        otherMenu.add(exitMenuItem);
        otherMenu.add(aboutSoftMenuItem);
        menuBar.add(aboutMenu);
        menuBar.add(otherMenu);

        //实例化按钮：借书、还书、查书、管理员
        borrow = new JButton(" 借  书 ");
        withdraw = new JButton(" 还  书 ");
        search = new JButton(" 查  书 ");
        manager = new JButton(" 管理员！");

        //注册事件
        borrow.addActionListener(this);
        withdraw.addActionListener(this);
        search.addActionListener(this);
        manager.addActionListener(this);

        // 批量为菜单项设置图标
        componentTools.setIcons(
                new JMenuItem[]{backMenuItem,userMenuItem,exitMenuItem,aboutSoftMenuItem},
                new String[]{"src/bookManageSystem/images/about.png","src/bookManageSystem/images/me.png",
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
        // 实例化一个面板用于放强哥，实例化三个box来调整布局
        JPanel panel = new JPanel();
        Box totalBox, upBox, downBox;

        //添加按钮到 Box
        upBox = Box.createHorizontalBox();
        upBox.add(borrow);
        upBox.add(Box.createHorizontalStrut(20));
        upBox.add(withdraw);
        upBox.setOpaque(false);

        downBox = Box.createHorizontalBox();
        downBox.add(search);
        downBox.add(Box.createHorizontalStrut(20));
        downBox.add(manager);

        totalBox = Box.createVerticalBox();
        totalBox.add(upBox);
        totalBox.add(downBox);
        totalBox.setOpaque(false);

        // 设置面板中的布局方式
        panel.setLayout(new BorderLayout());
        JLabel label_right = new JLabel();
        ImageIcon ii = new ComponentTools().iconSize(new ImageIcon("src/bookManageSystem/images/bookmanagesystem.png"), 512,662);
        JLabel label_left = new JLabel();
        ImageIcon i = new ComponentTools().iconSize(new ImageIcon("src/bookManageSystem/images/userframe_left.jpg"), 400,400);

        // 设置两个图片标签，一个是右边的强哥，一个是左边的按钮背景（还在处理
        label_right.setIcon(ii);
        label_left.setIcon(i);
        // 将标签添加到面板中
        panel.add(totalBox,BorderLayout.CENTER);
        panel.add(label_right,BorderLayout.EAST);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // “借书”按钮事件处理
        if (e.getSource() == borrow) {
            // 打开借书窗口
            this.setContentPane(new BorrowPanel());
            this.setVisible(true);
        }
        // “还书”按钮事件处理
        if (e.getSource() == withdraw) {
            // 打开还书窗口
            this.setContentPane(new WithDrawPanel());
            this.setVisible(true);
        }
        // “查询”按钮事件处理
        if (e.getSource() == search) {
            // 打开窗口显示用户信息
            this.setContentPane(new SearchPanel());
            this.setVisible(true);
        }
        // “管理员”按钮事件处理
        if (e.getSource() == manager && true) { //true替换为判断是否为管理员，数据库级别操作
            // 打开窗口显示用户信息
            this.setVisible(false);
            new ManagerFrame().setVisible(true);
        }
        // “返回”菜单项事件处理
        if (e.getSource() == backMenuItem) {
            // 回退窗口
            this.dispose();
            new UserFrameadmin().setVisible(true);
        }
        // “用户信息”菜单项事件处理
        if (e.getSource() == userMenuItem) {
            // 打开窗口显示用户信息
            new UserInfoDialog().setVisible(true);
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
