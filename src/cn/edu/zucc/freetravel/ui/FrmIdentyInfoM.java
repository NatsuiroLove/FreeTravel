package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class FrmIdentyInfoM extends JDialog implements ActionListener {
    public int route_id=0;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("修改信息");
    private JButton btnCancel = new JButton("退出");

    BeanProductOrder order = BeanProductOrder.currentUserOrder;

    private JLabel labelName = new JLabel("身份号:");
    private JLabel labelRealname = new JLabel("真实姓名：");


    private JTextField Name = new JTextField(30);
    private JTextField Realname = new JTextField(30);
    public FrmIdentyInfoM(FrmPay f, String s, boolean b) {
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
        this.getContentPane().add(workPane, BorderLayout.WEST);

        this.setSize(350, 150);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
        this.setResizable(false);//禁止自由缩放
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            try {
                String name= Name.getText();
                String realname = Realname.getText();

                FreeTravelUtil.adminOrderManager.identyInfoM(name,realname);

                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
