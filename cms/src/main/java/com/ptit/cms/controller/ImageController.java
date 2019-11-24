package com.ptit.cms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController
{
	@RequestMapping(value = "/resources/{category}/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public @ResponseBody byte[] getImage(@PathVariable String category, @PathVariable String fileName) throws FileNotFoundException, IOException
	{
		String path = "\\public\\static\\cmsdata\\";
		path = path + category + File.separator + fileName;
		Resource resource = null; 
		InputStream is = null;
		try
		{
			resource = new ClassPathResource(path);
			is = new FileInputStream(resource.getFile());
			
		} catch (IOException e)
		{
			path = "\\public\\static\\image_not_found.jpg";
			resource = new ClassPathResource(path);
			is = new FileInputStream(resource.getFile());
		}
		byte[] bytes = IOUtils.toByteArray(is);
		is.close();
		return bytes;
	}

	@RequestMapping(value = "/avatar/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public @ResponseBody byte[] getAvatar(@PathVariable String fileName) throws FileNotFoundException, IOException
	{
		String path = "\\public\\static\\avatar\\";
		path = path +  fileName;
		Resource resource = null;
		InputStream is = null;
		try
		{
			resource = new ClassPathResource(path);
			is = new FileInputStream(resource.getFile());

		} catch (IOException e)
		{
			path = "\\public\\static\\no-profile-picture-icon-12.jpg";
			resource = new ClassPathResource(path);
			is = new FileInputStream(resource.getFile());
		}
		byte[] bytes = IOUtils.toByteArray(is);
		is.close();
		return bytes;
	}
}
