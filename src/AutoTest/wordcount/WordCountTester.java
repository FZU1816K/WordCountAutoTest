package AutoTest.wordcount;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WordCountTester {
	private int timeLimit;
	private static String baseDir;
	private static String studentId;
	public static ArrayList<Integer> scores;  //每条命令下该同学的得分情况
	public static ArrayList<Double> times; //每条命令下该同学的用时情况。如果为负，则为用错误类型
	private static String[] argumentScoreMaps = new String[]{ //测试参数
			"tests/input1.txt ",
			"tests/input2.txt",
			"tests/input3.txt",
			"tests/input4.txt",
			"tests/input5.txt",
			"tests/input6.txt",
			"tests/input7.txt",
		};
	private static String[] stdResults = new String[]{ //测试用例的参考答案
			"stds/result1.txt",
			"stds/result2.txt",
			"stds/result3.txt",
			"stds/result4.txt",
			"stds/result5.txt",
			"stds/result6.txt",
			"stds/result7.txt",
	};
	
	public WordCountTester(String studentId, String baseDir, int timeLimit){
		scores = new ArrayList<Integer>();
		times = new ArrayList<Double>();
		WordCountTester.baseDir = baseDir;
		WordCountTester.studentId = studentId;
		this.timeLimit = timeLimit;
	}
	
	public static int executeTest(String argument, String stdTxt, int testId, int timeLimit){ // 返回执行时间 // String arguments,
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象  
        try {
			File f = new File("result.txt");
//			File directory = new File("");//设定为当前文件夹 
//			System.out.println(f.getAbsolutePath());//获取绝对路径 

        	System.out.println("Begin test: " + argument);
			long endTime = 0;
        	long startTime = System.currentTimeMillis();//获取开始时间

			//Process p = run.exec(baseDir + "/wc.exe "+ argument); //cmd // 启动另一个进程来执行命令
        	Process p = run.exec("java -jar " + baseDir + "/wc.jar " + baseDir + "/" + argument); //cmd // 启动另一个进程来执行命令
			if (p.waitFor(timeLimit, TimeUnit.SECONDS)) { //等待子进程结束
				endTime = System.currentTimeMillis(); //获取结束时间
				System.out.println("程序运行时间： " + (1.0*endTime - 1.0*startTime)/1000 + "s");
				//TimeUnit.MINUTES.sleep(1);//分
			}else {
				//timeout - kill the process.
				p.destroy(); // consider using destroyForcibly instead
				System.out.println("TLE") ;
				
				if (f.exists()) {
					System.gc();
					f.delete();
				}
				
				return -1; //"TLE"
			}
			
			//临时调上来的代码，验证java程序是否执行成功
			//检查命令是否执行失败。
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1) {//p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");
				}

				if (f.exists()) {
					System.gc();
					f.delete();
				}
				
				return -6; //"Fail"
			}
			
			//判断是否产生输出文件result.txt
			if (!f.exists()) {
				System.out.println("No result.txt") ;
				return -5; //"No result file"
			}
			
			//复制运行产生的结果文件result.txt到/log下相应学生目录，并编号
			File dst = new File("logs/"+studentId+"/result"+String.valueOf(testId)+".txt");
			Files.copy(f.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
//			//可以省略吧
//			//检查命令是否执行失败。
//			if (p.waitFor() != 0) {
//				if (p.exitValue() == 1) {//p.exitValue()==0表示正常结束，1：非正常结束
//					System.err.println("命令执行失败!");
//				}
//
//				if (f.exists()) {
//					System.gc();
//					f.delete();
//				}
//				
//				return -6; //"Fail"
//			}

			//与正确答案对比，评估得分
			int scorePerTest = checkValid(stdTxt, "result.txt");
			System.out.println(" 得分：" + scorePerTest);
			scores.add(scorePerTest);
			times.add((1.0 * endTime - 1.0 * startTime) / 1000);
			
			//声明免责并删除正在被使用的result.txt，否则下一次测试无法判断是否正确生成了result.txt
			if (f.exists()) {
				System.gc();
				f.delete();
			}
			
			return 0;//endTime-startTime; //TODO：返回得分与实践
		} catch (Exception e) {
			e.printStackTrace();
			return -3; //other error
		}
	}
	
	public ArrayList<String> getScore(){ //String standardPath, String filePath
		ArrayList<String> ans = new ArrayList<>();
		double totScore = 0; //初始化总分
		ans.add("\t"+WordCountTester.studentId); //插入学号

		//File fExe = new File(baseDir+"/wc.exe");
		File fExe = new File(baseDir+"/wc.jar");
//		System.out.println(studentId+" exe filePath: "+fExe.getAbsolutePath());

		//判断是否存在可运行程序.exe
		if (!fExe.exists()){
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			ans.add("Lack exe");
			ans.add("0");
			System.out.println(studentId+ " Lacks exe file!");
			return ans; //"No exe file"
		}

		for (int i = 0, j = 0; i < argumentScoreMaps.length; ++i){ //对于每个参数进行测试 //String argument: argumentScoreMaps
			int runStatus = executeTest(argumentScoreMaps[i], stdResults[i], i+1, this.timeLimit); //5
			if (runStatus == -1) {
				ans.add("0");
				ans.add("TLE");
			}else if (runStatus == -3) {
				ans.add("0");
				ans.add("Other Error");
			}else if (runStatus == -5) {
				ans.add("0");
				ans.add("No result.txt");
			}else if (runStatus == -6) {
				ans.add("0");
				ans.add("Failed");
			}else if (runStatus == 0) {
				ans.add(String.valueOf(scores.get(j)));
				ans.add(String.valueOf(times.get(j)));
				j++;
			}
		}
		
		//映射总分
		for (int i = 0; i < times.size(); ++i) {
			if (i < 5) {
				totScore = totScore + 8*(scores.get(i)/20.0);
			}else if (i == 5) {
				totScore = totScore + (scores.get(i)/2.0);
			}else if (i == 6) {
				totScore = totScore + (scores.get(i)/2.0);
			}
		}
		ans.add(String.valueOf(totScore));
		System.out.println(" 映射总分：" + totScore);
		
		return ans;
	}
	
	//Overview: 将标程standard生成的result文件和用户生成的result文件进行对比，完全一致则
	public static int checkValid(String standardPath, String filePath) throws FileNotFoundException{
		int count = 0; 
		InputStreamReader isrStandard = new InputStreamReader(new FileInputStream(standardPath));
		BufferedReader buffStandard = new BufferedReader(isrStandard);

		InputStreamReader isrTest = new InputStreamReader(new FileInputStream(filePath));
		BufferedReader buffTest = new BufferedReader(isrTest);
		
		String strStandard, strTest;
		try {
			strStandard = buffStandard.readLine();
			strTest = buffTest.readLine();
			if (strTest != null && strStandard.equals(strTest)){ //第一行characters
				count += 1; //得1分
			}
			
			strStandard = buffStandard.readLine();
			strTest = buffTest.readLine();
			if (strTest != null && strStandard.equals(strTest)){ //第二行words
				count += 2; //得2分
			}
			
			strStandard = buffStandard.readLine();
			strTest = buffTest.readLine();
			if (strTest != null && strStandard.equals(strTest)){ //第三行lines
				count += 2; //得2分
			}
			
			boolean checkMain = true; //检查词频部分是否全部正确，每对一行加1分，全对加15分
			int cntTmp = 0;
			while ((strStandard = buffStandard.readLine()) != null){
				strTest = buffTest.readLine();
				if (strTest == null || !strStandard.equals(strTest)){
					checkMain = false;
					continue;
//					break;
				}
				count += 1;
				cntTmp += 1;
			}
			if (checkMain){ //核心部分占15分
				count += 15;
				count -= cntTmp;
			}
			
			return count;
		} catch (IOException e) {
			e.printStackTrace();
			return -1; //其它错误
		}
	}
	
//	public static void main(String[] args){
//		WordCountTester wordCountTester = new WordCountTester("/Users/Mr.ZY/GitHub/WordCountAutoTest/", "14011100");
//		wordCountTester.executeTest("ls -lt", 4);
//	}
}
