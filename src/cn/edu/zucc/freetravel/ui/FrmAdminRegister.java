package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanAdmin;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrmAdminRegister extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("ע��");
    private JButton btnCancel = new JButton("ȡ��");

    private JLabel labelUser = new JLabel("�û���");
    private JLabel labelPwd = new JLabel("���룺");
    private JLabel labelPwd2 = new JLabel("���룺");
    private JTextField edtUserId = new JTextField(25);
    private JPasswordField edtPwd = new JPasswordField(25);
    private JPasswordField edtPwd2 = new JPasswordField(25);
    public FrmAdminRegister(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 180);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
        this.setResizable(false);//��ֹ��������
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            String admin_name=this.edtUserId.getText();
            String pwd1=new String(this.edtPwd.getPassword());
            String pwd2=new String(this.edtPwd2.getPassword());
            try {
                BeanAdmin admin= FreeTravelUtil.adminManager.reg(admin_name,pwd1,pwd2);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}