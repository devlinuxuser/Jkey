package io.github.devlinuxuser;
import io.github.devlinuxuser.utils.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JKey{
    private String tempDirPath = System.getProperty("java.io.tmpdir") + "/io_github_devlinuxuser/JKey/";
    private String library = "lib/JKey-" + OSValidator.getArch() + (OSValidator.isWindows() ? ".dll" : ".so");
    public native char readKey();
    public JKey() {
        File tmpDir = new File(tempDirPath);
        if(!tmpDir.exists()){
            tmpDir.mkdirs();        
        }
        if(!isFileExist(tempDirPath + library)){
            try {
                JarExtractor.extractFromJar(library,tempDirPath);
            } catch (IOException ex) {
                Logger.getLogger(JKey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.load(tempDirPath + library);
    }

    private static boolean isFileExist(String path){
        File f = new File(path);
        if (f.exists()) {
            return true;
        }else{
            return false;
        }
    }
}
