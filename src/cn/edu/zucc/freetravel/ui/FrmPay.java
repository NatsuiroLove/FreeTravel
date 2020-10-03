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
     * �������
     */
    /* ������������ */
    private JPanel money= new JPanel();
    //��
    private JButton orderinfoM = new JButton("����������Ϣ����");
    private JButton identyinfoM = new JButton("���������Ϣ����");
    private JButton Pay = new JButton("֧������");
    //״̬��
    private FrmLogin dlgLogin=null;

    private JLabel isvip = new JLabel("����Ա������8�ۣ�");
    private JLabel cost = new JLabel("��֧����"+BeanProductOrder.currentUserOrder.getReal_pay()+"Ԫ");

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
    List<BeanProduct> allProduct = null;
    List<BeanProduct> searchProduct = null;

    /*�������*/
    private void reloadSearchTable(){
        try {
            searchProduct=FreeTravelUtil.userProductManager.searchProduct();
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
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

    /*����ui��ʾ*/
    public FrmPay(Frame f ,String s ,boolean b) {
        super(f,s,b);
        /**
         * ��������˵�����
         */
        /*�������*/
        this.setTitle("֧������");

        /*�ؼ������¼�����*/
        this.Pay.addActionListener(this);
        this.orderinfoM.addActionListener(this);
        this.identyinfoM.addActionListener(this);

        /**
         * ����ڽ�����ʾ ��ʼ������ʾ�������
         */
        /*���ر��*/
        /*����������  */
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
        this.setResizable(false);//��ֹ��������
    }

    //    int show_flag=0;//ȷ�����ڵĿ��ƶ��� ��ʼ�趨Ϊ��ʼ����areaΪ���ƶ������
    /*�ؼ����ӹ���*/
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*��*/
        if(actionEvent.getSource()==Pay){
            try {
                FreeTravelUtil.orderManager.userOrderPay();
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(actionEvent.getSource()==orderinfoM){
            FrmOrderInfoM ofm = new FrmOrderInfoM(this,"�޸�",true);
            ofm.setVisible(true);
        }
        else if(actionEvent.getSource()==identyinfoM){
            FrmIdentyInfoM ifm = new FrmIdentyInfoM(this,"�޸�",true);
            ifm.setVisible(true);
        }
    }
}
