package persistence.manager;

import java.io.FileInputStream;
import java.util.Properties;

public class CfgManager implements ICfgManager {
	
	public static final String OTHER_CFG_FILE="OTHER_CFG_FILE";
	public static final String EXTERNAL_CFG_FILE="EXTERNAL_CFG_FILE";
	
	private static CfgManager INSTANCE = null;
	private Properties properties = null;
	
	private CfgManager() throws Exception {
		properties = new Properties();
		String path = System.getProperty(OTHER_CFG_FILE);
		if (path != null) {
				properties.load(this.getClass().getClassLoader().getResourceAsStream(System.getProperty(OTHER_CFG_FILE)));		
		} else {
			path = System.getProperty(EXTERNAL_CFG_FILE);
			if (path != null) {
				FileInputStream is = new FileInputStream(path);
				properties.load(is);
				
			} else {
				properties.load(this.getClass().getClassLoader().getResourceAsStream("files/cfg.ini"));
			}
		}
	}

	public static CfgManager getInstance() throws Exception {
		if(INSTANCE == null) {
			INSTANCE = new CfgManager();
		}
		return INSTANCE;
	}

	@Override
	public Properties getCfg() {
		// TODO Auto-generated method stub
		return properties;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(CfgManager.getInstance().getCfg());
	}
}
