package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddRestaurant extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("ȷ��");
    private JButton btnCancel = new JButton("ȡ��");

    private JLabel labelid = new JLabel("������ţ� ");
    private JLabel labelName = new JLabel("�����������ƣ� ");
    private JLabel labelBelongAreaName = new JLabel("�����������ƣ� ");
    private JLabel labelIntroduce = new JLabel ("�������ܣ�       ");
    private JLabel labelAddress = new JLabel( "������ϸ��ַ:   ");
    private JLabel labelSpecial_recommand = new JLabel("������ɫ�Ƽ���   ");
    private JLabel labelAverage_cost = new JLabel("ÿ��ƽ������:   ");
    private JLabel labelRoute = new JLabel("�����Ƽ���·��   ");
    private JLabel labelDiscount = new JLabel("��·���ۿۣ�     ");
    private JLabel tips = new JLabel("                                      ����������������Ϣ                                             ");

    private JTextField edtid = new JTextField(30);
    private JTextField edtName = new JTextField(30);
    private JTextField edtBelongAreaName = new JTextField(30);
    private JTextField edtIntroduce = new JTextField(30);
    private JTextField edtAddress = new JTextField(30);
    private JTextField edtLSpecial_recommand = new JTextField(30);
    private JTextField edtAverage_cost = new JTextField(30);
    private JTextField edtRoute = new JTextField(30);
    private JTextField edtDiscount = new JTextField(30);

    public FrmAddRestaurant(JFrame f, String s, boolean b) {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(tips);
        workPane.add(labelid);
        workPane.add(edtid);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelBelongAreaName);
        workPane.add(edtBelongAreaName);
        workPane.add(labelIntroduce);
        workPane.add(edtIntroduce);
        workPane.add(labelAddress);
        workPane.add(edtAddress);
        workPane.add(labelSpecial_recommand);
        workPane.add(edtLSpecial_recommand);
        workPane.add(labelAverage_cost);
        workPane.add(edtAverage_cost);
        workPane.add(labelRoute);
        workPane.add(edtRoute);
        workPane.add(labelDiscount);
        workPane.add(edtDiscount);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(450, 400);
        // ��Ļ������ʾ
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

        this.setResizable(false);//��ֹ��������
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(actionEvent.getSource()==this.btnOk){
            String restaurant_id  =this.edtid.getText();
            String restaurant_name=this.edtName.getText();
            String area_belong_name=this.edtBelongAreaName.getText();
            String restaurant_introduce = this.edtIntroduce.getText();
            String restaurant_address = this.edtAddress.getText();
            String restaurant_special_recommand = this.edtLSpecial_recommand.getText();
            String restaurant_average_cost = this.edtAverage_cost.getText();
            String restaurant_route = this.edtRoute.getText();
            String discount = this.edtDiscount.getText();

            try {
                FreeTravelUtil.adminRestaurantManager.addRestaurant(restaurant_id,restaurant_name,area_belong_name,restaurant_introduce,restaurant_address,restaurant_special_recommand,restaurant_average_cost,restaurant_route,discount);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
