package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.BeanProduct;
import cn.edu.zucc.freetravel.model.BeanProductOrder;
import cn.edu.zucc.freetravel.model.BeanRoute;
import cn.edu.zucc.freetravel.model.BeanUser;
import cn.edu.zucc.freetravel.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FrmPay extends JDialog implements ActionListener {
    private static final long serialVersionUID=1L;
    /**
     * 组件定义
     */
    /* 主界面控制组件 */
    private JPanel money= new JPanel();
    //栏
    private JButton orderinfoM = new JButton("订单附属信息增添");
    private JButton identyinfoM = new JButton("订单身份信息增添");
    private JButton Pay = new JButton("支付订单");
    //状态栏
    private FrmLogin dlgLogin=null;

    private JLabel isvip = new JLabel("（会员立减至8折）");
    private JLabel cost = new JLabel("总支付："+BeanProductOrder.currentUserOrder.getReal_pay()+"元");

    /**
     * 数据表格定义与实体
     */
    //状态栏
    private JPanel statusBar = new JPanel();
    /*窗口显示表格*/
    //搜索表格
    private Object tblSearchData[][];
    DefaultTableModel tabSearchModel=new DefaultTableModel();
    private JTable dataTableSearch=new JTable(tabSearchModel);

    /**
     * 从数据库中加载数据表格
     */
    /*加载表格*/
    List<BeanProduct> allProduct = null;
    List<BeanProduct> searchProduct = null;

    /*检索表格*/
    private void reloadSearchTable(){
        try {
            searchProduct=FreeTravelUtil.userProductManager.searchProduct();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSearchData =  new Object[searchProduct.size()][BeanProduct.tableTitles.length];
        for(int i=0;i<searchProduct.size();i++){
            for(int j=0;j<BeanProduct.tableTitles.length;j++)
                tblSearchData[i][j]=searchProduct.get(i).getItems(j);
        }
        tabSearchModel.setDataVector(tblSearchData,BeanProduct.tableTitles);
        dataTableSearch.setRowHeight(20);
        this.dataTableSearch.validate();
        this.dataTableSearch.repaint();
    }

    /*界面ui显示*/
    public FrmPay(Frame f ,String s ,boolean b) {
        super(f,s,b);
        /**
         * 主界面与菜单部分
         */
        /*界面标题*/
        this.setTitle("支付订单");

        /*控件加入事件监视*/
        this.Pay.addActionListener(this);
        this.orderinfoM.addActionListener(this);
        this.identyinfoM.addActionListener(this);

        /**
         * 表格在界面显示 初始界面显示区域界面
         */
        /*加载表格*/
        /*区域搜索栏  */
        this.getContentPane().add(new JScrollPane(dataTableSearch), BorderLayout.CENTER);
        money.add(orderinfoM);
        money.add(identyinfoM);
        money.add(Pay);

        if(BeanUser.currentLoginUser.getIsvip()==true&&BeanUser.currentLoginUser.getVip_end_time()!=null&&BeanUser.currentLoginUser.getVip_end_time().length()!=0)
        {money.add(isvip);}

        money.add(cost);
        this.getContentPane().add(money,BorderLayout.PAGE_END);

        this.reloadSearchTable();

        this.setSize(1200, 600);
        this.setVisible(true);
        this.setResizable(false);//禁止自由缩放
    }

    //    int show_flag=0;//确定现在的控制对象 初始设定为初始界面area为控制对象界面
    /*控件连接功能*/
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*买*/
        if(actionEvent.getSource()==Pay){
            try {
                FreeTravelUtil.orderManager.userOrderPay();
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(actionEvent.getSource()==orderinfoM){
            FrmOrderInfoM ofm = new FrmOrderInfoM(this,"修改",true);
            ofm.setVisible(true);
        }
        else if(actionEvent.getSource()==identyinfoM){
            FrmIdentyInfoM ifm = new FrmIdentyInfoM(this,"修改",true);
            ifm.setVisible(true);
        }
    }
}
