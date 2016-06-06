package cs311proj2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	
	
	//Main function starts here
    public static void main(String args[]) throws IOException {

    	String path = args[0];
    	//initialize a new buffer reader
    	BufferedReader br = new BufferedReader(new FileReader(path));
    	int vertex = Integer.parseInt(br.readLine());
    	int a;
    	int b;
    	ArrayList<Integer> a1 = new ArrayList<Integer>();
    	ArrayList<Integer> a2 = new ArrayList<Integer>();
    	ArrayList<Integer> a3 = new ArrayList<Integer>();
    	ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();
    	
    	//initialize the table
    	for (int i = 1; i <= vertex+1; i++)
        {
           table.add(new ArrayList<Integer>());
        }
    	//a buff to store input line
    	String templine;
    	int color[] = new int[vertex+1];
    	int from[] = new int[vertex+1];
    	//initialize the color to -2
    	for(int i =1;i <=vertex;i++){
    		color[i]=-2;
    	}
    	//while get a line,insert the line to table
		while  ((templine=br.readLine())!=null) {
			String[] elements = templine.split(" ");
			a=Integer.parseInt(elements[0]);
			b=Integer.parseInt(elements[1]);
			table.get(a).add(b);
			//initialize the color to -1
			color[a]=-1;
			color[b]=-1;
			table.get(b).add(a);
		}
		//close read
		br.close();
		//queue is used to store vertex waiting for search
		Queue<Integer> temp = new LinkedList<Integer>();
		//initialize the queue
		temp.add(1);
		int result =0;
		color[1]=1;
		int done = 1;
		//while the queue is not empty, search next vertex
		outerloop: 
			while(!temp.isEmpty()){
			int u = temp.remove();
			if(color[u]==-1){
				color[u]=1;
			}
			//search from the adjective node of dequeue vertex
			for( int v=0;v<table.get(u).size();v++){
				if (color[table.get(u).get(v)]==-1)
                {
                    // Assign alternate color to this adjacent v of u
                    color[table.get(u).get(v)] = 1-color[u];
                    from[table.get(u).get(v)]=u;
                    temp.add(table.get(u).get(v));
                }
				//if a cycle is found, return 0 and the odd cycle
				else if (color[table.get(u).get(v)]==color[u]){
					result = 0;	
					//ArrayList<Integer> a1 = new ArrayList<Integer>();
					int yy=u;
					while(true){
						if(yy==0){
							break;
						}
						a1.add(yy);
						yy=from[yy];
					}
					//ArrayList<Integer> a2 = new ArrayList<Integer>();
					int yy2=table.get(u).get(v);
					while(true){
						if(yy2==0){
							break;
						}
						a2.add(yy2);
						yy2=from[yy2];
					}
					//Print to cycle
					System.out.println("NO");
					out:	
					for(int k=0;k<a1.size();k++){
						for(int k2=0;k2<a2.size();k2++){
							if(a1.get(k)==a2.get(k2)){
								for(int k3=0;k3<=k;k3++){
									a3.add(a1.get(k3));
								System.out.print(a1.get(k3)+" ");}
								for(int k4=k2-1;k4>=0;k4--){
									a3.add(a2.get(k4));
									System.out.print(a2.get(k4)+" ");}
								break out;
							}
						}
					}
					break outerloop;
				}
			}
			//check if there is any vertex has not been visited 
			if(temp.isEmpty()){
				for(int i2=0;i2<=vertex;i2++){
					if(color[i2]==-1){
						temp.add(i2);
						done= 0;

						break;
					}
					
				}
				if(done==1){
				break outerloop;}
				
			}
			result = 1;
		}
		//begin writing
    	BufferedWriter bw = null;
    	FileWriter fw=null;
    	
    	//check the input file name
    	String output = "output";
    	if(path.equals("smallgraph")){
    		output="smallgraph-output";
    	}
    	if(path.equals("largegraph1")){
    		output="largegraph1-output";
    	}
    	if(path.equals("largegraph2")){
    		output="largegraph2-output";
    	}
    	if(path.equals("test")){
    		output="test-output";
    	}
    	File out = new File(output);
    	fw=new FileWriter(out);
    	bw = new BufferedWriter(fw);
    	
    	if(result==1){
			String temps ="";
			bw.write("Yes\n");
			
			for(int i = 1;i<=vertex;i++){
				if(color[i]==1){
					temps="RED";
				}
				else if(color[i]==-2)
					continue;
				else
					temps="BLUE";
				bw.write(i+" "+temps+"\n");
				
				
				}
			}
 
    	if(result==0){
    		bw.write("No\n");
    		for(int k=0;k<a3.size();k++){
    			bw.write(a3.get(k)+" ");
			}
    	}
    	bw.close();	    
    }

}
