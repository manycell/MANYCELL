dnl --------------------------------
dnl Initialization macros.
dnl --------------------------------

AC_INIT(mssimulator/copasiwsclient/CopasiWSSimulator.cpp)
AM_CONFIG_HEADER(config.h)

dnl -----------------------------------------------
dnl Package name and version number (user defined)
dnl -----------------------------------------------

AM_INIT_AUTOMAKE(mssimulator-1.0, 1.0.1)

AC_CONFIG_SRCDIR(Makefile.in)


AC_DEFINE([RUN_LDPATH], 1, [Define to 1 to enable library])

dnl ---------------------------------------------------------------------------
dnl Check host operating system.
dnl ---------------------------------------------------------------------------

AC_CANONICAL_HOST

case $host_os in
  *linux*)
    CPPFLAGS="-DLINUX $CPPFLAGS"
    HOST_TYPE="linux"
    SHAREDLIBEXT="so"
    ;;

  *solaris*)
    CPPFLAGS="-DSOLARIS $CPPFLAGS"
    HOST_TYPE="solaris"
    SHAREDLIBEXT="so"
    ;;

  *cygwin*)
    CPPFLAGS="-DWIN32 -DCYGWIN $CPPFLAGS"
    HOST_TYPE="cygwin"
    SHAREDLIBEXT="dll"
    ;;

  *darwin*)
    CPPFLAGS="-DMACOSX $CPPFLAGS"
    HOST_TYPE="darwin"
    SHAREDLIBEXT="dylib"
    ;;

  *aix*)
    CPPFLAGS="-DAIX $CPPFLAGS"
    HOST_TYPE="aix"
    SHAREDLIBEXT="a"
    ;;

  *freebsd*)
    CPPFLAGS="-DFREEBSD $CPPFLAGS"
    HOST_TYPE="freebsd"
    SHAREDLIBEXT="so"
    ;;

esac

AC_SUBST(HOST_TYPE)
AC_SUBST(SHAREDLIBEXT)

dnl Provide dynamic library path settings.

CONFIG_LDPATH

dnl -----------------------------------------------
dnl Checks for programs.
dnl -----------------------------------------------

AC_PROG_CC
AC_PROG_CXX
AC_PROG_FC
AC_PROG_F77
AM_PROG_LIBTOOL
AM_SANITY_CHECK

AC_SUBST(LIBTOOL)
DOLT

dnl -----------------------------------------------
dnl Add libraries location.
dnl -----------------------------------------------


dnl -----------------------------------------------
dnl Checks for libraries.
dnl -----------------------------------------------

# Paths to mssimulatorlib stuff:
AC_LANG_CPLUSPLUS

AC_PROG_RANLIB
#PKG_CHECK_MODULES(DEPS, mssimulatorlib-1.0 >= 0.3.0)
#PKG_CHECK_MODULES(DEPS, mssimulatorlib-1.0)
AC_SUBST(DEPS_CFLAGS)
AC_SUBST(DEPS_LIBS)

dnl ------------
dnl Output variables

echo "Configured on host \"`hostname`\""
echo "  host type                     = $HOST_TYPE"
echo "  host vendor                   = $host_vendor"
echo "  host operating system         = $host_os"
echo "  host CPU architecture         = $host_cpu"
echo ""
echo "General build flags:"
echo "  CC                            = $CC"
echo "  CXX                           = $CXX"
echo "  CFLAGS                        = $CFLAGS"
echo "  CXXFLAGS                      = $CXXFLAGS"
echo "  CPPFLAGS                      = $CPPFLAGS $ZLIB_CPPFLAGS $BZ2_CPPFLAGS"
echo "  LDFLAGS                       = $LDFLAGS $ZLIB_LDFLAGS $BZ2_LDFLAGS"
echo "  LIBS                          = $LIBS $ZLIB_LIBS $BZ2_LIBS"


dnl -----------------------------------------------
dnl Generates Makefiles.
dnl -----------------------------------------------
#AC_CONFIG_FILES([Makefile])
#AC_CONFIG_FILES([mssimulator/Makefile])
#AC_CONFIG_FILES([mssimulator/copasiwsclient/Makefile])
AC_OUTPUT(Makefile mssimulator/Makefile	\
	mssimulator/copasiwsclient/Makefile	\
	mssimulator/istabulator/Makefile	\
	mssimulator/psconnector/Makefile	\
	mssimulator/testapp/Makefile	\
	mssimulator/istabulator/ellipsoid/Makefile)
