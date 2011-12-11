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
package de.altimos.util.logger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.log4j.Level;

/**
 * A simple JLF (Java Logging Framework) to log4j bridge.
 * Just call JLFBridge.installBridge();
 * 
 * @author Jan Graichen <jan.graichen@gmx.de>
 * @version $Id$
 */
public class JLFBridge extends Handler {
	
	/**
	 * Install the JLFBridge.
	 * 
	 * This removes all JLF {@link Handler}s, set the global JLF filter level
	 * to {@link java.util.logging.Level.ALL} and registers this {@link JLFBridge}
	 * as the only JLF handler.
	 */
	static public void installBridge() {
		Logger globalLogger = Logger.getLogger("");
		// remove all handlers
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) {
			globalLogger.removeHandler(handler);
		}
		// add this one
		Logger.getLogger("").addHandler(new JLFBridge());
		// inline test
		Logger.getLogger(JLFBridge.class.getName()).info("JLF to log4j bridge installed.");
		// don't filter anything out, this is the task for log4j
		Logger.getLogger("").setLevel(java.util.logging.Level.ALL);
	}
	
	@Override
	public void publish(LogRecord record) {
		String message = record.getMessage();
		if(record.getParameters() != null) {
			for(int i = 0; i < record.getParameters().length; i++) {
				Object param = record.getParameters()[i];
				message = message.replaceAll("\\{" + i + "\\}", param == null ? "null" : param.toString());
			}
		}
		org.apache.log4j.Logger.getLogger(record.getLoggerName()).log(getLevel(record.getLevel()), message);
	}
	
	@Override
	public void flush() {}
	
	@Override
	public void close() throws SecurityException {}

	/**
	 * Assign log4j filter level to given JLF filter level.
	 * 
	 * @param level
	 * @return
	 */
	private Level getLevel(java.util.logging.Level level) {
		if(level.intValue() >= java.util.logging.Level.OFF.intValue()) {
			return Level.OFF;
		}
		if(level.intValue() >= java.util.logging.Level.SEVERE.intValue()) {
			return Level.FATAL;
		}
		if(level.intValue() >= java.util.logging.Level.WARNING.intValue()) {
			return Level.WARN;
		}
		if(level.intValue() >= java.util.logging.Level.INFO.intValue()) {
			return Level.INFO;
		}
		if(level.intValue() >= java.util.logging.Level.CONFIG.intValue()) {
			return Level.DEBUG;
		}
		// FINE and finer
		return Level.TRACE;
	}
}
