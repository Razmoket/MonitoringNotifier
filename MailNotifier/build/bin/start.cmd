rem set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_40"
set APP_NAME=MailNotifier
set INSTALL_DIR=..
set JAR=%INSTALL_DIR%\lib\*.jar

set mailServer="zzzzzzzz"
set userid="yyyyyy"
set pwd="xxxxxxx"
set refresh="10"

rem if "%JAVA_HOME%" == "" set JAVA_HOME="C:\Program Files\Java\jdk1.7.0_40"

echo %INSTALL_DIR%


setlocal enableDelayedExpansion
set CP=%INSTALL_DIR%\conf\
for /r %INSTALL_DIR%\lib\ %%i in (*.jar) do set CP=!CP!;%%i

set START_CLASS=fr.afnic.notifier.mail.listener.MailListener
rem %JAVA_HOME%\bin\java  -cp %CP% %START_CLASS%
java  -cp %CP% %START_CLASS% %mailServer% %userid% %pwd% %refresh%

cd 
pause