package cn.edu.zucc.freetravel.ui;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.*;
import cn.edu.zucc.freetravel.util.BaseException;
import cn.edu.zucc.freetravel.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class FrmUserMain extends JFrame implements ActionListener {
    private static final long serialVersionUID=1L;
    /**
     * 组件定义
     */
    /* 主界面控制组件 */
    private JPanel searchPanel= new JPanel();
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_route = new JMenu("线路查询");
    private JMenu menu_self = new JMenu("个人中心");
    private JMenu menu_other = new JMenu("其他功能");

    /*控制组件下选项*/
    //线路查询
    private JMenuItem menu_show_route = new JMenuItem ("显示线路信息");
    //个人管理
    private JMenuItem menu_show_self = new JMenuItem ("显示个人信息");
    //其他管理
    private JMenuItem menu_bevip = new JMenuItem("成为会员");
    private JMenuItem menu_modify_pwd = new JMenuItem("修改密码");
    //搜索栏
    private JButton Search = new JButton("搜索");
    private JButton Detail = new JButton("显示详细信息");
    private JButton Pay = new JButton("支付订单");
    private JLabel SearchLabel = new JLabel("请输入要查询的对象名称：");
    private JTextField SearchEdit = new JTextField(25);
    //状态栏
    private FrmLogin dlgLogin=null;

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
    private BeanRoute curRoute=null;//鼠标点击监视项

    List<BeanRoute> allRoute = null;
    List<BeanRoute> searchRoute = null;

    /*检索表格*/
    private void reloadSearchTable(){
        try {
            String search_name=SearchEdit.getText();
//            allRoute=FreeTravelUtil.adminRouteManager.loadAll();
            if(search_name==null||search_name.length()==0){
                searchRoute=FreeTravelUtil.adminRouteManager.loadAll();
            }
            else {
                searchRoute= FreeTravelUtil.adminRouteManager.searchRoute(search_name);
            }
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblSearchData =  new Object[searchRoute.size()][BeanRoute.tableTitles.length];
        for(int i=0;i<searchRoute.size();i++){
            for(int j=0;j<BeanRoute.tableTitles.length;j++)
                tblSearchData[i][j]=searchRoute.get(i).getItems(j);
        }
        tabSearchModel.setDataVector(tblSearchData,BeanRoute.tableTitles);
        dataTableSearch.setRowHeight(20);
        this.dataTableSearch.validate();
        this.dataTableSearch.repaint();
    }

    public static boolean flag=false;//检测是否为第一进入主界面

    /*界面ui显示*/
    public FrmUserMain() {
        /**
         * 主界面与菜单部分
         */
        /*界面标题*/
        this.setTitle("自由行31803112");

        if(BeanUser.currentLoginUser==null)return ; //非普通用户登录 退出

        if(flag==false){
            try {
                BeanProductOrder order = FreeTravelUtil.orderManager.userOrderStart();
            } catch (BaseException e) {
                e.printStackTrace();
            }finally {
                flag=true;
            }
        }

        /*菜单控件加入事件监视*/
        this.menu_route.add(this.menu_show_route);this.menu_show_route.addActionListener(this);
        this.menu_self.add(this.menu_show_self);this.menu_show_self.addActionListener(this);
        this.menu_other.add(this.menu_modify_pwd);this.menu_modify_pwd.addActionListener(this);
        this.menu_other.add(this.menu_bevip);this.menu_bevip.addActionListener(this);
        this.Detail.addActionListener(this);
        this.Pay.addActionListener(this);
        this.Search.addActionListener(this);

        /*顶部控件填装*/
        menubar.add(menu_route);
        menubar.add(menu_self);
        menubar.add(menu_other);
        this.setJMenuBar(menubar);
        /**
         * 表格在界面显示 初始界面显示区域界面
         */
        /*加载表格*/
        /*区域搜索栏  */
        this.getContentPane().add(new JScrollPane(dataTableSearch),BorderLayout.CENTER);
        this.dataTableSearch.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmUserMain.this.dataTableSearch.getSelectedRow();
                if(i<0) {
                    return;
                }
                curRoute=searchRoute.get(i);
            }

        });
        searchPanel.add(SearchLabel);
        searchPanel.add(SearchEdit);
        searchPanel.add(Search);
        searchPanel.add(Pay);
        this.getContentPane().add(searchPanel, BorderLayout.NORTH);
        this.getContentPane().add(Detail,BorderLayout.PAGE_END);
        this.reloadSearchTable();
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });

        this.setSize(1200, 600);
        this.setVisible(true);
        this.setResizable(false);//禁止自由缩放
    }

//    int show_flag=0;//确定现在的控制对象 初始设定为初始界面area为控制对象界面
    /*控件连接功能*/
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*修改密码*/
        if(actionEvent.getSource()==menu_modify_pwd){
            FrmUserModifyPwd dlg=new FrmUserModifyPwd(this,"密码修改",true);
            dlg.setVisible(true);
        }
        /*区域搜索*/
        else if(actionEvent.getSource()==Search){
            reloadSearchTable();
        }
        /*显示详细信息*/
        else if(actionEvent.getSource()==Detail){

            if(curRoute==null) {
                JOptionPane.showMessageDialog(null, "请选择路线", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmDetail dt = null;
            try {
                dt = new FrmDetail(this,"线路详情",true,curRoute);
            } catch (BaseException e) {
                e.printStackTrace();
            }
            dt.setVisible(true);
        }
        /*买*/
        else if(actionEvent.getSource()==Pay){
            FrmPay py = new FrmPay(this,"支付订单",true);
            py.setVisible(true);
        }
        /*变强*/
        else if(actionEvent.getSource()==menu_bevip){
            FrmUserBevip bv = new FrmUserBevip(this,"成为会员",true);
            bv.setVisible(true);
        }
        else if(actionEvent.getSource()==menu_show_self){
            FrmPersonInfo psi = new FrmPersonInfo(this,"个人信息",true);
            psi.setVisible(true);
        }
    }
}
