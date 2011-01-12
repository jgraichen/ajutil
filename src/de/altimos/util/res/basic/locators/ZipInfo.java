package de.altimos.util.res.basic.locators;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.altimos.util.res.ResourceKey;
import de.altimos.util.res.basic.AbstractResourceInfo;
import de.altimos.util.res.basic.BasicResourceManager;

public class ZipInfo extends AbstractResourceInfo {
	
	private ZipFile archive;
	private ZipEntry ze;
	
	
	public ZipInfo(BasicResourceManager mgr, ResourceKey key, ZipFile archive, ZipEntry ze) {
		super(mgr, key);
		this.archive = archive;
		this.ze = ze;
	}
	
	@Override
	public InputStream openStream() throws IOException {
		return archive.getInputStream(ze);
	}
	
}
