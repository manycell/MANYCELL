
//Binary Search Tree

#include <iostream>
#include <cstdlib>

#include "BSTree.h"

extern "C" {
	// Get declaration for all ellipsoid fortran functions
#include "ellipsoid.h"
}

using namespace std;


BSTree::BSTree()
{
	root=NULL;
}

BSTree::~BSTree(){}

void BSTree::insert(tree_node *leaf){
	tree_node* t = new tree_node;
	tree_node* parent;
	t->key = leaf->key;
	t->tol = leaf->tol;

	//copy data
	t->timeCourseResult = leaf->timeCourseResult;

	t->sInitialValue = new double[leaf->nSpecies];

	int nL =(leaf->nSpecies*(leaf->nSpecies+1))/2;
	t->cholSMatrixL = new double [nL];

	//copy the data
	t->nSpecies = leaf->nSpecies;
	t->nTimeSteps = leaf->nTimeSteps;

	//copy choleskey data
	for(int i=0; i<nL; i++){
		t->cholSMatrixL[i] = leaf->cholSMatrixL[i];	 //	;
	}

	for(int i=0; i<leaf->nSpecies; i++)	t->sInitialValue[i] = leaf->sInitialValue[i];
	t->left = NULL;
	t->right = NULL;
	parent = NULL;
	t->isANode = false;

	// is this a new tree?
	if(isEmpty()) root = t;
	else
	{
		//Note: ALL insertions are as leaf nodes
		tree_node* curr;
		curr = root;

		double qual = 0.003;
		int max_it = 3;
		bool intersect = false;
		//calculate the cutting plane
		double sdist;
		while(curr && curr->isANode){
			sdist = 0.0;
			parent = curr;
			for(int i=0; i<t->nSpecies; i++){
		//		cout <<"xh:   "<<curr->xH[i] <<" v: "<<curr->v[i]<<endl;
				sdist = sdist + curr->v[i]*(t->sInitialValue[i]-curr->xH[i]);
			}
			cout << "Distance of the initial valaue to the cutting plane "<<sdist<<endl;
			if(sdist > 0){
				curr = curr->right;
			}
			else{ curr = curr->left;}

		}

		//the tree for the current data
		tree_node* record_node = new tree_node;
		record_node->key = curr->key;
		record_node->tol = curr->tol;

		//initialise an array
		record_node->sInitialValue = new double[curr->nSpecies];

		int nL =(curr->nSpecies*(curr->nSpecies+1))/2;
		record_node->cholSMatrixL = new double [nL];

		//copy the data
		record_node->nSpecies = curr->nSpecies;
		record_node->nTimeSteps = curr->nTimeSteps;

		record_node->timeCourseResult = curr->timeCourseResult;

		//copy the choleskey matrix in packed format and the species intial condition
		for(int i=0; i<nL; i++)record_node->cholSMatrixL[i] = curr->cholSMatrixL[i];
		for(int i=0; i<curr->nSpecies; i++)	record_node->sInitialValue[i] = curr->sInitialValue[i];
		record_node->left = NULL;
		record_node->right = NULL;
		record_node->isANode = false;

		//calculate the cutting plane
		double xH[t->nSpecies];
		double v[t->nSpecies];
		double sdist1, sdist2;
		sdist1 = 0.0;
		sdist2 = 0.0;

		// Find the Node's parent
		parent = curr;
		cout<<"Inserting leaf data"<<endl;
		ell_pt_hyper_(&curr->nSpecies, curr->sInitialValue, curr->cholSMatrixL, t->sInitialValue, xH, v);
	//	ell_pair_separate_(&curr->nSpecies, curr->sInitialValue, curr->cholSMatrixL,t->sInitialValue, t->cholSMatrixL, &qual, &max_it, xH, v, &intersect);

		cout<<"Printing hyper plane"<<endl;
		for(int i=0; i<t->nSpecies; i++){
		//	cout <<"xH:   "<<xH[i] <<" v: "<<v[i]<<endl;
			sdist2 = sdist2 + v[i]*(t->sInitialValue[i]-xH[i]);
			sdist1 = sdist1 + v[i]*(curr->sInitialValue[i]-xH[i]);
		}

		cout <<"Distance 1 to the cutting plane " <<sdist1 <<" Distance 2 to cutting plane " <<sdist2 <<endl;
		parent->v = new double [t->nSpecies];
		parent->xH = new double [t->nSpecies];

		for(int i = 0; i<t->nSpecies; i++){
			parent->v[i] = v[i];
			parent->xH[i] = xH[i];
			//	cout << "Parent info "<<parent->xH[i]<<"  Current: "<< curr->xH[i] <<endl;
		}
		curr->isANode = true;
		parent->isANode = true;
		delete parent->cholSMatrixL;
	//	delete parent->timeCourseData;
		if(sdist2 > 0){
			parent->right = t;
			parent->left = record_node;

			cout << "Leaf data on the right"<<endl;
		///TODO	cout<<record_node->timeCourseResult<<endl;

			cout <<"Leaf Data on the left"<<endl;
		///TODO	cout<<t->timeCourseResult<<endl;

		}
		else {
			parent->right = record_node;
			parent->left = t;

			cout << "Leaf data on the rigth"<<endl;
		///TODO	cout<<record_node->timeCourseResult<<endl;

			cout <<"Leaf data on the left"<<endl;
			//TODO cout<<t->timeCourseResult<<endl;
		}

	}

}
// Records with negative distance to cutting plane go left
// Records with possitive distance to cutting plane go right
void BSTree::insert1(tree_node *leaf){
	tree_node* t = new tree_node;
	tree_node* parent;
	t->key = leaf->key;
	t->tol = leaf->tol;

	//initialise an array
	t->timeCourseData = new float *[leaf->nTimeSteps+1]; //add 1 for zero time
	for(int i=0; i<leaf->nTimeSteps+1; i++){
		t->timeCourseData[i] = new float[leaf->nSpecies+1]; //add one for time column
	}
	t->sInitialValue = new double[leaf->nSpecies];

	int nL =(leaf->nSpecies*(leaf->nSpecies+1))/2;
	t->cholSMatrixL = new double [nL];

	//copy the data
	t->nSpecies = leaf->nSpecies;
	t->nTimeSteps = leaf->nTimeSteps;

	//	cout <<"Printing the tree data to find where there is a problem"<<endl;
	for(int i=0; i<leaf->nTimeSteps+1; i++){
		for(int j=0; j<leaf->nSpecies+1; j++){
			t->timeCourseData[i][j] = leaf->timeCourseData[i][j];
			//	cout << t->timeCourseData[i][j]<<"\t";
		}
		//cout <<endl;
	}

	//copy choleskey data
	for(int i=0; i<nL; i++){
		t->cholSMatrixL[i] = leaf->cholSMatrixL[i];		//;
	}

	for(int i=0; i<leaf->nSpecies; i++)	t->sInitialValue[i] = leaf->sInitialValue[i];
	t->left = NULL;
	t->right = NULL;
	parent = NULL;
	t->isANode = false;

	// is this a new tree?
	if(isEmpty()) root = t;
	else
	{
		//Note: ALL insertions are as leaf nodes
		tree_node* curr;
		curr = root;

		double qual = 0.003;
		int max_it = 3;
		bool intersect = false;
		//calculate the cutting plane
		double sdist;
		while(curr && curr->isANode){
			sdist = 0.0;
			parent = curr;
			for(int i=0; i<t->nSpecies; i++){
		//		cout <<"xh:   "<<curr->xH[i] <<" v: "<<curr->v[i]<<endl;
				sdist = sdist + curr->v[i]*(t->sInitialValue[i]-curr->xH[i]);
			}
			cout << "Distance of the initial valaue to the cutting plane "<<sdist;
			if(sdist > 0){
				curr = curr->right;
			}
			else{ curr = curr->left;}

		}

		//the tree for the current data
		tree_node* record_node = new tree_node;
		record_node->key = curr->key;
		record_node->tol = curr->tol;

		//initialise an array
		record_node->timeCourseData = new float *[curr->nTimeSteps+1]; //add 1 for zero time
		for(int i=0; i<curr->nTimeSteps+1; i++){
			record_node->timeCourseData[i] = new float[curr->nSpecies+1]; //add one for time column
		}
		record_node->sInitialValue = new double[curr->nSpecies];

		int nL =(curr->nSpecies*(curr->nSpecies+1))/2;
		record_node->cholSMatrixL = new double [nL];

		//copy the data
		record_node->nSpecies = curr->nSpecies;
		record_node->nTimeSteps = curr->nTimeSteps;

		for(int i=0; i<curr->nTimeSteps+1; i++){
			for(int j=0; j<curr->nSpecies+1; j++){
				record_node->timeCourseData[i][j] = curr->timeCourseData[i][j];
			}
		}
		//copy the choleskey matrix in packed format and the species intial condition
		for(int i=0; i<nL; i++)record_node->cholSMatrixL[i] = curr->cholSMatrixL[i];
		for(int i=0; i<curr->nSpecies; i++)	record_node->sInitialValue[i] = curr->sInitialValue[i];
		record_node->left = NULL;
		record_node->right = NULL;
		record_node->isANode = false;

		//calculate the cutting plane
		double xH[t->nSpecies];
		double v[t->nSpecies];
		double sdist1, sdist2;
		sdist1 = 0.0;
		sdist2 = 0.0;

		// Find the Node's parent
		parent = curr;
		cout<<"Inserting leaf data";
		ell_pt_hyper_(&curr->nSpecies, curr->sInitialValue, curr->cholSMatrixL, t->sInitialValue, xH, v);
		//ell_pair_separate_(&curr->nSpecies, curr->sInitialValue, curr->cholSMatrixL,t->sInitialValue, t->cholSMatrixL, &qual, &max_it, xH, v, &intersect);

		for(int i=0; i<t->nSpecies; i++){
		///TODO	cout <<"xH:   "<<xH[i] <<" v: "<<v[i]<<endl;
			sdist2 = sdist2 + v[i]*(t->sInitialValue[i]-xH[i]);
			sdist1 = sdist1 + v[i]*(curr->sInitialValue[i]-xH[i]);
		}

		cout <<"Distance 1 to the cutting plane " <<sdist1 <<" Distance 2 to cutting plane " <<sdist2 <<endl;
		parent->v = new double [t->nSpecies];
		parent->xH = new double [t->nSpecies];

		for(int i = 0; i<t->nSpecies; i++){
			parent->v[i] = v[i];
			parent->xH[i] = xH[i];
			//	cout << "Parent info "<<parent->xH[i]<<"  Current: "<< curr->xH[i] <<endl;
		}
		curr->isANode = true;
		parent->isANode = true;
		delete parent->cholSMatrixL;
		delete parent->timeCourseData;
		if(sdist2 > 0){
			parent->right = t;
			parent->left = record_node;

		/* ///TODO	cout << "Leaf data on the rigth"<<endl;
			for(int i=0; i<curr->nTimeSteps+1; i++){
				for(int j=0; j<curr->nSpecies+1; j++){
					cout<<record_node->timeCourseData[i][j]<<"\t";
				}
				cout <<endl;
			}

			cout <<"Leaf Data on the left"<<endl;
			for(int i=0; i<t->nTimeSteps+1; i++){
				for(int j=0; j<t->nSpecies+1; j++){
					cout<<t->timeCourseData[i][j]<<"\t";
				}
				cout <<endl;
			} */

		}
		else {
			parent->right = record_node;
			parent->left = t;

	/*///TODO		cout << "Leaf data on the rigth"<<endl;
			for(int i=0; i<curr->nTimeSteps+1; i++){
				for(int j=0; j<curr->nSpecies+1; j++){
					cout<<record_node->timeCourseData[i][j]<<"\t";
				}
				cout <<endl;
			}

			cout <<"Leaf data on the left"<<endl;
			for(int i=0; i<t->nTimeSteps+1; i++){
				for(int j=0; j<t->nSpecies+1; j++){
					cout<<t->timeCourseData[i][j]<<"\t";
				}
				cout <<endl;
			}*/
		}

		/*

			while(curr)
			{
			//	cout <<"I am in the while loop"<<endl;
				tree_node* record_node = new tree_node;
				//===========
				record_node->key = curr->key;
				record_node->tol = curr->tol;

				//initialise an array
				record_node->timeCourseData = new float *[curr->nTimeSteps+1]; //add 1 for zero time
				for(int i=0; i<curr->nTimeSteps+1; i++){
					record_node->timeCourseData[i] = new float[curr->nSpecies+1]; //add one for time column
				}
				record_node->sInitialValue = new double[curr->nSpecies];

				int nL =(curr->nSpecies*(curr->nSpecies+1))/2;
				record_node->cholSMatrixL = new double [nL];

				//copy the data
				record_node->nSpecies = curr->nSpecies;
				record_node->nTimeSteps = curr->nTimeSteps;

				if(!curr->isANode){
				for(int i=0; i<curr->nTimeSteps+1; i++){
					for(int j=0; j<curr->nSpecies+1; j++){
						record_node->timeCourseData[i][j] = curr->timeCourseData[i][j];
						//cout<<record_node->timeCourseData[i][j]<<"\t";
					}
					//cout <<endl;
				}
				for(int i=0; i<nL; i++)record_node->cholSMatrixL[i] = curr->cholSMatrixL[i];
				}
				//record_node->cholSMatrixL = curr->cholSMatrixL;
				for(int i=0; i<curr->nSpecies; i++)	record_node->sInitialValue[i] = curr->sInitialValue[i];
				record_node->left = NULL;
				record_node->right = NULL;
			//	parent = NULL;
				record_node->isANode = false;
				//==============

				//calculate the cutting plane
				double xH[t->nSpecies];
				double v[t->nSpecies];
				double sdist1, sdist2, sdist;
				// Find the Node's parent

				sdist = 0.0;
				sdist1 = 0.0;
				sdist2 = 0.0;
				parent = curr;
				if((curr->isANode)){
				//	parent = curr;
					cout<<"======I get here===="<<endl;
					for(int i=0; i<t->nSpecies; i++){
					cout <<"xh:   "<<curr->xH[i] <<" v: "<<curr->v[i]<<endl;
					sdist = sdist + curr->v[i]*(t->sInitialValue[i]-curr->xH[i]);
				}
				cout << "distance measured "<<sdist;
				if(sdist > 0){
					curr = curr->right;
					cout <<"========I am right======";
				}
				else{ curr = curr->left;}
			}else{
			//	parent = curr;
				cout<<"==========Subsequent leaf insertion=========="<<endl;

				for(int i=0; i<curr->nSpecies; i++){
					cout <<curr->sInitialValue[i] <<endl;
				}
				for(int i=0; i<nL; i++){
					cout << curr->cholSMatrixL[i]<<endl;
				}
				ell_pair_separate_(&curr->nSpecies, curr->sInitialValue, curr->cholSMatrixL,t->sInitialValue, t->cholSMatrixL, &qual, &max_it, xH, v, &intersect);
				for(int i=0; i<t->nSpecies; i++){
					cout <<"xH:   "<<xH[i] <<" v: "<<v[i]<<endl;
					sdist2 = sdist2 + v[i]*(t->sInitialValue[i]-xH[i]);
					sdist1 = sdist1 + v[i]*(curr->sInitialValue[i]-xH[i]);
				}
				cout <<"distance 1 " <<sdist1 <<"distance 2 " <<sdist2 <<endl;
				parent->v = new double [t->nSpecies];
				parent->xH = new double [t->nSpecies];

				//parent information
				for(int i = 0; i<t->nSpecies; i++){
					parent->v[i] = v[i];
					parent->xH[i] = xH[i];
				}
				parent->isANode = true;
				delete parent->cholSMatrixL;
				delete parent->timeCourseData;
				if(sdist2 > 0){
					curr = curr->right;
					parent->right = record_node;
					parent->left = t;
				}
				else {
					curr = curr->left;
					parent->right = t;
					parent->right = record_node;
				}

				cout <<"Parent right node:== " <<parent->right->isANode;
				//we are at the end set curr = NULL to move out of the loop
			//	curr = NULL;


			}

		}
			cout <<"Root Parent right node:== " <<root->right->isANode<<endl;

		}*/
	}

}

//local searching for node for approximating simulation results
tree_node *BSTree::search(double *sIV, tree_node *leaf)
{
	if(leaf!=NULL)
	{
		int counter = 0;
		if(leaf->isANode){
			counter++;
			cout<<"It is a Node"<<endl;
			double sdist = 0.0;
			for(int i=0; i<leaf->nSpecies; i++){
			//	cout <<"xH:   "<<leaf->xH[i] <<" v: "<<leaf->v[i]<<endl;
				sdist = sdist + leaf->v[i]*(sIV[i]-leaf->xH[i]);
			}
			cout <<"Searching distance " <<sdist <<endl;
			if(sdist<0.0){
				cout <<"Go to Left Searching\n";
				return search(sIV, leaf->left);
			}else{
				cout <<"Go to right searching\n";
				return search(sIV, leaf->right);
			}
			cout <<"Node counter: " <<counter<<endl;
		}

		//check to see if it is a node only
		//	int counter = 0;
		cout <<"Problem Here"<< leaf->isANode<<endl;
		bool inn = false;
		if(!leaf->isANode){
			cout <<"No Problem Here Am a Leaf"<< leaf->isANode<<endl;
			//	bool inn = false;
			ell_pt_in_(&leaf->nSpecies, leaf->sInitialValue, leaf->cholSMatrixL, sIV, &inn);
			cout << "Output: " << inn <<endl;
		}
		if(inn)
			return leaf;
		else return NULL;
		//	}


		/*		if(leaf->isANode){
			counter++;
			cout<<"It is a Node"<<endl;
			double sdist = 0.0;
			for(int i=0; i<leaf->nSpecies; i++){
				cout <<"xH:   "<<leaf->xH[i] <<" v: "<<leaf->v[i]<<endl;
				sdist = sdist + leaf->v[i]*(sIV[i]-leaf->xH[i]);
			}
			cout <<"Searching distance " <<sdist <<endl;
			if(sdist<0.0){
				cout <<"Go to Left Searching\n";
				return search(sIV, leaf->left);
			}else{
				cout <<"Go to right searching\n";
				return search(sIV, leaf->right);
			}
			cout <<"Node counter: " <<counter<<endl;
		}else{
			cout <<"No Problem Here Am a Leaf"<< leaf->isANode<<endl;
			bool inn = false;
			ell_pt_in_(&leaf->nSpecies, leaf->sInitialValue, leaf->cholSMatrixL, sIV, &inn);
			cout << "Output: " << inn <<endl;

			if(inn)
				return leaf;
			else return NULL;
		}*/
		//test to see if the initial condition can be approximated from the tree data
		/*	bool inn = false;
		ell_pt_in_(&leaf->nSpecies, leaf->sInitialValue, leaf->cholSMatrixL, sIV, &inn);
		cout << "Output: " << inn <<endl;

		if(inn)
			return leaf;

		//calculate the cutting plane
		double xh[leaf->nSpecies];
		double v[leaf->nSpecies];
		double sdist1 = 0.0, sdist2 = 0.0;
		ell_pt_hyper_(&leaf->nSpecies, leaf->sInitialValue, leaf->cholSMatrixL, sIV, xh, v);
		for(int i=0; i<leaf->nSpecies; i++){
			cout <<"xh:   "<<xh[i] <<" v: "<<v[i]<<endl;
			sdist2 = sdist2 + v[i]*(leaf->sInitialValue[i]-xh[i]);
			sdist1 = sdist1 + v[i]*(sIV[i]-xh[i]);
		}
		cout <<"Distance for 1: " <<sdist1 <<" Distance for 2: " <<sdist2 <<endl;

		if(sdist2<0.0){
			cout <<"Left Second Searching\n";
			return search(sIV, leaf->left);
		}
		else{
			cout <<"Right Second searching\n";
			return search(sIV, leaf->right);
		}*/
	}
	//	else {cout << "Returning NULL"<<endl; return NULL;}
}

//searching for node that is in an ellipsoid
tree_node *BSTree::search(double* sIV)
{
	return search(sIV, root);
}

// Records with smaller keys go left
// Records with larger keys go right
void BSTree::insert(int d, float tolerance, float **tcData, int row,int col)
{
	row = row+1; //add 1 for zero time
	col = col+1; //add one for time column

	tree_node* t = new tree_node;
	tree_node* parent;
	t->key = d;
	t->tol = tolerance;

	//initialise an array
	t->timeCourseData = new float *[row];
	for(int i=0; i<row; i++){
		t->timeCourseData[i] = new float[col];
	}
	for(int i=0; i<row; i++){
		for(int j=0; j<col; j++){
			t->timeCourseData[i][j] = tcData[i][j];
		}

	}
	t->left = NULL;
	t->right = NULL;
	parent = NULL;

	// is this a new tree?
	if(isEmpty()) root = t;
	else
	{
		//Note: ALL insertions are as leaf nodes
		tree_node* curr;
		curr = root;
		// Find the Node's parent
		while(curr)
		{
			parent = curr;
			if(t->key > curr->key) curr = curr->right;
			else curr = curr->left;
		}

		if(t->key < parent->key)
			parent->left = t;
		else
			parent->right = t;
	}
}

// Records with smaller keys go left
// Records with larger keys go right
void BSTree::insert(int d, float tolerance, string* timeCourseResult, int row,int col)
{
	row = row+1; //add 1 for zero time
	col = col+1; //add one for time column

	tree_node* t = new tree_node;
	tree_node* parent;
	t->key = d;
	t->tol = tolerance;
	t->timeCourseResult = *timeCourseResult;


	t->left = NULL;
	t->right = NULL;
	parent = NULL;

	// is this a new tree?
	if(isEmpty()) root = t;
	else
	{
		//Note: ALL insertions are as leaf nodes
		tree_node* curr;
		curr = root;
		// Find the Node's parent
		while(curr)
		{
			parent = curr;
			if(t->key > curr->key) curr = curr->right;
			else curr = curr->left;
		}

		if(t->key < parent->key)
			parent->left = t;
		else
			parent->right = t;
	}
}

void BSTree::remove(int d)
{
	//Locate the element
	bool found = false;
	if(isEmpty())
	{
		cout<<" This Tree is empty! "<<endl;
		return;
	}

	tree_node* curr;
	tree_node* parent;
	curr = root;

	while(curr != NULL)
	{
		if(curr->key == d)
		{
			found = true;
			break;
		}
		else
		{
			parent = curr;
			if(d>curr->key) curr = curr->right;
			else curr = curr->left;
		}
	}
	if(!found)
	{
		cout<<" key not found! "<<endl;
		return;
	}


	// 3 cases :
	// 1. We're removing a leaf node
	// 2. We're removing a node with a single child
	// 3. we're removing a node with 2 children

	// Node with single child
	if((curr->left == NULL && curr->right != NULL)|| (curr->left != NULL
			&& curr->right == NULL))
	{
		if(curr->left == NULL && curr->right != NULL)
		{
			if(parent->left == curr)
			{
				parent->left = curr->right;
				delete curr;
			}
			else
			{
				parent->right = curr->right;
				delete curr;
			}
		}
		else // left child present, no right child
		{
			if(parent->left == curr)
			{
				parent->left = curr->left;
				delete curr;
			}
			else
			{
				parent->right = curr->left;
				delete curr;
			}
		}
		return;
	}

	//We're looking at a leaf node
	if( curr->left == NULL && curr->right == NULL)
	{
		if(parent->left == curr) parent->left = NULL;
		else parent->right = NULL;
		delete curr;
		return;
	}


	//Node with 2 children
	// replace node with smallest value in right suBSTree
	if (curr->left != NULL && curr->right != NULL)
	{
		tree_node* chkr;
		chkr = curr->right;
		if((chkr->left == NULL) && (chkr->right == NULL))
		{
			curr = chkr;
			delete chkr;
			curr->right = NULL;
		}
		else // right child has children
		{
			//if the node's right child has a left child
			// Move all the way down left to locate smallest element

			if((curr->right)->left != NULL)
			{
				tree_node* lcurr;
				tree_node* lcurrp;
				lcurrp = curr->right;
				lcurr = (curr->right)->left;
				while(lcurr->left != NULL)
				{
					lcurrp = lcurr;
					lcurr = lcurr->left;
				}
				curr->key = lcurr->key;
				delete lcurr;
				lcurrp->left = NULL;
			}
			else
			{
				tree_node* tmp;
				tmp = curr->right;
				curr->key = tmp->key;
				curr->right = tmp->right;
				delete tmp;
			}

		}
		return;
	}

}

//local searching
tree_node *BSTree::search(int key, tree_node *leaf)
{
	if(leaf!=NULL)
	{
		if(key==leaf->key)
			return leaf;
		if(key<leaf->key)
			return search(key, leaf->left);
		else
			return search(key, leaf->right);
	}
	else return NULL;
}

//searching for node with key
tree_node *BSTree::search(int key)
{
	return search(key, root);
}

void BSTree::print_inorder()
{
	inorder(root);
}

void BSTree::inorder(tree_node* p)
{
	if(p != NULL)
	{
		//if(p->isANode){
		int nL =(p->nSpecies*(p->nSpecies+1))/2;

		if(p->left){
			cout <<"Is it a Node: "<< p->isANode <<endl;
			//inorder(p->left);
			/*	if(p->isANode){
				cout <<"=======start of Data on Left side======"<<endl;
				for(int i = 0; i<p->nSpecies; i++){
					cout <<p->xH[i]<<endl;
				}
				cout <<"===========Initial Value========="<<endl;
				for(int i=0; i<p->nSpecies; i++) cout << p->sInitialValue[i]<<endl;

				for(int i=0; i<p->nTimeSteps+1; i++){
					for(int j=0; j<p->nSpecies+1; j++){
						cout<<p->timeCourseData[i][j]<<"\t";
					}
					cout<<endl;
				}
				cout <<"end of Data on left\n";

			}*/
			inorder(p->left);
		}

		if(p->right){
			if(p->isANode){
				inorder(p->right);
				//if(p->isANode){
				cout <<"=======start of Data====== on Right side";
				for(int i = 0; i<nL; i++){
					cout <<p->cholSMatrixL[i]<<endl;
				}
				for(int i=0; i<p->nTimeSteps+1; i++){
					for(int j=0; j<p->nSpecies+1; j++){
						cout<<p->timeCourseData[i][j]<<"\t";
					}
					cout<<endl;
				}
				cout <<"end of Data on right\n";

			}
		}

	}
	else return;
}

void BSTree::print_preorder()
{
	preorder(root);
}

void BSTree::preorder(tree_node* p)
{
	if(p != NULL)
	{
		int nL =(p->nSpecies*(p->nSpecies+1))/2;
		cout <<"=======start of Data======";
		if(!p->isANode){
			for(int i = 0; i<nL; i++){
				cout <<p->cholSMatrixL[i]<<endl;
			}
			for(int i=0; i<p->nTimeSteps+1; i++){
				for(int j=0; j<p->nSpecies+1; j++){
					cout<<p->timeCourseData[i][j]<<"\t";
				}
				cout<<endl;
			}
			cout <<"end of Data\n";
		}else{
			cout << "=====Node Data========"<<endl;
			for(int i = 0; i<p->nSpecies; i++){
				cout<<"V "<<p->v[i]<<" xH "<<p->xH[i]<<endl;
			}
		}
		//	cout<<" "<<p->key<<" ";
		if(p->left) preorder(p->left);
		if(p->right) preorder(p->right);
	}
	else return;
}

void BSTree::print_postorder()
{
	postorder(root);
}

void BSTree::postorder(tree_node* p)
{
	if(p != NULL)
	{
		if(p->left) postorder(p->left);
		if(p->right) postorder(p->right);
		cout<<" "<<p->key<<" ";
		cout<<" "<<p->tol<<" ";
	}
	else return;
}

//TODO example for removing node from binary tree


/*
 void BinarySearchTree::remove(int d)
{
    //Locate the element
    bool found = false;
    if(isEmpty())
    {
        cout<<" This Tree is empty! "<<endl;
        return;
    }

    tree_node* curr;
    tree_node* parent;
    curr = root;

    while(curr != NULL)
    {
         if(curr->data == d)
         {
            found = true;
            break;
         }
         else
         {
             parent = curr;
             if(d>curr->data) curr = curr->right;
             else curr = curr->left;
         }
    }
    if(!found)
		 {
        cout<<" Data not found! "<<endl;
        return;
    }


		 // 3 cases :
    // 1. We're removing a leaf node
    // 2. We're removing a node with a single child
    // 3. we're removing a node with 2 children

    // Node with single child
    if((curr->left == NULL && curr->right != NULL)|| (curr->left != NULL
&& curr->right == NULL))
    {
       if(curr->left == NULL && curr->right != NULL)
       {
           if(parent->left == curr)
           {
             parent->left = curr->right;
             delete curr;
           }
           else
           {
             parent->right = curr->right;
             delete curr;
           }
       }
       else // left child present, no right child
       {
          if(parent->left == curr)
           {
             parent->left = curr->left;
             delete curr;
           }
           else
           {
             parent->right = curr->left;
             delete curr;
           }
       }
     return;
    }

		 //We're looking at a leaf node
		 if( curr->left == NULL && curr->right == NULL)
    {
        if(parent->left == curr) parent->left = NULL;
        else parent->right = NULL;
		 		 delete curr;
		 		 return;
    }


    //Node with 2 children
    // replace node with smallest value in right subtree
    if (curr->left != NULL && curr->right != NULL)
    {
        tree_node* chkr;
        chkr = curr->right;
        if((chkr->left == NULL) && (chkr->right == NULL))
        {
            curr = chkr;
            delete chkr;
            curr->right = NULL;
        }
        else // right child has children
        {
            //if the node's right child has a left child
            // Move all the way down left to locate smallest element

            if((curr->right)->left != NULL)
            {
                tree_node* lcurr;
                tree_node* lcurrp;
                lcurrp = curr->right;
                lcurr = (curr->right)->left;
                while(lcurr->left != NULL)
                {
                   lcurrp = lcurr;
                   lcurr = lcurr->left;
                }
		curr->data = lcurr->data;
                delete lcurr;
                lcurrp->left = NULL;
           }
           else
           {
               tree_node* tmp;
               tmp = curr->right;
               curr->data = tmp->data;
	       curr->right = tmp->right;
               delete tmp;
           }

        }
		 return;
    }

}
 */

/*bool BinarySearchTree::remove(int value) {

      if (root == NULL)

            return false;

      else {

            if (root->getValue() == value) {

                  BSTNode auxRoot(0);

                  auxRoot.setLeftChild(root);

                  BSTNode* removedNode = root->remove(value, &auxRoot);

                  root = auxRoot.getLeft();

                  if (removedNode != NULL) {

                        delete removedNode;

                        return true;

                  } else

                        return false;

            } else {

                  BSTNode* removedNode = root->remove(value, NULL);

                  if (removedNode != NULL) {

                        delete removedNode;

                        return true;

                  } else

                        return false;

            }

      }

}



BSTNode* BSTNode::remove(int value, BSTNode *parent) {

      if (value < this->value) {

            if (left != NULL)

                  return left->remove(value, this);

            else

                  return NULL;

      } else if (value > this->value) {

            if (right != NULL)

                  return right->remove(value, this);

            else

                  return NULL;

      } else {

            if (left != NULL && right != NULL) {

                  this->value = right->minValue();

                  return right->remove(this->value, this);

            } else if (parent->left == this) {

                  parent->left = (left != NULL) ? left : right;

                  return this;

            } else if (parent->right == this) {

                  parent->right = (left != NULL) ? left : right;

                  return this;

            }

      }

}



int BSTNode::minValue() {

      if (left == NULL)

            return value;

      else

            return left->minValue();

}*/

