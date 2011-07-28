package greendrm.editor;

import java.util.ArrayList;

public interface IEditorFile {
	//public void setBaseDir(String dir);
	//public String getBaseDir();
	public void mkDir(String filename);
	public void createFile(String filename);
	public void deleteFile(String filename);
	public void saveFile(String filename, String data);
	public String loadFile(String filename);
    public String parseFileName(String fullPath);
    public String parseFileNameNoExt(String filename);
    public ArrayList<String> retreiveFiles(ArrayList<String> items);
}
