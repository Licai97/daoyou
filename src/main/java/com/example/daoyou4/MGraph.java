package com.example.daoyou4;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MGraph {
    ArrayList<VerData> vexs;
    HashMap<String,VerData> map;
    int[][] arcs;
    int vernum,arcnum;
    int A=10000;

    public static int[][] readArray(String dataAdd) {
        //1.声明一个字符输入流
        FileReader reader = null;
        //2.声明一个字符输入缓冲流
        BufferedReader readerBuf = null;
        //3.声明一个二维数组
        int[][] array = null;
        try {
            //4.指定reader的读取路径
            reader = new FileReader(dataAdd);
            //5.通过BufferedReader包装字符输入流
            readerBuf = new BufferedReader(reader);
            //6.创建一个集合，用来存放读取的文件的数据
            List<String> strList = new ArrayList<>();
            //7.用来存放一行的数据
            String lineStr;
            //8.逐行读取txt文件中的内容
            while((lineStr = readerBuf.readLine()) != null) {
                //9.把读取的行添加到list中
                strList.add(lineStr);
            }
            //10.获取文件有多少行
            int lineNum = strList.size();
            //11.获取数组有多少列
            String s =  strList.get(0);
            int columnNum = s.split(",").length;
            //12.根据文件行数创建对应的数组
            array = new int[strList.size()][columnNum];
            //13.记录输出当前行
            int count = 0;
            //14.循环遍历集合，将集合中的数据放入数组中
            for(String str : strList) {
                //15.将读取的str按照","分割，用字符串数组来接收
                String[] strs = str.split(",");
                for(int i = 0; i < columnNum; i++) {
                    array[count][i] = Integer.parseInt(strs[i]);
                }
                //16.将行数 + 1
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //17.关闭字符输入流
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //18.关闭字符输入缓冲流
            try {
                if(readerBuf != null)
                    readerBuf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //19.返回稀疏数组
        return array;
    }

    public static void saveArray(String dataAdd,int[][] array){
        //1.创建字符输出流
        FileWriter writeFile = null;
        try {
            //2.数据想写入的路径及文件
            File file = new File(dataAdd);
            //3.如果该文件不存在，就创建
            if(!file.exists()) {
                file.createNewFile();
            }
            //4.给字节输出流赋予实例
            writeFile = new FileWriter(file);
            //5.通过循环将数组写入txt文件中
            for(int i = 0; i < array.length; i++) {
                //6.数据前n - 1列尾部加入","
                for(int j = 0; j < array[0].length - 1; j++) {
                    writeFile.write(array[i][j] + ",");
                }
                //7.数组最后一列后面不加","
                writeFile.write(array[i][array[0].length - 1] + "");
                //8.加上换行符
                writeFile.write("\n");
            }
            //9.把writeFile里的数据全部刷新一次，全部写入文件中
            writeFile.flush();
        } catch (Exception e) {//10.异常捕获
            e.printStackTrace();
        } finally {
            try {
                //11.如果writeFile不为空，就将其关闭
                if(writeFile != null)
                    writeFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MGraph(String dataAdd,String distance) throws IOException{
        Path path = Paths.get(dataAdd);//读取文件路径
        List<String> lines = Files.readAllLines(path);//逐行读取
        int n = lines.size();
        this.vexs= new ArrayList<>();//创建对象
        map=new HashMap<>();
        arcs=readArray(distance);
        for (String line : lines) {
            String[] strs = line.split(">");
            int i = Integer.parseInt(strs[0]);
            VerData data = new VerData(i, strs[1], strs[2]);
            vexs.add(data);
            map.put(strs[1],data);
        }
        this.vernum = vexs.size();
        arcnum=arcs[0].length;
    }

    public static void saveData(String dataAdd, ArrayList<VerData> vexs) throws IOException {
        Path path = Paths.get(dataAdd);//读取文件路径
        String s="";
        for (VerData vex : vexs) {
            s+=vex.toString();
        }
        Files.write(path,s.getBytes(StandardCharsets.UTF_8));//逐行读取
    }

}
