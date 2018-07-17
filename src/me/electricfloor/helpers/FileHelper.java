package me.electricfloor.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import me.electricfloor.main.ElectricFloor;

public class FileHelper {
	
	public static void extractFile(String path, String name) {
		try {
			File target = new File(path + File.separator + name);
	        if (target.exists())
	            return;

	        File pathh = new File(path);
	        if (!pathh.exists()) {
	        	pathh.mkdirs();
	        }
	        
	        FileOutputStream out = new FileOutputStream(target);
	        ClassLoader cl = ElectricFloor.class.getClassLoader();
	        InputStream in = cl.getResourceAsStream(name);

	        byte[] buf = new byte[2*1024];
	        int len;
	        while((len = in.read(buf)) != -1)
	        {
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	        
		} catch (Exception e) {
			ElectricFloor.getELogger().logException(e);
		}
	}

}
