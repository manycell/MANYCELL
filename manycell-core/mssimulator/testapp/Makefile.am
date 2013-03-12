bin_PROGRAMS = testsimulator

testsimulator_SOURCES = \
	TestSimulator.h	\
	TestSimulator.cpp	

testsimulator_LDADD = ../copasiwsclient/libcopasiwsclient.la ../istabulator/libistabulator.la -llapack -lblas
testsimulator_CPPFLAGS = -I$(top_srcdir)/include
#../istabulator/ellipsoid/libellipsoid.la