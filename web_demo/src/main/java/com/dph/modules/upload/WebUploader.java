package com.dph.modules.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dph.common.web.BaseController;

@Controller
@RequestMapping("webUploader")
public class WebUploader extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(WebUploader.class);
	private final static String UPLOAD_PATH = "/tmp/web_demo/upload/";
	

	@RequestMapping("upload")
	public String upload() {
		String destPage = "/modules/webUploader/upload";
		return destPage;
	}

	@RequestMapping("uploadRich")
	public String uploadRich() {
		String destPage = "/modules/webUploader/uploadRich";
		return destPage;
	}
	
	/*
	 * 采用spring提供的上传文件的方法
	 */
	@RequestMapping("fileUpload")
	@ResponseBody
	public String fileUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		long startTime = System.currentTimeMillis();
		StringBuilder builder = new StringBuilder();
		try {
			// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				File dir = new File(UPLOAD_PATH);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// 将request变成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 获取multiRequest 中所有的文件名
				Iterator<?> iter = multiRequest.getFileNames();

				while (iter.hasNext()) {
					// 一次遍历所有文件
					MultipartFile file = multiRequest.getFile(iter.next().toString());
					if (file != null) {
						String path = UPLOAD_PATH + file.getOriginalFilename();
						// 上传
						file.transferTo(new File(path));
						builder.append(path);
						builder.append(",");
					}
				}
			}
		} catch (Exception e) {
			logger.error("fail to upload file.", e);
			throw e;
		}
		long endTime = System.currentTimeMillis();
		logger.info("文件上传总耗时：" + String.valueOf(endTime - startTime) + "ms");
		
		return builder.substring(0, builder.length() - 1);
	}

}
