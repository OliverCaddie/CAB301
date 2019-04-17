@echo off

for %%f in (".\*") do (
if "%%~nf" NEQ "clean" if "%%~nxf" NEQ "report.pdf" if "%%~nxf" NEQ "report.Rnw" (
del "%%~nxf"
))
