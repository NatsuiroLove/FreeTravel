package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.util.BaseException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.*;

public class FrmAddArea extends JDialog implements ActionListener{
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("ȷ��");
    private JButton btnCancel = new JButton("ȡ��");
    private JLabel labelId = new JLabel("�����ţ�");
    private JLabel labelName = new JLabel("�������ƣ�");
    private JLabel labelBelongName = new JLabel("�����������ƣ�");
    private JLabel tips = new JLabel("���������������� ��Ϊʡ�������뽫����������Ϊ��");

    private JTextField edtName = new JTextField(20);
    private JTextField edtName2 = new JTextField(20);
    private JTextField edtId= new JTextField(20);

    public FrmAddArea(JFrame f, String s, boolean b) {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(tips);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelBelongName);
        workPane.add(edtName2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 210);
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
            String area_id = this.edtId.getText();
            String area_name=this.edtName.getText();
            String area_belong_name=this.edtName2.getText();
            try {
                FreeTravelUtil.adminAreaManager.addArea(area_id,area_name,area_belong_name);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
