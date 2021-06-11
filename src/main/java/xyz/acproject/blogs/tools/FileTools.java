package xyz.acproject.blogs.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileTools {
	public static void write(String filePath,List<?> list,String string,String fileName) {
		File file = new File(filePath);
		if(file.exists()==false)file.mkdirs();
		file = new File(filePath+"//"+fileName+".txt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		StringBuilder stringBuilder = new StringBuilder();
		BufferedWriter bufferedWriter =null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			for (Object object : list) {
				stringBuilder.append(string+"\r\n"+object.toString()+"\r\n");
			}
			bufferedWriter.write(stringBuilder.toString());
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if(bufferedWriter!=null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	public static String read(String filePath,String fileName) {
		File file = new File(filePath);
		if(file.exists()==false)file.mkdirs();
		file = new File(filePath+"//"+fileName+".txt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader =null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			char[] by = new char[2048];
			int length =-1;
			while((length=bufferedReader.read(by))!=-1) {
				String string = new String(by,0,length);
				byte[] bytes = string.getBytes("utf-8");
				stringBuilder.append(new String(bytes,"utf-8"));
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return "\r\n";
	}
}
