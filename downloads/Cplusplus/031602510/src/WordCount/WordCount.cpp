#include "stdafx.h"


using namespace std;

int main(int argc, char *argv[])
{

	const char* filename;
	if(argv[1]==NULL)
	{
		cerr << "input format error!" << endl;
		exit(1);
	}
	if (argc==2)
		filename = argv[1];
	else
	{
		cerr << "input format error!" << endl;
		exit(1);
	}
	FILE *fp;
	errno_t err=fopen_s(&fp, filename, "r");
	if (err != 0)
	{
		cerr << "open error!" << endl;
		exit(1);
	}
	WordCount A(fp,filename);
	
	A.result();
	fclose(fp);

	return 0;
}