package com.variantcapptool.common.components;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class DocsComponent
{
	private static final String	DOCS_FOLDER_NAME	= "c:/variant_capp_tool_docs/";
	private static final String	FILE_EXTENSION		= ".docx";
	private static Set<String>	allDocs				= getDocs();

	private static Set<String> getDocs()
	{
		allDocs = new HashSet<>();
		File directory = new File(DOCS_FOLDER_NAME);
		File[] fList = directory.listFiles();
		if (fList != null)
		{
			for (File file : fList)
			{
				String[] fileparts = file.getName().split("\\.");
				if (!allDocs.add(fileparts[0]))
				{
					String errorMessage = "Sorry. You have duplicate file in " + DOCS_FOLDER_NAME + "folder";
					System.out.println(errorMessage);
					System.exit(0);
				}
			}
		}
		return allDocs;
	}

	public boolean isDocExist(String partName)
	{
		return allDocs.contains(partName);
	}

	public String getDocsFolderName()
	{
		return DOCS_FOLDER_NAME;
	}

	public String getFileNameExtension()
	{
		return FILE_EXTENSION;
	}

	public String getFileName(String partName)
	{
		if (isDocExist(partName))
		{
			return getDocsFolderName() + partName + getFileNameExtension();
		}
		return null;
	}
}
