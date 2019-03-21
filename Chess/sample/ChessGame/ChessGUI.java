package sample.ChessGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.Network.Client;
import sample.Network.ConnectionSide;
import sample.Network.Server;
import sample.Network.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class ChessGUI extends Application {
    public static void main(String[] args) {
        try {
            launch(args);
            System.exit(0);
        }catch (Exception error){
            error.printStackTrace();
            System.exit(0);
        }
    }

    static Stage pStage ;

    public static ConnectionSide connection;
    private Board board;
    private TextArea textArea; //for chats
    private TextArea InfoArea; //for information //TODO : Generating an info box as graphical content to show information of Spots'moves
    private boolean WhitePlayer;
    private boolean OfflineMode = false;

    String Use = "";
    String Pass = "";

    @Override
    public void start(Stage primaryStage)  {
        LogIn();
        pStage = primaryStage ;
        pStage.setTitle("chess Game");
        pStage.getIcons().add(new Image("sample/Assets/Icon/app_icon.png"));
        BorderPane root = new BorderPane();
        Scene MainScene = new Scene(root);
        pStage.setScene(MainScene);

        //add StyleSheet
        MainScene.getStylesheets().add("sample/StyleSheets/stylesheet.css");

        /* selecting coloring of pieces*/
        ChoosePlayerColor();

        //drawing the  chessBoard
        board = new Board(WhitePlayer);
        root.setCenter(board);  // width:400 - length:400

        if(!OfflineMode){
            VBox chatBox = this.GenerateChatBox() ;
            root.setLeft(chatBox);// set the position of the chatBox
            if(WhitePlayer){
                connection = createServer() ;
                textArea.appendText("Connecting to server ...\n");
            } else {
                connection = createClient() ;
                textArea.appendText("Connecting to client ...\n");
                board.setDisable(true); // it is disable until the white player choose a move
            }

            try {
                connection.StartConnection();
            } catch (Exception e) {
                System.err.println("Error: Failed to start connection");
                System.exit(1);
            }

            pStage.show();
        }
        // adding menuBar
        MenuBar menuBar = generateMenuBar();
        root.setTop(menuBar);
    }

    @Override
    public void stop()  {
        try {
            connection.closeConnection();
        }catch (NullPointerException e){
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Source:
     * http://code.makery.ch/blog/javafx-dialogs-official/
     *
     */

    public void LogIn(){

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Sign-Up");
        dialog.setWidth(600);
        dialog.setHeight(600);
        dialog.setResizable(true);
        dialog.setHeaderText("Fill The necessary fields");
        ButtonType loginButtonType = new ButtonType("Sign Up", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        ImageView Chess = new ImageView("sample/Assets/Icon/chess.png");
        Chess.setX(300);
        Chess.setY(300);
        Chess.setFitWidth(80);
        Chess.setFitHeight(80);
        dialog.setGraphic(Chess);
        // Create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 180, 20, 20));
        //grid.getChildren().add(Chess);
        TextField username = new TextField();
        username.setPromptText("Username");
        username.getStyleClass().add("user_name");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        Label user_name =new Label ("User Name");
        user_name.getStyleClass().add("user_name");
        grid.add(user_name, 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        // disabling dialog pad until both field are written
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.getStyleClass().add("user_name");
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {

            Use=username.getText();
            Pass=password.toString();

            User users = new User();
            users.clients.add(Use);
            users.CLIENTS[0]=usernamePassword.getKey();
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });


    }

    /**
     * In this method we create an alert which indicates the mode Of the game and the player
     * Source:
     * https://stackoverflow.com/questions/43031602/how-to-set-a-method-to-a-javafx-alert-button
     * we used this tutorial of clicking the button by making an Optional Class
     */

    public void ChoosePlayerColor(){
        Alert NewGameAlert = new Alert(Alert.AlertType.CONFIRMATION);
        NewGameAlert.setTitle("Start new Game");
        NewGameAlert.setHeaderText(null);
        NewGameAlert.setContentText("Choose Your Mode:");
        ButtonType White = new ButtonType("client-1: (white)");
        ButtonType Black = new ButtonType("client-2: (black)");
        ButtonType Close = new ButtonType("Close");

        NewGameAlert.getButtonTypes().setAll(White,Black,Close);
        Optional<ButtonType> result = NewGameAlert.showAndWait();

        if(result.get()==White){
            WhitePlayer = true;
        }else if(result.get()==Black){
            WhitePlayer=false;
        }else if(result.get()==Close){
            WhitePlayer = true ;
            OfflineMode=true;
        }
    }

    /**
     * Source:
     * https://stackoverflow.com/questions/47183795/how-does-platform-runlater-functions
     * Part of the Explanation in case of necessity situation Caused by Mr.Mohseni :))
     *Now, say something in method A needed to be on the application thread, for example,
     * it updated a UI component, like a Label or a TextField.
     * Then inside your Thread in Method A you would need to put the part that affects the UI into a Platform.runLater(),
     * so that it will run on the Application Thread with the rest of the UI.
     */
    private Server createServer() {

        return new Server(444 , data -> {
            //        Runs whenever data is recieved from the client.
            //        runLater() gives JavaFX time to draw GUI.
            Platform.runLater(() -> {
                if(data instanceof MoveInfo){
                    board.processOpponentMove((MoveInfo) data);
                }else {
                    // else the data is string and is for chat ..
                    // display the chat
                    textArea.appendText(data.toString() + '\n');
                }
            });
        }) ;
    }

    private Client createClient() {
        return new Client("127.0.0.1" , 444 , data -> {
            Platform.runLater(() -> {
                if(data instanceof MoveInfo){
                    board.processOpponentMove((MoveInfo) data);
                } else {
                    textArea.appendText(data.toString() + '\n');
                }
            });
        });
    }

    private VBox GenerateChatBox(){
        //sending messages
        TextField chatField = new TextField();
        chatField.getStyleClass().add("chat-field");
        chatField.setPromptText("Write Your Message:");
        chatField.setOnAction(event -> {
            //specify if server is chatting
            String message = "   " + Use + " :  " ;
            message+=chatField.getText();
            chatField.clear();
            textArea.appendText(message + "\n");

            try {
                connection.send(message);
            } catch (Exception e) {
                textArea.appendText("Field to send your message\n");
            }
        });
        // display the chat ....
        textArea = new TextArea() ;
        textArea.setEditable(false); // Indicates whether this Text can be edited by the user or not.
        textArea.getStyleClass().add("chat-area") ;

        VBox chatBox = new VBox(20, textArea, chatField);
        chatBox.getStyleClass().add("chat-box");

        return chatBox;
    }
    public void onDisplayAbout()
    {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("About this program");
        infoAlert.setHeaderText(null);
        // set window icon
        Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add( new Image("sample/Assets/Icon/about.png") );

        infoAlert.setContentText("This is a Chess Game Programmed by S.Peyman Alavi & AmirHosein Ebrahimi\n" +
                "here we give a warm thank to Dr.Vahidi Which made these unshaped Way for us well Shaped And \n" +
                "a great thanks toward Mr.Mohseni for his unfinished hellps\n" +
                "Rewards!<3");
        infoAlert.setResizable(true);
        infoAlert.showAndWait();
    }

    // Generate main menu bar
    private MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        Menu gameMenu = new Menu("Game");
        menuBar.getMenus().add(gameMenu);
        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        menuItemQuit.setAccelerator( new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN) );
        gameMenu.getItems().add(menuItemQuit);
        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);
        MenuItem menuItemAbout = new MenuItem("About");
        menuItemAbout.setAccelerator( new KeyCodeCombination(KeyCode.F1) );
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }
    public void onQuit()
    {
        Platform.exit();
        System.exit(0);
    }

}
