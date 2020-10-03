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
    private JButton btnOk = new JButton("ע��");
    private JButton btnCancel = new JButton("ȡ��");

    private JLabel labelUser = new JLabel("�˺ţ�   ");
    private JLabel labelName = new JLabel("�û�������");
    private JLabel labelSex = new JLabel("�û��Ա�");
    private JLabel labelPhonenum = new JLabel("���ֻ���");
    private JLabel labelEmail = new JLabel("�û����䣺");
    private JLabel labelLivecity = new JLabel("���ڳ���");
    private JLabel labelPwd = new JLabel("���룺   ");
    private JLabel labelPwd2 = new JLabel("ȷ�����룺");

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
        this.setResizable(false);//��ֹ��������
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
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
