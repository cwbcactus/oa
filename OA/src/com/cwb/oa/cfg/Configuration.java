package com.cwb.oa.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private static int pageSize = 10;

	static {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = Configuration.class.getClassLoader().getResourceAsStream("default.properties");
			props.load(in);
			pageSize = Integer.parseInt(props.getProperty("pageSize"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
			}
		}

	}
	
	public static int getPageSize() {
		return pageSize;
	}
}
