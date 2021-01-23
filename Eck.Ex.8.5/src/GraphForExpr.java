import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/** Eck Exercise 8.5
 * Create GUI version of ex 8.4
 * Text box to enter function string
 * Click button/press enter to evaluate
 * Display graph of function x: -5,+5; y: -5,+5
 * 
 * User enters a function string into text box, pressed enter or clicks button
 * Convert string to expr object
 * 	if expr is valid
 * 		draw plot
 * 	else
 * 		update message on canvas
 * 		refocus to text field
 * 
 * to draw plot:
 * 	draw white square
 * 	draw axes on bottom & left of square between -5:+5 x & y
 * 	create x & y arrays for data point pairs from function evaluation
 * 	draw curve:
 * 		Let i = data point index: loop between 0 & length-1
 * 			if y[i] != NaN && y[i+1] != NaN
 * 				draw line xi,yi to xi+1 xi+1 
 * 	NOTE:
 * 	Eck's solution uses a subclass GraphCanvas extends canvas
 * 	This has Expr attributes and methods to setFunction drawFunction, draw axes etc...
 * 	
 * @author Joel
 *
 */


public class GraphForExpr extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private Canvas board;     // The canvas on which plot axes & curve is drawn.
	private GraphicsContext g;
	private String message = "Enter a function in the text box";

	private double boardWidth = 500;
	private double boardHeight = 500;

	// Definition of plot dimensions
	private double xMargin = 5;
	private double yMargin = 5;
	private double plotWidth = boardWidth - xMargin - 5;
	private double plotHeight = boardHeight - 50  - yMargin -5;

	// Plot ranges (equal x & y)
	private double xMin = -5;
	private double xRange = (-xMin)*2; // Ensure centred on 0


	// uicontrols
	private Button drawPlotButton;
	private TextField funcStrTextBox;	

	// func variables
	private Expr funcExpr;
	boolean funcLegal = false;

	public void start(Stage stage){

		board = new Canvas(boardWidth, boardHeight); 
		// space for 5 cards, 2 rows, with 20-pixel border
		// and space on the bottom for a message
		g = board.getGraphicsContext2D();

		drawPlotButton = new Button("Draw Plot");
		drawPlotButton.setDefaultButton(true);
		drawPlotButton.setOnAction( e -> doPlotDraw() );
		Label funcLabel = new Label("Enter function:");
		funcStrTextBox = new TextField();
		funcStrTextBox.setPrefWidth(300);
		//		funcStrTextBox.setOnKeyPressed(e-> getFunc(e));

		HBox buttonBar = new HBox(15, funcLabel, funcStrTextBox, drawPlotButton );
		buttonBar.setPrefWidth(board.getWidth());
		buttonBar.setAlignment(Pos.CENTER);

		/* Improve the layout of the buttonBar. Without adjustment
		 * the buttons will be grouped at the left end of the
		 * HBox.  My solution is to set their preferred widths
		 * to make them all the same size and make them fill the
		 * entire HBox.  */

		BorderPane root = new BorderPane();
		root.setCenter(board);
		root.setBottom(buttonBar);
		resetPlot();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Plot a function");
		stage.setResizable(false);
		stage.show();

	} // end start


	/*
	 * 
	 */
	private void getFunc() {
		String funcStr = funcStrTextBox.getText();

		try {
			funcExpr = new Expr(funcStr);
			message = String.format("f(x) = %s", funcExpr.toString());
			funcLegal = true;
			resetPlot(); // reset plot if successful
		} catch(IllegalArgumentException e) {
			drawBackground();
			message = String.format("Couldn't parse function:\n%s\nTry again.",e.getMessage());
			g.setFill(Color.BLACK);
			g.fillText(message,20,boardHeight-35);
			funcStrTextBox.requestFocus();
			funcLegal = false;
		} // funcLegal try catch		

	}


	/* Convert string to expr object
	 * 	if expr is valid
	 * 		draw plot
	 * 	else
	 * 		update message on canvas
	 * 		refocus to text field
	 * 
	 * to draw plot:
	 * 	draw white square
	 * 	draw axes on bottom & left of square between -5:+5 x & y
	 * 	create x & y arrays for data point pairs from function evaluation
	 * 	draw curve:
	 * 		Let i = data point index: loop between 0 & length-1
	 * 			if y[i] != NaN && y[i+1] != NaN
	 * 				draw line xi,yi to xi+1 xi+1 
	 */ 		
	private void doPlotDraw() {
		getFunc();
		if (!funcLegal) { // if function isn't legal then don't do the rest.
			g.fillText(message,20,boardHeight-35);
			return;
		}
		g.setFill(Color.BLACK);
		g.fillText(message,20,boardHeight-35);
		
		// plot points
		int nPoints = 201;
		double[] x = new double[nPoints];
		double[] y = new double[nPoints];
		// Populate x & y arrays
		for (int i = 0; i < nPoints; i++) {
			x[i] = (double)(i)*(xRange/(nPoints-1)) + xMin;
			y[i] = funcExpr.value(x[i]);			
		} // end for nPoints
		
		// Draw lines
		double xpos0, ypos0, xpos1, ypos1;
		g.setStroke(Color.RED);
		for (int i = 0; i < nPoints - 1; i ++) {
			
			if ( Double.isNaN(y[i]) || Double.isNaN(y[i+1]) ) {
				// only draw if both point non-NaN
			} else {
				xpos0 = xval2xpos(x[i]);
				ypos0 = yval2ypos(y[i]);
				xpos1 = xval2xpos(x[i+1]);
				ypos1 = yval2ypos(y[i+1]);
				g.strokeLine(xpos0, ypos0, xpos1, ypos1);
			} // end if NaN check
		} // end draw lines
		
		
	}

	private double xval2xpos(double xval) {
		double xpos = (xval + (xMin + xRange))/xRange * plotWidth + xMargin;
		return xpos;
	}

	private double yval2ypos(double yval) {
		double ypos = ((xMin + xRange) - yval)/xRange * plotHeight + yMargin;
		return ypos;
	}

	/* 
	 * Draw blank square and axes
	 */
	private void resetPlot() {
		// draw background
		drawBackground();
		// draw blank plot space
		g.setFill(Color.WHITE);
		g.fillRect(xMargin,yMargin, plotWidth, plotHeight);
		
		// draw axes at centre
		double x0 = xMargin + plotWidth/2;
		double y0 = yMargin + plotHeight/2;
		g.setStroke(Color.BLACK);
		g.strokeLine(x0,yMargin,x0,yMargin + plotHeight);
		g.strokeLine(xMargin,y0,xMargin + plotWidth,y0);

		g.setStroke(Color.BLACK);
		g.setFill(Color.BLACK);
		double xTickLength = 5; 
		double yTickLength = 5; 
		double xpos, ypos;
		for (int x = (int)xMin ; x < (int)(xMin + xRange) + 1; x++ ) {
			// draw tick on x-axis at position x
			if ( x != 0 ) {
				xpos = xval2xpos((double)x);
				ypos = yval2ypos((double)x);
				// draw ticks
				g.strokeLine(xpos,y0-xTickLength,xpos,y0+xTickLength);
				g.strokeLine(x0-yTickLength,ypos,x0+yTickLength,ypos);
				//draw tick labels
				g.fillText(Integer.toString(x),xpos-5,y0+xTickLength+15);
				g.fillText(Integer.toString(x),x0+yTickLength-25,ypos+5);
			}
		}
	} // end resetPlot
		
	private void drawBackground() {
		g.setFill(Color.LIGHTGRAY);
		g.fillRect(0,0,boardWidth, boardHeight);
		
	}
	
} // end Class GraphForExpr
