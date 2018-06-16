class Node{
  //values of node
  int f; 
  int g; 
  int h; 
  
  //position of Node in window
  int x; 
  int y; 
  
  //Total rows and colums in window
  int cols; 
  int rows; 
  
  //Width and Height of window
  float wid; 
  float hig; 
  
  //Neighbor Nodes of current Node
  ArrayList<Node> neighbors = new ArrayList<Node>(); 
  Node cameFrom = null; 
  
  //Make Node an obstacle
  boolean obs = false; 
  
  Node(int i, int j, int cols, int rows, float w, float h){
    this.x = i; 
    this.y = j; 
    this.cols = cols; 
    this.rows = rows; 
    this.f = 0; 
    this.g = 0; 
    this.h = 0; 
    this.wid = w; 
    this.hig = h; 
    this.obs = (random(1) < 0.1); 
  }
  
  void addNeighbors(Node[][] grid){
    for(int i = this.x - 1; i <= (this.x + 1); i++){
      for(int j = this.y - 1; j <= (this.y + 1); j++){
        if((i >= 0 && i < cols) && (j >= 0 && j < rows)) 
          neighbors.add(grid[i][j]); 
      }
    }
  } 
  
  void show(int c){
    if(this.obs){ 
      fill(0); 
      stroke(0); 
      ellipse(this.x * this.wid + this.wid/2, this.y * this.hig + this.hig/2, this.wid/2, this.hig/2); 
    }
  }
}