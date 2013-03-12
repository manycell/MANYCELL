lib_LTLIBRARIES = libistabulator.la

libistabulator_la_SOURCES = \
	BSTree.cpp	\
	ISTabulator.cpp	
#SimulatorFactory.cpp
	
library_includedir=$(includedir)/mssimualtor

libistabulator_la_CPPFLAGS = -I$(top_srcdir)/include

libistabulator_la_LIBADD = ../copasiwsclient/libcopasiwsclient.la ../istabulator/ellipsoid/libellipsoid.la

AM_CPPFLAGS = -DBAZ

#INCLUDE = -I@top_srcdir@/

SUBDIRS = ellipsoid