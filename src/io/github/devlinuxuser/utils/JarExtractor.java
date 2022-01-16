/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.devlinuxuser.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Enumeration;
import java.util.jar.JarEntry;

/**
 *
 * @author DIPU
 */
public class JarExtractor {
//    public static void main(String[] args) {
//        try {
//            extractFromJar("lib/ConsoleKey.dll", "C:/temp/io.github.devlinuxuser/JKey/");
//        } catch (IOException ex) {
//            Logger.getLogger(JarExtractor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    
    public static String getJarFilePath(Class c){
        try {
            return new File(c.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(JarExtractor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void extractFromJar(String location , String destDir) throws IOException{
        try (JarFile jar = new JarFile(getJarFilePath(JarExtractor.class))) {
            Enumeration enumEntries;
            enumEntries = jar.entries();
            while (enumEntries.hasMoreElements()) {
                JarEntry file = (JarEntry) enumEntries.nextElement();
//                System.out.println(file.getName());
                if((file.getName().equals(location.split("/")[0]+"/") || file.getName().equalsIgnoreCase(location))){
                    File f = new java.io.File(destDir + File.separator + file.getName());
                    if (file.isDirectory()) { // if its a directory, create it
                        f.mkdir();
                        continue;
                    }
                    try (java.io.InputStream is = jar.getInputStream(file) // get the input stream
                    ; java.io.FileOutputStream fos = new java.io.FileOutputStream(f)) {
                        while (is.available() > 0) {  // write contents of 'is' to 'fos'
                            fos.write(is.read());
                        }
                    }
                }
            }
        }
            
    }
}
   