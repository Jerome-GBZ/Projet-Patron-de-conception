# Projet-Patron-de-conception
persistence_g1_3: 6694cec615d0d3612e12b6c0064506453d78894f
## Table des matières
1. [Introduction](#intro)
2. [Développeur](#dev)
3. [Diagrammes](#diagrammes)
4. [Sonar](#sonar)
5. [ToDo / Done](#todo)
6. [Points d'améliorations](#improves)


## Introduction <a id="intro"></a>

Créer un affichage graphique de gestion de forme. Le code doit pouvoir être adaptatif sur le futur. Ajouter de nouvelle forme doit être le plus simple possible. Cela se traduit par la mise en place de 2 patrons de conception :
  1. Visiteur
    Une manière de séparer un algorithme d'une structure de données.
  2. Composite
    L'idée est de manipuler un groupe d'objets de la même façon que s'il s'agissait d'un seul objet.

## Développeurs <a id="dev"></a>
- Jérôme G
- Riad M

## Diagrammes <a id="diagrammes"></a>
Diagramme de classe
![Diagrammes de classe](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_class.png?raw=true)

Diagramme de séquence
![Diagrammes de séquence](https://github.com/Jerome-GBZ/Projet-Patron-de-conception/blob/master/Diagrammes/diag_sequence.png?raw=true)

Le diagramme représente la création d'un carré aux coordonnées (90,90) ainsi qu'un cercle aux coordonnées (120,250). L'utilisateur va grouper ces 2 formes pour en faire qu'une. Puis il va déplacer le groupe en (400,300). Dans cet exemple on partira du principe que l'utilisateur correspond à la classe "JDrawingFrame".

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
