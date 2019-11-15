package com.ptit.cms.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.cms.service.MiscService;
import com.ptit.cms.util.Constant;

@Service
public class MiscServiceImpl implements MiscService
{

	@Override
	public String saveFile(MultipartFile file, String description) throws Exception
	{
		Resource resource = new ClassPathResource("\\public\\static\\cmsdata");
		String path = resource.getFile().getAbsolutePath() + File.separator + description + File.separator;
		String name = file.getOriginalFilename();
		if(name == null || name.length() <= 0)
			throw new Exception(Constant.EXCEPTION_FILE_CORRUPT);
		File folder = new File(path);
		if(!folder.exists())
			folder.mkdirs();
		try
		{
			File serverFile = File.createTempFile(description, file.getOriginalFilename(), folder);
			name = serverFile.getName();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serverFile));
			bos.write(file.getBytes());
			bos.close();
			path = serverFile.getAbsolutePath();
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return description + "/" + name;
	}
	
}
