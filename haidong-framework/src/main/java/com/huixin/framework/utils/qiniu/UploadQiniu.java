package com.huixin.framework.utils.qiniu;

import com.qiniu.util.Auth;

/***
 * 七牛服务器初始化配置
 * 
 * @author Administrator
 *
 */
public class UploadQiniu {
	// 设置好账号的ACCESS_KEY和SECRET_KEY
//	String ACCESS_KEY = "fNnwo_Pswm4Sk9vYZY-CQBVZ5SQYr1nFSzNOh-FN";
//	String SECRET_KEY = "pBTqKoqXHPB1TQkVEU3_QUKxlINQfCC7-6bVBNZI";
	
	//新七牛云key
	String ACCESS_KEY = "LptE79BXONRA41QbCaotvRQogvQgumCrWSCylij-";
	String SECRET_KEY = "zx8A6xALSc1sHRZxjVTTHPxxrbPdcNrkuOcwbXSJ";
	// 要上传的空间
	public final static String bucketname = "hidongtv";
	// 密钥配置
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	private static final UploadQiniu qiniu = new UploadQiniu();

	private UploadQiniu() {

	}

	public static UploadQiniu getInstance() {
		return qiniu;
	}

	public String getUpToken() {
		return auth.uploadToken(bucketname);
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

}
