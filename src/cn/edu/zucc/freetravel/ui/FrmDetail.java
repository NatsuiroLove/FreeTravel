package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.*;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;
import cn.edu.zucc.freetravel.util.DBUtil;
import cn.edu.zucc.freetravel.util.DbException;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class FrmDetail extends JDialog implements ActionListener {
    public int route_id=0;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("加入订单");
    private JButton btnCancel = new JButton("退出");

    private JLabel labelName = new JLabel("线路名称：");
    private JLabel labelIntroduce = new JLabel("线路介绍：");
    private JLabel labelCost = new JLabel("线路消费：");
    private JLabel labelRoad = new JLabel("线路路程：");
    private JLabel labelDayspend = new JLabel("线路天数：");
    private JLabel labelSpecial_recommand = new JLabel("特色推荐:");
    private JLabel labelRecommandplay = new JLabel("推荐玩法：");
    private JLabel labelTraffic_tips = new JLabel("交通提示：");
    private JLabel labelAccommodation_grade = new JLabel("住宿平均评分");
    private JLabel labelRecommandPlay_grade = new JLabel("推荐玩法平均评分");
    private JLabel labelBelongProduct_grade = new JLabel("附属产品平均评分");

    private JLabel labelStart_time = new JLabel("起程时间");
    private JTextField start_time = new JTextField(20);
    private JLabel labelDiscount  = new JLabel("折扣");
    private JTextField discount  = new JTextField(20);

    public FrmDetail(Frame f, String s, boolean b,BeanRoute route) throws BaseException {
        super(f, s, b);

        float accommodation_grade = 0;
        float recommandPlay_grade =0;
        float belongProduct_grade=0;
        /*推荐玩法*/
        String attractions_name ="";
        int attractions_id=0;
        String attractions_rec="";

        String hotel_name="";
        int hotel_id=0;
        String hotel_rec="";

        String restaurant_name="";
        int restaurant_id= 0;
        float restaurant_disc=0;

        route_id=route.getRoute_id();
        int daySpend= route.getDayspend();

        String [] DayRecAttractions= new String [daySpend];
        String [] DayRecHotel= new String [daySpend];
        String DayRecRestaurant= "推荐餐厅：";
        for(int i=0;i<daySpend;i++){//字符串数组初始化
            DayRecAttractions[i]="推荐景区：";
            DayRecHotel[i]="推荐酒店：";
        }

        Connection conn=null;
        try{
            conn= DBUtil.getConnection();//链接数据库

            String sql1="select attractions_id,recommand_time from route_attractions where route_id = ? ";
            java.sql.PreparedStatement pst1= conn.prepareStatement(sql1);
            pst1.setInt(1,route_id);
            java.sql.ResultSet rs1  =pst1.executeQuery();
            while(rs1.next()){
                attractions_id=rs1.getInt(1);
                attractions_rec=rs1.getString(2);
                String sql2= "select attractions_name from tbl_attractions where attractions_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,attractions_id);
                java.sql.ResultSet rs2= pst2.executeQuery();
                if(rs2.next()){
                    attractions_name=rs2.getString(1);
                }
                for(int j=0;j<daySpend;j++){
                    if(attractions_rec.charAt(j)=='1'){//此天推荐该景点
                        String str = DayRecAttractions[j];
                        String newstr = str+" "+attractions_name;
                        DayRecAttractions[j]= newstr;
                    }
                }
            }

            sql1="select hotel_id,recommand_time from route_hotel where route_id = ? ";
            pst1= conn.prepareStatement(sql1);
            pst1.setInt(1,route_id);
            rs1  =pst1.executeQuery();
            while(rs1.next()){
                hotel_id=rs1.getInt(1);
                hotel_rec=rs1.getString(2);
                String sql2= "select hotel_name from tbl_hotel where hotel_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,hotel_id);
                java.sql.ResultSet rs2= pst2.executeQuery();
                if(rs2.next()){
                    hotel_name=rs2.getString(1);
                }
                for(int j=0;j<daySpend;j++){
                    if(hotel_rec.charAt(j)=='1'){//此天推荐该景点
                        String str = DayRecHotel[j];
                        String newstr = str+" "+hotel_name;
                        DayRecHotel[j]= newstr;
                    }
                }
            }

            sql1="select restaurant_id,dicount from route_restaurant where route_id = ? ";
            pst1= conn.prepareStatement(sql1);
            pst1.setInt(1,route_id);
            rs1  =pst1.executeQuery();
            while(rs1.next()){
                restaurant_id=rs1.getInt(1);
                restaurant_disc=rs1.getFloat(2);
                String sql2= "select restaurant_name from tbl_restaurant where restaurant_id = ?";
                java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                pst2.setInt(1,restaurant_id);
                java.sql.ResultSet rs2= pst2.executeQuery();
                if(rs2.next()){
                    restaurant_name=rs2.getString(1);
                }
                DayRecRestaurant+=" "+restaurant_name;
            }

            sql1="select accommodation_grade,recommandPlay_grade,belongProduct_grade from tbl_productorder ";
            pst1=conn.prepareStatement(sql1);
            rs1 = pst1.executeQuery();
            int count=0;
            while(rs1.next()){
                if(rs1.getInt(1)==0&&rs1.getInt(2)==0&&rs1.getInt(3)==0)continue;//未评价
                else {
                    count++;
                    accommodation_grade+= rs1.getInt(1);
                    recommandPlay_grade+= rs1.getInt(2);
                    belongProduct_grade+= rs1.getInt(3);
                }
            }
            accommodation_grade/=1.0*count;
            recommandPlay_grade/=1.0*count;
            belongProduct_grade/=1.0*count;

        }catch(SQLException ex){
            throw new DbException(ex);
        }finally{
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }

        JLabel Name = new JLabel(route.getRoute_name());
        JLabel Introduce = new JLabel(route.getRoute_introduce());
        JLabel Cost = new JLabel(String.valueOf(route.getRoute_cost()));
        JLabel Road = new JLabel(route.getDestination());
        JLabel Dayspend = new JLabel(String.valueOf(route.getDayspend()));
        JLabel Special_recommand = new JLabel(route.getSpecial_recommand());
        JLabel Traffic_tips = new JLabel(route.getTraffic_tips());

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.PAGE_END);

        BoxLayout arealo = new BoxLayout(this.workPane, Y_AXIS);
        this.workPane.setLayout(arealo);
        workPane.add(labelName);
        workPane.add(Name);
        workPane.add(labelIntroduce);
        workPane.add(Introduce);
        workPane.add(labelCost);
        workPane.add(Cost);
        workPane.add(labelRoad);
        workPane.add(Road);
        workPane.add(labelDayspend);
        workPane.add(Dayspend);
        workPane.add(labelSpecial_recommand);
        workPane.add(Special_recommand);
        workPane.add(labelRecommandplay);

        String recommandplay="";
        for(int i=0;i<daySpend;i++){
            recommandplay="Day "+String.valueOf(i+1)+" :  "+DayRecAttractions[i]+"   "+DayRecHotel[i]+"    ";
            workPane.add(new JLabel(recommandplay));
        }
        workPane.add(new JLabel(DayRecRestaurant));

        workPane.add(labelTraffic_tips);
        workPane.add(Traffic_tips);
        workPane.add(labelAccommodation_grade);
        workPane.add(new Label("         "+String.valueOf(accommodation_grade)));
        workPane.add(labelRecommandPlay_grade);
        workPane.add(new Label("         "+String.valueOf(recommandPlay_grade)));
        workPane.add(labelBelongProduct_grade);
        workPane.add(new Label("         "+String.valueOf(belongProduct_grade)));
        workPane.add(labelStart_time);
        workPane.add(start_time);
//        workPane.add(labelDiscount);
//        workPane.add(discount);
        this.getContentPane().add(workPane, BorderLayout.WEST);

        this.setSize(800, 600);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
        this.setResizable(false);//禁止自由缩放
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            String start_time=this.start_time.getText();
//            String discount  =this.discount.getText();
            try {
                BeanProduct product= FreeTravelUtil.userProductManager.addProduct(route_id,start_time);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
