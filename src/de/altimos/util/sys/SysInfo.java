/*
 * Altimos JUtil
 * 
 * Copyright (C) 2010-2011 Jan Graichen <jan.graichen@gmx.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed along with this work for further
 * information.
 */
package de.altimos.util.sys;

import java.util.Locale;

/**
 * This class provides information about the users system.
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class SysInfo {
	
	private static OS os;
	private static Arch arch;
	private static Platform platform;
	
	public static enum OS {
		Windows, Linux, MacOSX, Unknown;
	}
	
	public static enum Arch {
		amd64, x86, ppc32, ppc64, unknown;
	}
	
	public static enum Platform {
		Windows_x86, Windows_amd64,
		Linux_x86, Linux_amd64,
		MacOSX_x86, MacOSX_amd64,
		MacOSX_ppc32, MacOSX_ppc64,
		Unknown;
		
		public OS getOS() {
			switch(this) {
				case Windows_x86:
				case Windows_amd64:
					return OS.Windows;
				case Linux_x86:
				case Linux_amd64:
					return OS.Linux;
				case MacOSX_x86:
				case MacOSX_amd64:
				case MacOSX_ppc32:
				case MacOSX_ppc64:
					return OS.MacOSX;
			}
			return OS.Unknown;
		}
		
		public Arch getArch() {
			switch(this) {
				case Windows_x86:
				case Linux_x86:
				case MacOSX_x86:
					return Arch.x86;
				case Linux_amd64:
				case Windows_amd64:
				case MacOSX_amd64:
					return Arch.amd64;
				case MacOSX_ppc32:
					return Arch.ppc32;
				case MacOSX_ppc64:
					return Arch.ppc64;
			}
			return Arch.unknown;
		}
	}
	
	/**
	 * Returns true if system has a 64-bit architecture.
	 * 
	 * @return if 64 bit arch
	 */
	public static boolean is64Bit() {
		switch(getArch()) {
			case x86:
			case ppc32:
				return false;
			case amd64:
			case ppc64:
				return true;
		}
		throw new UnsupportedOperationException("Unsupported architecture " + System.getProperty("os.arch") + ".");
	}
	
	/**
	 * Returns system architecture.
	 * 
	 * @return system arch
	 */
	public static Arch getArch() {
		if(arch == null) {
			arch = scanArch();
		}
		return arch;
	}
	
	private static Arch scanArch() {
		String arch = getArchName().toLowerCase(Locale.ENGLISH);
		if(arch.equals("x86")) {
			return Arch.x86;
		}else if(arch.equals("amd64")) {
			return Arch.amd64;
		}else if(arch.equals("x86_64")) {
			return Arch.amd64;
		}else if(arch.equals("ppc") || arch.equals("powerpc")) {
			return Arch.ppc32;
		}else if(arch.equals("ppc64")) {
			return Arch.ppc64;
		}else if(arch.equals("i386") || arch.equals("i686")) {
			return Arch.x86;
		}else {
			return Arch.unknown;
		}
	}
	
	/**
	 * Returns system architecture name.
	 * 
	 * @return system arch name
	 */
	public static String getArchName() {
		return System.getProperty("os.arch");
	}
	
	/**
	 * Returns system operating system name.
	 * 
	 * @return system os name
	 */
	public static String getOSName() {
		return System.getProperty("os.name");
	}
	
	/**
	 * Returns system operating system.
	 * 
	 * @return system os
	 */
	public static OS getOS() {
		if(os == null) {
			os = scanOS();
		}
		return os;
	}
	
	private static OS scanOS() {
		String os = getOSName().toLowerCase(Locale.ENGLISH);
		if(os.contains("windows")) {
			return OS.Windows;
		}else if(os.contains("linux") || os.contains("freebsd") || os.contains("sunos")) {
			return OS.Linux;
		}else if(os.contains("mac os x")) {
			return OS.MacOSX;
		}else{
			return OS.Unknown;
		}
	}
	
	/**
	 * Returns system platform.
	 * 
	 * @return system platform
	 */
	public static Platform getPlatform() {
		if(platform == null) {
			platform = scanPlatform();
		}
		return platform;
	}
	
	private static Platform scanPlatform() {
		Arch arch = getArch();
		OS   os   = getOS();
		
		if(os == OS.Windows) {
			switch(arch) {
				case x86:
					return Platform.Windows_x86;
				case amd64:
					return Platform.Windows_amd64;
			}
		}else if(os == OS.Linux) {
			switch(arch) {
				case x86:
					return Platform.Linux_x86;
				case amd64:
					return Platform.Linux_amd64;
			}
		}else if(os == OS.MacOSX) {
			switch(arch) {
				case x86:
					return Platform.MacOSX_x86;
				case amd64:
					return Platform.MacOSX_amd64;
				case ppc32:
					return Platform.MacOSX_ppc32;
				case ppc64:
					return Platform.MacOSX_ppc64;
			}
		}
		return Platform.Unknown;
	}
}
