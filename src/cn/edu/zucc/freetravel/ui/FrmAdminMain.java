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
     * �������
     */
    /* ������������ */
    private JPanel searchPanel= new JPanel();
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_area = new JMenu("�������ݹ���");
    private JMenu menu_attractions = new JMenu("�������ݹ���");
    private JMenu menu_hotel = new JMenu("�Ƶ����ݹ���");
    private JMenu menu_restaurant = new JMenu("�������ݹ���");
    private JMenu menu_route = new JMenu ("��·���ݹ���");//
    private JMenu menu_user = new JMenu ("�û�������¼");//�����û�������Ϣ��¼����Ӧ�û��������乺���Ʒ��Ϣ
    private JMenu menu_other = new JMenu ("��������");

    /*���������ѡ��*/
    //�������
    private JMenuItem menu_show_area = new JMenuItem ("��ʾ������Ϣ");
    private JMenuItem menu_add_area = new JMenuItem ("��������");
    private JMenuItem menu_delete_area = new JMenuItem("ɾ������");
    //�������
    private JMenuItem menu_show_attractions = new JMenuItem ("��ʾ������Ϣ");
    private JMenuItem menu_add_attractions = new JMenuItem( "��������");
    private JMenuItem menu_delete_attractions = new JMenuItem("ɾ������");
    //�Ƶ����ݹ���
    private JMenuItem menu_show_hotel = new JMenuItem ("��ʾ�Ƶ���Ϣ");
    private JMenuItem menu_add_hotel = new JMenuItem( "�����Ƶ�");
    private JMenuItem menu_delete_hotel = new JMenuItem( "ɾ���Ƶ�");
    //�������ݹ���
    private  JMenuItem menu_show_restaurant = new JMenuItem ("��ʾ������Ϣ");
    private  JMenuItem menu_add_restaurant = new JMenuItem( "��������");
    private  JMenuItem menu_delete_restaurant = new JMenuItem( "ɾ������");
    //��·���ݹ���
    private  JMenuItem menu_show_routeInfo = new JMenuItem("��ʾ��·/��Ʒ�������");
    private  JMenuItem menu_add_route = new JMenuItem("������·");
    private  JMenuItem menu_delete_route = new JMenuItem( "ɾ����·");
    //���������ݹ���
    private  JMenuItem menu_show_associate = new JMenuItem("��ʾ��·����Ƽ�");
    private  JMenuItem menu_add_associate = new JMenuItem("������·�Ƽ�����");
    private  JMenuItem menu_delete_associate = new JMenuItem("ɾ����·�Ƽ�����");
    //�û���Ϣչʾ
    private  JMenuItem menu_show_orderInfo = new JMenuItem( "��ʾ������Ϣ");
    private  JMenuItem menu_show_userInfo = new JMenuItem( "��ʾ�û���Ϣ");
    //����������ʾ
    private  JMenuItem menu_modify_pwd = new JMenuItem("�޸�����");
    //������
    private JButton Search = new JButton("����");
    private JLabel SearchLabel = new JLabel("������Ҫ��ѯ�Ķ������ƣ�");
    private JTextField SearchEdit = new JTextField(25);
    //״̬��
    private FrmLogin dlgLogin=null;

    /**
     * ���ݱ������ʵ��
     */
    //״̬��
    private JPanel statusBar = new JPanel();
    /*������ʾ���*/
    //��Ϣ���
    private Object tblData[][];
    DefaultTableModel tabModel=new DefaultTableModel();
    private JTable dataTable=new JTable(tabModel);
    //�������
    private Object tblSearchData[][];
    DefaultTableModel tabSearchModel=new DefaultTableModel();
    private JTable dataTableSearch=new JTable(tabSearchModel);

    /**
     * �����ݿ��м������ݱ��
     */
    /*���ر��*/
    private BeanArea curArea=null;//���������������
    private BeanAttractions curAttractions= null;//��������Ӿ�����
    private BeanHotel curHotel =null;//��������ӾƵ���
    private BeanRestaurant curRestaurant = null;
    private BeanRoute curRoute = null;

    List<BeanArea>  allArea=null;//����������
    List<BeanArea>  searchArea =null;//��������������
    List<BeanAttractions>  allAttractions=null;//����������
    List<BeanAttractions>  searchAttractions =null;//��������������
    List<BeanHotel> allHotel = null;//���оƵ���
    List<BeanHotel> searchHotel = null;//�����������оƵ�
    List<BeanRestaurant> allRestaurant = null; //���в������
    List<BeanRestaurant> searchRestaurant = null;//�����������в���
    List<BeanRoute> allRoute = null;
    List<BeanRoute> searchRoute = null;
    List<BeanProductOrder> allOrder = null;
    List<BeanProductOrder> searchOrder = null;
    List<BeanUser> allUser = null;
    List<BeanUser> searchUser = null;

    /*����Ϣ���*/
    private void reloadTable(int col){
        if(col==0){
            try {
                allArea= FreeTravelUtil.adminAreaManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
        /*�Ƶ����*/
        else if(col==2){
            try {
                allHotel= FreeTravelUtil.adminHotelManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
        /* ��������*/
        else if(col==3){
            try {
                allRestaurant= FreeTravelUtil.adminRestaurantManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
        /*��·����*/
        else if(col==4){
            try {
                allRoute= FreeTravelUtil.adminRouteManager.loadAll();
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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

    /*�������*/
    private void reloadSearchTable(int col){

        if(col==0){
            try {
                String search_name=SearchEdit.getText();
                searchArea= FreeTravelUtil.adminAreaManager.searchArea(search_name);
            } catch (BaseException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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


    /*����ui��ʾ*/
    public FrmAdminMain() {
        /**
         * ��������˵�����
         */
        /*�������*/
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("�����������Ϣ����ϵͳ31803112");

        /*��¼�������ӻ�*/
        dlgLogin=new FrmLogin(this,"�����������½",true);
        /*�˴��жϵ�¼���û�����*/
        dlgLogin.setVisible(true);

        if(BeanAdmin.currentLoginAdmin==null)return ;//�ǹ���Ա��¼ �˳�


        /*�˵��ؼ������¼�����*/
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

        /*�����ؼ���װ*/
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
         * ����ڽ�����ʾ ��ʼ������ʾ�������
         */
        /*���ر��*/
        /*������*/
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        /*����������  */
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
         * �ײ�״̬��
         */
        //״̬��
        String nowLogin=BeanAdmin.currentLoginAdmin.getAdmin_name();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("���� ! "+nowLogin+" !");//�޸ĳ�   ���ã�+��½�û���
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });

        this.setSize(600, 500);
        this.setVisible(true);
//        this.setResizable(false);//��ֹ��������
    }

    int show_flag=0;//ȷ�����ڵĿ��ƶ��� ��ʼ�趨Ϊ��ʼ����areaΪ���ƶ������
    /*�ؼ����ӹ���*/
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*�޸�����*/
        if(actionEvent.getSource()==menu_modify_pwd){
            FrmAdminModifyPwd dlg=new FrmAdminModifyPwd(this,"�����޸�",true);
            dlg.setVisible(true);
        }

        /*�������*/
        else if(actionEvent.getSource()==menu_add_area){
            FrmAddArea dlg=new FrmAddArea(this,"�������",true);
            dlg.setVisible(true);
            reloadTable(0);
        }
        else if(actionEvent.getSource()==menu_show_area){
            show_flag=0;
            reloadTable(0);
            reloadSearchTable(0);

        }
        else if(actionEvent.getSource()==menu_delete_area&&show_flag==0){
            FrmDeleteArea dlg=new FrmDeleteArea(this,"ɾ������",true);
            dlg.setVisible(true);
            reloadTable(0);
        }
        /*��������*/
        else if(actionEvent.getSource()==Search&&show_flag==0){
            reloadSearchTable(0);
        }
        /*�������*/
        else  if(actionEvent.getSource()==menu_add_attractions){
            FrmAddAttractions dlg=new FrmAddAttractions(this,"��Ӿ���",true);
            dlg.setVisible(true);
            reloadTable(1);
        }
        else if(actionEvent.getSource()==menu_show_attractions){
            show_flag=1;
            this.reloadTable(1);
            this.reloadSearchTable(1);

        }
        else if(actionEvent.getSource()==menu_delete_attractions&&show_flag==1){
            FrmDeleteAttractions dlg=new FrmDeleteAttractions(this,"ɾ������",true);
            dlg.setVisible(true);
            reloadTable(1);
        }
        /*����*/
        else if(actionEvent.getSource()==Search&&show_flag==1){
            reloadSearchTable(1);
        }
        /*�Ƶ����*/
        else if(actionEvent.getSource()==menu_add_hotel){
            FrmAddHotel dlg=new FrmAddHotel(this,"��ӾƵ�",true);
            dlg.setVisible(true);
            reloadTable(2);
        }
        else if(actionEvent.getSource()==menu_show_hotel){
            show_flag=2;
            this.reloadTable(2);
            this.reloadSearchTable(2);

        }
        else if(actionEvent.getSource()==menu_delete_hotel&&show_flag==2){
            FrmDeleteHotel dlg=new FrmDeleteHotel(this,"ɾ���Ƶ�",true);
            dlg.setVisible(true);
            reloadTable(2);
        }
        /*�Ƶ�����*/
        else if(actionEvent.getSource()==Search&&show_flag==2){
            reloadSearchTable(2);
        }
        /*�������*/
        else if(actionEvent.getSource()==menu_add_restaurant){
            FrmAddRestaurant dlg=new FrmAddRestaurant(this,"��Ӳ���",true);
            dlg.setVisible(true);
            reloadTable(3);
        }
        else if(actionEvent.getSource()==menu_show_restaurant){
            show_flag=3;
            reloadTable(3);
            reloadSearchTable(3);

        }
        else if(actionEvent.getSource()==menu_delete_restaurant&&show_flag==3){
            FrmDeleteRestaurant dlg=new FrmDeleteRestaurant(this,"ɾ������",true);
            dlg.setVisible(true);
            reloadTable(3);
        }
        /*��������*/
        else if(actionEvent.getSource()==Search&&show_flag==3){
            reloadSearchTable(3);
        }
        /*·�����*/
        else if(actionEvent.getSource()==menu_add_route){
            FrmAddRoute dlg=new FrmAddRoute(this,"���·��",true);
            dlg.setVisible(true);
            reloadTable(4);
        }
        else if(actionEvent.getSource()==menu_show_routeInfo){
            show_flag=4;
            reloadTable(4);
            reloadSearchTable(4);

        }
        else if(actionEvent.getSource()==menu_delete_route&&show_flag==4){
            FrmDeleteRoute dlg=new FrmDeleteRoute(this,"ɾ��·��",true);
            dlg.setVisible(true);
            reloadTable(4);
        }
        /*��������*/
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
