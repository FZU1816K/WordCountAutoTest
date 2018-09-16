#include "stdafx.h" 

unordered_map<string, int> strMap;


bool mysort(pair < int, string > a, pair < int, string > b)//定义sort规则
{
	if (a.first != b.first)
		return a.first > b.first;
	else
		return a.second < b.second;
}


void GetMap(stringstream &ss)//生成string中单词的键值对
{
	string strTmp;
	while (ss >> strTmp)
	{
		unordered_map<string, int>::iterator it = strMap.find(strTmp);
		if (it == strMap.end())
		{
			strMap.insert(unordered_map<string, int>::value_type(strTmp, 1));
		}
		else
			strMap[strTmp]++;
	}
}


void WordCount::LetterCount() //字符统计函数
{
	string strFile, tmp;
	int i = 0;
	ifstream file(target_file);      //读取当前文件夹下input.txt文件
	while (getline(file, tmp))//直到文件结尾，依次逐行读入文本
	{
		strFile.append(tmp); //每次读入一行附加到strFile结尾
		strFile.append(" ");//行尾补充空格
		tmp.clear();            //记得清除，否则上一次读入比这次文本长，不会完全覆盖而出错
	}
	for (unsigned int i = 1; i <= strFile.size(); i++)
	{

		if (strFile[i] >= 'A'&&strFile[i] <= 'Z')
			strFile[i] += 32;
	}
	for (unsigned int i = 0; i < strFile.length(); i++)
	{
		if (ispunct(strFile[i]))
			strFile[i] = ' '; //符号位替换成为空格
	}
	stringstream ss(strFile);
	if (strMap.empty()) GetMap(ss);
	
	vector < pair < int, string > > m;
	for (unordered_map<string, int>::iterator it = strMap.begin(); it != strMap.end(); ++it)
		m.push_back(make_pair(it->second, it->first));
	sort(m.begin(), m.end(), mysort);
	for (unsigned int k = 0; k < m.size(); ++k)
	{
		string a = m[k].second.c_str();
		if (
			((a[0] >= 'a'&&a[0] <= 'z') || (a[0] >= 'A'&&a[0] <= 'Z')) &&
			((a[1] >= 'a'&&a[1] <= 'z') || (a[1] >= 'A'&&a[1] <= 'Z')) &&
			((a[2] >= 'a'&&a[2] <= 'z') || (a[2] >= 'A'&&a[2] <= 'Z')) &&
			((a[3] >= 'a'&&a[3] <= 'z') || (a[3] >= 'A'&&a[3] <= 'Z')))
		{
			cout << '<' << a << '>' << ":" << m[k].first << endl;//打印结果
			i++;
			if (i >= 10)
				break;//输出频率前十
		}
	}
}

int WordCount::WordNum()
{
	string strFile, tmp;
	int i = 0;
	ifstream file(target_file);      //读取当前文件夹下input.txt文件
	while (getline(file, tmp))//直到文件结尾，依次逐行读入文本
	{
		strFile.append(tmp); //每次读入一行附加到strFile结尾
		strFile.append(" ");//行尾补充空格
		tmp.clear();            //记得清除，否则上一次读入比这次文本长，不会完全覆盖而出错
	}
	for (unsigned int i = 1; i <= strFile.size(); i++)
	{

		if (strFile[i] >= 'A'&&strFile[i] <= 'Z')
			strFile[i] += 32;
	}
	for (unsigned int i = 0; i < strFile.length(); i++)
	{
		if (ispunct(strFile[i]))
			strFile[i] = ' '; //符号位替换成为空格
	}
	stringstream ss(strFile);

	if (strMap.empty()) 
		GetMap(ss);
	vector < pair < int, string > > v;
	for (unordered_map<string, int>::iterator it = strMap.begin(); it != strMap.end(); ++it)
		v.push_back(make_pair(it->second, it->first));
	sort(v.begin(), v.end(), mysort);

	for (unsigned int k = 0; k < v.size(); ++k)
	{
		string a = v[k].second.c_str();
		if (
			((a[0] >= 'a'&&a[0] <= 'z') || (a[0] >= 'A'&&a[0] <= 'Z')) &&
			((a[1] >= 'a'&&a[1] <= 'z') || (a[1] >= 'A'&&a[1] <= 'Z')) &&
			((a[2] >= 'a'&&a[2] <= 'z') || (a[2] >= 'A'&&a[2] <= 'Z')) &&
			((a[3] >= 'a'&&a[3] <= 'z') || (a[3] >= 'A'&&a[3] <= 'Z')))
		{
			i += v[k].first;
		}
	}

	return i;

}

int WordCount::CharCount()
{
	FILE *fp;
	errno_t err = fopen_s(&fp, target_file, "r");
	if (err != NULL)
	{
		printf("file read failure.");//文件读取失败提示
	}
	int c = 0;
	char ch;
	ch = fgetc(fp);
	while (ch != EOF)
	{
		c++;
		ch = fgetc(fp);
	}
	fclose(fp);
	return c;
}

int WordCount::LineCount()
{
	FILE *fp;
	errno_t err = fopen_s(&fp, target_file, "r");
	if (err != NULL)
	{
		printf("file read failure.");//文件读取失败提示
	}
	int l = 0, e = 0;
	char ch;
	ch = fgetc(fp);
	while (ch != EOF)
	{
		if (l == 0 && ch != ' '&&ch != '/n')
			l++;
		else if (ch == '\n')
		{
			l++;
			ch = fgetc(fp);
			if (ch == EOF)
				l--;
			while (ch == ' ')
			{
				ch = fgetc(fp);
				if (ch == EOF)
					l--;
			}
			while (ch == '\n')
			{
				e++;
				l++;
				ch = fgetc(fp);
			}
		}
		else
		{
			ch = fgetc(fp);
		}
	}

	fclose(fp);
	return l - e;
}

void WordCount::result() 
{
	string strFile, tmp;
	int i = 0;
	ifstream file(target_file);      //读取当前文件夹下input.txt文件
	while (getline(file, tmp))//直到文件结尾，依次逐行读入文本
	{
		strFile.append(tmp); //每次读入一行附加到strFile结尾
		strFile.append(" ");//行尾补充空格
		tmp.clear();            //记得清除，否则上一次读入比这次文本长，不会完全覆盖而出错
	}
	for (unsigned int i = 1; i <= strFile.size(); i++)
	{

		if (strFile[i] >= 'A'&&strFile[i] <= 'Z')
			strFile[i] += 32;
	}
	for (unsigned int i = 0; i < strFile.length(); i++)
	{
		if (ispunct(strFile[i]))
			strFile[i] = ' '; //符号位替换成为空格
	}
	stringstream ss(strFile);

	if (strMap.empty())
		GetMap(ss);
	vector < pair < int, string > > z;
	for (unordered_map<string, int>::iterator it = strMap.begin(); it != strMap.end(); ++it)
		z.push_back(make_pair(it->second, it->first));
	sort(z.begin(), z.end(), mysort);

	FILE *fp;
	fopen_s(&fp, target_file, "r");
	WordCount A(fp, target_file);
	ofstream outfile("result.txt", ios::out);
	if (!outfile) {
		cerr << "open error!" << endl;
		exit(1);
	}
	int t = 0;
	outfile << "characters:" <<' '<< A.CharCount() << endl;
	outfile << "words:" << ' '<<A.WordNum() << endl;
	outfile << "lines:" <<' '<< A.LineCount() << endl;
	for (unsigned int k = 0; k < z.size(); ++k)
	{
		string a = z[k].second.c_str();
		if (
			((a[0] >= 'a'&&a[0] <= 'z') || (a[0] >= 'A'&&a[0] <= 'Z')) &&
			((a[1] >= 'a'&&a[1] <= 'z') || (a[1] >= 'A'&&a[1] <= 'Z')) &&
			((a[2] >= 'a'&&a[2] <= 'z') || (a[2] >= 'A'&&a[2] <= 'Z')) &&
			((a[3] >= 'a'&&a[3] <= 'z') || (a[3] >= 'A'&&a[3] <= 'Z')))
		{
			outfile<< '<' << a << '>' << ":" <<' '<< z[k].first << endl;//打印结果
			i++;
			if (i >= 10)
				break;//输出频率前十
		}
	}
		outfile.close();
}