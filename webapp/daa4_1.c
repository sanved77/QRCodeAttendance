#include <stdio.h>
#include <stdlib.h> 
#include <time.h>
#include <stdbool.h>
#include <limits.h> 

struct kr_edge
{
	int node1;
	int node2;
	int wt;
};	

int Sort(struct kr_edge *edges, int n);

void Kruskals(int rand_1,int adjacency_matrix[rand_1][rand_1],int 
random_num,struct kr_edge *edges,int size);

int Sort(struct kr_edge *edges, int n) 
{ 
	int i, j;
	
	
    struct kr_edge value;
    for (i = 1; i < n; i++) {
        value.wt = edges[i].wt;
        value.node1 = edges[i].node1;
        value.node2 = edges[i].node2;
        j = i - 1;

        while (j >= 0 && edges[j].wt > value.wt) {
            edges[j + 1].wt = edges[j].wt;
            edges[j + 1].node1 = edges[j].node1;
            edges[j + 1].node2 = edges[j].node2;
            j = j - 1;
        }
        edges[j + 1].wt = value.wt;
        edges[j + 1].node1 =  value.node1;
        edges[j + 1].node2 =  value.node2;
    }
	return 1;
}
 
   


void PrintMatrix(int m, int adjacency_matrix[m][m],int random_num);
//void printMatrix(int adjacency_matrix[][]);

void prims(int m, int adjacency_matrix[m][m],int random_num);


void InitiliseMatrix(int m,int adjacency_matrix[m][m],int 
vert_matrix[m][m],int random_num)
{		
	int i,j;
		for(i=1;i<=random_num;i++){
			for(j=1;j<=random_num;j++){
				adjacency_matrix[i][j]=-1;
				if(i==j){ 									
//If i==j then make vertex is 0
					vert_matrix[i][j]=0;
				}
				else{ 										
//Else then make vertex is i
					vert_matrix[i][j]=i;
				}
			}
		}	
	
}

void PrintMatrix(int m, int adjacency_matrix[m][m],int random_num)
{
	int i,j;
printf("\n");
	printf("The adjancy matrix is: \n");
	for(i=1;i<=random_num;i++)
	{
		for(j=1;j<=random_num;j++)
		{
			printf("%d\t",adjacency_matrix[j][i]);
		}	
		printf("\n");
	}	
}	

int minvalue(int value[], bool mstSet[],int m) 
{ 
// Initialize min value 
int min = INT_MAX, min_index; 
 int v; 
for (v = 0; v < m; v++) 
    if (mstSet[v] == false && value[v] < min) 
        min = value[v], min_index = v; 
  
return min_index; 
} 
  
// A utility function to print the  
// constructed MST stored in parent[] 
int printMST(int parent[], int m, int adjacency_matrix[m][m],int random_num) 
{ 
    int sumMst=0;
	int i;
printf("Edge \tWeight\n"); 
for (i = 1; i <= random_num; i++) 
    {
        if(parent[i]!=0)
        
        printf("%d - %d \t%d \n", parent[i], i, 
adjacency_matrix[i][parent[i]]); 
        sumMst=sumMst+adjacency_matrix[i][parent[i]];
        
    }
   // printf("Sum of MST : %d\n",sumMst);
} 





void prims(int m, int adjacency_matrix[m][m],int random_num)
{

int parent[m];
int value[m];

bool mstSet[m];

int i ;
for (i = 0; i < m; i++) 
    value[i] = INT_MAX, mstSet[i] = false; 
  
    // Always include first 1st vertex in MST. 
    // Make value 0 so that this vertex is picked as first vertex. 
    value[0] = 0;      
    parent[0] = -1; // First node is always root of MST  
  
    // The MST will have V vertices 
    int count;
	for ( count = 0; count < m-1; count++) 
    { 
        // Pick the minimum value vertex from the  
        // set of vertices not yet included in MST 
        int u = minvalue(value, mstSet,m); 
  
        // Add the picked vertex to the MST Set 
        mstSet[u] = true; 
  
        // Update value value and parent index of  
        // the adjacent vertices of the picked vertex.  
        // Consider only those vertices which are not  
        // yet included in MST 
        int v;
		for (v  = 0; v < m; v++) 
  
        // graph[u][v] is non zero only for adjacent vertices of m 
        // mstSet[v] is false for vertices not yet included in MST 
        // Update the value only if graph[u][v] is smaller than value[v] 
        if (adjacency_matrix[u][v] && mstSet[v] == false && adjacency_matrix[u][v] < value[v]) 
            parent[v] = u, value[v] = adjacency_matrix[u][v]; 
    } 
  
    // print the constructed MST 
    printMST(parent, m, adjacency_matrix,random_num);


}

int find(int p, int *id) 
{
  int root = p;
  while( root != id[root] )
  {
	root = id[root];  
  }
    

  while(p != root) 
  {
    int next = id[p];
    id[p] = root;
    p = next;
  }

  return root;

}

bool Isconnected(int p, int q, int *id) 
{
  return find(p, id) == find(q, id);
}


void union3(int p, int q, int *id, int *heightOfTree) {

  int parent1 = find(p, id);
  int parent2 = find(q, id);

  if (heightOfTree[find(p, id)] < heightOfTree[find(q, id)]) {
    heightOfTree[parent2] = heightOfTree[parent2] +  
heightOfTree[parent1];
    id[parent1] = parent2;
  }
  if (parent1 == parent2) 
  {
	return;  
  }
 else {
    heightOfTree[parent1] = heightOfTree[parent1] +  
heightOfTree[parent2];
    id[parent2] = parent1;
  }


}

void Kruskals(int rand_1,int adjacency_matrix[rand_1][rand_1],int 
random_num,struct kr_edge *edges,int size)
{
	  
  int j, k;

  int height[100];

  int id[100];

  bool yn;

  struct kr_edge spannedEdges[size];
  struct kr_edge e;
  
  int i;
  for(i = 0; i < random_num; i++)
  {
    id[i] = i;
    height[i] = 1;
  }

  
  j=0;

  for(k = 0;k < (random_num - 1); )
  {
    e = edges[j];
    j++;
    yn = Isconnected(e.node1, e.node2, id);
    if(yn == false)
    {
      spannedEdges[k].node1 = e.node1;
	  spannedEdges[k].node2 = e.node2;
	  spannedEdges[k].wt = e.wt;
      union3(e.node1, e.node2, id, height);
      k++;
    }
  }
  printf("The Spanned edges are\n");
  printf("\n\nEdge\tWeight\n");
  while(k > 0)
  {
    e = spannedEdges[--k];
    printf("%d - %d\t%d\n", e.node1,e.node2, e.wt);
  }

	
}	







 int RandomFunction(int lower, int upper) {
     
        int num = (rand() %(upper - lower + 1)) + lower;
        //printf("Number of vertices %d ", num);
    
return num;
} 
  
 int main() {
	 
	 
 
	 
    int lower = 5, upper = 10;
	int random_num;
	int random_lower_matrix=1;
    int random_upper_matrix=10;
    
    // Use current time as
    // seed for random generator
    srand(time(0));
  
	random_num =RandomFunction(lower, upper);
	printf("Number of vertices %d ", random_num);
	
	int adjacency_matrix[random_num+1][random_num+1];
    int vert_matrix[random_num+1][random_num+1];
    int rand_1=random_num+1;
	
	InitiliseMatrix(rand_1,adjacency_matrix,vert_matrix,random_num);
	
	
	int size_matrix= (((random_num*random_num)-random_num)/2);
	
	struct kr_edge *edges;
	edges = malloc(sizeof(struct kr_edge)*size_matrix);
	
	int i,j;
    for(i=1;i<=random_num;i++){
        for(j=1;j<=random_num;j++){
            if(i==j){ 						
                adjacency_matrix[i][j]=0;
            }
            else{
                if(adjacency_matrix[j][i]==-1){ 
					int random_num_matrix=0;
					
random_num_matrix=RandomFunction(random_lower_matrix,random_upper_matrix);
					
adjacency_matrix[i][j]=random_num_matrix;
				}
				else{
                    adjacency_matrix[i][j]=adjacency_matrix[j][i]; 

                }
			}
				
		}
	}
	
	int p=0;
	
	for(i=0;i<random_num;i++){
        for(j=0;j<random_num;j++){
			
				if(i<j)
				{
					edges[p].node1=i;
					edges[p].node2=j;
					
edges[p].wt=adjacency_matrix[i+1][j+1];
					p++;
				}	
			
		}	
	}
	
	//Sort all edges
	
	
	
	
	
	
	PrintMatrix(rand_1,adjacency_matrix,random_num); 
	
		
	
	
	int choice=1;
	while(choice== 1 || choice == 2)
	{
		printf( "\n Please slect following \n 1. Prims \n 2. Kruskals \n ");
		scanf ("%d",&choice);
		
		 switch(choice)
		 {
		    case 1 : prims(rand_1,adjacency_matrix,random_num);
				break;
		    case 2 : 
				
				Sort(edges,p);
				int m =0;
				while(m<p)
				{
				printf("\n %d - %d   ---->   %d", edges[m].node1,edges[m].node2, edges[m].wt);
					m++;
				}
				
		
		
				
Kruskals(rand_1,adjacency_matrix,random_num,edges, p);
				break;
		 }
		 
		
		
	}	
	
	
	
	free(edges);
	
	//prims(rand_1,adjacency_matrix,random_num);
	
	
	
    return 0;
} 

