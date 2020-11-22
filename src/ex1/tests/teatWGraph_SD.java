package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class teatWGraph_SD {
    private static Random _rnd = null;
    private weighted_graph g;
    @BeforeEach
    @Test
    void createWgraph10() {
        g =graph_creator(10,30,1);
        assertEquals(10,g.nodeSize());
        assertEquals(30,g.edgeSize());
    }
    @Test
    void removeNodeFromGraph() {
        g = graph_creator(10,30,1);
        int e_size = g.edgeSize();
        int v_size =g.nodeSize();
        Iterator<node_info> node = g.getV().iterator();
        node_info n = node.next();
        int ni = g.getV(n.getKey()).size();
        g.removeNode(n.getKey());
        assertEquals(--v_size,g.nodeSize());
        assertEquals(e_size-ni,g.edgeSize());
    }
    @Test
    void removeEdgeFromGraph() {
        g = graph_creator(10, 30, 1);
        int e_size = g.edgeSize();
        Iterator<node_info> node = g.getV().iterator();
        node_info n1 = node.next();
        while (node.hasNext()) {
            node_info n2 = node.next();
            e_size -= (g.hasEdge(n1.getKey(), n2.getKey())) ? 1 : 0;
            g.removeEdge(n1.getKey(), n2.getKey());
            assertEquals(e_size, g.edgeSize());
        }
    }
    @Test
    void testEquals(){
        g = graph_creator(10,30,1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph g1 = ga.copy();
        boolean ans = g1.equals(g);
        assertEquals(true,ans);
    }
    /*
    I took the graph creator from the TA testes because I didn't know how to make a huge connected graph
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }

    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes);
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}