package epbit.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;

public class FileSystemUtil {
	public static String getInternalStoragePath(Context context) {
		String path = "";
		path = Environment.getExternalStorageDirectory().getAbsolutePath();
		return path;

	}

	public static File makeDirectory(Context context, String name, String path) {

		File dir = new File(path + "/" + name);
		dir.mkdir();
		

		return dir;

	}

	public static File getDirectoryFromPath(String path) {
		File dir = new File(path);

		return dir;

	}

	public static boolean isDirectoryExists(String path) {
		boolean flag = false;
		File dir = new File(path);
		flag = dir.exists();

		return flag;

	}

	public static boolean isDirectoryExists(File directory) {

		return directory.exists();

	}

	public static void DeleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				DeleteRecursive(child);

		fileOrDirectory.delete();
	}

	public static List<String> getFilesNamesFromDirectory(String path) {
		List<String> names = new ArrayList<String>();

		File file = new File(path);

		for (File f : file.listFiles()) {
			if (f.isFile())
				names.add(f.getName());

		}
		return names;

	}
}
