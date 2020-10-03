package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanProduct;
import cn.edu.zucc.freetravel.model.BeanRoute;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class FrmPersonInfo extends JDialog implements ActionListener {
    public int route_id=0;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("修改信息");
    private JButton btnCancel = new JButton("退出");

    BeanUser user = BeanUser.currentLoginUser;

    private JLabel labelName = new JLabel("用户名:");
    private JLabel labelRealname = new JLabel("用户姓名：");
    private JLabel labelSex = new JLabel("用户性别：");
    private JLabel labelPhone = new JLabel(" 用户电话:");
    private JLabel labelMail = new JLabel("用户邮箱：");
    private JLabel labelLivecity = new JLabel("所在城市:");
    private JLabel labelRegistTime = new JLabel("注册时间：");
    private JLabel labelIsvip = new JLabel("是否会员：");
    private JLabel labelVipEndTime = new JLabel("会员到期时间：");

    private JTextField Name = new JTextField(user.getUser_name());
    private JTextField Realname = new JTextField(user.getUser_realname());
    private JTextField Sex = new JTextField(user.getUser_sex()==1?"男":"女");
    private JTextField Phone = new JTextField(user.getUser_phonenum());
    private JTextField Mail = new JTextField(String.valueOf(user.getUser_mail()));
    private JTextField Livecity = new JTextField(user.getUser_livecity());
    private JTextField RegistTime = new JTextField(user.getReg_time());
    private JTextField Isvip = new JTextField(String.valueOf(user.getIsvip()));
    private JTextField VipEndTime = new JTextField(user.getVip_end_time());

    public FrmPersonInfo(Frame f, String s, boolean b) {
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
        workPane.add(labelRegistTime);
        workPane.add(RegistTime);
        workPane.add(labelIsvip);
        workPane.add(Isvip);
        workPane.add(labelVipEndTime);
        workPane.add(VipEndTime);
        this.getContentPane().add(workPane, BorderLayout.WEST);

        this.setSize(350, 650);
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
                String sex = Sex.getText();
                String phone = Phone.getText();
                String mail= Mail.getText();
                String livecity = Livecity.getText();
                String registTime = RegistTime.getText();
                String isvip = Isvip.getText();
                String vipendtime = VipEndTime.getText();

                FreeTravelUtil.userManager.userModifyInfo(name,realname,sex,phone,mail,livecity,registTime,isvip,vipendtime);

                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
