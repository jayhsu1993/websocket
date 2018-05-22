package com.huixin.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 上传文件 创建人：FH 创建时间：2014年12月23日
 * 
 * @version
 */
public class FileUpload {

	public static String POINT = ".";

	/**
	 * @param file
	 *            //文件对象
	 * @param filePath
	 *            //上传路径
	 * @param fileName
	 *            //文件名
	 * @return 文件名
	 * @throws IOException
	 */
	public static List<PageData> fileUp(PageData data, MultipartHttpServletRequest multiRequest, String filePath)
			throws IOException {
		String extName = ""; // 扩展名格式：
		List<PageData> result = new ArrayList<PageData>();
		Iterator<String> iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			PageData pd = new PageData();
			// 取得上传文件
			MultipartFile file = multiRequest.getFile(iter.next());
			if (file != null) {
				String path = filePath + "\\" + DateUtil.getMonth();
				String fileId = FileMD5.getMD5(file);
				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
					extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
					pd.put("id", fileId);
					pd.put("proId", data.get("proId"));
					pd.put("type", data.get("type"));
					pd.put("descriptive", data.get("descriptive"));
					pd.put("extName", extName.toLowerCase());
					pd.put("fileName", file.getOriginalFilename());
					pd.put("attPath", path);
					result.add(pd);
				}
				copyFile(file.getInputStream(), path, fileId + POINT + extName).replaceAll("-", "");
			}
		}
		return result;
	}

	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String dir, String realName) throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}

	/**
	 * @param file
	 *            //文件对象
	 * @param filePath
	 *            //上传路径
	 * @param fileName
	 *            //文件名
	 * @return 文件名
	 * @throws IOException
	 */
	public static List<PageData> fileUp(MultipartHttpServletRequest multiRequest) throws IOException {
		String extName = ""; // 扩展名格式：
		List<PageData> result = new ArrayList<PageData>();
		Iterator<String> iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			PageData pd = new PageData();
			// 取得上传文件
			MultipartFile file = multiRequest.getFile(iter.next());
			if (file != null) {
				String fileId = FileMD5.getMD5(file);
			}
		}
		return result;
	}

	/**
	 * @param file
	 *            //文件对象
	 * @param filePath
	 *            //上传路径
	 * @param fileName
	 *            //文件名
	 * @return 文件名
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName) {
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName + extName;
	}
}
