package ad;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    GUI() {
    }

    public void CreatGUI(ExperimentalData experimentalData) {
        Frame frame = new Frame("不确定度计算");
        //将frame的布局管理器设置为CardLayout
        CardLayout cardLayout = new CardLayout();
        frame.setLayout(cardLayout);
        //创建三个Panel容器p1，p2，p3分别当作主页面，单组数据与多组数据的计算界面，并将它们添加到frame中
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        Panel p3 = new Panel();
        frame.add(p1);
        frame.add(p2);
        frame.add(p3);
        //主页
        //将p1的布局管理器设置为GridLayout
        p1.setLayout(new GridLayout(3, 1));
        //第一行添加一个Label

        p1.add(new Label("欢迎使用不确定度计算器", Label.CENTER));


        Panel p1_2 = new Panel(new GridLayout(1, 2));
        p1.add(p1_2);


        Button p1_b1 = new Button("单组数据");
        Button p1_b2 = new Button("多组数据");
        p1_2.add(p1_b1);
        p1_2.add(p1_b2);

        //添加监视器
        p1_b1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cardLayout.next(frame);
                frame.setBounds(560, 300, 500, 500);

            }
        });
        p1_b2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cardLayout.last(frame);
            }
        });


        //p2
        //将p2的布局管理器设置为BorderLayout
        p2.setLayout(new BorderLayout());

        //North部分
        //在north部分添加一个box容器
        Box p2_north = Box.createVerticalBox();

        Box p2_north_1 = Box.createHorizontalBox();
        Box p2_north_1_1 = Box.createVerticalBox();
        p2_north_1_1.add(new Label("置信度", Label.CENTER));
        //添加Choice下拉组件
        Choice zhixin = new Choice();
        zhixin.add("0.683");
        zhixin.add("0.90");
        zhixin.add("0.95");
        zhixin.add("0.99");
        zhixin.add("0.999");
        p2_north_1_1.add(zhixin);
        p2_north_1.add(p2_north_1_1);
        zhixin.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String str1 = zhixin.getSelectedItem();
                experimentalData.setChoice1(str1);
//___________________________________

            }
        });

        Box p2_north_1_2 = Box.createVerticalBox();
        p2_north_1_2.add(new Label("分布关系", Label.CENTER));
        //同理添加一个Choice组件
        Choice fenbuguanxi = new Choice();
        fenbuguanxi.add("正态");
        fenbuguanxi.add("均匀");
        fenbuguanxi.add("三角");
        fenbuguanxi.add("反正弦");
        fenbuguanxi.add("两点");
        p2_north_1_2.add(fenbuguanxi);
        p2_north_1.add(p2_north_1_2);
        fenbuguanxi.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String str2 = fenbuguanxi.getSelectedItem();
                experimentalData.setChoice2(str2);
//___________________________________


            }
        });

        Box p2_north_1_3 = Box.createVerticalBox();

        //添加一个TextField文本框组件
        TextField yiqifenbianlv = new TextField();
        p2_north_1_3.add(new Label("仪器分辨率", Label.CENTER));
        p2_north_1_3.add(yiqifenbianlv);
        p2_north_1.add(p2_north_1_3);


        //new一个ScrollPane的实例，放在center部分
        Box p2_center = Box.createVerticalBox();
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);


        Box p2_north_2 = Box.createHorizontalBox();
        p2_north_2.add(new Label("数据个数：", Label.CENTER));
        TextField NumText = new TextField();
        p2_north_2.add(NumText);
        Button OK = new Button("OK");
        p2_north_2.add(OK);


        boolean ok = true;
        //OK按钮的监听器
        OK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (ok) {

                    String str = yiqifenbianlv.getText();
                    String str1 = NumText.getText();
                    double derta_x = Double.parseDouble(str);
                    int num = Integer.parseInt(str1);//可能会输入的不是数字
                    experimentalData.setDelta_x(derta_x);
                    experimentalData.setNum(num);


                    //Center部分
                    //利用一个for循环添加Lable和TextField组件并显示
                    for (int i = 0; i < experimentalData.getNum(); i++) {

                        p2_center.add(new Label("第" + (i + 1) + "个数据"));
                        p2_center.add(new TextField());
                        scrollPane.add(p2_center);
                        p2.add(scrollPane, BorderLayout.CENTER);


                    }
                    p2.validate();
                }


            }
        });

        p2_north.add(p2_north_1);
        p2_north.add(p2_north_2);

        p2.add(p2_north, BorderLayout.NORTH);


        //South部分
        Box p2_Sorth = Box.createHorizontalBox();
        Button p2_South_1 = new Button("计算");
        Button p2_South_2 = new Button("刷新");
        Button p2_South_3 = new Button("返回");
        p2_Sorth.add(p2_South_1);
        p2_Sorth.add(p2_South_2);
        p2_Sorth.add(p2_South_3);


        //计算按钮
        p2_South_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                experimentalData.setData(experimentalData.getNum());
                for (int i = 0; (i / 2) < experimentalData.getNum(); i += 2) {
                    TextField data = (TextField) p2_center.getComponent(i + 1);
                    String abc = data.getText();
                    double Data = Double.parseDouble(abc);
                    experimentalData.setData(Data, (i / 2));
                }
                experimentalData.calculateData();

                //对结果进行显示
                Dialog d = new Dialog(frame, "计算结果", false);
                GridLayout gridLayout = new GridLayout(5, 2);
                d.setLayout(gridLayout);
                d.add(new Label("平均值:", Label.CENTER));
                d.add(new TextField(Double.toString(experimentalData.getAverage())));
                d.add(new Label("A类不确定度:", Label.CENTER));
                d.add(new TextField(Double.toString(experimentalData.getMu_a())));
                d.add(new Label("B类不确定度:", Label.CENTER));
                d.add(new TextField(Double.toString(experimentalData.getMu_b())));
                d.add(new Label("合成不确定度:", Label.CENTER));
                d.add(new TextField(Double.toString(experimentalData.getMu())));
                d.add(new Label("最终结果:", Label.CENTER));
                d.add(new TextField(experimentalData.getAverage() + "±" + experimentalData.getMu()));


                d.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        d.setVisible(false);
                    }
                });
                d.setBounds(1200, 300, 450, 300);
                d.setVisible(true);

            }
        });

        //刷新按钮
        p2_South_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //重置ExperimentalData的各个参数
                experimentalData.refresh();
                p2_center.removeAll();
                NumText.setText("");
                yiqifenbianlv.setText("");
                fenbuguanxi.select(0);
                zhixin.select(0);

                p2.validate();


            }
        });

        //返回按钮
        p2_South_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cardLayout.first(frame);
                frame.setBounds(650, 300, 300, 150);
            }
        });

        p2.add(p2_Sorth, BorderLayout.SOUTH);


        //多组数据
        Button p3_1 = new Button("尚未完工，敬请期待！");
        p3.add(p3_1, BorderLayout.CENTER);
        p3_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cardLayout.first(frame);
            }
        });


        //关闭窗口
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        frame.setBounds(650, 300, 300, 150);
        frame.setVisible(true);

    }


}
