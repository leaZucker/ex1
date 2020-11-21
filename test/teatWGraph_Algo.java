
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class teatWGraph_Algo extends teatWGraph_SD {

    private weighted_graph_algorithms ga;

    @Test
    void WgraphCpoy(){
        weighted_graph g = graph_creator(10,9,1);
        ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph copy_ga = ga.copy();

        boolean ans = true;
        for(node_info n: g.getV()){
            if(copy_ga.getV().contains(n)||n.toString().equals(copy_ga.getNode(n.getKey()).toString())){
                ans=false;
            }
            }
        assertEquals(true,ans);
        }

    @Test
    void WgraphConnected10() {
        weighted_graph g = graph_creator(10,30,1);
        ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph g_c = ga.copy();
        boolean ans = ga.isConnected();
        assertEquals(true, ans);
        int k= 0;
        Collection<node_info> Neighbor = g_c.getV(k);
        for(node_info ni:Neighbor)
            g.removeEdge(k,ni.getKey());
        ans = ga.isConnected();
        assertEquals(false, ans);
    }
    @Test
    void WgraphConnected5000(){
        weighted_graph g = graph_creator(5000,30000,1);
        ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph g_c = ga.copy();
        boolean ans = ga.isConnected();
        assertEquals(true, ans);
        Iterator<node_info> Neighbor = g_c.getV().iterator();
        node_info n = Neighbor.next();
        for(node_info ni: g_c.getV(n.getKey()))
            g.removeEdge(n.getKey(),ni.getKey());
        ans = ga.isConnected();
        assertEquals(false, ans);
    }
    @Test
    void WgraphConnected50000(){
        weighted_graph g = graph_creator(50000,902650,1);
        ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph g_c = ga.copy();
        boolean ans = ga.isConnected();
        assertEquals(true, ans);
        int k= 0;
        Collection<node_info> Neighbor = g_c.getV(k);
        for(node_info ni:Neighbor)
            g.removeEdge(k,ni.getKey());
        ans = ga.isConnected();
        assertEquals(false, ans);
    }
    @Test
    void shortestDistance(){
        ga = new WGraph_Algo();
        weighted_graph g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.connect(1,3,1.1);
        g.connect(1,2,3.2);
        g.connect(2,4,1.3);
        g.connect(2,3,2.1);
        g.connect(3,4,1.2);
        g.connect(3,5,3.3);
        g.connect(4,5,5.5);
        g.connect(4,6,2.7);
        g.connect(5,6,1.3);
        g.connect(5,7,1.3);
        g.connect(6,7,5.5);
        g.connect(6,1,2.7);
        ga.init(g);
        Double dist = ga.shortestPathDist(1,7);
        assertEquals(5.3,dist);
    }
    @Test
    void shortestPath(){
        ga = new WGraph_Algo();
        weighted_graph g= new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.connect(1,3,1);
        g.connect(1,2,3);
        g.connect(2,4,1.3);
        g.connect(2,3,2);
        g.connect(3,4,1);
        g.connect(3,5,3.2);
        g.connect(4,5,5);
        g.connect(4,6,2.1);
        g.connect(5,6,1);
        g.connect(5,7,1.1);
        g.connect(6,7,5.3);
        g.connect(6,1,2.2);
        ga.init(g);
        List<node_info> path = ga.shortestPath(1,7);
        assertEquals(4,path.size());
        int[] keys = {1,6,5,7};
        int k =0;
        for(node_info n: path){
            assertEquals(keys[k++],n.getKey());
            }
    }
    @Test
    void testSave(){
        ga = new WGraph_Algo();
        weighted_graph g1= new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.connect(1,3,1);
        g1.connect(1,2,3);
        g1.connect(2,4,1.3);
        g1.connect(2,3,2);
        g1.connect(3,4,1);
        g1.connect(3,5,3.2);
        ga.init(g1);
        ga.save("g1.obj");
        try{
            FileReader f = new FileReader("g1.obg");
            BufferedReader bf = new BufferedReader(f);
            String Load = bf.readLine();
            while (Load != null){
                int key = Integer.parseInt(Load);
                assertEquals(true,g1.getV(key)!=null);
                Load = bf.readLine();
                String[] ni = Load.split(";");
                assertEquals(g1.getV(key).size(),ni.length);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void testLoad(){
        ga = new WGraph_Algo();
        weighted_graph g1= new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.connect(1,3,1);
        g1.connect(1,2,3);
        g1.connect(2,4,1.3);
        g1.connect(2,3,2);
        g1.connect(3,4,1);
        g1.connect(3,5,3.2);
        ga.init(g1);
        ga.save("g1.obj");
        weighted_graph_algorithms ga2 =new WGraph_Algo();
        weighted_graph g2 = new WGraph_DS();
        ga2.init(g2);
        ga2.load("g1.obj");
        assertEquals(g1,g2);
    }
}
