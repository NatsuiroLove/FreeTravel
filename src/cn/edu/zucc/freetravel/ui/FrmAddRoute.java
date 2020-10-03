package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddRoute extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("ȷ��");
    private JButton btnCancel = new JButton("ȡ��");
    private JLabel labelName = new JLabel("������·���ƣ� ");
//    private JLabel labelBelongAreaName = new JLabel("�����������ƣ� ");
    private JLabel labelIntroduce = new JLabel ("��·���ܣ�       ");
    private JLabel labelCost = new JLabel( "��·����:       ");
    private JLabel labelDestination = new JLabel("��·Ŀ�ĵأ�     ");
    private JLabel labelDayspend = new JLabel("��·������       ");
    private JLabel labelSpecial = new JLabel("��·��ɫ����:   ");
    private JLabel labelTraffic_tips = new JLabel("��·��ͨ�Ƽ���     ");
//    private JLabel labelRecommandplay_id = new JLabel("��·�Ƽ��淨��ţ�");
    private JLabel tips = new JLabel("                                      ������������·��Ϣ                                             ");

    private JTextField edtName = new JTextField(30);
//    private JTextField edtBelongAreaName = new JTextField(30);
    private JTextField edtIntroduce = new JTextField(30);
    private JTextField edtCost = new JTextField(30);
    private JTextField edtDestination = new JTextField(30);
    private JTextField edtDayspend = new JTextField(30);
    private JTextField edtSpecial = new JTextField(30);
    private JTextField edtTraffic_tips = new JTextField(30);
//    private JTextField edtRecommandplay_id = new JTextField(30);

    public FrmAddRoute(JFrame f, String s, boolean b) {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(tips);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelIntroduce);
        workPane.add(edtIntroduce);
        workPane.add(labelCost);
        workPane.add(edtCost);
        workPane.add(labelDestination);
        workPane.add(edtDestination);
        workPane.add(labelDayspend);
        workPane.add(edtDayspend);
        workPane.add(labelSpecial);
        workPane.add(edtSpecial);
        workPane.add(labelTraffic_tips);
        workPane.add(edtTraffic_tips);
//        workPane.add(labelRecommandplay_id);
//        workPane.add(edtRecommandplay_id);
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
            String route_name=this.edtName.getText();
//            String area_belong_name=this.edtBelongAreaName.getText();
            String route_introduce = this.edtIntroduce.getText();
            String route_cost = this.edtCost.getText();
            String route_destination = this.edtDestination.getText();
            String route_dayspend = this.edtDayspend.getText();
            String route_special = this.edtSpecial.getText();
            String route_traffic_tips = this.edtTraffic_tips.getText();
//            String route_recommandplay_id = this.edtRecommandplay_id.getText();

            try {
                FreeTravelUtil.adminRouteManager.addRoute(route_name,route_introduce,route_cost,route_destination,route_dayspend,route_special,route_traffic_tips);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
