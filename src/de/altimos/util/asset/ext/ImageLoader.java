package de.altimos.util.asset.ext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import de.altimos.util.asset.AssetInfo;
import de.altimos.util.asset.AssetLoader;
import de.altimos.util.asset.AssetManager;

/**
 * Image loader using ImageIO.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class ImageLoader implements AssetLoader {

	private static String[] formats;

	/**
	 * Registers this ImageLoader for all supported formats.
	 * 
	 * @param mgr
	 * 			AssetManager where this loader should be registered.
	 */
	static public void registerImageLoader(AssetManager mgr) {
		if (formats == null) {
			final List<String> fs = new ArrayList<String>();
			for (String format : ImageIO.getReaderFormatNames()) {
				format = "." + format.toLowerCase(Locale.ENGLISH);
				if (!fs.contains(format)) {
					fs.add(format);
				}
			}
			formats = fs.toArray(new String[fs.size()]);
		}
		mgr.registerLoader(ImageLoader.class, formats);
	}

	@Override
	public Object loadAsset(AssetManager mgr, AssetInfo info) throws IOException {
		return ImageIO.read(info.openStream());
	}
}
