package com.ptit.cms.service;

import org.springframework.web.multipart.MultipartFile;

public interface MiscService
{

	public String saveFile(MultipartFile file, String description) throws Exception;

}
