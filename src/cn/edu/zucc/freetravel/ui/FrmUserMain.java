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
     * �������
     */
    /* ������������ */
    private JPanel searchPanel= new JPanel();
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_route = new JMenu("��·��ѯ");
    private JMenu menu_self = new JMenu("��������");
    private JMenu menu_other = new JMenu("��������");

    /*���������ѡ��*/
    //��·��ѯ
    private JMenuItem menu_show_route = new JMenuItem ("��ʾ��·��Ϣ");
    //���˹���
    private JMenuItem menu_show_self = new JMenuItem ("��ʾ������Ϣ");
    //��������
    private JMenuItem menu_bevip = new JMenuItem("��Ϊ��Ա");
    private JMenuItem menu_modify_pwd = new JMenuItem("�޸�����");
    //������
    private JButton Search = new JButton("����");
    private JButton Detail = new JButton("��ʾ��ϸ��Ϣ");
    private JButton Pay = new JButton("֧������");
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
    //�������
    private Object tblSearchData[][];
    DefaultTableModel tabSearchModel=new DefaultTableModel();
    private JTable dataTableSearch=new JTable(tabSearchModel);

    /**
     * �����ݿ��м������ݱ��
     */
    /*���ر��*/
    private BeanRoute curRoute=null;//�����������

    List<BeanRoute> allRoute = null;
    List<BeanRoute> searchRoute = null;

    /*�������*/
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

    public static boolean flag=false;//����Ƿ�Ϊ��һ����������

    /*����ui��ʾ*/
    public FrmUserMain() {
        /**
         * ��������˵�����
         */
        /*�������*/
        this.setTitle("������31803112");

        if(BeanUser.currentLoginUser==null)return ; //����ͨ�û���¼ �˳�

        if(flag==false){
            try {
                BeanProductOrder order = FreeTravelUtil.orderManager.userOrderStart();
            } catch (BaseException e) {
                e.printStackTrace();
            }finally {
                flag=true;
            }
        }

        /*�˵��ؼ������¼�����*/
        this.menu_route.add(this.menu_show_route);this.menu_show_route.addActionListener(this);
        this.menu_self.add(this.menu_show_self);this.menu_show_self.addActionListener(this);
        this.menu_other.add(this.menu_modify_pwd);this.menu_modify_pwd.addActionListener(this);
        this.menu_other.add(this.menu_bevip);this.menu_bevip.addActionListener(this);
        this.Detail.addActionListener(this);
        this.Pay.addActionListener(this);
        this.Search.addActionListener(this);

        /*�����ؼ���װ*/
        menubar.add(menu_route);
        menubar.add(menu_self);
        menubar.add(menu_other);
        this.setJMenuBar(menubar);
        /**
         * ����ڽ�����ʾ ��ʼ������ʾ�������
         */
        /*���ر��*/
        /*����������  */
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
        this.setResizable(false);//��ֹ��������
    }

//    int show_flag=0;//ȷ�����ڵĿ��ƶ��� ��ʼ�趨Ϊ��ʼ����areaΪ���ƶ������
    /*�ؼ����ӹ���*/
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*�޸�����*/
        if(actionEvent.getSource()==menu_modify_pwd){
            FrmUserModifyPwd dlg=new FrmUserModifyPwd(this,"�����޸�",true);
            dlg.setVisible(true);
        }
        /*��������*/
        else if(actionEvent.getSource()==Search){
            reloadSearchTable();
        }
        /*��ʾ��ϸ��Ϣ*/
        else if(actionEvent.getSource()==Detail){

            if(curRoute==null) {
                JOptionPane.showMessageDialog(null, "��ѡ��·��", "����",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmDetail dt = null;
            try {
                dt = new FrmDetail(this,"��·����",true,curRoute);
            } catch (BaseException e) {
                e.printStackTrace();
            }
            dt.setVisible(true);
        }
        /*��*/
        else if(actionEvent.getSource()==Pay){
            FrmPay py = new FrmPay(this,"֧������",true);
            py.setVisible(true);
        }
        /*��ǿ*/
        else if(actionEvent.getSource()==menu_bevip){
            FrmUserBevip bv = new FrmUserBevip(this,"��Ϊ��Ա",true);
            bv.setVisible(true);
        }
        else if(actionEvent.getSource()==menu_show_self){
            FrmPersonInfo psi = new FrmPersonInfo(this,"������Ϣ",true);
            psi.setVisible(true);
        }
    }
}
