# Projet-Patron-de-conception
persistence_g1_3: 6694cec615d0d3612e12b6c0064506453d78894f
## Table des matières
1. [Introduction](#intro)
2. [Développeur](#dev)
3. [Conception](#conception)
4. [Diagrammes](#diagrammes)
5. [Sonar](#sonar)
6. [ToDo / Done](#todo)
7. [Points d'améliorations](#improves)


## Introduction <a id="intro"></a>

Créer un affichage graphique de gestion de forme. Le code doit pouvoir être adaptatif sur le futur. Ajouter de nouvelle forme doit être le plus simple possible. Cela se traduit par la mise en place de 4 patrons de conception :
  1. Visiteur
    Une manière de séparer un algorithme d'une structure de données. Nous l'avons utilisé pour ajouter nos formes dans les fichiers XML et JSon durant l'export.
  <br>

  2. Composite
    L'idée est de manipuler un groupe d'objets de la même façon que s'il s'agissait d'un seul objet. Cela nous sert pour faire un groupe de formes qui est déplaçable.
 <br>

  3. Simple Factory
    Le simple factory est la représentation d'une seule méthode dans une seule classe. Nous l'utiliserons pour la création de la forme que l'on souhaite.
  <br>

  4. Commande
    Il permet de séparer complètement le code initiateur de l'action, du code de l'action elle-même. On va s'en servir pour créer des commandes sur notre panel graphique.

## Développeurs <a id="dev"></a>
- Jérôme G
- Riad M

## Conception <a id="conception"></a>
Nous avons fait plusieurs choix de conception durant ce projet. Le premier a été de recentrer le x et y d'une forme pour qu'il correspond au centre de la forme.

Nous avons aussi décidé de ne pas définir de x et y pour un groupe. Pour déplacer un groupe, il faudra cliquer et déplacer une forme du groupe pour déplacer l'ensemble. Le déplacement d'un groupe ce fait à partir la première forme du groupe. Une translation est alors calculée:  **Coordonnées futures - coordonnées première forme du groupe**  <br>
<img src="https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/c21263837f84ea2cedf69ed73efadbecf1c66bc2/src/main/resources/edu/uga/miage/m1/polygons/gui/documentation/translation.png?raw=true" alt="Translation exemple" width="500" style="max-width: 100%;margin-left: calc(50% - 250px);"/>

Un autre choix de conception a été sur la commande undo, nous avons décidé de stocker une liste de commandes réalisée. Avec la position avans l'action et la position après l'action. Cela nous permet de connaitre la position X et Y avant un déplacement. A chaque undo nous supprimons la dernière commande réalisé de la liste.



## Diagrammes <a id="diagrammes"></a>
Diagramme de classe
![Diagrammes de classe](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_class.png?raw=true)

Diagramme de séquence n°1

Dans cet exemple on partira du principe que l'utilisateur correspond à la classe "JDrawingFrame".
Le diagramme représente la création d'un carré aux coordonnées (90,90) ainsi qu'un cercle aux coordonnées (120,250). On va regrouper ces 2 formes pour en faire qu'une. Puis il va déplacer le groupe en (400,300).

![Diagrammes de séquence](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_sequence.png?raw=true)

Etape 1 à 8 : <br>

<img src="https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/target/classes/edu/uga/miage/m1/polygons/gui/documentation/Etape-1_a_8.png?raw=true" alt="Image illustration diagramme" width="500" style="max-width: 100%;margin-left: calc(50% - 250px);"/>

Etape 9 à 25 : <br>

<img src="https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/target/classes/edu/uga/miage/m1/polygons/gui/documentation/Etape-9_a_25.png?raw=true" alt="Image illustration diagramme" width="500" style="max-width: 100%;margin-left: calc(50% - 250px);"/>


Diagramme de séquence n°2

Dans cet exemple on partira du principe que l'utilisateur correspond à la classe "JDrawingFrame".
Le diagramme représente la création d'un cercle aux coordonnées (120,250). Puis on va le déplacer en (200,250). Puis on réaliser un retour en arrière pour retourner à la position avant le déplacement.

![Diagrammes de séquence](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_sequence2.png?raw=true)


## Sonar <a id="sonar"></a>
![Sonar dashboard](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/target/classes/edu/uga/miage/m1/polygons/gui/documentation/Sonar.png?raw=true)


## ToDo / Done <a id="todo"></a>
- [x] Grouper des formes entre elles
- [x] Rendre une forme déplacable
- [x] Rendre un groupe de forme déplacable
- [x] Faire un export de notre figure
  - [x] JSON
  - [x] XML
- [x] Faire un import de fichier
  - [x] JSON
  - [x] XML
- [x] Rendre déplacable un groupe de forme apres import JSON
- [x] Rendre déplacable un groupe de forme apres import XML
- [x] 90% de coverage sur Sonar
- [x] 0min de dettes techniques
- [ ] Déplacer un groupe contenant un groupe + une forme ou 2 groupes (pb de coordoné lors du déplacement)
- [x] 0min de dettes techniques
- [x] cmd-z ou ctrl-z remplacé par un bouton retour en arrière

## Points d'améliorations <a id="improves"></a>
Avec plus de temps on pourrait améliorer le code pour qui match encore plus avec les 4 patrons de conception.
On pourrait aussi redévelopper la partie déplacement de forme. Actuellement à chaque déplacement on repaint toutes la fenêtre. Il faudrait dans le futur repeint seulement la forme ou le groupe de forme.

## Commandes utiles

```
mvn install
```

```
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=persistence_g1_3 \
  -Dsonar.host.url=http://im2ag-sonar.u-ga.fr:9000 \
  -Dsonar.login=6694cec615d0d3612e12b6c0064506453d78894f
```
