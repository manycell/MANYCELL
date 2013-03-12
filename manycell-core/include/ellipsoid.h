/* Begin CVS Header
 *  $Source: /cvs/mssim_dev/include/ellipsoid.h,v $
 *  $Revision: 1.4 $
 *  $Name:  $
 *  $Author: jdada $
 *  $Date: 2010/08/23 15:57:09 $
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
 *  This header provides interface to the subroutines developed in Fotran 90 by
 *  S.B. Pope  6/4/2006
 *  The subroutines are used for the calculation and testing of Ellipsoid
 */

#ifndef ELLIPSOID_H_
#define ELLIPSOID_H_

/*subroutine */
void ell_full2eig_(int* n, double* a1, double* u1, double* lam);

/*subroutine */
void ell_eig2chol_(int* n, double*, double*, double*);

/*subroutine */
void ell_full2low_(int* n, double*, double*);

/*subroutine */
void ell_low2chol_(int* n, double*, double*);

/*subroutine */
void ell_pair_cover_cv_(int* n, double* c1, double* gg1, double* c2, double *gg2, double* c, double*gg);

/*subroutine */
void ell_pair_cover_cv_ql_(int*n, double*c1, double*gg1, double*c2,double*gg2, double*c, double*gg);

/*subroutine */
void ell_pair_cover_it_(int* n, double* c1, double* gg1, double* c2, double* gg2, double* cc, double* gg);

/*subroutine */
void ell_pair_cover_query_(int* n, double* c1, double* gg1, double* c2, double* gg2, double* rmax );

/*subroutine
 * Given a pair of ellipsoids, E1 and E2, determine a third ellipsoid
 * E which covers E1 and E2.
 * E is defined by:  (x-cc)^T * G * G^T * (x-cc) <= 1, where the array gg
 * contains G in packed format.  Similarly for E1 and E2.
 *
 * Algorithms to compute E are:
 * algorithm = 1 - spheroid (no shrinking)
 * algorithm = 2 - covariance (QL implementation)
 * algorithm = 3 - iterative
 * algorithm = 4 - spheroid (shrinking)
 * algorithm = 5 - covariance (Cholesky implementation)
 */
void ell_pair_cover_(int* algorithm, int* n, double* c1, double* gg1, double* c2, double* gg2, double* cc, double* gg);

/*subroutine*/
void ell_pair_separate_(int* n, double* c1, double* gg1, double* c2, double* gg2, double* qual, int* max_it, double* xh, double* v, bool* intersect);

/*subroutine
 *  Given the ellipse E = { x | (x-c)^T * G * G^T * (x-c) <= 1 },
 *  where G is lower triangular, and the point p which is covered by E, we define:
 * 1/ Ev: the maximum volume ellipse covered by E with p on its boundary
 * 2/ En: the ellipse of maximal near content covered by E with p on its
 * boundary
 * 3/ Ec: the minimum volume ellipse covering Ev and En.
 * For k=|k_ell|=1,2 or 3,  G is modified to correspond to Ev, En or Ec, respectively.
 *  G (in packed format) is contained in gg.
 * info = 0 - p is in the interior of E (intended case): G is returned corresponding to Ev, En or Ec.
 * info = 1 - p is on the boundary of E (within tol): G is returned unchanged
 * info = 2 - p is not covered by E: G is returned unchanged
 */
void ell_pt_shrink_( int *n, double *c, double *gg, double *p, int *k_ell, int *info );

/*subroutine */
void ell_pair_shrink_(int*n, double* gg1, double* gg2, int* n_shrink);


/* subroutine
 * Given the ellipsoid E (centered at c) and the point p, return in s
 * the relative distance from c to the boundary of E along the ray c-p
 * (relative to the distance |p-c|).
 * Let b denote the intersection of the boundary of E and the ray c-p.
 * Then s is:     s = |b-c| / |p-c|.
 *
 * E is given by { x | norm(G^T * (x-c) ) <=1 ),  where G is an
 * n x n lower triangular matrix.  The array gg contains
 * the matrix G in packed format.
 * Errors: s = -1 is returned if p = c
 * 		   s = -2 is returned if G is not positive definite
 */
void ell_pt_dist_(int* n, double* c, double* gg, double* p, double* s);

/*subroutine
 * Given an ellipsoid E (centered at c) and a point p, determine the
 * hyperplane H which is the perpendicular bisector of the line c-p
 * in the transformed space in which E is the unit ball.
 * E is defined by:  (x-c)^T * G * G^T * (x-c) <= 1.  The array
 * gg contains the lower triangular n x n matrix G in packed format.
 * The hyperplane H is defined by v^T * ( x - xh  ) = 0, where v is
 * a unit vector.  The quantity s(x) = v^T * ( x - xh  ) is the signed
 * distance of the point x from the hyperplane: s(c) is negative, and
 * s(p) is positive.
 */
void ell_pt_hyper_(int* n, double*, double*, double*, double* xh, double* v);


/*subroutine:
 * Return  in=.true. if the point p is in the ellipsoid E.
 * E is given by { x | norm(G^T * (x-c) ) <=1 ),  where G is an
 * n x n lower triangular matrix.  The array gg contains
 * the matrix G in packed format.
 * Note: select below method to be used.
 */
void ell_pt_in_(int* n, double* c, double* gg, double* p, bool *in);

//#define B(i,j) b[j-1][i-1]
//#define C(i,j) C[j-1][i-1]

/* Subroutine
 * Given an n x n matrix B, compute the lower
 * Cholesky triangle  G:   G * G^T = B * B^T
 * The Cholesky triangle G is returned in g in packed format.
 * Method: B = L * Q;  so that B * B^T = L * L^T; and so G = L.
 */
void ell_bbt2chol_(int *n, double *B, double *g);

/* Subroutine
 * g contains (in packed format) the lower Cholesky factor G of the
 * n x n PSD matrix A = G * G^T.  The eigen-decomposition of A is:
 * A = u * lam^2 * u^T.  This routine returns u and lam (which are U
 * and S in the SVD of G = U S V^T).
 * void ell_chol2eig_(int *n, double *B, double *g);
 */
void ell_chol2eig_(int *n, double *B, double *g, double *lam);

/* Subrutine
 * The n x n PSD matrix A is given by A = B * B^T.
 * The SVD of B is:  B = U * S * V^T, so that the
 * eigendecomposition of A is: A = U * S^2 * U^T.
 *  Given B, return U and lam=diag(S).
 */

void ell_bbt2eig_( int*n, double *g,  double *U, double *lam);

/* subroutine
 * gg contains (in packed format) the lower triangular matrix G
 * which defines the ellipsoid E = {x | |G^T * x | <=1 }.
 * This routine returns in  r_in  and  r_out  the rdii of the
 * inscribed and circumscribed balls.  These are the inverses of the
 * larges and smallest singular vaues of G.
 */
void ell_radii_(int *n, double *gg, double* r_in, double*r_out);

/*  Determine the radius r of the ball inscribed in the ellipsoid E given
 *  by { x | norm(G^T * x) <=1 ), where G is an n x n lower triangular
 *  matrix.  The array gg contains the matrix G in packed format.
 *  Method:  r = 1 / sqrt(lam_max)  where lam_max is the largest
 *  eigenvalue of G * G^T
 */
void ell_rad_lower_(int *n, double *gg, double* r);

/*  Determine the radius r of the ball covering the ellipsoid E given
 *  by { x | norm(G^T * x) <=1 ), where G is an n x n lower triangular
 *  matrix.  The array g contains the matrix G (unpacked).
 */
void ell_rad_upper_(int *n, double *gg, double* r);

void ell_pt_near_far_(int *knf, int *n, double *c, double *gg, double *p, double *xnf);
#endif /* ELLIPSOID_H_ */
