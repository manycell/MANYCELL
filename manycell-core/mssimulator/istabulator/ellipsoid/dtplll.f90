subroutine dtplll( n, a, b, c )

!  Evaluate C = A * B where A, B and C are square lower triangular
!  matrices of size n x n, which are stored in packed format.

implicit none
integer, intent(in) :: n
real(kind(1.d0)), dimension( (n*(n+1))/2 ), intent(in)  :: a, b
real(kind(1.d0)), dimension( (n*(n+1))/2 ), intent(out) :: c

integer :: i, j, k, l, kd, kb, kb0
real(kind(1.d0)), dimension( (n*(n+1))/2 ) :: d

!  Method:  C(i,j) = sum_{p=j}^{p=i} A(i,p) * B(p,j),  for j <=i
!                  = sum_{p=j}^{p=i} D(p.i) * B(p,j),  for j <=i, D = A^T.

!  Store D = A^T in packed format, then evaluate.

k = 0
do j = 1, n
   do i = 1, j
      k = k + 1  
	  l = j + ((2*n-i)*(i-1))/2
	  d(k) = a(l)
   end do
end do

k = 0
do j = 1, n
   	  kb0 = ((2*n-j)*(j-1))/2
   do i = j, n
      k = k + 1
	  kd = j + (i*(i-1))/2       !  index of D(j,i)
	  kb = j + kb0               !  index of B(j,j)
	  l  = i-j
	  c(k) = sum( d(kd:kd+l) * b(kb:kb+l) )
   end do
end do

return
end subroutine dtplll