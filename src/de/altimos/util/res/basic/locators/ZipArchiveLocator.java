package de.altimos.util.res.basic.locators;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import de.altimos.util.res.ResourceInfo;
import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.ResourceLocator;
import de.altimos.util.res.basic.BasicResourceManager;

public class ZipArchiveLocator implements ResourceLocator {
	
	private ZipFile archive;
	
	@Override
	public void setPath(String path) {
		try {
			archive = new ZipFile(new File(path));
		} catch(ZipException e) {
			
		} catch(IOException e) {
			
		}
	}
	
	@Override
	public ResourceInfo locateResource(BasicResourceManager mgr, ResourceKey key) {
		if(archive != null) {
			ZipEntry ze = archive.getEntry(key.getName());
			if(ze != null) {
				return new ZipInfo(mgr, key, archive, ze);
			}
		}
		return null;
	}
	
}
