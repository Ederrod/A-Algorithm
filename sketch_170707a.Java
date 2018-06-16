float w; 
float h; 

int cols = 300; 
int rows = 300; 
Node[][] grid = new Node[cols][rows]; 


ArrayList<Node> openSet = new ArrayList<Node>();
ArrayList<Node> closedSet = new ArrayList<Node>();  
ArrayList<Node> path = new ArrayList<Node>(); 

Node start; 
Node end; 

//Helper method to color in array list set
void colorSet(ArrayList<Node> Set, int r,int g,int b){
  for(int i = 0; i < Set.size(); i++){
    Set.get(i).show(color(r,g,b));  
  }  
}

//Trace back all path from optimal neighbor our current Node came from
void makePath(Node current){
  Node temp = current; 
  path.add(temp);
  while(temp.cameFrom != null){
    path.add(temp);
    temp = temp.cameFrom; 
  }
}

int heuristic(Node a, Node b){
  return (int) dist(a.x, a.y, b.x,b.y); 
}

void setup(){
  size(1200,1200); 
  w = width / cols; 
  h = height / rows; 
  
  //initialize grid 
  for(int i = 0; i < cols; i++){
    for(int j = 0; j < rows; j++){
      grid[i][j] = new Node(i,j,cols,rows,w,h); 
    }
  }
  
  //add in all possible neighbors
  for(int i = 0; i < cols; i++){
    for(int j = 0; j < rows; j++){
      grid[i][j].addNeighbors(grid); 
    }
  }
  
  //Start point of our path
  start = grid[0][0];
  
  //Our end goal point of our path
  end = grid[cols-1][rows-1]; 
  
  //Make them both not obstacles
  start.obs = false;
  end.obs = false; 
  
  //Init our openSet
  openSet.add(start); 
}

void Astar(){
  //Will hold the index where the lowest f value in the openSet
  int lowestIndex = 0; 
  
  //find the lowest value of f in openset and save it
  for(int i = 0; i < openSet.size(); i++){
    if(openSet.get(i).f < openSet.get(lowestIndex).f){
      lowestIndex = i; 
    }
  }
  
  Node current = openSet.get(lowestIndex); 
  
  //if in our openSet the lowestIndex is our goal we are done
  if(current == end){
    makePath(current); 
    noLoop(); 
    println("Done!"); 
  }
  
  
  //remove lowest index from open set, as if the node has been checked 
  openSet.remove(lowestIndex); 
  
  //add it to closed set 
  closedSet.add(current); 
  
  ArrayList<Node> neighbors = current.neighbors; 
  for(int i = 0; i < neighbors.size(); i++){
    Node neighbor = neighbors.get(i);
    
    if(!closedSet.contains(neighbor) && !neighbor.obs){
      int tempG = current.g + 1; 
      
      boolean newPath = false; 
      if(openSet.contains(neighbor)){
        neighbor.g = (tempG < neighbor.g)? tempG : neighbor.g; 
        newPath = (neighbor.g == tempG);
      }else{
        neighbor.g = tempG; 
        newPath = true;
        openSet.add(neighbor); 
      }
      if(newPath){
        neighbor.h = heuristic(neighbor, end); 
        neighbor.f = neighbor.g + neighbor.h; 
        neighbor.cameFrom = current;
      }
    } 
  } 
}

void draw(){ 
  if(openSet.size() > 0){
    Astar();    
  }else{
    println("No Path Found");
    noLoop(); 
    return;  
  }
  background(255); 
  
  for(int i = 0; i < cols; i++){
    for(int j = 0; j < rows; j++){
      grid[i][j].show(color(255)); 
    }
  }
  //Color in all cells in openSet
  colorSet(openSet,0,255,0); 
  
  //Color in all cells in closedSet
  colorSet(closedSet,255,0,0); 
  
  
  
  //Color in all cells in path
  //colorSet(path,0,0,255); 
  
  noFill(); 
  stroke(0,255,0); 
  strokeWeight(w/2); 
  beginShape();
  for(int i = 0; i < path.size(); i++){
    vertex(path.get(i).x * w + w/2, path.get(i).y * h + h/2); 
  }
  endShape(); 
  
  start.show(color(0,0,255)); 
}