@echo off
rem VCM UI benchmark script
rem Expects the following plugins to be installed:
rem   org.eclipse.core.tests.harness
rem   org.eclipse.team.core
rem   org.eclipse.team.cvs.core
rem   org.eclipse.team.cvs.ui
rem   org.eclipse.team.tests.cvs.core
rem   org.eclipse.team.ui
rem   org.eclipse.vcm.core
rem   org.eclipse.vcm.core.cvs
rem   org.eclipse.vcm.tests.core
rem   org.eclipse.vcm.tests.ui
rem   org.eclipse.vcm.ui
rem   org.eclipse.vcm.ui.cvs
rem   org.junit

set ROOT=D:\PerformanceTesting

set ECLIPSE=%ROOT%\eclipse
set REPOSITORY_PROPERTIES=%ROOT%\repository.properties
set TEST=vcmui.benchmark.all
set LOG=%ROOT%\%TEST%.xml
set REPEAT=21
set IGNOREFIRST=

set PLUGINS=%ECLIPSE%\plugins
set WORKSPACE=%ECLIPSE%\workspace
set JRE=%ROOT%\jre
set JAVA=%JRE%\bin\java.exe
set HARNESS=org.eclipse.team.tests.cvs.core.harness

set VMARGS=-Declipse.tests.vcm.properties=%REPOSITORY_PROPERTIES%
set PROGARGS=-dev bin -application %HARNESS% -test %TEST% -log %LOG% -purge -repeat %REPEAT% %IGNOREFIRST%

pushd %ECLIPSE%
echo Purging the workspace: %WORKSPACE%
del /S /F /Q %WORKSPACE% >NUL:
@echo on
@echo Running VCM UI benchmark test
%JAVA% -cp startup.jar %VMARGS% org.eclipse.core.launcher.UIMain %PROGARGS%
@echo off
popd
