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

Créer un affichage graphique de gestion de forme. Le code doit pouvoir être adaptatif sur le futur. Ajouter de nouvelle forme doit être le plus simple possible. Cela se traduit par la mise en place de 2 patrons de conception :
  1. Visiteur
    Une manière de séparer un algorithme d'une structure de données.
  2. Composite
    L'idée est de manipuler un groupe d'objets de la même façon que s'il s'agissait d'un seul objet.

## Développeurs <a id="dev"></a>
- Jérôme G
- Riad M

## Conception <a id="conception"></a>
Nous avons fait plusieurs choix durant ce projet. Le premier est de recentrer le x et y d'une forme qui est dans notre cas au centre de la forme. Nous avons aussi décidé de ne pas définir de x et y pour un groupe. Pour déplacer un groupe il faut cliquer et déplacer une forme du groupe pour déplacer l'ensemble.

## Diagrammes <a id="diagrammes"></a>
Diagramme de classe
![Diagrammes de classe](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_class.png?raw=true)

Diagramme de séquence

Dans cet exemple on partira du principe que l'utilisateur correspond à la classe "JDrawingFrame".
Le diagramme représente la création d'un carré aux coordonnées (90,90) ainsi qu'un cercle aux coordonnées (120,250). On va regrouper ces 2 formes pour en faire qu'une. Puis il va déplacer le groupe en (400,300).

![Diagrammes de séquence](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_sequence.png?raw=true)

Etape 1 à 8 :
![Image illustration diagramme](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/Etape-1_a_8.png?raw=true)
Etape 9 à 25 :
![Image illustration diagramme](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/Etape-9_a_25.png?raw=true)

## Sonar <a id="sonar"></a>
![Sonar dashboard](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/Sonar.png?raw=true)


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
- [ ] Rendre déplacable un groupe de forme apres import XML
- [ ] 80% de coverage sur Sonar (actuellement 40%)
- [x] 0min de dettes techniques
- [ ] Déplacer un groupe contenant un groupe + une forme ou 2 groupes (pb de coordoné lors du déplacement)

## Points d'améliorations <a id="improves"></a>
Avec plus de temps on pourrait améliorer le code pour qui match encore plus avec les 2 patrons de conception. On pourrait aussi redévelopper la partie déplacement de forme. Actuellement à chaque déplacement on repaint toutes la fenêtre. Il faudrait dans le futur repeint seulement la forme ou le groupe de forme.

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
