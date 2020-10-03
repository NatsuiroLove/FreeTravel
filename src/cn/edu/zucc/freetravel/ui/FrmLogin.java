package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanAdmin;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import javax.xml.xpath.XPathNodes;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.management.PlatformLoggingMXBean;

public class FrmLogin extends JDialog implements ActionListener {
    /*建立组件*/
//    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            ImageIcon icon=new ImageIcon("src\\timg.png");
            icon.setImage(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING));//设置图片大小跟随面板大小
            g.drawImage(icon.getImage(), 0,0,null);
        }
    };

    private JButton btnLogin = new JButton("登陆");
    private JButton btnCancel = new JButton("退出");
    private JButton btnRegister = new JButton("注册");

    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel identifyChoose = new JLabel("身份选择：");
    private JTextField edtUserId = new JTextField(25);
    private JPasswordField edtPwd = new JPasswordField(25);

    ButtonGroup group = new ButtonGroup();
    private JRadioButton radioButton1 = new JRadioButton("普通用户");
    private JRadioButton radioButton2 = new JRadioButton("管理员");

    public FrmLogin( Frame f,String s,boolean b) {
        super(f, s, b);
        btnLogin.setContentAreaFilled(false);
//        btnLogin.setBorder(null);//取消边框
        btnCancel.setContentAreaFilled(false);
//        btnCancel.setBorder(null);
        btnRegister.setContentAreaFilled(false);
//        btnRegister.setBorder(null);

        workPane.setLayout(null);


        btnRegister.setBounds(300,245,60,25);workPane.add(btnRegister);
        btnLogin.setBounds(380,245,60,25);workPane.add(btnLogin);
        btnCancel.setBounds(460,245,60,25);workPane.add(btnCancel);
//        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        labelUser.setBounds(80,90,50,30);workPane.add(labelUser);
        edtUserId.setBounds(140,90,300,30);workPane.add(edtUserId);
        labelPwd.setBounds(80,140,50,30);workPane.add(labelPwd);
        edtPwd.setBounds(140,140,300,30);workPane.add(edtPwd);
        identifyChoose.setBounds( 150,190,80,40);workPane.add(identifyChoose);

        /*将身份选择与登录按钮放置同一面板组件*/
        radioButton1.setContentAreaFilled(false);
        radioButton1.setBorder(null);
        radioButton2.setContentAreaFilled(false);
        radioButton2.setBorder(null);
        group.add(radioButton1);
        group.add(radioButton2);
        radioButton1.setBounds( 250,190,80,40);workPane.add(radioButton1);
        radioButton2.setBounds( 350,190,80,40);workPane.add(radioButton2);
//        f.add(workPane,BorderLayout.PAGE_END);

        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(576, 324);

        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        this.btnRegister.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setResizable(false);//禁止自由缩放
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*普通用户*/
        if(radioButton1.isSelected()){
            if(e.getSource()==this.btnLogin){
                //登录
                String user_name=this.edtUserId.getText();
                String user_pwd=new String(this.edtPwd.getPassword());
                try {
                    BeanUser.currentLoginUser= FreeTravelUtil.userManager.login(user_name, user_pwd);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.setVisible(false);
            }
            else if(e.getSource()==this.btnRegister){
                //注册
                FrmUserRegister dlg=new FrmUserRegister(this,"注册",true);
                dlg.setVisible(true);
            }
            else if(e.getSource()==btnCancel){
                //退出
                System.exit(0);
            }
        }
        /*管理员*/
        else if(radioButton2.isSelected()){
            if(e.getSource()==this.btnLogin){
                //登录
                String admin_name=this.edtUserId.getText();
                String admin_pwd=new String(this.edtPwd.getPassword());
                try {
                    BeanAdmin.currentLoginAdmin= FreeTravelUtil.adminManager.login(admin_name, admin_pwd);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.setVisible(false);
            }
            else if(e.getSource()==this.btnRegister){
                //注册
                FrmAdminRegister dlg=new FrmAdminRegister(this,"注册",true);
                dlg.setVisible(true);
            }
            else if(e.getSource()==btnCancel){
                //退出
                System.exit(0);
            }
        }
        /*未选择*/
        else{
            if(e.getSource()==btnLogin||e.getSource()==btnRegister){
                JOptionPane.showMessageDialog(null,"请选择用户类型！","警告",JOptionPane.ERROR_MESSAGE);
            }
            else{
                System.exit(0);
            }
        }
    }
}
