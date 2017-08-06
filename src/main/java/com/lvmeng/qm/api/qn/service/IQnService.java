package com.lvmeng.qm.api.qn.service;

import org.springframework.web.multipart.MultipartFile;

import com.lvmeng.qm.base.vo.BaseResult;

public interface IQnService {
	public BaseResult dealWithQn(MultipartFile file);
}
