package com.huixin.framework.utils.qiniu;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.huixin.framework.utils.Logger;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import sun.misc.BASE64Decoder;

/**
 * 七牛云服务器上传文件
 * 
 * @author Administrator
 *
 */
public class FileUploadQiNiu {
	// 创建上传对象
	static UploadManager uploadManager = new UploadManager();
	private static Logger logger = Logger.getLogger(FileUploadQiNiu.class);
	// 简单上传，使用默认策略，只需要设置上传的空间名就可以了

	/**
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param filePath
	 *            上传之后文件的位置
	 * @param fileName
	 *            上传之后文件的名称
	 * @return 返回服务器存储该文件地址
	 * @throws QiniuException
	 */
	public static String updateFile(MultipartFile file, String filePath, String fileName) {
		String token = UploadQiniu.getInstance().getUpToken();
		byte[] files = null;
		try {
			files = file.getBytes();
		} catch (IOException e1) {
			logger.info("文件转换异常");
		}
		try {
			uploadManager.put(files, filePath + "/" + fileName, token);
		} catch (QiniuException e) {
			Response r = e.response;
			logger.info("上传七牛服务器错误：" + r.toString());
		}
		return filePath + "/" + fileName;

	}

	/**
	 * 
	 * @param file
	 *            需要上传App的文件
	 * @param filePath
	 *            上传之后文件的位置
	 * @param fileName
	 *            上传之后文件的名称
	 * @return 返回服务器存储该文件地址
	 * @throws QiniuException
	 */
	public static String updateAppFile(String file, String filePath, String fileName) {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = null;
		try {
			data = decoder.decodeBuffer(replaceImg(file));
		} catch (IOException e1) {
			logger.info("文件转换异常");
		} // 获取文件流
		String token = UploadQiniu.getInstance().getUpToken();
		try {
			 uploadManager.put(data, filePath + "/" + fileName, token);
		} catch (QiniuException e) {
			Response r = e.response;
			logger.info("上传七牛服务器错误：" + r.toString());
		}
		return filePath + "/" + fileName;
	}

	/**
	 * 
	 * @param image
	 *            压缩图片流
	 * @param filePath
	 *            上传之后文件的位置
	 * @param fileName
	 *            上传之后文件的名称
	 * @return 返回服务器存储该文件地址
	 * @throws IOException 
	 */
	public static String uploadCompressFile(BufferedImage image, String filePath) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			ImageIO.write(image, "jpg", baos);
			data = baos.toByteArray();
		} catch (IOException e1) {
			baos.close();
			logger.info("文件转换异常");
		} // 获取文件流
		String token = UploadQiniu.getInstance().getUpToken();
		try {
			uploadManager.put(data, filePath, token);
		} catch (QiniuException e) {
			baos.close();
			Response r = e.response;
			logger.info("上传七牛服务器错误：" + r.toString());
		}
		baos.close();
		return filePath;
	}

	/**
	 * 
	 * @param bucketname
	 *            // 七牛服务器空间名称
	 * @param fileName
	 *            //文件名称全称
	 * @return
	 */
	public static void deleteFile(String bucketname, String fileName) {
		BucketManager bucketManager = new BucketManager(UploadQiniu.getInstance().getAuth());
		try {
			bucketManager.delete(bucketname, fileName);
		} catch (QiniuException e) {
			Response r = e.response;
			logger.info("删除七牛服务器文件错误：" + r.toString() + "文件名称：" + fileName);
		}

	}

	/**
	 * APP 文件上传转换
	 * 
	 * @param img
	 * @return
	 */
	private static String replaceImg(String img) {
		return img.replace("data:image/jpeg;base64,", "").replace("data:image/png;base64,", "")
				.replace("data:image/jpg;base64,", "");
	}
}
