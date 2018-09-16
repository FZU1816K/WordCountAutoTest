@echo off
set now=%cd%
call "D:\0_System\Visual_Studio\vs_community\VC\Auxiliary\Build\vcvarsall.bat" x86
::echo on 
d: 
for /f "delims=" %%a in (gen.txt) do (
    cd %dp~0%%%a
    cl *.cpp /EHsc /Fewc.exe
    cd %now%
    endlocal
)
