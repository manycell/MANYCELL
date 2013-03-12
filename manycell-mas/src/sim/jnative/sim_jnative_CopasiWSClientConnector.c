/* Begin CVS Header
 *  $Source$
 *  $Revision$
 *  $Name$
 *  $Author$
 *  $Date$
 * End CVS Header
 *
 *<!---------------------------------------------------------------------------
 * This file is part of multi-scale simulation software.
 *
 * Copyright 2010-2012 The University of Manchester.  
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation.  A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 *----------------------------------------------------------------------- -->
 */
#include "sim_jnative_CopasiWSClientConnector.h"
#include <SimulatorFactory.h>
#include <CopasiWSSimulator.h>
 JNIEXPORT jint JNICALL Java_sim_jnative_CopasiWSClientConnector_initFloat2DArray
  (JNIEnv *, jclass, jstring, jfloatArray, jfloat, jint, jint, jobjectArray){
	 jobjectArray result;
	       int i;
	       jclass intArrCls = (*env)->FindClass(env, "(Ljava/lang/String;[FFII[[F)I");
	       if (intArrCls == NULL) {
	           return NULL; /* exception thrown */
	       }
	       result = (*env)->NewObjectArray(env, size, intArrCls, NULL);

	       Factory *fact = new SimulatorFactory();
	       CopasiWSSimulator *sim = new CopasiWSSimulator();
	    //   jclass intArrCls =

	       return result;
 }

 //convert jstring to character string
 char *JNU_GetStringNativeChars(JNIEnv *env, jstring jstr)
  {
      jbyteArray bytes = 0;
      jthrowable exc;
      char *result = 0;
      if ((*env)->EnsureLocalCapacity(env, 2) < 0) {
          return 0; /* out of memory error */
      }
      bytes = (*env)->CallObjectMethod(env, jstr,
                                       MID_String_getBytes);
      exc = (*env)->ExceptionOccurred(env);
      if (!exc) {
          jint len = (*env)->GetArrayLength(env, bytes);
          result = (char *)malloc(len + 1);
          if (result == 0) {
              JNU_ThrowByName(env, "java/lang/OutOfMemoryError",
                              0);
              (*env)->DeleteLocalRef(env, bytes);
              return 0;
          }
          (*env)->GetByteArrayRegion(env, bytes, 0, len,
                                     (jbyte *)result);
          result[len] = 0; /* NULL-terminate */
      } else {
          (*env)->DeleteLocalRef(env, exc);
      }
      (*env)->DeleteLocalRef(env, bytes);
      return result;
  }

