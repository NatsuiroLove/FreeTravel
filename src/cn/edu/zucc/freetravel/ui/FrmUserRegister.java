package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanAdmin;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmUserRegister extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("注册");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelUser = new JLabel("账号：   ");
    private JLabel labelName = new JLabel("用户姓名：");
    private JLabel labelSex = new JLabel("用户性别：");
    private JLabel labelPhonenum = new JLabel("绑定手机：");
    private JLabel labelEmail = new JLabel("用户邮箱：");
    private JLabel labelLivecity = new JLabel("所在城市");
    private JLabel labelPwd = new JLabel("密码：   ");
    private JLabel labelPwd2 = new JLabel("确认密码：");

    private JTextField edtUser = new JTextField(25);
    private JTextField edtName = new JTextField(25);
    private JTextField edtSex = new JTextField(25);
    private JTextField edtPhonenum = new JTextField(25);
    private JTextField edtEmail = new JTextField(25);
    private JTextField edtLivecity = new JTextField(25);
    private JPasswordField edtPwd = new JPasswordField(25);
    private JPasswordField edtPwd2 = new JPasswordField(25);

    public FrmUserRegister(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUser);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelSex);
        workPane.add(edtSex);
        workPane.add(labelPhonenum);
        workPane.add(edtPhonenum);
        workPane.add(labelEmail);
        workPane.add(edtEmail);
        workPane.add(labelLivecity);
        workPane.add(edtLivecity);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(350, 350);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
        this.setResizable(false);//禁止自由缩放
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            String user_name=this.edtUser.getText();
            String realname = this.edtName.getText();
            String sex = this.edtSex.getText();
            String phonenum = this.edtPhonenum.getText();
            String email = this.edtEmail.getText();
            String livecity = this.edtLivecity.getText();
            String pwd1=new String(this.edtPwd.getPassword());
            String pwd2=new String(this.edtPwd2.getPassword());
            try {
                BeanUser user= FreeTravelUtil.userManager.reg(user_name,realname,sex,phonenum,email,livecity,pwd1,pwd2);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
