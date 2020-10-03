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
    /*�������*/
//    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            ImageIcon icon=new ImageIcon("src\\timg.png");
            icon.setImage(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING));//����ͼƬ��С��������С
            g.drawImage(icon.getImage(), 0,0,null);
        }
    };

    private JButton btnLogin = new JButton("��½");
    private JButton btnCancel = new JButton("�˳�");
    private JButton btnRegister = new JButton("ע��");

    private JLabel labelUser = new JLabel("�û���");
    private JLabel labelPwd = new JLabel("���룺");
    private JLabel identifyChoose = new JLabel("���ѡ��");
    private JTextField edtUserId = new JTextField(25);
    private JPasswordField edtPwd = new JPasswordField(25);

    ButtonGroup group = new ButtonGroup();
    private JRadioButton radioButton1 = new JRadioButton("��ͨ�û�");
    private JRadioButton radioButton2 = new JRadioButton("����Ա");

    public FrmLogin( Frame f,String s,boolean b) {
        super(f, s, b);
        btnLogin.setContentAreaFilled(false);
//        btnLogin.setBorder(null);//ȡ���߿�
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

        /*�����ѡ�����¼��ť����ͬһ������*/
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

        // ��Ļ������ʾ
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

        this.setResizable(false);//��ֹ��������
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*��ͨ�û�*/
        if(radioButton1.isSelected()){
            if(e.getSource()==this.btnLogin){
                //��¼
                String user_name=this.edtUserId.getText();
                String user_pwd=new String(this.edtPwd.getPassword());
                try {
                    BeanUser.currentLoginUser= FreeTravelUtil.userManager.login(user_name, user_pwd);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.setVisible(false);
            }
            else if(e.getSource()==this.btnRegister){
                //ע��
                FrmUserRegister dlg=new FrmUserRegister(this,"ע��",true);
                dlg.setVisible(true);
            }
            else if(e.getSource()==btnCancel){
                //�˳�
                System.exit(0);
            }
        }
        /*����Ա*/
        else if(radioButton2.isSelected()){
            if(e.getSource()==this.btnLogin){
                //��¼
                String admin_name=this.edtUserId.getText();
                String admin_pwd=new String(this.edtPwd.getPassword());
                try {
                    BeanAdmin.currentLoginAdmin= FreeTravelUtil.adminManager.login(admin_name, admin_pwd);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.setVisible(false);
            }
            else if(e.getSource()==this.btnRegister){
                //ע��
                FrmAdminRegister dlg=new FrmAdminRegister(this,"ע��",true);
                dlg.setVisible(true);
            }
            else if(e.getSource()==btnCancel){
                //�˳�
                System.exit(0);
            }
        }
        /*δѡ��*/
        else{
            if(e.getSource()==btnLogin||e.getSource()==btnRegister){
                JOptionPane.showMessageDialog(null,"��ѡ���û����ͣ�","����",JOptionPane.ERROR_MESSAGE);
            }
            else{
                System.exit(0);
            }
        }
    }
}
