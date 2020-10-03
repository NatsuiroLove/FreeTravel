package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanAdmin;
import cn.edu.zucc.freetravel.model.BeanProduct;
import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmUserBevip extends JDialog implements ActionListener {
    /*建立组件*/

    private JButton btnbevip = new JButton("成为会员");
    private JButton btncode = new JButton(){
        @Override
        public void paintComponent(Graphics g) {
            ImageIcon icon=new ImageIcon("src\\code.png");
            icon.setImage(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING));//设置图片大小跟随面板大小
            g.drawImage(icon.getImage(), 0,0,null);
        }
    };;

    private JLabel vip = new JLabel("充值30天会员 扫码付款");

    public FrmUserBevip( Frame f,String s,boolean b) {
        super(f, s, b);
//        btnbevip.setContentAreaFilled(false);

        this.getContentPane().add(vip,BorderLayout.NORTH);
        this.getContentPane().add(btncode,BorderLayout.CENTER);
        this.getContentPane().add(btnbevip,BorderLayout.SOUTH);
        this.setSize(576, 576);

        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);
        this.validate();

        btnbevip.addActionListener(this);


        this.setResizable(false);//禁止自由缩放
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnbevip){
            try {
                FreeTravelUtil.userManager.beVip();
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.setVisible(false);
        }
    }
}
