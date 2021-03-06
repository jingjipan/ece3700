package lab5.sectionA.JingjiPan.entity;
import lab5.sectionA.JingjiPan.logic.NetworkSimulator;
public class Entity1 extends Entity
{    
    // Perform any necessary initialization in the constructor
    boolean change =false;
    public Entity1()
    {
        distanceTable[1][0]=1;
        distanceTable[1][1]=0;
        distanceTable[1][2]=1;
        distanceTable[1][3]=999;
        Packet p1 = new Packet(1,0,distanceTable[1]);
        Packet p2 = new Packet(1,2,distanceTable[1]);
        NetworkSimulator.toLayer2(p1);
        NetworkSimulator.toLayer2(p2);
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
       int source = p.getSource();
       System.out.printf("A distance table from %1d is received.\r",source);
       for (int i=0;i<4;i++){
           distanceTable[source][i]=p.getMincost(i);
       }
       for (int i=0;i<4;i++){
            if (Math.min(distanceTable[1][i],distanceTable[source][1]+distanceTable[source][i])<distanceTable[1][i]){
                distanceTable[1][i]=distanceTable[source][1]+distanceTable[source][i];
                change=true;
            }
       }
       
       if (change){
            System.out.println("The distance table of 1 is updated.");
            Packet p1 = new Packet(1,0,distanceTable[1]);
            Packet p2 = new Packet(1,2,distanceTable[1]);
            NetworkSimulator.toLayer2(p1);
            NetworkSimulator.toLayer2(p2);
            change=false;
       }
       printDT();
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("         via");
        System.out.println(" D1 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 1)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
            
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
