package com.dunbian.jujiabao.service.file;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.FileAO;
import com.dunbian.jujiabao.db.generator.FileMapper;
import com.dunbian.jujiabao.framework.obj.Result;


public class FileService implements IFileService {
	
	@Resource
	private FileMapper fileMapper;

	private String uploadDir;
	
	private int totalPartition = 1024;
	
	private String fileNameEncoding = "utf-8";
	
	public int getTotalPartition() {
		return totalPartition;
	}

	public void setTotalPartition(int totalPartition) {
		this.totalPartition = totalPartition;
	}

	public Result<FileAO> beforeSaveFile(FileAO file) {
		fileMapper.insert(file);
		return new Result<FileAO>(file);
	}

	public Result<FileAO> afterSaveFile(FileAO file) {
		fileMapper.updateByPrimaryKeySelective(file);
		return new Result<FileAO>(file);
	}

	public Result<FileAO> getFile(String id) {
		FileAO ret = fileMapper.selectByPrimaryKey(id);
		return new Result<FileAO>(ret);
	}

	
	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> removeFile(String fileId) {
		if(fileId == null || fileId.isEmpty()) {
			return new Result<Boolean>(false, "参数fileId不能为空！");
		}
		fileMapper.deleteByPrimaryKey(fileId);
		return new Result<Boolean>(true);
	}

	public String getFileNameEncoding() {
		return fileNameEncoding;
	}

	public void setFileNameEncoding(String fileNameEncoding) {
		this.fileNameEncoding = fileNameEncoding;
	}

}
