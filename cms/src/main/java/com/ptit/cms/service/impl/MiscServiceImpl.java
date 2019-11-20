package com.ptit.cms.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.ptit.cms.util.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.cms.service.MiscService;
import com.ptit.cms.util.Constant;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class MiscServiceImpl implements MiscService
{
	@Autowired
	private EntityManager entityManager;

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

	@Override
	public void setMaxAllowedPacket() {
		String sql = "SET GLOBAL max_allowed_packet = 1024*1024*14";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}

	@Override
	public String saveAvatar(MultipartFile file) throws Exception {
		Resource resource = new ClassPathResource("\\public\\static\\avatar");
		String path = resource.getFile().getAbsolutePath() + File.separator;
		String name = file.getOriginalFilename();
		if(name == null || name.length() <= 0)
			throw new Exception(Constant.EXCEPTION_FILE_CORRUPT);
		File folder = new File(path);
		if(!folder.exists())
			folder.mkdirs();
		try
		{
			File serverFile = File.createTempFile(CmsUtils.convertDateToString(new Date(), Constant.DATE_FORMAT_NO_SEPERATOR), file.getOriginalFilename(), folder);
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
		return "avatar/" + name;
	}

}
