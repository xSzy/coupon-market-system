package com.ptit.cms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MiscService
{

	public String saveFile(MultipartFile file, String description) throws Exception;

	public void setMaxAllowedPacket();

	String saveAvatar(MultipartFile file) throws Exception;
}
