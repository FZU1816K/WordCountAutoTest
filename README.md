# WordCountAutoTest
A tool for personal-project testing

### 说明：
1. 此代码修改于北航助教老师提供的 autotestscripts.zip ，主要针对本次作业实际需求，做出适应测试需要的修改，仅了使用原程序的部分功能
2. 由于本人代码不精，直接将本次的个人需求配置在源程序中，他人使用时也需适应性修改，故未封装程序

### 测试需要：
1. 所有同学均fork源仓库，最终代码通过pr提交
2. 源仓库规范目录为：\
　　Cplusplus\
　　　|- xxxxxxxxx (文件夹名字为学号)\
　　　　|- src\
　　　　　|- WordCount.sln\
　　　　　|- WordCount\
　　　　　　|- stdafx.cpp\
　　　　　　|- stdafx.h\
　　　　　　|- WordCount.cpp\
　　　　　　|- WordCount.vcxproj\ 
3. 自动化测试对象为C++编写的程序，并输出得分、运行时间与错误原因

### 程序思路：
1. 下载github源项目仓库到 /downloads
2. 搜索学生目录，将源代码程序所在目录输出到 gen.txt
3. 批量编译源程序
4. 批量测试并输出结果

### 使用操作：
1. 配置 gen.bat 文件
   - 修改 call "D:\0_System\Visual_Studio\vs_community\VC\Auxiliary\Build\vcvarsall.bat" x86
     为 本地VS相应 vcvarsall.bat 文件位置
   - 修改 d:
     为 本地该程序所在盘符
2. 修改 源程序
   - src\AutoTest\wordcount\Main.java：115  
     studentId = vals[2]; //获取学生的学号
     // 根据clone到本地的目录修改vals[x]，我的仓库目录是downloads\Cplusplus\031502530，所以x=2
   - src\AutoTest\wordcount\GitRepoHanlder.java：101  
     int status = gitRepoCloner.cloneRepository("https://github.com/FZU1816K/personal-project", "./downloads/", "0"); //下载项目，返回相应的状态到status
     // 根据需要clone的仓库地址修改url，我的源仓库地址是"https://github.com/FZU1816K/personal-project"
   - src\AutoTest\wordcount\GitRepoHanlder.java：114
     String mainFolder = "D:/0_System/Eclipse/workplace/WordCountAutoTest/downloads/Cplusplus";
     // 修改为学生文件夹所在目录
3. 如有 测试用例变更、分数映射修改 必要，代码位置在 src\AutoTest\wordcount\WordCountTester.java （内部注释很详细，已优化得分制表部分代码）
4. 配置 tests 目录 为测试输入（由于本次作业未结束，暂不提供个人用例）
5. 配置 stds  目录 为标准答案（由于本次作业未结束，暂不提供个人用例）
6. 执行命令行参数 -m clone : 搜索学生目录，在gen.txt 中生成学生源程序路径
7. 运行批处理程序 gen.bat : 批量编译 gen.txt 中路径目录下的源程序
8. 执行命令行参数 -m test : 批量运行每个学生的可运行程序，自动对比输出结果与标准答案的差异，得出评分，并在工程目录下生成scores.csv
9. 可用excel打开.csv文件，查看测试运行结果

### 制表输出的错误提示
1. TLE : 运行超时，默认为5s
2. No result.txt : 没有输出 result.txt 结果文件
3. Lack exe : 编译失败，没有生成.exe
4. Failed : 命令行运行失败
5. Other error : 其他错误

#### End
