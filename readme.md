# Il me faut de la place  - 2ème partie
**Vivien Louradour - IMT Atlantique FIL A1**  
*Date de rendu : 09.01.2018*  
*`Cette IHM s'appuie sur le code de Baptiste Vrignaud`*  
  
Version minimum java : 8

## Installation
Executer le fichier "il-me-faut-de-la-place.jar" se trouvant à la racine du projet.

## Implémentation
Ceci est le code source de l'IHM, le backend a été développé par Baptiste Vrignaud et est importé sous forme de jar ([lib/Projet_ACDC_IMFDLP.jar](lib/Projet_ACDC_IMFDLP.jar)) et est disponible [ici](https://github.com/batnyu/Projet_ACDC_IMFDLP)

### Liste des fonctionnalités
- Affichage d'une arborescence sous forme d'arbre + tableau détaillé.
- Possibilité de supprimer ou d'ouvrir un fichier ou de copier le contenu d'une cellule via le clique droit dans les tableaux.
- Recherche des doublons à partir d'un répertoire racine (affichage sous forme d'une liste de tableaux, chaque tableau contenant une liste de doublons).
- Appliquer des filtres pour construire l'arborescence et/ou pour la recherche de doublons.
- Fenetre de paramètre. Permet de désactiver le multithread lors du parcours de fichiers.

### Structuration du code
Le code est décomposé en 3 paquets selon la logique de séparation des tâches : 
- View : contient les classes responsables de la création des fenêtres et de l'affichage des données
- Model : Contient principalement la classe AppModel qui permet d'effectuer les appels aux principales méthodes du backend ainsi que d'avertir (via le patron de conception Observer) les classes de changements. 
- Controller : Contient les classes gérant des actions (MouseListener, ActionListener, Observer, ...)
