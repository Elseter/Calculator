package example.javafxprojects;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class CalcControlRVK extends Application {
    // private variables used to move window location
    private double xOffset = 0;
    private double yOffset = 0;


    public Pane creatButtons(String s1){
        Pane button = new Pane();
        button.setId(s1);
        button.setStyle("-fx-background-color: #00000025;" +
                "-fx-background-radius: 20;");
        Label buttonLabel = new Label(s1);
        buttonLabel.setTextFill(Color.WHITE);
        buttonLabel.setAlignment(Pos.CENTER);
        buttonLabel.setFont(Font.font("arial", 36));
        buttonLabel.setMinSize(75, 75);
        button.getChildren().add(buttonLabel);
        return button;
    }
    public void addHoverListener(Pane button){
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle("-fx-background-color: #00000050;" +
                        "-fx-background-radius: 20;");
            } else {
                button.setStyle("-fx-background-color: #00000025;" +
                        "-fx-background-radius: 20;");
            }
        });
    }
    public void addPressListenerNUMPAD(Pane button, CalcRVK calc) {
        button.setOnMouseClicked(mouseEvent -> {
            String value = button.getId().replaceAll("button", "");
            switch (value) {
                case "DOT" -> calc.appendCurrentDisplay(".");
                case "+/-" -> {
                    calc.appendCurrentDisplay("-");
                    calc.displayCurrentDisplay();
                }
                default -> {
                    calc.appendCurrentDisplay(value);
                    calc.displayCurrentDisplay();
                }
            }
        });
    }
    public void addClearListener(Pane button, CalcRVK calc){
        button.setOnMouseClicked(mouseEvent -> {
            calc.resetVisible("", "0.0");
            calc.setOperator(".");
        });
    }
    public void addActionListener(Pane button, CalcRVK calc){
        button.setOnMouseClicked(mouseEvent -> {
            String value = button.getId().replaceAll("button", "");
            switch (value){
                case"%" -> calc.modulus();
                case"=" ->{
                    calc.getCurrent();
                    switch(calc.operator){
                        case "+" -> calc.addition();
                        case "-" -> calc.subtraction();
                        case "*" -> calc.multiplication();
                        case "/" ->calc.division();
                    }
                    calc.displayCurrentDisplay();
                    calc.setOperator(".");
                }
                case"÷" -> {
                    calc.setOperator("÷");
                    calc.setStored();
                    calc.resetVisible();
                    button.setStyle("-fx-background-color: #00000075;" +
                            "-fx-background-radius: 20;");
                }
                case"×" ->{
                    calc.setOperator("×");
                    calc.setStored();
                    calc.resetVisible();
                    button.setStyle("-fx-background-color: #00000075;" +
                            "-fx-background-radius: 20;");
                }
                case"-" ->{
                    calc.setOperator("-");
                    calc.setStored();
                    calc.resetVisible();
                    button.setStyle("-fx-background-color: #00000075;" +
                            "-fx-background-radius: 20;");}
                case"+" ->{
                    calc.setOperator("+");
                    calc.setStored();
                    calc.resetVisible();
                    button.setStyle("-fx-background-color: #00000075;" +
                            "-fx-background-radius: 20;");}
                default -> System.out.println("If you got here, you got issue");

            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

        //----------------------------------------------------------------------------
        // Setting Background and Colors

        // Set absolute bottom level pane, which all future panes will be attached to
        // This is also what the final scene will use as a base
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-radius: 20;" +
                "-fx-background-color: rgb(45, 45, 50), rgb(60, 60, 65);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        root.setPadding(new Insets(30, 5, 5, 5));

        // This is the pane which will dictate the background colors using setStyle
        // As a stand-in for proper css
        BorderPane background = new BorderPane();
        background.setStyle("-fx-background-radius: 20;" +
                "-fx-background-color: linear-gradient(to bottom right, #FF7F50, #6A5ACD);" +
                "-fx-background-insets: 0, 0 1 1 0;");
        background.setPadding(new Insets(7, 7, 7, 7));
        root.setCenter(background);


        // Make window still draggable
        // set X and Y coordinates on press, then update them to new locations as mouse moves
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        //----------------------------------------------------------------------------
        // Close/Minimize Buttons and Title Label
        //--------------------------------------
        //Close Button and Handler

        //  Declare close button as Toggle button
        ToggleButton closeButton = new ToggleButton();
        closeButton.setBackground(null);

        // select the pngs that will be displayed
        final Image selected_closePNG = new Image("selectedCloseSmall.png");
        final Image unselected_closePNG = new Image("closeSmall.png");
        final ImageView close = new ImageView();
        closeButton.setGraphic(close);

        // Change from one png to another when hovered over
        close.imageProperty().bind(Bindings
                .when(closeButton.hoverProperty())
                .then(selected_closePNG)
                .otherwise(unselected_closePNG)
        );

        // Set button location and add it to the root
        closeButton.setLayoutX(380);
        closeButton.setLayoutY(15);
        root.getChildren().add(closeButton);

        // Set actionHandler
        EventHandler<ActionEvent> close_event = e -> System.exit(0);

        // Assign the ActionHandler to the event
        closeButton.setOnAction(close_event);

        //--------------------------------------
        //Minimize Button and Handler

        // Declare minimize Button
        ToggleButton minimizeButton = new ToggleButton();
        minimizeButton.setBackground(null);

        // select PNGs to be displayed
        final Image selected_minPNG = new Image("selectedHideSmall.png");
        final Image unselected_minPNG = new Image("hideSmall.png");
        final ImageView hide = new ImageView();
        minimizeButton.setGraphic(hide);

        // select which is image is displayed when hovered over
        hide.imageProperty().bind(Bindings
                .when(minimizeButton.hoverProperty())
                .then(selected_minPNG)
                .otherwise(unselected_minPNG)
        );

        // Set button location and add it to the root
        minimizeButton.setLayoutX(353);
        minimizeButton.setLayoutY(15);
        root.getChildren().add(minimizeButton);

        // create actionHandler
        EventHandler<ActionEvent> min_event = e -> primaryStage.setIconified(true);

        // Assign actionHandler to button
        minimizeButton.setOnAction(min_event);

        //----------------------------------------------------------------------------
        // textPane Pane and Adaptive Label for displaying input

        // textPane
        Pane textPane = new Pane();
        textPane.setStyle("-fx-background-color: #00000025;" +
                         "-fx-background-radius: 20;");
        textPane.setPrefSize(50,75);
        textPane.relocate(100,100);
        background.setTop(textPane);

        // Display text label
        Label displayText = new Label("0.0");
        displayText.setTextFill(Color.WHITE);
        displayText.setMinWidth(350);
        displayText.setAlignment(Pos.CENTER_RIGHT);
        displayText.setFont(Font.font("arial", 36));
        displayText.setLayoutX(10);
        displayText.setLayoutY(15);
        displayText.setMaxSize(300, 15);
        displayText.setContentDisplay(ContentDisplay.LEFT);
        textPane.getChildren().add(displayText);

        CalcRVK calculator = new CalcRVK(displayText);
        //----------------------------------------------------------------------------
        // add GridPane to background pane and start to place panes on it which will later act as buttons

        // Top Grid Pane to contain the first 4 rows
        GridPane buttonPane = new GridPane();
        background.setCenter(buttonPane);
        buttonPane.setPadding(new Insets(20, 20, 0, 20));
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);

        // Bottom Grid Pane which will contain the final row
        GridPane buttonPaneBott = new GridPane();
        background.setBottom(buttonPaneBott);
        buttonPaneBott.setPadding(new Insets(10, 20, 50, 20));
        buttonPaneBott.setHgap(10);


        //----------------------------------------------------------------------------
        // ALL PANES THAT WILL ACT AS BUTTONS

        Pane buttonC = creatButtons("C");
        buttonPane.add(buttonC, 0,0);
        addHoverListener(buttonC);
        addClearListener(buttonC, calculator);

        Pane buttonINV = creatButtons("+/-");
        buttonPane.add(buttonINV, 1, 0);
        addHoverListener(buttonINV);
        addPressListenerNUMPAD(buttonINV, calculator);

        Pane buttonMOD = creatButtons("%");
        buttonPane.add(buttonMOD, 2, 0);
        addHoverListener(buttonMOD);
        addActionListener(buttonMOD, calculator);

        Pane buttonDIV = creatButtons("÷");
        buttonPane.add(buttonDIV, 3, 0);
        addHoverListener(buttonDIV);
        addActionListener(buttonDIV, calculator);

        Pane button7 = creatButtons("7");
        buttonPane.add(button7, 0, 1);
        addHoverListener(button7);
        addPressListenerNUMPAD(button7, calculator);

        Pane button8 = creatButtons("8");
        buttonPane.add(button8, 1, 1);
        addHoverListener(button8);
        addPressListenerNUMPAD(button8, calculator);

        Pane button9 = creatButtons("9");
        buttonPane.add(button9, 2, 1);
        addHoverListener(button9);
        addPressListenerNUMPAD(button9, calculator);

        Pane buttonMULT = creatButtons("×");
        buttonPane.add(buttonMULT, 3, 1);
        addHoverListener(buttonMULT);
        addActionListener(buttonMULT, calculator);

        Pane button4 = creatButtons("4");
        buttonPane.add(button4, 0, 2);
        addHoverListener(button4);
        addPressListenerNUMPAD(button4, calculator);

        Pane button5 = creatButtons("5");
        buttonPane.add(button5, 1, 2);
        addHoverListener(button5);
        addPressListenerNUMPAD(button5, calculator);

        Pane button6 = creatButtons("6");
        buttonPane.add(button6, 2, 2);
        addHoverListener(button6);
        addPressListenerNUMPAD(button6, calculator);

        Pane buttonMIN = creatButtons("-");
        buttonPane.add(buttonMIN, 3, 2);
        addHoverListener(buttonMIN);
        addActionListener(buttonMIN, calculator);

        Pane button1 = creatButtons("1");
        buttonPane.add(button1, 0, 3);
        addHoverListener(button1);
        addPressListenerNUMPAD(button1, calculator);

        Pane button2 = creatButtons("2");
        buttonPane.add(button2, 1, 3);
        addHoverListener(button2);
        addPressListenerNUMPAD(button2, calculator);

        Pane button3 = creatButtons("3");
        buttonPane.add(button3, 2, 3);
        addHoverListener(button3);
        addPressListenerNUMPAD(button3, calculator);

        Pane buttonADD = creatButtons("+");
        buttonPane.add(buttonADD, 3, 3);
        addHoverListener(buttonADD);
        addActionListener(buttonADD, calculator);

        Pane button0 = new Pane();
        button0.setId("0");
        button0.setStyle("-fx-background-color: #00000025;" +
                "-fx-background-radius: 20;");
        Label buttonLabel0 = new Label("0");
        buttonLabel0.setTextFill(Color.WHITE);
        buttonLabel0.setAlignment(Pos.CENTER);
        buttonLabel0.setFont(Font.font("arial", 36));
        buttonLabel0.setMinSize(160, 75);
        button0.getChildren().add(buttonLabel0);
        buttonPaneBott.add(button0, 0, 0);
        addHoverListener(button0);
        addPressListenerNUMPAD(button0, calculator);

        Pane buttonDOT = creatButtons(".");
        buttonPaneBott.add(buttonDOT, 1, 0);
        addHoverListener(buttonDOT);
        addPressListenerNUMPAD(buttonDOT, calculator);

        Pane buttonEQU = creatButtons("=");
        buttonPaneBott.add(buttonEQU, 2, 0);
        addHoverListener(buttonEQU);
        addActionListener(buttonEQU, calculator);


        //----------------------------------------------------------------------------
        // Final Scene Creation and Show

        Scene scene = new Scene(root, 400, 600 );
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("minimizeCalc(clear).png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }
    public static void main(String[] args){
        Application.launch(args);
    }
}
