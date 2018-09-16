package AutoTest.wordcount;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitRepoHanlder {
	public Map<String, String> repoMapTable = new HashMap<String, String>();
	public boolean hasFound = false;
	public String baseDir = "";

	public boolean hasFoundExe = false;
	public void writeGen(String mainFolder, BufferedWriter out) {		
		String exeLocation = "null";
		File f = new File(mainFolder);
		File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
		for (File file : files) {
			if (hasFoundExe){
				//如果找到了cpp文件，即这是需要编译生成exe文件的位置，将路径存入gen.txt
				hasFoundExe = false;
				int startOp = exeLocation.lastIndexOf("downloads");
				int endOp = exeLocation.lastIndexOf("\\");
				if (startOp == -1 || endOp == -1) {
					System.out.print("剪切路径失败，需手动修改："+exeLocation+"\n");
					return ;
				}
				String genAddLine = exeLocation.substring(startOp, endOp); //从downloads开始获取exe所在文件夹名称
				System.out.print(genAddLine+"\n");
				try {
					out.write(genAddLine + "\r\n"); //将路径输出到gen.txt
				} catch (IOException e) {
					e.printStackTrace();
				}
				return ;
			}
			if(file.isDirectory()) {
				//如果当前路径是文件夹，则循环读取这个文件夹下的所有文件
				writeGen(file.getAbsolutePath(), out);
			} else {
				String fileName = file.getName();//获取当前读取文件的名称
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); //获取文件的后缀名
				if (suffix != null && suffix.equals("cpp")){ //如果该文件是cpp						
					String absoultePath = mainFolder.replaceAll("\\\\","/"); //这个一般是用不着的，防止在Windows里遇到的路径格式
					int pos = absoultePath.lastIndexOf('/');
					String folderName = absoultePath.substring(pos+1).toLowerCase(); //找当前路径的最后的文件夹的名字，匹配是不是wordcount
					if (folderName.equals("wordcount")){
						hasFoundExe = true;
						exeLocation = file.getAbsolutePath();
//						System.out.print(exeLocation+"\n");
					}
				}
			}			
		}			
	}
	
	public void handle(String filename){
		if (filename == null || filename.equals(""))
			filename = "GithubRepos.txt";
		loadGithubMap(filename);
		GitRepoCloner gitRepoCloner = new GitRepoCloner();
		gitRepoCloner.createFolder("downloads");
		
		int status = gitRepoCloner.cloneRepository("https://github.com/FZU1816K/personal-project", "./downloads/", "0"); //下载项目，返回相应的状态到status
		if (status == 0) { //项目不存在
			System.out.println("项目不存在!");
		} else if (status == 3) {  //无法下载
			System.out.println("网络异常!");
		} else if (status == 2) { //如果是2，代表已经保存过了
			System.out.println("已经下载");
		} else {
			System.out.println("下载完成！");
		}
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("gen.txt"));
			String mainFolder = "D:/0_System/Eclipse/workplace/WordCountAutoTest/downloads/Cplusplus";
			writeGen(mainFolder, out);
//			if (!hasFoundExe){
//				System.out.println("!!!Exception:"+"No cpp file under wordcount folder Found!!!");
//				out.write(" !!!Exception:"+"No cpp file under wordcount folder Found!!!");
//			}
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadGithubMap(String filePath){
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader("GithubRepos.txt"));
			String str;
			String[] vals;
			int cnt = 0;
			while ((str = bufferedReader.readLine()) != null) {//按行读取GithubRepos.txt
				vals = str.split(" ");//以空格分割字符串
				if (vals.length < 2){
					System.out.println("非法的github仓库对应格式！");
					System.exit(0);
				}
				cnt++;
				repoMapTable.put(vals[0], vals[1]);
			 }
			bufferedReader.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
