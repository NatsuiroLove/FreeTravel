package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class FrmOrderInfoM extends JDialog implements ActionListener {
    public int route_id=0;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("�޸���Ϣ");
    private JButton btnCancel = new JButton("�˳�");

    BeanProductOrder order = BeanProductOrder.currentUserOrder;

    private JLabel labelName = new JLabel("��������:");
    private JLabel labelRealname = new JLabel("�������У�");
    private JLabel labelSex = new JLabel("ס�����ۣ�");
    private JLabel labelPhone = new JLabel(" �淨����:");
    private JLabel labelMail = new JLabel("��Ʒ���ۣ�");
    private JLabel labelLivecity = new JLabel("��������:");


    private JTextField Name = new JTextField(order.getOrder_group_num());
    private JTextField Realname = new JTextField(order.getStart_city());
    private JTextField Sex = new JTextField(order.getAccommodation_grade());
    private JTextField Phone = new JTextField(order.getRecommandPlay_grade());
    private JTextField Mail = new JTextField(order.getBelongProduct_grade());
    private JTextField Livecity = new JTextField(order.getEvaluate());


    public FrmOrderInfoM(FrmPay f, String s, boolean b) {
        super(f, s, b);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.PAGE_END);

        BoxLayout arealo = new BoxLayout(this.workPane, Y_AXIS);
        this.workPane.setLayout(arealo);
        workPane.add(labelName);
        workPane.add(Name);
        workPane.add(labelRealname);
        workPane.add(Realname);
        workPane.add(labelSex);
        workPane.add(Sex);
        workPane.add(labelPhone);
        workPane.add(Phone);
        workPane.add(labelMail);
        workPane.add(Mail);
        workPane.add(labelLivecity);
        workPane.add(Livecity);
        this.getContentPane().add(workPane, BorderLayout.WEST);

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
            try {
                String name= Name.getText();
                String realname = Realname.getText();
                String sex = Sex.getText();
                String phone = Phone.getText();
                String mail= Mail.getText();
                String livecity = Livecity.getText();

                FreeTravelUtil.adminOrderManager.orderInfoM(name,realname,sex,phone,mail,livecity);

                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
