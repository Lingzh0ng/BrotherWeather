package com.wearapay.brotherweather.utils;

import android.os.Environment;

import com.wearapay.brotherweather.domain.City;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class CityUtils {
    private static String a = "</p><p style=\"text-align:left;\">";
    private static String b = "</p><p><br /></p>";
    private static String c =
            "<p style=\"text-align:left;\"><strong>citycode 城市 二级 &nbsp;一级</strong></p><p style=\"text-align:left;\">";

    public static List<City> copyCity(String path) {
        List<City> cities = new ArrayList<>();
        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains(c)) {
                    line = line.replace(c, "");
                    line = line.replaceAll(a, "==");
                    line = line.replaceAll("，", ",");
                    line = line.replace(b, "");
                    line = line.replace(" ", "");
                    FileWriter fileWriter = new FileWriter(
                            new File(Environment.getExternalStorageDirectory().getPath(), "myCity.txt"));
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(line);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    reader.close();
                    return perseCity(file);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static List<City> perseCity(File file) {
        List<City> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            String[] split = stringBuffer.toString().split("==");
            City city = null;
            for (int i = 0; i < split.length; i++) {
                city = new City(split[i]);
                list.add(city);
                System.out.println(city);
            }
            System.out.println("citys:" + list.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
