package com.example.daoyou4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements ControllerStage, Initializable {
    @FXML
    public TextField add1;
    @FXML
    public TextField add2;
    @FXML
    public TextField length;
    @FXML
    public ImageView image;
    @FXML
    public Label path;
    @FXML
    public Label error;
    @FXML
    private TextField introduce;
    int[][] D;//最短路径矩阵
    String dataAdd = "src/main/resources/com/example/daoyou4/data.txt";
    String dis = "src/main/resources/com/example/daoyou4/distance.txt";


    StageController myController;
    private MGraph graph;

    //查看最短路径
    public void shortestPath(ActionEvent actionEvent) {
        int v, u, i, w, j, k;
        int n = graph.vernum;
        //创建p矩阵存储最短路径
        int[][][] p = new int[n][n][n];
        //从输入框获取地名并在map中查找并判断是否存在
        VerData start = graph.map.get(add1.getText());
        VerData end = graph.map.get(add2.getText());
        if (start == null || end == null) {
            error.setText("起点或终点不存在");
            error.setVisible(true);
        } else {
            //复制邻接矩阵到D矩阵 如果任意两点有直接路径就标记到p矩阵
        for (v = 0; v < n; v++)
            for (w = 0; w < n; w++) {
                D[v][w] = graph.arcs[v][w];
                for (u = 0; u < n; u++)
                    p[v][w][u] = 0;
                if (D[v][w] < graph.A) {
                    p[v][w][v] = 1;
                    p[v][w][w] = 1;
                }
            }
        //逐行更新D矩阵和p矩阵的最短路径
        for (u = 0; u < n; u++) {
            for (v = 0; v < n; v++)//0
                for (w = 0; w < n; w++)//3
                    if (D[v][u] + D[u][w] < D[v][w]) {
                        D[v][w] = D[v][u] + D[u][w];
                        for (i = 0; i < n; i++)
                            p[v][w][i] = p[v][u][i] == 1 || p[u][w][i] == 1 ? 1 : 0;
                    }
        }
        //获得起点和终点的下标
            k = start.position;
            j = end.position;
            if (D[k][j]==graph.A) {
                error.setText("路径不存在");
                error.setVisible(true);
                return;
            }
            error.setVisible(false);
            //输出p矩阵中的有效路径
            String s = graph.vexs.get(k).name;
            for (u = 0; u < n; u++)
                if (p[k][j][u] == 1 && k != u && j != u)
                    s += "-->" + graph.vexs.get(u).name;
            s += "-->" + graph.vexs.get(j).name;
            length.setText(String.valueOf(D[k][j]));
            path.setText(s);
        }
    }
    //查看所有路径(由于节点数量较多,所以只显示3段以内的路径)
    public void allPath(ActionEvent actionEvent) {
        int v, w, k, j;
        //从输入框获取地名并在map中查找并判断是否存在
        VerData start = graph.map.get(add1.getText());
        VerData end = graph.map.get(add2.getText());
        if (start == null || end == null) {
            error.setText("起点或终点不存在");
            error.setVisible(true);
        } else {
            k = start.position;
            j = end.position;
            error.setVisible(false);
        int n = graph.vernum;
        int[][] p = new int[n][n];
        String s="";
            for ( v = 0; v < n; v++) {
                for ( w = 0; w < n; w++) {
                    if (D[v][w] != graph.A) {
                        p[v][w] = 1;
                        p[w][v] = 1;
                    }}}
                //两点有直接路径就先输出
                    if (p[k][j] == 1) {
                        s += graph.vexs.get(k).name;
                        s += "-->" + graph.vexs.get(j).name;
                        s += "总路线长" + D[k][j]+"\n";
                }
                    //输出长度两段的路径
            for (w = 0; w < n; w++){
                if (p[k][w] == 1 && p[w][j] == 1) {
                    s += graph.vexs.get(k).name;
                    s += "-->" + graph.vexs.get(w).name;
                    s += "-->" + graph.vexs.get(j).name;
                    s += "总路线长" + (D[k][w] + D[w][j]) + "\n";
                }}
            //输出长度三段的路径
            for (v = 0; v < n; v++){
                for (w = 0; w < n; w++){
                    if (p[k][v] == 1 && p[v][w] == 1 && p[w][j] == 1) {
                        s += graph.vexs.get(k).name;
                        s += "-->" + graph.vexs.get(v).name;
                        s += "-->" + graph.vexs.get(w).name;
                        s += "-->" + graph.vexs.get(j).name;
                        s += "总路线长" + (D[k][v] + D[w][j] + D[v][w]) + "\n";
                    }}
        }
            path.setText(s);
        }
    }

        //删除路径
        public void deletePath (ActionEvent actionEvent){
            //获得输入框的两个地点名
            VerData start = graph.map.get(add1.getText());
            VerData end = graph.map.get(add2.getText());
            //判断是否有效
            if (start == null || end == null || start == end) {
                //显示错误信息
                error.setText("起点或终点不存在");
                error.setVisible(true);
            } else {
                //修改邻接矩阵
                graph.arcs[start.position][end.position] = graph.A;
                graph.arcs[end.position][start.position] = graph.A;
                error.setText("路径删除成功!");
                error.setVisible(true);
            }
        }


        @Override
        public void setStageController (StageController stagecontroller){
            this.myController = stagecontroller;
        }
        //返回登录页
        public void exit (ActionEvent actionEvent){
            myController.changeShowStage("mindex", "login");
            myController.changeShowStage("index", "login");
        }
        //查看单个景点信息
        public void detail (ActionEvent actionEvent){
        //获取点击事件的来源按钮
            Button button = (Button) actionEvent.getSource();
            //获取按钮的文本(地名)
            String name = button.getText();
            //从map中查找地点对象
            VerData data = graph.map.get(name);
            //校验判断
            if (data==null) {
                error.setText("景点不存在");
                error.setVisible(true);
                return;
            }
            if (add1.getText().isBlank()) {
                add1.setText(name);
            } else if (add2.getText().isBlank()) {
                add2.setText(name);
            }
            //更换对应图片
            image.setImage(new Image("file:src/main/resources/com/example/daoyou4/img/" + name + ".png"));
            //更新介绍框
            introduce.setText(name + ":" + data.introduction);
        }
    //查看全部景点信息
    public void allDetails (ActionEvent actionEvent) throws InterruptedException {
        String s="";
        //遍历列表打印景点信息
        for (VerData data : graph.vexs) {
            s+=data.toString();
        }
        //显示全部景点信息和图片
            path.setText(s);
            myController.showStage("details");
        }


        //初始化地图
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
        //读取文件数据
            try {
                //地图初始化
                this.graph = new MGraph(dataAdd, dis);
                int n = graph.vernum;
                //拷贝邻接矩阵到最短路径矩阵
                D= new int[n][n];
                for (int v = 0; v < n; v++) {
                    System.arraycopy(graph.arcs[v], 0, D[v], 0, n);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //修改路径
        public void updatePath (ActionEvent actionEvent){
        //从输入框获取两点的地名
            VerData start = graph.map.get(add1.getText());
            VerData end = graph.map.get(add2.getText());
            //校验判断
            if (start == null || end == null || start == end) {
                error.setText("起点或终点不存在");
                error.setVisible(true);
            } else if (length.getText().isBlank() || length.getText().equals("0")) {
                error.setText("距离不能是空或者0");
                error.setVisible(true);
            } else {
                //修改邻接矩阵
                int dis = Integer.parseInt(length.getText());
                graph.arcs[start.position][end.position] = dis;
                graph.arcs[end.position][start.position] = dis;
                error.setText("路径修改成功!");
                error.setVisible(true);
            }
        }
        //保存路径到文件里
        public void savePath (ActionEvent actionEvent) throws IOException {
            MGraph.saveArray(dis, graph.arcs);//保存到邻接矩阵文件
            MGraph.saveData(dataAdd, graph.vexs);//保存到数据文件
            error.setText("修改保存成功!");
            error.setVisible(true);
        }
    //删除景点信息(从起点输入框获得)
    public void deleteV(ActionEvent actionEvent) {
        String deletedAdd = add1.getText();
        //在map中删除景点对象并判断
        VerData remove = graph.map.remove(deletedAdd);
        if (remove==null) {
            error.setText("该点不存在");
            error.setVisible(true);
            return;
        }
        //获得对象的下标
        int pos = remove.position;
        //顺序表修改排在后面的对象的下标
        for (int i = pos+1; i < graph.vernum; i++) {
            graph.vexs.get(i).position--;
        }
        //从顺序表里移除这个对象
        graph.vexs.remove(remove);
        graph.vernum--;
        //更新邻接矩阵
        int[][] update= new int[graph.vernum][graph.vernum];
        for (int i = 0; i < graph.vernum; i++) {
            for (int j = 0; j < graph.vernum; j++) {
                int up1=i>=pos?i+1:i;
                int up2=j>=pos?j+1:j;
                update[i][j]=graph.arcs[up1][up2];
            }
        }
        //输出更新后的全部景点信息
        graph.arcs=update;
        error.setText("删除成功");
        error.setVisible(true);
        String s="修改后的地点信息:\n";
        for (VerData vex : graph.vexs) {
            s+=vex.toString();
        }
        path.setText(s);
    }
    //添加景点信息
    public void addV(ActionEvent actionEvent) {
        //从起点输入框获取景点名
        String s = add1.getText();
        //从介绍框获取景点介绍内容
        String introduceText = introduce.getText();
        //判断
        if (s.isBlank()||introduceText.isBlank()) {
            error.setText("在起点栏和介绍框输入新的景点信息");
            error.setVisible(true);
            return;
        }
        //创建新的景点对象添加到顺序表和map中
        VerData data = new VerData(graph.vernum, s, introduceText);
        graph.vexs.add(data);
        graph.map.put(s,data);
        int vernum = ++graph.vernum;
        //更新邻接矩阵
        int[][] update= new int[vernum][vernum];
        for (int i = 0; i < vernum; i++) {
            for (int j = 0; j <vernum; j++) {
                update[i][j]=(i==vernum-1||j==vernum-1)?graph.A:graph.arcs[i][j];
            }
        }
        graph.arcs=update;
        //输出添加后的全部景点信息
        String u="";
        for (VerData verData : graph.vexs) {
            s+=verData.toString();
        }
        path.setText(u);
        error.setText("添加成功！");
        error.setVisible(true);
    }
    //修改景点信息
    public void updateV(ActionEvent actionEvent) {
        //从起点输入框获取要修改的景点名
        String s = add1.getText();
        //从介绍框获取景点介绍内容
        String introduceText = introduce.getText();
        //查找map中的景点对象
        VerData data = graph.map.get(s);
        //判断是否存在
        if (s.isBlank()||introduceText.isBlank()) {
            error.setText("在起点栏和介绍框输入景点信息");
            error.setVisible(true);
            return;
        }else if (data == null) {
            error.setText("起点栏输入的景点不存在");
            error.setVisible(true);
            return;
        }
        //修改该景点的简介
        data.introduction=introduceText;
        error.setText("景点信息修改成功");
        error.setVisible(true);
    }
}
