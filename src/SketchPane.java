//Description: This program allows you to draw various shapes. It also 
//             allows you to undo or erase your drawings.

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Scale;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;


public class SketchPane extends BorderPane {
	
	//Task 1: Declare all instance variables listed in UML diagram
		private Color [] colors;
		private String [] colorLabels;
		private Label fillColorLabel;
		private Label strokeColorLabel;
		private Label strokeWidthLabel;
		private String [] strokeWidth;
		
		private ArrayList<Shape> shapeList;
		private ArrayList<Shape> tempList;
		private Button undoButton;
		private Button eraseButton;
		private ComboBox<String> fillColorCombo;
		private ComboBox<String> strokeWidthCombo;
		private ComboBox<String> strokeColorCombo;
		
		private RadioButton radioButtonRectangle;
		private RadioButton radioButtonEllipse;
		private RadioButton radioButtonTriangle;
		
			private Pane sketchCanvas;
		
		private Color currentStrokeColor;
		private Color currentFillColor;
		private int currentStrokeWidth;
		
		private Rectangle rectangle;
		private Ellipse ellipse;
		private Polygon triangle;
		
		private double x1;
		private double x2;
		private double y1;
		
		
	//Task 2: Implement the constructor
	public SketchPane() {
		// Define colors, labels, stroke widths that are available to the user
		colors = new Color[] {Color.BLACK, Color.GREY, Color.YELLOW, Color.GOLD, Color.ORANGE, Color.DARKRED, Color.PURPLE, Color.HOTPINK, Color.TEAL, Color.DEEPSKYBLUE, Color.LIME} ;
		colorLabels = new String[] { "black", "grey", "yellow", "gold", "orange", "dark red", "purple", "hot pink", "teal", "deep sky blue", "lime"};
		fillColorLabel = new Label("Fill Color:");
		strokeColorLabel = new Label("Stroke Color:");
		strokeWidthLabel = new Label("Stroke Width:");
		strokeWidth = new String[] {"1", "3", "5", "7", "9", "11", "13"};  
		
		shapeList = new ArrayList<Shape>();
		tempList = new ArrayList<Shape>();
		
		undoButton = new Button("undo");
			undoButton.setOnAction(new ButtonHandler());
		eraseButton = new Button("erase");
			eraseButton.setOnAction(new ButtonHandler());
		//*** fill color
		fillColorCombo = new ComboBox<String>();
			fillColorCombo.getItems().addAll(colorLabels);
			fillColorCombo.setOnAction(new ColorHandler());
			fillColorCombo.setValue("black");
		//****	width
		strokeWidthCombo = new ComboBox<String>();
			strokeWidthCombo.getItems().addAll(strokeWidth);
			strokeWidthCombo.setOnAction(new WidthHandler());
			strokeWidthCombo.setValue("1");
		//*** stroke color
		strokeColorCombo = new ComboBox<String>();
			strokeColorCombo.getItems().addAll(colorLabels);
			strokeColorCombo.setOnAction(new ColorHandler());
			strokeColorCombo.setValue("black");
		
		//create radio buttons
		radioButtonRectangle = new RadioButton("Rectangle");
		radioButtonTriangle = new RadioButton("Triangle");
		radioButtonEllipse = new RadioButton("Ellipse");
			ToggleGroup toggle = new ToggleGroup();
			radioButtonEllipse.setToggleGroup(toggle);
			radioButtonRectangle.setToggleGroup(toggle);
			radioButtonTriangle.setToggleGroup(toggle);
			radioButtonRectangle.isSelected();
			
		//canvas setup
		sketchCanvas = new Pane();
		sketchCanvas.setBackground((new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));
		
		//combobox HBox
		HBox comboBoxes = new HBox(20.0);
		comboBoxes.setMinSize(20.0, 40.0);
		comboBoxes.setBackground((new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY))));
		comboBoxes.setAlignment(Pos.CENTER);
		comboBoxes.getChildren().addAll(fillColorLabel, fillColorCombo, strokeWidthLabel, strokeWidthCombo, 
										strokeColorLabel, strokeColorCombo);
		
		//radiobuttons HBox
		HBox radioButtons = new HBox(20.0);
		radioButtons.setMinSize(20.0, 40.0);
		radioButtons.setAlignment(Pos.CENTER);
		radioButtons.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		radioButtons.getChildren().addAll(radioButtonRectangle, radioButtonEllipse, radioButtonTriangle, undoButton, eraseButton);
		
		//the order matters here. ensures canvas at the bottom layer
		setCenter(sketchCanvas);
		setTop(comboBoxes);
		setBottom(radioButtons);
		
		//set defaults
		x1 = 0;
		y1 = 0;
		currentStrokeColor = Color.BLACK;
		currentFillColor = Color.BLACK;
		currentStrokeWidth = 1;

		//register mouse events
		sketchCanvas.setOnMouseReleased(new MouseHandler());
		sketchCanvas.setOnMouseDragged(new MouseHandler());
		sketchCanvas.setOnMousePressed(new MouseHandler());
		

	}

	//mousehandler for mouse events
	private class MouseHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// TASK 3: Implement the mouse handler for Triangle and Ellipse
			
			//**********************************************
			//if ellipse is selected 
			if (radioButtonEllipse.isSelected()) {
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					ellipse = new Ellipse();
					ellipse.setCenterX(x1);
					ellipse.setCenterY(y1);
					shapeList.add(ellipse);
					ellipse.setFill(Color.WHITE);
					ellipse.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(ellipse);
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					double x2 = event.getX();
					double y2 = event.getY();
					ellipse.setRadiusX(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
					ellipse.setRadiusY(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))/2);

				}
				//If the Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					ellipse.setFill(currentFillColor);
					ellipse.setStroke(currentStrokeColor);
					ellipse.setStrokeWidth(currentStrokeWidth);
				}
			}//end of ellipse
			
			//If triangle is selected
			else if (radioButtonTriangle.isSelected()) {
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					triangle = new Polygon();
					triangle.getPoints().addAll(x1, y1);
					shapeList.add(triangle);
					triangle.setStrokeWidth(currentStrokeWidth/200.0);
					triangle.setFill(Color.WHITE);
					triangle.setStroke(Color.BLACK);
					sketchCanvas.getChildren().add(triangle);
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					x2 = event.getX();
					double y2 = event.getY();
					triangle.getPoints().clear();
					triangle.getPoints().addAll(x1, y1, x2, y2, (x1-(x2-x1)), y2);
					triangle.setStrokeWidth(currentStrokeWidth);
				}
				//If the Mouse is released 
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					triangle.setFill(currentFillColor);
					triangle.setStroke(currentStrokeColor);
					triangle.setStrokeWidth(Math.abs(currentStrokeWidth));
				}
			} //end of triangle
				
			//***********************
				
			// Rectangle Example given!
			//If user chooses Rectangle
				else if (radioButtonRectangle.isSelected()) {
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					rectangle = new Rectangle();
					rectangle.setX(x1);
					rectangle.setY(y1);
					shapeList.add(rectangle);
					rectangle.setFill(Color.WHITE);
					rectangle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(rectangle);
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					rectangle.setWidth(Math.abs(event.getX() - x1));
					rectangle.setHeight(Math.abs(event.getY() - y1));

				}
				//If the Mouse is released 
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					rectangle.setFill(currentFillColor);
					rectangle.setStroke(currentStrokeColor);
					rectangle.setStrokeWidth(currentStrokeWidth);
				}
			} //end of rectangle
		}
	}
	
//Undo and Erase Button Handlers. 	
	

	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
						
			
			if (event.getSource() == undoButton) {
				//If canvas is not empty. Undo last action
				if(shapeList.isEmpty() == false) {
					shapeList.remove(shapeList.size()-1);
					sketchCanvas.getChildren().clear();
					for(int i = 0; i < shapeList.size(); i++) {
					sketchCanvas.getChildren().addAll(shapeList.get(i));
					}
				}
				
				//If canvas IS Empty after erase. Undo brings back all elements before
					else {
						for(int i = 0; i < shapeList.size(); i++) {
							Shape s = shapeList.get(i);
							tempList.add(s);
						}
						shapeList.clear();
						for(int i = 0; i < tempList.size(); i++) {
						Shape s = tempList.get(i);
						shapeList.add(s);
						}
						for(int i = 0; i < shapeList.size(); i++) {
							sketchCanvas.getChildren().addAll(shapeList.get(i));
							}
					}
				}
			//ERASE Button Handler
			else if(event.getSource() == eraseButton) {
				if(shapeList.isEmpty() == false) {
					tempList.clear();
					for(int i = 0; i < shapeList.size(); i++) {
						Shape s = shapeList.get(i);
						tempList.add(s);
						}
					shapeList.clear();
					sketchCanvas.getChildren().clear();
					
				}
				
			}
			
			//add shapes to tempList in case a redo happens
			else {
				for(int i = 0; i < shapeList.size(); i++) {
					Shape s = shapeList.get(i);
					tempList.add(s);
					}
			}
			
		}
	}
	


	private class ColorHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// TASK 5: Implement the color handler		
//			colorLabels = new String[] { "black", "grey", "yellow", "gold", "orange", "dark red",
//			"purple", "hot pink", "teal", "deep sky blue", "lime"
			
			//select fill color from combo box
			if(event.getSource() == fillColorCombo) {
			for(int i = 0; i < colorLabels.length; i++) {
				if(fillColorCombo.getSelectionModel().getSelectedIndex() == i) {
					currentFillColor = colors[i];
				}
			}
		}
			
			//assign currentStrokeColor with selected color
			else if(event.getSource() == strokeColorCombo) {
				for(int i = 0; i < colorLabels.length; i++) {
					if(strokeColorCombo.getSelectionModel().getSelectedIndex() == i) {
						currentStrokeColor = colors[i];
				}
			}
		}
        }
	}

	private class WidthHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event){
			// TASK 6: Implement the stroke width handler
			//The stroke width selected by the user is parsed as Integer and assigned to the
			//variable currentStrokeWidth.
			String s = strokeWidthCombo.getValue();
			int j = Integer.parseInt(s);
			currentStrokeWidth = j;
		}
	}
	
	// Get the Euclidean distance between (x1,y1) and (x2,y2)
    private double getDistance(double x1, double y1, double x2, double y2)  {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
