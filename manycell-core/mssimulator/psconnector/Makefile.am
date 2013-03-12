lib_LTLIBRARIES = libpsconnector.la
#lib_LIBRARIES = libpsconnector.a

libpsconnector_la_SOURCES = \
	sim_jnative_PSConnector.h	\
	SimulatorFactory.h	\
	sim_jnative_PSConnector.cpp
	
library_includedir=$(includedir)/mssimualtor
libpsconnector_la_LIBADD = ../copasiwsclient/libcopasiwsclient.la ../istabulator/libistabulator.la
libpsconnector_la_CPPFLAGS = -I$(top_srcdir)/include \
	-I/usr/lib/java/jdk1.6.0_16/include	\
	-I/home/dada/tools/jdk1.6.0_25/include	\
	-I/home/dada/tools/jdk1.6.0_25/include/linux	\
	-I/usr/lib/java/jdk1.6.0_16/include/linux	\
	-I/home/dada/tools/libsbml-4.0.1/include
 
AM_CPPFLAGS = -DBAZ

#INCLUDE = -I@top_srcdir@/
