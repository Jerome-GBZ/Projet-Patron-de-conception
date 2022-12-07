# Dans ce package vous pouvez :
    - Ajouter le package :
```
    import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;
```
    - Créer des shapes :
```
    import ShapePackage.factories.ShapeFactory;
    private ShapeFactory shapeFac = new ShapeFactory();
    shapeFac.createSimpleShape(type, x, y);

    // type :
    public enum Shapes {
        SQUARE,
        TRIANGLE,
        CIRCLE,
        FIGMA,
        COMPOUNDSHAPE
    }
```
