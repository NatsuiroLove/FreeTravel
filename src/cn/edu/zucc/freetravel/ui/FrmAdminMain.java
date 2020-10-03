package cn.edu.zucc.freetravel.ui;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.ListUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import cn.edu.zucc.freetravel.FreeTravelUtil;
import cn.edu.zucc.freetravel.model.*;
import cn.edu.zucc.freetravel.util.BaseException;

import java.beans.BeanInfo;
import java.util.List;

import static javax.swing.BoxLayout.Y_AXIS;

public class FrmAdminMain extends JFrame implements ActionListener {
    private static final long serialVersionUID=1L;
    /**
     * 组件定义
     */
    /* 主界面控制组件 */
    private JPanel searchPanel= new JPanel();
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_area = new JMenu("区域数据管理");
    private JMenu menu_attractions = new JMenu("景点数据管理");
    private JMenu menu_hotel = new JMenu("酒店数据管理");
    private JMenu menu_restaurant = new JMenu("餐厅数据管理");
    private JMenu menu_route = new JMenu ("线路数据管理");//
    private JMenu menu_user = new JMenu ("用户订单记录");//包含用户基础信息记录与相应用户订单及其购买产品信息
    private JMenu menu_other = new JMenu ("其他功能");

    /*控制组件下选项*/
    //区域管理
    private JMenuItem menu_show_area = new JMenuItem ("显示区域信息");
    private JMenuItem menu_add_area = new JMenuItem ("新增区域");
    private JMenuItem menu_delete_area = new JMenuItem("删除区域");
    //景点管理
    private JMenuItem menu_show_attractions = new JMenuItem ("显示景点信息");
    private JMenuItem menu_add_attractions = new JMenuItem( "新增景点");
    private JMenuItem menu_delete_attractions = new JMenuItem("删除景点");
    //酒店数据管理
    private JMenuItem menu_show_hotel = new JMenuItem ("显示酒店信息");
    private JMenuItem menu_add_hotel = new JMenuItem( "新增酒店");
    private JMenuItem menu_delete_hotel = new JMenuItem( "删除酒店");
    //餐厅数据管理
    private  JMenuItem menu_show_restaurant = new JMenuItem ("显示餐厅信息");
    private  JMenuItem menu_add_restaurant = new JMenuItem( "新增餐厅");
    private  JMenuItem menu_delete_restaurant = new JMenuItem( "删除餐厅");
    //线路数据管理
    private  JMenuItem menu_show_routeInfo = new JMenuItem("显示线路/产品销售情况");
    private  JMenuItem menu_add_route = new JMenuItem("新增线路");
    private  JMenuItem menu_delete_route = new JMenuItem( "删除线路");
    //关联表数据管理
    private  JMenuItem menu_show_associate = new JMenuItem("显示线路相关推荐");
    private  JMenuItem menu_add_associate = new JMenuItem("新增线路推荐内容");
    private  JMenuItem menu_delete_associate = new JMenuItem("删除线路推荐内容");
    //用户信息展示
    private  JMenuItem menu_show_orderInfo = new JMenuItem( "显示订单信息");
    private  JMenuItem menu_show_userInfo = new JMenuItem( "显示用户信息");
    //其他功能显示
    private  JMenuItem menu_modify_pwd = new JMenuItem("修改密码");
    //搜索栏
    private JButton Search = new JButton("搜索");
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
    //信息表格
    private Object tblData[][];
    DefaultTableModel tabModel=new DefaultTableModel();
    private JTable dataTable=new JTable(tabModel);
    //搜索表格
    private Object tblSearchData[][];
    DefaultTableModel tabSearchModel=new DefaultTableModel();
    private JTable dataTableSearch=new JTable(tabSearchModel);

    /**
     * 从数据库中加载数据表格
     */
    /*加载表格*/
    private BeanArea curArea=null;//鼠标点击监视区域项
    private BeanAttractions curAttractions= null;//鼠标点击监视景区项
    private BeanHotel curHotel =null;//鼠标点击监视酒店项
    private BeanRestaurant curRestaurant = null;
    private BeanRoute curRoute = null;

    List<BeanArea>  allArea=null;//所有区域表格
    List<BeanArea>  searchArea =null;//被搜索的区域表格
    List<BeanAttractions>  allAttractions=null;//所有区域表格
    List<BeanAttractions>  searchAttractions =null;//被搜索的区域表格
    List<BeanHotel> allHotel = null;//所有酒店表格
    List<BeanHotel> searchHotel = null;//被搜索的所有酒店
    List<BeanRestaurant> allRestaurant = null; //所有餐厅表格
    List<BeanRestaurant> searchRestaurant = null;//被搜索的所有餐厅
    List<BeanRoute> allRoute = null;
    List<BeanRoute> searchRoute = null;
    List<BeanProductOrder> allOrder = null;
    List<BeanProductOrder> searchOrder = null;
    List<BeanUser> allUser = null;
    List<BeanUser> searchUser = null;

    /*主信息表格*/
    private void reloadTable(int col){
        if(col==0){
            try {
                allArea= FreeTravelUtil.adminAreaManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allArea.size()][BeanArea.tableTitles.length];
            for(int i=0;i<allArea.size();i++){
                for(int j=0;j<BeanArea.tableTitles.length;j++)
                    tblData[i][j]=allArea.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanArea.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        else if(col==1){
            try {
                allAttractions= FreeTravelUtil.adminAttractionsManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allAttractions.size()][BeanAttractions.tableTitles.length];
            for(int i=0;i<allAttractions.size();i++){
                for(int j=0;j<BeanAttractions.tableTitles.length;j++)
                    tblData[i][j]=allAttractions.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanAttractions.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        /*酒店控制*/
        else if(col==2){
            try {
                allHotel= FreeTravelUtil.adminHotelManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allHotel.size()][BeanHotel.tableTitles.length];
            for(int i=0;i<allHotel.size();i++){
                for(int j=0;j<BeanHotel.tableTitles.length;j++)
                    tblData[i][j]=allHotel.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanHotel.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        /* 餐厅控制*/
        else if(col==3){
            try {
                allRestaurant= FreeTravelUtil.adminRestaurantManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allRestaurant.size()][BeanRestaurant.tableTitles.length];
            for(int i=0;i<allRestaurant.size();i++){
                for(int j=0;j<BeanRestaurant.tableTitles.length;j++)
                    tblData[i][j]=allRestaurant.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanRestaurant.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        /*线路控制*/
        else if(col==4){
            try {
                allRoute= FreeTravelUtil.adminRouteManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allRoute.size()][BeanRoute.tableTitles.length];
            for(int i=0;i<allRoute.size();i++){
                for(int j=0;j<BeanRoute.tableTitles.length;j++)
                    tblData[i][j]=allRoute.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanRoute.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        else if(col==5){
            try {
                allOrder= FreeTravelUtil.adminOrderManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allOrder.size()][BeanProductOrder.tableTitles.length];
            for(int i=0;i<allOrder.size();i++){
                for(int j=0;j<BeanProductOrder.tableTitles.length;j++)
                    tblData[i][j]=allOrder.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanProductOrder.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        else if(col==6){
            try {
                allUser= FreeTravelUtil.userManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblData =  new Object[allUser.size()][BeanUser.tableTitles.length];
            for(int i=0;i<allUser.size();i++){
                for(int j=0;j<BeanUser.tableTitles.length;j++)
                    tblData[i][j]=allUser.get(i).getItems(j);
            }
            tabModel.setDataVector(tblData,BeanUser.tableTitles);
            dataTable.setRowHeight(20);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
    }

    /*检索表格*/
    private void reloadSearchTable(int col){

        if(col==0){
            try {
                String search_name=SearchEdit.getText();
                searchArea= FreeTravelUtil.adminAreaManager.searchArea(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblSearchData =  new Object[searchArea.size()][BeanArea.tableTitles.length];
            for(int i=0;i<searchArea.size();i++){
                for(int j=0;j<BeanArea.tableTitles.length;j++)
                    tblSearchData[i][j]=searchArea.get(i).getItems(j);
            }
            tabSearchModel.setDataVector(tblSearchData,BeanArea.tableTitles);
            dataTableSearch.setRowHeight(20);
            this.dataTableSearch.validate();
            this.dataTableSearch.repaint();
        }
        else if(col==1){
            try {
                String search_name=SearchEdit.getText();
                searchAttractions= FreeTravelUtil.adminAttractionsManager.searchAttractions(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblSearchData =  new Object[searchAttractions.size()][BeanAttractions.tableTitles.length];
            for(int i=0;i<searchAttractions.size();i++){
                for(int j=0;j<BeanAttractions.tableTitles.length;j++)
                    tblSearchData[i][j]=searchAttractions.get(i).getItems(j);
            }
            tabSearchModel.setDataVector(tblSearchData,BeanAttractions.tableTitles);
            dataTableSearch.setRowHeight(20);
            this.dataTableSearch.validate();
            this.dataTableSearch.repaint();
        }
        else if(col==2){
            try {
                String search_name=SearchEdit.getText();
                searchHotel= FreeTravelUtil.adminHotelManager.searchHotel(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblSearchData =  new Object[searchHotel.size()][BeanHotel.tableTitles.length];
            for(int i=0;i<searchHotel.size();i++){
                for(int j=0;j<BeanHotel.tableTitles.length;j++)
                    tblSearchData[i][j]=searchHotel.get(i).getItems(j);
            }
            tabSearchModel.setDataVector(tblSearchData,BeanHotel.tableTitles);
            dataTableSearch.setRowHeight(20);
            this.dataTableSearch.validate();
            this.dataTableSearch.repaint();
        }
        else if(col==3){
            try {
                String search_name=SearchEdit.getText();
                searchRestaurant= FreeTravelUtil.adminRestaurantManager.searchRestaurant(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblSearchData =  new Object[searchRestaurant.size()][BeanRestaurant.tableTitles.length];
            for(int i=0;i<searchRestaurant.size();i++){
                for(int j=0;j<BeanRestaurant.tableTitles.length;j++)
                    tblSearchData[i][j]=searchRestaurant.get(i).getItems(j);
            }
            tabSearchModel.setDataVector(tblSearchData,BeanRestaurant.tableTitles);
            dataTableSearch.setRowHeight(20);
            this.dataTableSearch.validate();
            this.dataTableSearch.repaint();
        }
        else if(col==4){
            try {
                String search_name=SearchEdit.getText();
                searchRoute= FreeTravelUtil.adminRouteManager.searchRoute(search_name);
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
        else if(col==5){
            try {
                String search_name=SearchEdit.getText();
                searchOrder= FreeTravelUtil.adminOrderManager.searchOrder(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblSearchData =  new Object[searchOrder.size()][BeanProductOrder.tableTitles.length];
            for(int i=0;i<searchOrder.size();i++){
                for(int j=0;j<BeanProductOrder.tableTitles.length;j++)
                    tblSearchData[i][j]=searchOrder.get(i).getItems(j);
            }
            tabSearchModel.setDataVector(tblSearchData,BeanProductOrder.tableTitles);
            dataTableSearch.setRowHeight(20);
            this.dataTableSearch.validate();
            this.dataTableSearch.repaint();
        }
        else if(col==6){
            try {
                String search_name=SearchEdit.getText();
                searchUser= FreeTravelUtil.userManager.searchUser(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tblSearchData =  new Object[searchUser.size()][BeanUser.tableTitles.length];
            for(int i=0;i<searchUser.size();i++){
                for(int j=0;j<BeanUser.tableTitles.length;j++)
                    tblSearchData[i][j]=searchUser.get(i).getItems(j);
            }
            tabSearchModel.setDataVector(tblSearchData,BeanUser.tableTitles);
            dataTableSearch.setRowHeight(20);
            this.dataTableSearch.validate();
            this.dataTableSearch.repaint();
        }
    }


    /*界面ui显示*/
    public FrmAdminMain() {
        /**
         * 主界面与菜单部分
         */
        /*界面标题*/
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("自由行软件信息管理系统31803112");

        /*登录后界面可视化*/
        dlgLogin=new FrmLogin(this,"自由行软件登陆",true);
        /*此处判断登录的用户类型*/
        dlgLogin.setVisible(true);

        if(BeanAdmin.currentLoginAdmin==null)return ;//非管理员登录 退出


        /*菜单控件加入事件监视*/
        this.menu_area.add(this.menu_show_area);this.menu_show_area.addActionListener(this);
        this.menu_area.add(this.menu_add_area);this.menu_add_area.addActionListener(this);
        this.menu_area.add(this.menu_delete_area);this.menu_delete_area.addActionListener(this);
        this.menu_attractions.add(this.menu_show_attractions);this.menu_show_attractions.addActionListener(this);
        this.menu_attractions.add(this.menu_add_attractions);this.menu_add_attractions.addActionListener(this);
        this.menu_attractions.add(this.menu_delete_attractions);this.menu_delete_attractions.addActionListener(this);
        this.menu_hotel.add(this.menu_show_hotel);this.menu_show_hotel.addActionListener(this);
        this.menu_hotel.add(this.menu_add_hotel);this.menu_add_hotel.addActionListener(this);
        this.menu_hotel.add(this.menu_delete_hotel);this.menu_delete_hotel.addActionListener(this);
        this.menu_restaurant.add(this.menu_show_restaurant);this.menu_show_restaurant.addActionListener(this);
        this.menu_restaurant.add(this.menu_add_restaurant);this.menu_add_restaurant.addActionListener(this);
        this.menu_restaurant.add(this.menu_delete_restaurant);this.menu_delete_restaurant.addActionListener(this);
        this.menu_route.add(this.menu_show_routeInfo);this.menu_show_routeInfo.addActionListener(this);
        this.menu_route.add(this.menu_add_route);this.menu_add_route.addActionListener(this);
        this.menu_route.add(this.menu_delete_route);this.menu_delete_route.addActionListener(this);
        this.menu_user.add(this.menu_show_userInfo);this.menu_show_userInfo.addActionListener(this);
        this.menu_user.add(this.menu_show_orderInfo);this.menu_show_orderInfo.addActionListener(this);
        this.menu_other.add(this.menu_modify_pwd);this.menu_modify_pwd.addActionListener(this);
        this.Search.addActionListener(this);

        /*顶部控件填装*/
        menubar.add(menu_area);
        menubar.add(menu_attractions);
        menubar.add(menu_hotel);
        menubar.add(menu_restaurant);
        menubar.add(menu_route);
//        menubar.add(menu_associate);
        menubar.add(menu_user);
        menubar.add(menu_other);
        this.setJMenuBar(menubar);
        /**
         * 表格在界面显示 初始界面显示区域界面
         */
        /*加载表格*/
        /*区域表格*/
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        /*区域搜索栏  */
        BoxLayout arealo = new BoxLayout(this.getContentPane(), Y_AXIS);
        this.getContentPane().setLayout(arealo);
        searchPanel.add(new JScrollPane(dataTableSearch));
        searchPanel.add(SearchLabel);
        searchPanel.add(SearchEdit);
        searchPanel.add(Search);
        this.getContentPane().add(searchPanel, BorderLayout.EAST);
        this.reloadSearchTable(0);
        this.reloadTable(0);

        /**
         * 底部状态栏
         */
        //状态栏
        String nowLogin=BeanAdmin.currentLoginAdmin.getAdmin_name();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好 ! "+nowLogin+" !");//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });

        this.setSize(600, 500);
        this.setVisible(true);
//        this.setResizable(false);//禁止自由缩放
    }

    int show_flag=0;//确定现在的控制对象 初始设定为初始界面area为控制对象界面
    /*控件连接功能*/
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*修改密码*/
        if(actionEvent.getSource()==menu_modify_pwd){
            FrmAdminModifyPwd dlg=new FrmAdminModifyPwd(this,"密码修改",true);
            dlg.setVisible(true);
        }

        /*区域相关*/
        else if(actionEvent.getSource()==menu_add_area){
            FrmAddArea dlg=new FrmAddArea(this,"添加区域",true);
            dlg.setVisible(true);
            reloadTable(0);
        }
        else if(actionEvent.getSource()==menu_show_area){
            show_flag=0;
            reloadTable(0);
            reloadSearchTable(0);

        }
        else if(actionEvent.getSource()==menu_delete_area&&show_flag==0){
            FrmDeleteArea dlg=new FrmDeleteArea(this,"删除区域",true);
            dlg.setVisible(true);
            reloadTable(0);
        }
        /*区域搜索*/
        else if(actionEvent.getSource()==Search&&show_flag==0){
            reloadSearchTable(0);
        }
        /*景区相关*/
        else  if(actionEvent.getSource()==menu_add_attractions){
            FrmAddAttractions dlg=new FrmAddAttractions(this,"添加景区",true);
            dlg.setVisible(true);
            reloadTable(1);
        }
        else if(actionEvent.getSource()==menu_show_attractions){
            show_flag=1;
            this.reloadTable(1);
            this.reloadSearchTable(1);

        }
        else if(actionEvent.getSource()==menu_delete_attractions&&show_flag==1){
            FrmDeleteAttractions dlg=new FrmDeleteAttractions(this,"删除景区",true);
            dlg.setVisible(true);
            reloadTable(1);
        }
        /*搜索*/
        else if(actionEvent.getSource()==Search&&show_flag==1){
            reloadSearchTable(1);
        }
        /*酒店相关*/
        else if(actionEvent.getSource()==menu_add_hotel){
            FrmAddHotel dlg=new FrmAddHotel(this,"添加酒店",true);
            dlg.setVisible(true);
            reloadTable(2);
        }
        else if(actionEvent.getSource()==menu_show_hotel){
            show_flag=2;
            this.reloadTable(2);
            this.reloadSearchTable(2);

        }
        else if(actionEvent.getSource()==menu_delete_hotel&&show_flag==2){
            FrmDeleteHotel dlg=new FrmDeleteHotel(this,"删除酒店",true);
            dlg.setVisible(true);
            reloadTable(2);
        }
        /*酒店搜索*/
        else if(actionEvent.getSource()==Search&&show_flag==2){
            reloadSearchTable(2);
        }
        /*餐厅相关*/
        else if(actionEvent.getSource()==menu_add_restaurant){
            FrmAddRestaurant dlg=new FrmAddRestaurant(this,"添加餐厅",true);
            dlg.setVisible(true);
            reloadTable(3);
        }
        else if(actionEvent.getSource()==menu_show_restaurant){
            show_flag=3;
            reloadTable(3);
            reloadSearchTable(3);

        }
        else if(actionEvent.getSource()==menu_delete_restaurant&&show_flag==3){
            FrmDeleteRestaurant dlg=new FrmDeleteRestaurant(this,"删除餐厅",true);
            dlg.setVisible(true);
            reloadTable(3);
        }
        /*区域搜索*/
        else if(actionEvent.getSource()==Search&&show_flag==3){
            reloadSearchTable(3);
        }
        /*路线相关*/
        else if(actionEvent.getSource()==menu_add_route){
            FrmAddRoute dlg=new FrmAddRoute(this,"添加路线",true);
            dlg.setVisible(true);
            reloadTable(4);
        }
        else if(actionEvent.getSource()==menu_show_routeInfo){
            show_flag=4;
            reloadTable(4);
            reloadSearchTable(4);

        }
        else if(actionEvent.getSource()==menu_delete_route&&show_flag==4){
            FrmDeleteRoute dlg=new FrmDeleteRoute(this,"删除路线",true);
            dlg.setVisible(true);
            reloadTable(4);
        }
        /*区域搜索*/
        else if(actionEvent.getSource()==Search&&show_flag==4){
            reloadSearchTable(4);
        }
        else if(actionEvent.getSource()==menu_show_orderInfo){
            show_flag=5;
            reloadTable(5);
            reloadSearchTable(5);
        }
        else if(actionEvent.getSource()==Search&&show_flag==5){
            reloadSearchTable(5);
        }
        else if(actionEvent.getSource()==menu_show_userInfo){
            show_flag=6;
            reloadTable(6);
            reloadSearchTable(6);
        }
        else if(actionEvent.getSource()==Search&&show_flag==6){
            reloadSearchTable(6);
        }
    }
}
