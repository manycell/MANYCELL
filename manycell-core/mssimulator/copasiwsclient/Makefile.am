lib_LTLIBRARIES = libcopasiwsclient.la
#lib_LIBRARIES = libcopasiwsclient.a

libcopasiwsclient_la_SOURCES = \
	CopasiWSSimulator.h	\
	SimulatorFactory.h	\
	CopasiWSSimulator.cpp	\
	SimulatorFactory.cpp	\
	copasiws/soapC.cpp	\
	copasiws/soapClient.cpp	\
	copasiws/stdsoap2.cpp		
	
#include_HEADERS =	\
#	Factory.h	\
#	SimulatorFactory.h	\
#	CopasiWSSimulator.h	\
#	Simulator.h

libcopasiwsclient_la_CPPFLAGS = -I$(top_srcdir)/include
library_includedir=$(includedir)/mssimualtor
#library_include_HEADERS = Simulator.h Factory.h SimulatorFactory.h
AM_CPPFLAGS = -DBAZ 
#-lsbml

#INCLUDE = -I@top_srcdir@/
