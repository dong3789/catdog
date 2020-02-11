package common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		long currentTime = System.currentTimeMillis();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		int ranNum = (int)(Math.random() * 100000);
		
		String name = originFile.getName(); // getName을 가져옴
		String ext = null;
		int dot = name.lastIndexOf(".");
		if(dot != 1) { // 확장자가 있으면.subString사용해서 끝까지 점을 넣음. 
			ext = name.substring(dot);
		} else { // 점이 없으면.  
			ext = "";  //없으면 빈칸으로 둠
		}
		
		String fileName = sdf.format(new Date(currentTime)) + ranNum + ext; //db에 넣는게 아니므로 utill 
		File newFile = new File(originFile.getParent(), fileName);
		// 내가 갖고온 파일의 부모 파일
		return newFile;
	}
	
	
}
