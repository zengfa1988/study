package com.tsh.util;

import java.io.File;

public class FileUtil {

	public static void main(String[] args) {
		FileUtil.deleteFolder("D:\\学习资料\\workspace", "^.*\\.svn$");
//		FileUtil.deleteFile("D:\\学习资料\\workspace", "^.*README$");
//		FileUtil.deleteFile("D:\\学习资料\\workspace", ".classpath");
	}
	
	/**
	 * 删除目录下所有匹配的文件夹
	 * @param baseDir
	 * @param folderPatter
	 */
	public static void deleteFolder(String baseDir,String folderPatter) {
		File dirFile = new File(baseDir);
		if(!dirFile.exists() || dirFile.isFile()) {
			System.out.println("目录不存在或不是目录");
			return;
		}
		File[] files = dirFile.listFiles();
		for(File file : files) {
			if(file.isFile()) {
				continue;
			}
			String filename = file.getName();
			if(filename.matches(folderPatter)) {
				System.out.println("删除："+file.getAbsolutePath());
				Boolean delResult = deleteDirectory(file);
				System.out.println(delResult?"删除成功":"删除失败");
			}else {
				deleteFolder(file.getAbsolutePath(), folderPatter);
			}
		}
	}
	
	/**
	 * 删除目录下匹配的所有文件
	 * @param baseDir
	 * @param folderPatter
	 */
	public static void deleteFile(String baseDir,String folderPatter) {
		File dirFile = new File(baseDir);
		if(!dirFile.exists() || dirFile.isFile()) {
			System.out.println("目录不存在或不是目录");
			return;
		}
		File[] files = dirFile.listFiles();
		for(File file : files) {
			if(file.isDirectory()) {
				deleteFile(file.getAbsolutePath(), folderPatter);
			}
			String filename = file.getName();
			if(filename.matches(folderPatter)) {
				System.out.println("删除："+file.getAbsolutePath());
				Boolean delResult = file.delete();
				System.out.println(delResult?"删除成功":"删除失败");
			}
		}
	}
	
	/**
	 * 删除文件夹
	 * @param dirFile
	 * @return
	 */
	public static boolean deleteDirectory(File dirFile) {
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for(File file : files) {
        	// 删除子文件
        	if(file.isFile()) {
        		flag = file.delete();
        	}else {
        		//删除子目录
        		flag = deleteDirectory(file);
        	}
        	if (!flag) {
   			 	break;
        	}
        }
        // 删除当前目录
        if(flag) {
        	flag = dirFile.delete();
        }
        return flag;
	}
}
