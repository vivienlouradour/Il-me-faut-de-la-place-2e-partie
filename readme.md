# Il me faut de la place  - 2ème partie
**Vivien Louradour - IMT Atlantique FIL A1**  
*Date de rendu : 09.01.2018*  
*`Cette IHM s'appuie sur le code de Baptiste Vrignaud`*  
  
Version minimum java : 8

## Installation
Executer le fichier "il-me-faut-de-la-place.jar" se trouvant à la racine du projet.

## Implémentation
Ceci est le code source de l'IHM, le backend a été développé par Baptiste Vrignaud et est importé sous forme de jar ([lib/Projet_ACDC_IMFDLP.jar](lib/Projet_ACDC_IMFDLP.jar)). Code source github disponible [ici](https://github.com/batnyu/Projet_ACDC_IMFDLP)

### Liste des fonctionnalités
- Affichage d'une arborescence sous forme d'arbre + tableau détaillé.
- Possibilité de supprimer ou d'ouvrir un fichier ou de copier le contenu d'une cellule via le clique droit dans les tableaux.
- Possibilité de trier les fichiers dans les tableaux (Taille, date de modification, nom, etc.)
- Recherche des doublons à partir d'un répertoire racine (affichage sous forme d'une liste de tableaux, chaque tableau contenant une liste de doublons).
- Appliquer des filtres pour construire l'arborescence et/ou pour la recherche de doublons.
- Fenetre de paramètre. Permet de désactiver le multithread lors du parcours de fichiers.

### Structuration du code
Le code est décomposé en 3 paquets selon la logique de séparation des tâches : 
- View : contient les classes responsables de la création des fenêtres et de l'affichage des données
- Model : Contient principalement la classe AppModel qui permet d'effectuer les appels aux principales méthodes du backend ainsi que d'avertir (via le patron de conception Observer) les classes de changements. 
- Controller : Contient les classes gérant des actions (MouseListener, ActionListener, Observer, ...)

### Difficultés et améliorations 
La principale difficulté a été avec la bibliothèque graphique Swing, qui m'a souvent donné du mal.  
Je n'ai pas pu afficher une arborescence suffisemment complete (avec affichage des caractéristiques des fichiers, possibilité de trier, etc.). J'ai donc décider de combiner une arborescence "classique" avec un tableau affichant les détails voulus.       

L'amélioration la plus importante qu'il resterais à faire, c'est de threader les appels aux méthodes de recherche de doublons et de construction d'arbres pour le pas bloquer l'IHM pendant de long temps de traitement. Cependant, le logiciel étant exclusivement fait pour ce traitement, bloquer l'IHM ne me parait pas catastrophique.  
Enfin, j'avais penser utiliser une bibliothèque de création de diagrammes (comme charts4j ou JFreeChart) afin d'afficher des données sous forme de diagrammes ou graphiques (par exemple un camembert représentant le nombre/pourcentage de chaque format de fichier dans l'arborescence). Cependant, j'ai manqué de temps pour le réaliser. 
  

