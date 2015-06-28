package com.pig8.api.platform.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author navy
 *
 */
public class FtpUtils {
	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param host
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String host, int port, String username,
			String password, String path, String filename, InputStream input) {
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				ftp.changeWorkingDirectory(path);
				ftp.storeFile(filename, input);
				input.close();
				return true;
			}
		} catch (Exception e) {
			logger.error("uploadFile", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (Exception ioe) {
				}
			}
		}
		return false;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param host
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String host, int port, String username,
			String password, String remotePath, String fileName,
			String localPath) {
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host, port);
			ftp.login(username, password);// 登录
			int reply = ftp.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
				FTPFile[] fs = ftp.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().equals(fileName)) {
						File localFile = new File(localPath + "/"
								+ ff.getName());
						OutputStream is = new FileOutputStream(localFile);
						ftp.retrieveFile(ff.getName(), is);
						is.close();
					}
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("downFile", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (Exception ioe) {
				}
			}
		}
		return false;
	}

	/**
	 * 重命名远程文件
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param fileName
	 * @return
	 */
	public static boolean renameRemoteFile(String host, int port,
			String username, String password, String remotePath,
			String fileName, String renameName) {
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host, port);
			ftp.login(username, password);// 登录
			int reply = ftp.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
				return ftp.rename(fileName, renameName);
			}
			return false;
		} catch (Exception e) {
			logger.error("renameRemoteFile", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (Exception ioe) {
				}
			}
		}
		return false;
	}

	/**
	 * 删除远程文件
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param fileName
	 * @return
	 */
	public static boolean delRemoteFile(String host, int port, String username,
			String password, String remotePath, String fileName) {
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host, port);
			ftp.login(username, password);// 登录
			int reply = ftp.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
				ftp.deleteFile(fileName);
			}
			return true;
		} catch (Exception e) {
			logger.error("renameRemoteFile", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (Exception ioe) {
				}
			}
		}
		return false;
	}

	/**
	 * 查询远程文件夹下的所以文件
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param extName
	 *            扩展名
	 * @return
	 */
	public static List<String> queryRemoteFiles(String host, int port,
			String username, String password, String remotePath, String extName) {
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(host, port);
			ftp.login(username, password);// 登录
			int reply = ftp.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
				FTPFile[] fs = ftp.listFiles();
				if (null != fs && fs.length > 0) {
					List<String> fileList = new ArrayList<String>();
					for (FTPFile ftpFile : fs) {
						if (ftpFile.getName().endsWith(extName)) {
							fileList.add(ftpFile.getName());
						}
					}
					return fileList;
				}
			}
		} catch (Exception e) {
			logger.error("queryRemoteFiles", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (Exception ioe) {
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			InputStream input = new ByteArrayInputStream(
					"test ftp".getBytes("utf-8"));
			boolean flag = uploadFile("120.197.93.99", 36002, "root",
					"58L41DKE51A6", "ftp", "test.txt", input);
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
