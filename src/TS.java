import java.io.FileNotFoundException;
import java.util.*;

//Topological Sort
public class TS {
    // No. of vertices
    private int V;

    static Stack<Integer> s;
    static ArrayList<Integer> tsort;
    static ArrayList<ArrayList<Integer>> adj;
    static int[] visited = new int[(int)1e5 + 1];

    // Constructor
    TS(int v)
    {
        V = v;
        adj = new ArrayList<>(v);
        for (int i = 0; i < v; ++i) {
            adj.add(new ArrayList<>());
        }
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) { adj.get(v).add(w); }

    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean[] visited,
                             Stack<Integer> stack)
    {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent
        // to this vertex
        for (Integer integer : adj.get(v)) {
            i = integer;
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        // Push current vertex to stack
        // which stores result
        stack.push(v);
    }

    // The function to do Topological Sort.
    // It uses recursive topologicalSortUtil()
    void topologicalSort()
    {
        Stack<Integer> stack = new Stack<>();

        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Call the recursive helper
        // function to store
        // Topological Sort starting
        // from all vertices one by one
        for (int i = 0; i < V; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);

        // Print contents of stack
        while (!stack.empty())
            System.out.print(stack.pop() + " ");
    }

    static boolean check_cycle(int vertex)
    {

        // Stores the position of
        // vertex in topological order
        Map<Integer, Integer> pos = new HashMap<>();

        int ind = 0;

        // Pop all elements from stack
        while (!s.isEmpty())
        {
            pos.put(s.peek(), ind);

            // get Topological Order
            tsort.add(s.peek());

            ind += 1;

            // Pop from the stack
            s.pop();
        }

        for(int i = 0; i < vertex; i++)
        {
            for(Integer it : adj.get(i))
            {
                if (pos.get(i) > pos.get(it))
                {
                    // Cycle exists
                    return true;
                }
            }
        }
        return false;
    }

    // Function to perform DFS
    static void dfs(int u)
    {

        // Set the vertex as visited
        visited[u] = 1;
        for(Integer it : adj.get(u))
        {
            // Visit connected vertices
            if (visited[it] == 0)
                dfs(it);
        }
        s.push(u);
    }

    public static void main(String[] args) throws FileNotFoundException {
        GraphConstruct graphConstruct = new GraphConstruct("C1000.txt");
        if(graphConstruct.graphType.equals("D")) {

            s = new Stack<>();
            tsort = new ArrayList<>();

            int numberOfVertices = graphConstruct.numberOfVertices;

            List<Integer> startList = graphConstruct.startList;
            List<Integer> endList = graphConstruct.endList;

            TS g = new TS(numberOfVertices);
            for (int i = 0; i < numberOfVertices; i++) {
                g.addEdge(startList.get(i), endList.get(i));
            }
            System.out.println("Following is a Topological sort of the given graph");

            g.topologicalSort();

            for(int i = 0; i < numberOfVertices; i++)
            {
                if (visited[i] == 0)
                {
                    dfs(i);
                }
            }
            System.out.println("\n");
            if (check_cycle(numberOfVertices)) {
                System.out.println("Graph has cycle");
            } else {
                System.out.println("Graph has no cycle");
            }
        } else {
            System.out.println("Expected directed graph");
        }
    }
}
