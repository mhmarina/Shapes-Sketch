//Description: This program allows you to draw various shapes. It also 
//             allows you to undo or erase your drawings.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShapesApp extends Application
{
    public static final int WINSIZE_X = 700, WINSIZE_Y = 600;
    private final String WINTITLE = "Sketchy";

    @Override
    public void start(Stage stage) throws Exception
    {
        SketchPane rootPane = new SketchPane();
        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);
        stage.setTitle(WINTITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
