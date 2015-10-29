package com.dunbian.jujiabao.service.file;

import com.dunbian.jujiabao.appobj.extend.FileAO;
import com.dunbian.jujiabao.framework.obj.Result;


public interface IFileService {
	

	Result<FileAO> beforeSaveFile(FileAO file);
	
	Result<FileAO> afterSaveFile(FileAO file);
	
	Result<FileAO> getFile(String id);
	
	/**
	 * 删除文件数据
	 * @param fileId 文件数据id
	 * @return
	 */
	Result<Boolean> removeFile(String fileId);
	
	/**
	 * 返回配置的上传文件路径
	 * @return
	 */
	String getUploadDir();
	
	int getTotalPartition();
	
	String getFileNameEncoding();
}
