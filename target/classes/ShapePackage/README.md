# Dans ce package vous pouvez :
    - Ajouter le package :
```
    import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;
```
    - Créer des shapes :
```
    import ShapePackage.factories.ShapeFactory;
    private ShapeFactory shapeFac = new ShapeFactory();
    SimpleShape shape = shapeFac.createSimpleShape(type, x, y);

    // type :
    public enum Shapes {
        SQUARE,
        TRIANGLE,
        CIRCLE,
        FIGMA,
        COMPOUNDSHAPE
    }
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