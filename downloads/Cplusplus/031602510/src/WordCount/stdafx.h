#include <iostream>
#include <stdio.h>
#include <direct.h>
#include <stdlib.h>
#include<string.h>
#include<sstream>
#include<fstream>
#include<string>
#include<iterator>
#include<cctype>
#include<ctype.h>
#include<unordered_map>
#include<algorithm>

using namespace std;

class WordCount
{

private:
	FILE * f;
	const char *target_file;
public:
	WordCount(FILE *a, const char *b)
	{
		f = a;
		target_file = b;
	}
	~WordCount() {};
	int WordNum();
	int CharCount();
	int LineCount();
	void LetterCount();
	void result();
};