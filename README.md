# WordCountAutoTest
A tool for personal-project testing

˵����
1. �˴����޸��ڱ���������ʦ�ṩ�� autotestscripts.zip ����Ҫ��Ա�����ҵʵ������������Ӧ������Ҫ���޸ģ�����ʹ��ԭ����Ĳ��ֹ���
2. ���ڱ��˴��벻����ֱ�ӽ����εĸ�������ж��Դ�����У�����ʹ��ʱҲ����Ӧ���޸ģ���δ��װ����

������Ҫ��
1. ����ͬѧ��forkԴ�ֿ⣬���մ���ͨ��pr�ύ
2. Դ�ֿ�淶Ŀ¼Ϊ��
   Cplusplus
     |- xxxxxxxxx (�ļ�������Ϊѧ��)
       |- src
          |- WordCount.sln
          |- WordCount
            |- stdafx.cpp
            |- stdafx.h
            |- WordCount.cpp
            |- WordCount.vcxproj
3. �Զ������Զ���ΪC++��д�ĳ��򣬲�����÷֡�����ʱ�������ԭ��

����˼·��
1. ����githubԴ��Ŀ�ֿ⵽ /downloads
2. ����ѧ��Ŀ¼����Դ�����������Ŀ¼����� gen.txt
3. ��������Դ����
4. �������Բ�������

ʹ�ò�����
1. ���� gen.bat �ļ�
   - �޸� call "D:\0_System\Visual_Studio\vs_community\VC\Auxiliary\Build\vcvarsall.bat" x86
     Ϊ ����VS��Ӧ vcvarsall.bat �ļ�λ��
   - �޸� d:
     Ϊ ���ظó��������̷�
2. �޸� Դ����
   - src\AutoTest\wordcount\Main.java��115  
     studentId = vals[2]; //��ȡѧ����ѧ��
     // ����clone�����ص�Ŀ¼�޸�vals[x]���ҵĲֿ�Ŀ¼��downloads\Cplusplus\031502530������x=2
   - src\AutoTest\wordcount\GitRepoHanlder.java��101  
     int status = gitRepoCloner.cloneRepository("https://github.com/FZU1816K/personal-project", "./downloads/", "0"); //������Ŀ��������Ӧ��״̬��status
     // ������Ҫclone�Ĳֿ��ַ�޸�url���ҵ�Դ�ֿ��ַ��"https://github.com/FZU1816K/personal-project"
   - src\AutoTest\wordcount\GitRepoHanlder.java��114
     String mainFolder = "D:/0_System/Eclipse/workplace/WordCountAutoTest/downloads/Cplusplus";
     // �޸�Ϊѧ���ļ�������Ŀ¼
3. ���� �����������������ӳ���޸� ��Ҫ������λ���� src\AutoTest\wordcount\WordCountTester.java ���ڲ�ע�ͺ���ϸ�����Ż��÷��Ʊ��ִ��룩
4. ���� tests Ŀ¼ Ϊ�������루���ڱ�����ҵδ�������ݲ��ṩ����������
5. ���� stds  Ŀ¼ Ϊ��׼�𰸣����ڱ�����ҵδ�������ݲ��ṩ����������
6. ִ�������в��� -m clone : ����ѧ��Ŀ¼����gen.txt ������ѧ��Դ����·��
7. ������������� gen.bat : �������� gen.txt ��·��Ŀ¼�µ�Դ����
8. ִ�������в��� -m test : ��������ÿ��ѧ���Ŀ����г����Զ��Ա����������׼�𰸵Ĳ��죬�ó����֣����ڹ���Ŀ¼������scores.csv
9. ����excel��.csv�ļ����鿴�������н��

End
