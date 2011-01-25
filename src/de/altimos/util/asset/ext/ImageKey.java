package de.altimos.util.asset.ext;

import java.awt.Image;

import de.altimos.util.asset.basic.BasicAssetKey;

/**
 * AssetKey fpr image resources.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class ImageKey extends BasicAssetKey<Image> {
	
	public ImageKey(String name) {
		this(name, false);
	}
	
	public ImageKey(String name, boolean flip) {
		super(name, true);
		this.flip = flip;
	}
	
	@Override
	public int hashCode() {
		int hash = getName().hashCode() + getType().hashCode();
		if(getLoader() != null) {
			hash += getLoader().hashCode();
		}
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof ImageKey) {
			return ((ImageKey) o).getName().equals(getName());
		}
		return super.equals(o);
	}
}
