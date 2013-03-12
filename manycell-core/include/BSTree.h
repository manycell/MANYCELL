/*
 * BSTree.h
 *
 *  Created on: 4 Aug 2010
 *      Author: dada
 */

#ifndef BSTREE_H_
#define BSTREE_H_

struct tree_node
{
	tree_node* left;
	tree_node* right;
	int key;

	//cutting plane data
	double* v;
	double* xH;
	bool isANode;

	//simulation data records
	float tol;
	double* sInitialValue;
	float stepSize;
	int nTimeSteps;
	int nSpecies;
	double* cholSMatrixL;
	float** timeCourseData;
	int size;
};

class BSTree
{
private:
        tree_node* root;

    public:
       BSTree();
       ~BSTree();
        bool isEmpty() const { return root==NULL; }
        void print_inorder();
        void inorder(tree_node*);
        void print_preorder();
        void preorder(tree_node*);
        void print_postorder();
        void postorder(tree_node*);
        void insert(int, float, float**, int, int);
        void insert(tree_node *leaf);
        tree_node *search(int key);
        //searching for node that is in an ellipsoid
        tree_node *search(double* sIV);
        void remove(int);
    private:
        tree_node *search(int key, tree_node *leaf);

        //local searching for node for approximating simulation results
        tree_node *search(double *sIV, tree_node leaf);

};

#endif /* BSTREE_H_ */
