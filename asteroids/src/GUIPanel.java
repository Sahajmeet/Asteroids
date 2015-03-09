import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
 
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Text;

import javax.swing.*;
 
/*
 * A custom {@link java.applet.Applet Applet} that sets-up a JavaFX environment.
 */

public class GUIPanel extends JApplet {
	
    /*
     * The JavaFX scene.
     * Guaranteed to be <code>non-null</code> when {@link #initApplet()} is called.
     */
	
    protected Scene scene;
    
    /*
     * The JavaFX scene's root node.
     * Guaranteed to be <code>non-null</code> when {@link #initApplet()} is called.
     */
    
    protected Group root;
 
    /*
     * This method is declared final and sets-up the JavaFX environment.
     * In order to add initialization code, override {@link #initApplet()}.
     * <p></p>
     * <b>Original Description:</b> <br>
     * {@inheritDoc}
     */
    
	protected GridPane grid;
	
    @Override
    public final void init() { // This method is invoked when applet is loaded
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initSwing();
            }
        });
    }
 
    private void initSwing() { // This method is invoked on Swing thread
        final JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);
 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
                initApplet();
            }
        });
    }
 
    private void initFX(JFXPanel fxPanel) { // This method is invoked on JavaFX thread
        root = new Group();
        grid = new GridPane();
        scene = new Scene(grid, 900, 600);
        fxPanel.setScene(scene);
        scene.getStylesheets().add
        (GUIPanel.class.getResource("GUIPanel.css").toExternalForm());
    }
 
    /*
     * Add custom initialization code here. <br>
     * This method is called by FXApplet once the {@link java.applet.Applet#init() init} method was invoked (in order
     * to signal that the applet has been loaded) and
     * the {@link #scene scene} & {@link #root root} fields has been set-up.
     * @see java.applet.Applet#init()
     */
    
    public void initApplet() {
    	grid.setAlignment(Pos.CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(30, 30, 30, 30));
   
    	final Text actiontarget = new Text();
    	Text sceneTitle = new Text("Asteroids");
    	Button play = new Button("PLAY");
    	Button easy = new Button("Easy");
    	Button medium = new Button("Medium");
    	Button hard = new Button("Hard");
    	
    	grid.add(actiontarget, 1, 7);
    	grid.add(sceneTitle, 0, 0, 2, 1);
    	grid.add(play, 0,5);
    	grid.add(easy, 2, 5);
    	grid.add(medium, 3, 5);
    	grid.add(hard, 4, 5);
    	
    	actiontarget.setId("actiontarget");
    	sceneTitle.setId("Asteroids-text");
    	play.setId("play-btn");
    	easy.setId("easy-btn");
    	medium.setId("medium-btn");
    	hard.setId("hard-btn");
    	
        this.setSize(900, 600);
    	
        play.setOnAction(new EventHandler<ActionEvent> () {

    		@Override
    		public void handle(ActionEvent arg0) {
    			// TODO Auto-generated method stub

    		}
        		
        });
    	
        easy.setOnAction(new EventHandler<ActionEvent> () {

    		@Override
    		public void handle(ActionEvent arg0) {
    			// TODO Auto-generated method stub
    			actiontarget.setText("Easy Selected");
    		}
        		
        });
    	
        medium.setOnAction(new EventHandler<ActionEvent> () {

    		@Override
    		public void handle(ActionEvent arg0) {
    			// TODO Auto-generated method stub
    			actiontarget.setText("Medium Selected");
    		}
        		
        });
    	
        hard.setOnAction(new EventHandler<ActionEvent> () {

    		@Override
    		public void handle(ActionEvent arg0) {
    			// TODO Auto-generated method stub
    			actiontarget.setText("Hard Selected");
    		}
        		
        });
      
    }
    
}


