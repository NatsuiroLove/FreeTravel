package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddHotel extends JDialog implements ActionListener{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("ȷ��");
    private JButton btnCancel = new JButton("ȡ��");
    private JLabel labelid = new JLabel("������ţ� ");
    private JLabel labelName = new JLabel("�����Ƶ����ƣ� ");
    private JLabel labelBelongAreaName = new JLabel("�����������ƣ� ");
    private JLabel labelIntroduce = new JLabel ("�Ƶ���ܣ�       ");
    private JLabel labelAddress = new JLabel( "�Ƶ���ϸ��ַ:   ");
    private JLabel labelLevel = new JLabel("�Ƶ�����ȼ���   ");
    private JLabel labelRunTime = new JLabel("Ӫҵʱ��:hh:mm-hh:mm");
    private JLabel labelRoute = new JLabel("�Ƶ��Ƽ�·�ߣ�   ");
    private JLabel labelRecommand_time= new JLabel("��·���Ƽ�ʱ�䣺 ");
    private JLabel tips = new JLabel("                                      �����������Ƶ���Ϣ                                             ");

    private JTextField edtid = new JTextField(30);
    private JTextField edtName = new JTextField(30);
    private JTextField edtBelongAreaName = new JTextField(30);
    private JTextField edtIntroduce = new JTextField(30);
    private JTextField edtAddress = new JTextField(30);
    private JTextField edtLevel = new JTextField(30);
    private JTextField edtRunTime = new JTextField(30);
    private JTextField edtRoute = new JTextField(30);
    private JTextField edtRecommand_time = new JTextField(30);

    public FrmAddHotel(JFrame f, String s, boolean b) {
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
        workPane.add(labelLevel);
        workPane.add(edtLevel);
        workPane.add(labelRunTime);
        workPane.add(edtRunTime);
        workPane.add(labelRoute);
        workPane.add(edtRoute);
        workPane.add(labelRecommand_time);
        workPane.add(edtRecommand_time);
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
            String hotel_id = this.edtid.getText();
            String hotel_name=this.edtName.getText();
            String area_belong_name=this.edtBelongAreaName.getText();
            String hotel_introduce = this.edtIntroduce.getText();
            String hotel_address = this.edtAddress.getText();
            String hotel_level = this.edtLevel.getText();
            String hotel_runtime = this.edtRunTime.getText();
            String hotel_route = this.edtRoute.getText();
            String recommand_time = this.edtRecommand_time.getText();

            try {
                FreeTravelUtil.adminHotelManager.addHotel(hotel_id,hotel_name,area_belong_name,hotel_introduce,hotel_address,hotel_level,hotel_runtime,hotel_route,recommand_time);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

}
