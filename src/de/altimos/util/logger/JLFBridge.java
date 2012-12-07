/*
 * A Java Utility Library
 * 
 * Copyright (C) 2010-2012 Jan Graichen <jg@altimos.de>
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
import java.util.regex.Pattern;

import org.apache.log4j.Level;

/**
 * A simple JLF (Java Logging Framework) to log4j bridge.
 * Just call {@link JLFBridge#installBridge()}.
 * 
 * This bridge supports parameterized {@linkplain LogRecord}s, LogRecords
 * with associated throwables.
 * 
 * This bridge does not support I18N via {@linkplain java.util.ResourceBundle}s.
 * 
 * @author Jan Graichen <jg@altimos.de>
 * @version $Id$
 */
public class JLFBridge extends Handler {
	
	static private final Pattern paramPat = Pattern.compile("\\{\\d");
	
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
		if(message == null) {
			message = "";
		}
		if(record.getParameters() != null) {
			// java.util.logging.Formatter.formatMessage(LogRecord) does this
			// optimization and delegates the whole formatting thing to
			// java.text.MessageFormat
			if(paramPat.matcher(message).find()) {
				message = java.text.MessageFormat.format(message, record.getParameters());
			}
		}
		String loggerName = record.getLoggerName();
		org.apache.log4j.Logger logger = null;
		// if there is no logger name given we choose the root logger
		if(loggerName == null) {
			logger = org.apache.log4j.Logger.getRootLogger();
		} else {
			logger = org.apache.log4j.Logger.getLogger(loggerName);
		}
		logger.log(getLevel(record.getLevel()), message, record.getThrown());
	}
	
	@Override
	public void flush() {}
	
	@Override
	public void close() throws SecurityException {}

	/**
	 * Assign log4j filter level to given JLF filter level.
	 * 
	 * @param level JLF level
	 * @return associated log4j level
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
