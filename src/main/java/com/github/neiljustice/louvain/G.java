package com.github.neiljustice.louvain;

import com.github.neiljustice.louvain.graph.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.neiljustice.louvain.clustering.*;
import com.github.neiljustice.louvain.nmi.*;

public class G {
  public static void main(String[] args) throws FileNotFoundException, IOException {
    G prog = new G();
    prog.run();
  }
  
  public void run() throws FileNotFoundException, IOException {
    
    Graph g = new GraphBuilder().fromFile("input.txt", true);
    LouvainDetector ld = new LouvainDetector(g);
    LayeredCommunityStructure cs = new LayeredCommunityStructure(ld.run());
    
    OutputFinalizer finalizer = new OutputFinalizer("input.txt");
    
    // le deuxième paramètre de finalizeOutput représente l'étape à laquelle vous souhaitez récuperer les clusters. 
    // Avec 1 vous obtiendrez les clusters après la première découpe en cluster.
    finalizer.finalizeOutput("output.csv", 1);
  }
}