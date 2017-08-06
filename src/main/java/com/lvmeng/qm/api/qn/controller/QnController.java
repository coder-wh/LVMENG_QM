package com.lvmeng.qm.api.qn.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lvmeng.qm.api.qn.service.IQnService;
import com.lvmeng.qm.base.vo.BaseResult;
@RestController
@RequestMapping("/qn")
public class QnController {
	@Resource
	private IQnService qnService;
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public BaseResult updateQn(MultipartFile file) {
		BaseResult result = qnService.dealWithQn(file);
		return result;
	}
}
