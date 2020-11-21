import java.io.*;
import java.util.*;


public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph Wgraph_a;

    /**
     * create new weight graph algorithms
     */
    public WGraph_Algo(){
        Wgraph_a = new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) {
        Wgraph_a = g;
    }

    @Override
    public weighted_graph getGraph() {
        return Wgraph_a;
    }
    /**
     * make deep copy to the graph
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph Wgraph_c = new WGraph_DS();
        for(node_info node: Wgraph_a.getV()){
            Wgraph_c.addNode(node.getKey());
                for(node_info ni: Wgraph_a.getV(node.getKey())){
                    Wgraph_c.addNode(ni.getKey());
                    Wgraph_c.connect(node.getKey(), ni.getKey(), ni.getTag());
                }
            }
        return Wgraph_c;
    }

    /**
     * check if weighted underacted graph is connected with dijkstra's algorithm O(|E|+|V|)
     * @return
     */
    @Override
    public boolean isConnected() {
        if (Wgraph_a.getV().size() < 2)
            return true;
        for (node_info node : Wgraph_a.getV()) {
            node.setTag(Double.MAX_EXPONENT);
        }
        PriorityQueue<node_info> frontier = new PriorityQueue<>();
        Iterator<node_info> next = Wgraph_a.getV().iterator();
        node_info nxt = next.next();
        nxt.setTag(0);
        frontier.add(nxt);
        HashSet<Integer> out_nodes =new HashSet<>();
        while (!frontier.isEmpty()) {
            nxt = frontier.poll();
            for (node_info ni : Wgraph_a.getV(nxt.getKey())) {
                    double t = nxt.getTag() + ni.getTag();
                    if (t < Wgraph_a.getNode(ni.getKey()).getTag()) {
                        Wgraph_a.getNode(ni.getKey()).setTag(t);
                        frontier.add(Wgraph_a.getNode(ni.getKey()));
                    }

            }
            if(!out_nodes.contains(nxt.getKey()))
                out_nodes.add(nxt.getKey());
        }
        boolean connect = true;
        if (out_nodes.size() != Wgraph_a.nodeSize()) {
            connect = false;
       }
        return connect;
    }
    /**
     * check the shortest weight distance from source node to the destination node with dijkstra's algorithm O(|E|+|V|)
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
     @Override
    public double shortestPathDist(int src, int dest) {
        if (Wgraph_a.getV().size() < 2)
            return 0;
        for (node_info node : Wgraph_a.getV()) {
            node.setTag(Double.MAX_EXPONENT);
        }
        PriorityQueue<node_info> frontier = new PriorityQueue<>();
        node_info nxt = Wgraph_a.getNode(src);
        nxt.setTag(0.0);
        frontier.add(nxt);
        HashSet<node_info> out_nodes = new HashSet<>();
        while (!frontier.isEmpty()) {
            nxt = frontier.poll();
            if (nxt.getKey() != dest) {
                for (node_info ni : Wgraph_a.getV(nxt.getKey())) {
                    if (!out_nodes.contains(ni.getKey())) {
                        double t = nxt.getTag() + ni.getTag();
                        if (t < Wgraph_a.getNode(ni.getKey()).getTag()) {
                            Wgraph_a.getNode(ni.getKey()).setTag(t);
                            frontier.add(Wgraph_a.getNode(ni.getKey()));
                        }
                    }
                }
            }
             else {
                    out_nodes.add(nxt);
                    break;
                }
            out_nodes.add(nxt);
        }
        if (!out_nodes.contains(Wgraph_a.getNode(dest)))
            return -1;
        return Wgraph_a.getNode(dest).getTag();
    }
    /**
     * check the shortest weight path from source node to the destination node with dijkstra's algorithm O(|E|+|V|)
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> path = new LinkedList<>();
        HashMap<Integer, node_info> parent = new HashMap<>();
        if (src == dest)
            return path;
        for (node_info node : Wgraph_a.getV()) {
            node.setTag(Double.MAX_EXPONENT);
        }
        PriorityQueue<node_info> frontier = new PriorityQueue<>();
        node_info nxt = Wgraph_a.getNode(src);
        nxt.setTag(0);
        frontier.add(nxt);
        HashSet<node_info> out_nodes = new HashSet<>();
        while (!frontier.isEmpty()) {
            nxt = frontier.poll();
            if (nxt.getKey() != dest) {
                for (node_info ni : Wgraph_a.getV(nxt.getKey())) {
                        double t = nxt.getTag() + Wgraph_a.getEdge(nxt.getKey(),ni.getKey());
                        if (t < Wgraph_a.getNode(ni.getKey()).getTag()) {
                            Wgraph_a.getNode(ni.getKey()).setTag(t);
                            frontier.add(Wgraph_a.getNode(ni.getKey()));
                            if(parent.containsKey(ni.getKey()))
                                parent.replace(ni.getKey(), nxt);
                            else
                                parent.put(ni.getKey(),nxt);
                    }
                }
            }
            else {
                out_nodes.add(nxt);
                break;
            }
            out_nodes.add(nxt);
        }
        nxt = Wgraph_a.getNode(dest);
        node_info stop = Wgraph_a.getNode(src);
        path.add(nxt);
        if(out_nodes.contains(nxt)) {
            while (nxt != null && nxt != stop) {
                node_info p = parent.get(nxt.getKey());
                int k= p.getKey();
               double u = p.getTag();
                path.add(p);
                nxt = p;
            }
        }
        Comparator<node_info> comper = new WGraph_DS.InfoComparator();
        path.sort(comper);
        return path;
    }

    /**
     * save info of the graph
     * the format:
     * "key
     * ni-key,ni-weight;ni-key,ni-weight;....."
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        File f = new File(file);
        FileWriter w = null;
       if(!f.exists()) {
            try {
                w = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(w);
                for (node_info node : Wgraph_a.getV()) {
                    bw.write("" + node.getKey()+"\n");
                    String Save = "";
                    Iterator<node_info> neighbor = Wgraph_a.getV(node.getKey()).iterator();
                    while (neighbor.hasNext()) {
                        node_info ni = neighbor.next();
                        Save += ""+ ni.getKey() + "," + ni.getTag()+ "";
                        Save += (neighbor.hasNext())?";":"\n";

                    }
                    bw.write(Save);
                }
                bw.close();
            }
             catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    /**
     * load info from file to the graph
     * the format:
     * "key
     * ni-key,ni-weight;ni-key,ni-weight;....."
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean load(String file) {
        File f = new File(file);
        String Load ="";
        if(f.exists()){
            try{
                BufferedReader r = new BufferedReader(new FileReader(f));
                while((Load = r.readLine())!= null){
                    Integer key = Integer.parseInt(Load);
                    Wgraph_a.addNode(key);
                    Load = r.readLine();
                    String[] neighbor = Load.split(";");
                    for(String str :neighbor){
                        String[] info = str.split(",");
                        Double weight = Double.parseDouble(info[1]);
                        Integer niKey = Integer.parseInt(info[0]);
                        if(Wgraph_a.getNode(niKey) != null)
                            Wgraph_a.connect(key,niKey,weight);
                        else{
                            Wgraph_a.addNode(niKey);
                            Wgraph_a.connect(key,niKey,weight);
                        }
                    }
                }
                r.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
