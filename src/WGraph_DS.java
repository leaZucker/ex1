import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node_info> WgraphInfo;
    private HashMap<Integer, neighbors> Neighbor;
    private int e_size, MC;

    /**
     * create new graph  weighted undirected graph
     */
    public WGraph_DS() {
        this.WgraphInfo = new HashMap<>();
        this.Neighbor = new HashMap<>();
        this.e_size = 0;
        this.MC = 0;
    }

    /**
     * get node from the graph
     * if the key not in the graph return null
     *
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        if (WgraphInfo.containsKey(key))
            return WgraphInfo.get(key);
        return null;
    }

    /**
     *  returen true if key node1 has key node2 neighbor
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (node1 == node2)
            return false;
        if(WgraphInfo.containsKey(node1)&&WgraphInfo.containsKey(node2))
            return Neighbor.get(node1).hasNi(node2);
        return false;
    }

    /**
     * return the weight of the edge -1 if don't as an edge
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2))
            return Neighbor.get(node1).getNi(node2).getTag();
        return -1;
    }

    /**
     * add new key to the graph
     * if the graph contains the key does nothing
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!WgraphInfo.containsKey(key)) {
            node_info node_k = new NodeInfo(key);
            neighbors ni = new neighbors(key);
            WgraphInfo.put(key, node_k);
            Neighbor.put(key, ni);
            MC++;
        }
    }
    /**
     *create new weighted edge between two exists vertex
     *if the edge exist do nothing
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (WgraphInfo.containsKey(node1) && WgraphInfo.containsKey(node2)) {
            if (node1 != node2 && !hasEdge(node1, node2)) {
                Neighbor.get(node1).addNi(node2, w);
                Neighbor.get(node2).addNi(node1, w);
                e_size++;
                MC++;
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return WgraphInfo.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if(Neighbor.containsKey(node_id))
            return Neighbor.get(node_id).getNi();
        return null;
    }

    /**
     * remove vertex and his edges from the graph by key
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        if (WgraphInfo.containsKey(key)) {
            node_info node_R = WgraphInfo.get(key);
            for (node_info ni : Neighbor.get(key).getNi()) {
                Neighbor.get(ni.getKey()).removeNi(key);
                e_size--;
            }
            WgraphInfo.remove(key);
            Neighbor.remove(key);
            MC++;
            return node_R;
        }
        return null;
    }

    /**
     * remove edge between two vertexes
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            Neighbor.get(node1).removeNi(node2);
            Neighbor.get(node2).removeNi(node1);
            e_size--;
            MC++;
        }
    }

    @Override
    public int nodeSize() {
        return WgraphInfo.size();
    }

    @Override
    public int edgeSize() {
        return this.e_size;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * check if two weighted graph are equals by vertexes keys and the edges weight
     * @param Wgraph
     * @return
     */
    @Override
    public boolean equals(Object Wgraph) {
        weighted_graph other = (weighted_graph) Wgraph;
        boolean ans = true;
        for(node_info node : this.getV()){
             for(node_info ni : this.getV(node.getKey())){
                 if(other.hasEdge(node.getKey(),ni.getKey())&&this.hasEdge(node.getKey(),ni.getKey())){
                     if(other.getEdge(node.getKey(),ni.getKey()) != this.getEdge(node.getKey(),ni.getKey()))   {
                        ans =false;
                        break;
                     }
                 }
                 else
                     {
                     ans = false;
                     break;
                 }
             }
        }
         return ans;
    }


    private class NodeInfo implements node_info, Comparable<node_info> {
        private int _key;
        private String info;
        private double Tag;
        private int num = 0;

        /**
         * create new node info with random key
         */
        public NodeInfo() {
            this._key = num++;
            this.Tag = 0;
            this.info = "vertex: " + _key;
        }

        /**
         * create new node with given key
         * @param key
         */
        public NodeInfo(int key) {
            this();
            this._key = key;
        }

        @Override
        public int getKey() {
            return this._key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * return info a associated with this node
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * return temporal tag for algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return this.Tag;
        }

        /**
         * set new tag for algorithms
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.Tag = t;
        }

        /**
         * compare between two node info by the tag
         * @param o
         * @return
         */
        @Override
        public int compareTo(node_info o) {
            int ans = 0;
            if (this.Tag - o.getTag() > 0) ans = 1;
            else if (this.Tag - o.getTag() < 0) ans = -1;
            return ans;
        }
    }

    /*
    private class to keep for each vertex his edges and their weight
     */
    private class neighbors {
        private int _key;
        private HashMap<Integer, node_info> nei;

        /**
         * create new collection of neighbors with unique key
         * @param key
         */
        public neighbors(int key) {
            this._key = key;
            this.nei = new HashMap<>();
        }

        public int get_key() {
            return _key;
        }

        /**
         * add new node info neighbor by key
         * @param key
         * @param w
         */
        public void addNi(int key, double w) {
            if (this._key != key && !nei.containsKey(key)) {
                node_info ni = new NodeInfo(key);
                ni.setTag(w);
                nei.put(key, ni);
            }
        }

        /**
         * remove neighbor by key
         * @param key
         */
        public void removeNi(int key) {
            if (nei.containsKey(key))
                nei.remove(key);
        }

        /**
         * return the node info associated with key
         * @param key
         * @return
         */
        public node_info getNi(int key) {
            if (nei.containsKey(key))
                return nei.get(key);
            return null;
        }

        /**
         * return if this node info key as a neighbor with the given key
         * @param key
         * @return
         */
        public boolean hasNi(int key) {
            return nei.containsKey(key);
        }

        public int size() {
            return nei.size();
        }

        public Collection<node_info> getNi() {
            return nei.values();
        }
    }

    public static class InfoComparator implements Comparator<node_info> {
        /**
         * return 0 if the tag is equal
         * return 1 if o1 tag is bigger then o2 tag
         * return -1 if o2 tag is bigger then o1 tag
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(node_info o1, node_info o2) {
            if (o1.getTag() - o2.getTag() > 0) return 1;
            if (o1.getTag() - o2.getTag() < 0) return -1;
            return 0;
        }

    }

}