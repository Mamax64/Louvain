Based on : https://github.com/hwyywh/louvain-1

Java implementation of the Louvain method for community detection.  

Input: weighted, undirected graph, defined in a CSV file as a list of edges.  See the graphs at src/test/resources for examples.  If nodes are named or non-zero indexed they can also be read.

Pas d’installation nécessaire, cloner le répo : 
(Ouvrir le projet eclipse, pas besoin de gradle etc…) 
Mettre le fichier d’input dans le dossier /data dans le projet. Format (csv/txt) : 
Classe1,Classe2,Poids
Il faut aller dans la classe G du package com.github.neiljustice.louvain 
Modifier dans la méthode run() le nom du fichier d’input par le nom du fichier ajouté précédemment ligne 19 et 23.

- Graph g = new GraphBuilder().fromFile(System.getProperty("user.dir") + "\\data\\input.txt", true);
- LouvainDetector ld = new LouvainDetector(g);
- LayeredCommunityStructure cs = new LayeredCommunityStructure(ld.run());

Ces trois lignes permettent de faire le Clustering, et nous donnera un fichier d’output ( output.csv dans le dossier data)
Si les poids dans le fichier d’input ne sont pas adaptés (ici plus le poids est élevé moins les deux classes sont liées, si ce n’est pas le cas sur votre fichier d’input, suivre les instructions suivantes)
-	Aller dans la classe GraphBuilder, ligne 72 : décommenter la ligne permettant d’inverser les poids.
-	Répéter l’opération à la ligne 117.

Il s’agit d’un CSV qui contient plusieurs lignes similaires à celle-ci :
40 :454 : 38 : 2
Chacun des nombres entre les « : » représentent le cluster à laquelle la classe appartient à chaque étape du clustering en Louvain. 
Ainsi cette classe, initialement dans le cluster 40 (puisqu’à la première étape, chaque classe est un cluster), sera durant la 2eme étape dans le cluster 454 puis dans le 38 dans la 3ème étape et enfin dans le cluster 2 dans la 4ème et dernière étape. 

J’ai ensuite implémenté une classe OutputFinalizer qui va permettre de récupérer tous les clusters ainsi que les noms des classes qui les composent à l’étape désirée.
Il faudra appeler la méthode finalizeOutput de cette classe avec en argument le chemin du fichier input de cette méthode (qui correspond au fichier d’output généré après la clusterisation, vu plus haut) ainsi qu’un integer représentant le nombre d’étape (de Louvain) à prendre en compte souhaité. Par exemple, si on indique comme paramètre le chiffre 2, alors la classe du cluster 40 vue plus haut finira dans le cluster 38.
Une fois la transformation de l’ouput finie, vous verrez en console apparaître une array composée d’objet qui sont des clusters et qui contiennent les noms des classes le composant.
