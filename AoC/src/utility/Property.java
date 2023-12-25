package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {

	public static String getFilePathHome() throws IOException {
		Properties p = new Properties();
		FileInputStream ip = new FileInputStream("config.properties");
		p.load(ip);
		return p.getProperty("folderpathHome");
	}
}
