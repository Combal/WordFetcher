package ge.combal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vano on 4/20/15.
 */
public class Util {
	private static final String appPropertiesFile = "application.properties";
	private static final Properties properties = new Properties();

	static {
		try {
			InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(appPropertiesFile);
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getConfig(String key) {
		return (properties != null && properties.get(key) != null) ? properties.get(key).toString() : null;
	}
}
