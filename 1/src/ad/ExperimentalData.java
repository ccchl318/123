package ad;

import java.util.Arrays;


public class ExperimentalData {
    ExperimentalData() {
    }

    //需要的一些变量
    final double t[][] = {{1.84, 1.32, 1.20, 1.14, 1.11, 1.09, 1.08, 1.07, 1.06, 1.05},
            {6.31, 2.92, 2.35, 2.13, 2.02, 1.94, 1.90, 1.86, 1.83, 1.81},
            {12.71, 4.30, 3.18, 2.78, 2.57, 2.45, 2.36, 2.31, 2.26, 2.23},
            {63.66, 9.93, 5.84, 4.60, 4.03, 3.71, 3.50, 3.36, 3.25, 3.17},
            {636.62, 31.60, 12.92, 8.61, 6.86, 5.96, 5.40, 5.04, 4.78, 4.59}};
    private double Data[];

    private double average;
    private double Mu_a;
    private double Mu_b;
    private double Mu;
    private int choice1 = 0;
    private double choice2 = 3;
    private double delta_x;
    private int num;

    //一些set，get方法
    public void setData(double[] Data) {
        this.Data = Data;
    }

    public void ShowData() {
        System.out.println(Arrays.toString(this.Data));
    }


    public double getAverage() {
        return average;
    }


    public double getMu_a() {
        return Mu_a;
    }


    public double getMu_b() {
        return Mu_b;
    }


    public double getMu() {
        return Mu;
    }


    public void setDelta_x(double delta_x) {
        this.delta_x = delta_x;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setData(double data, int i) {
        this.Data[i] = data;

    }

    public void setData(int x) {
        Data = new double[x];
    }


    //--------------------------------------------
    public void setChoice1(String str1) {

        switch (str1) {
            case "0.683":
                choice1 = 0;

                break;
            case "0.90":
                choice1 = 1;

                break;
            case "0.95":
                choice1 = 2;

                break;
            case "0.99":
                choice1 = 3;

                break;
            case "0.999":
                choice1 = 4;

                break;

        }
    }


    public void setChoice2(String str2) {

        switch (str2) {
            case "正态":
                choice2 = 3;

                break;
            case "均匀":
                choice2 = Math.sqrt(3);

                break;
            case "三角":
                choice2 = Math.sqrt(6);

                break;
            case "反正弦":
                choice2 = Math.sqrt(2);

                break;
            case "两点":
                choice2 = 1;

                break;

        }
    }



    public void calculateData() {

        double sum = 0;
        double Sum = 0;
        double sigma;
        for (int i = 0; i < num; i++)
            sum += Data[i];
        average = sum / (num);
        for (int i = 0; i < num; i++)
            Sum += (Data[i] - average) * (Data[i] - average);
        sigma = Math.sqrt(Sum / (num - 1));
        //A类不确定度
        Mu_a = t[choice1][num - 2] * sigma / Math.sqrt(num);
        //B类不确定度
        Mu_b = delta_x / choice2;
        //计算合成不确定度
        Mu = Math.sqrt(Mu_a * Mu_a + Mu_b * Mu_b);
    }

    public void refresh() {
        Data = null;
        average = 0;
        Mu_a = 0;
        Mu_b = 0;
        Mu = 0;
        choice1 = 0;
        choice2 = 3;
        delta_x = 0;
        num = 0;

    }
}
