package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddAttractions extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("ȷ��");
    private JButton btnCancel = new JButton("ȡ��");
    private JLabel labelid = new JLabel("������ţ� ");
    private JLabel labelName = new JLabel("�����������ƣ� ");
    private JLabel labelBelongAreaName = new JLabel("�����������ƣ� ");
    private JLabel labelCost = new JLabel("����Ʊ�ۣ�       ");
    private JLabel labelIntroduce = new JLabel ("�������ܣ�       ");
    private JLabel labelAddress = new JLabel( "������ϸ��ַ:   ");
    private JLabel labelTime = new JLabel("��������ʱ�䣺   ");
    private JLabel labelIntroduceRoute = new JLabel("���������Ƽ�����·��:");
    private JLabel labelRecommand_time= new JLabel("��·���Ƽ�ʱ�䣺 ");
    private JLabel tips = new JLabel("                                      ����������������Ϣ                                             ");

    private JTextField edtid = new JTextField(30);
    private JTextField edtName = new JTextField(30);
    private JTextField edtBelongAreaName = new JTextField(30);
    private JTextField edtCost = new JTextField(30);
    private JTextField edtIntroduce = new JTextField(30);
    private JTextField edtAddress = new JTextField(30);
    private JTextField edtTime = new JTextField(30);
    private JTextField edtIntroduceRoute = new JTextField(30);
    private JTextField edtRecommand_time = new JTextField(30);

    public FrmAddAttractions(JFrame f, String s, boolean b) {
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
        workPane.add(labelCost);
        workPane.add(edtCost);
        workPane.add(labelIntroduce);
        workPane.add(edtIntroduce);
        workPane.add(labelAddress);
        workPane.add(edtAddress);
        workPane.add(labelTime);
        workPane.add(edtTime);
        workPane.add(labelIntroduceRoute);
        workPane.add(edtIntroduceRoute);
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
            String attractions_id = this.edtid.getText();
            String attractions_name=this.edtName.getText();
            String area_belong_name=this.edtBelongAreaName.getText();
            String attractions_cost=this.edtCost.getText();
            String attractions_introduce = this.edtIntroduce.getText();
            String attractions_address = this.edtAddress.getText();
            String attractions_time = this.edtTime.getText();
            String attractions_introduceRoute = this.edtIntroduceRoute.getText();
            String recommand_time = this.edtRecommand_time.getText();

            try {
                FreeTravelUtil.adminAttractionsManager.addAttractions(attractions_id,attractions_name,area_belong_name,attractions_cost,attractions_introduce,attractions_address,attractions_time,attractions_introduceRoute,recommand_time);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
