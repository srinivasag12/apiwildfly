package com.bsol.iri.fileSharing.util;

/**
 * 
 * @author rupesh
 *	
 * This is used to delete all the files and folder present in specified directory
 */


import java.io.File;

public class DeleteDirectories {

	// Recusive method to delete a folder
	public void recursiveDelete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }
}
