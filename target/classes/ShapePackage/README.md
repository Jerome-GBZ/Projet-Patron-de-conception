# Dans ce package vous pouvez :
    - Ajouter le package :
```
    import ShapePackage.shapes.ShapesEnum.Shapes;
```
    - Créer des shapes :
```
    import ShapePackage.factories.ShapeFactory;
    import ShapePackage.shapes.ShapesEnum.Shapes;

    private ShapeFactory shapeFac = new ShapeFactory();
    SimpleShape shape = shapeFac.createSimpleShape(type, x, y);
```

    - move shape
```
    shape.moveTo(x, y);
```

    - draw shape
```
    // Nombre correspond à la taille de l'encadré de la forme
    // Utile pour montrer les formes selectionées dans un groupe

    // par default : 0px
    shape.draw(g2, 0);

    // par encadré : 2px
    shape.draw(g2, 2);
```