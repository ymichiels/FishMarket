package fr.univpau.controllers.components.buyer;

import java.io.IOException;
import java.util.function.Function;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;

import fr.univpau.agents.BuyerAgent;
import fr.univpau.utils.Bid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class BuyerController {

    @FXML
    private JFXButton buttonSubscribeBuyer;
    @FXML
    private JFXButton buttonCancelBuyer;
    @FXML
    private Label labelTitleSection;
    @FXML
    private JFXButton buttonPopupInfoSection;
    @FXML
    private JFXButton buttonBack;
    
  //TableView1
    @FXML
    private JFXTreeTableView<Bid> treeTableViewBuyer;
    @FXML
    private JFXTreeTableColumn<Bid, String> column1SellerName;
    @FXML
    private JFXTreeTableColumn<Bid, String> column2FishName;
    @FXML
    private JFXTreeTableColumn<Bid, String> column3BuyerName;
    @FXML
    private JFXTreeTableColumn<Bid, Integer> column4Price;
    @FXML
    private JFXTreeTableColumn<Bid, Boolean> column5IsFinished;
    
    private BuyerAgent _buyer;
	private static ObservableList<TreeItem<Bid>> subscribedEncheres;

    public void initialize() {
    	initButtonTitle();
    	this.setLabelTitleSection("Achat de poisson");
    }

    protected void initButtonTitle() {
        SVGGlyph arrow = new SVGGlyph(0,
                "FULLSCREEN",
                "M402.746 877.254l-320-320c-24.994-24.992-24.994-65.516 0-90.51l320-320c24.994-24.992 65.516-24.992 90.51 0 24.994 24.994 "
                        + "24.994 65.516 0 90.51l-210.746 210.746h613.49c35.346 0 64 28.654 64 64s-28.654 64-64 64h-613.49l210.746 210.746c12.496 "
                        + "12.496 18.744 28.876 18.744 45.254s-6.248 32.758-18.744 45.254c-24.994 24.994-65.516 24.994-90.51 0z",
                Color.WHITE);
        arrow.setSize(20, 16);
        buttonBack.setGraphic(arrow);
        buttonBack.setRipplerFill(Color.WHITE);

        SVGGlyph help = new SVGGlyph(0,
                "FULLSCREEN",
                "M15.047 11.25q0.938-0.938 0.938-2.25 0-1.641-1.172-2.813t-2.813-1.172-2.813 1.172-1.172 2.813h1.969q0-0.797 0.609-1.406t1.406-0.609 1.406 0.609 0.609 1.406-0.609 1.406l-1.219 1.266q-1.172 1.266-1.172 2.813v0.516h1.969q0-1.547 1.172-2.813zM12.984 18.984v-1.969h-1.969v1.969h1.969zM12 2.016q4.125 0 7.055 2.93t2.93 7.055-2.93 7.055-7.055 2.93-7.055-2.93-2.93-7.055 2.93-7.055 7.055-2.93z",
                Color.WHITE);
        help.setSize(24, 24);
        buttonPopupInfoSection.setGraphic(help);
        buttonPopupInfoSection.setRipplerFill(Color.WHITE);
    }
    
    private <T> void setupCellValueFactory(JFXTreeTableColumn<Bid, T> column, Function<Bid, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Bid, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
    
    private void setupEditableTableViewBid() {
        setupCellValueFactory(column1SellerName, b -> b.sellerName);
        setupCellValueFactory(column2FishName, b -> b.fishName);
        setupCellValueFactory(column3BuyerName, b -> b.buyerName);
        setupCellValueFactory(column4Price, b -> b.price.asObject());
        setupCellValueFactory(column5IsFinished, b -> b.isFinished.asObject());
        
        column1SellerName.setOnEditCommit((TreeTableColumn.CellEditEvent<Bid, String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setSellerName(t.getNewValue());
        });
        column2FishName.setOnEditCommit((TreeTableColumn.CellEditEvent<Bid, String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setFishName(t.getNewValue());
        });
        column3BuyerName.setOnEditCommit((TreeTableColumn.CellEditEvent<Bid, String> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setBuyerName(t.getNewValue());
        });
        column4Price.setOnEditCommit((TreeTableColumn.CellEditEvent<Bid, Integer> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setPrice(t.getNewValue());
        });
        column5IsFinished.setOnEditCommit((TreeTableColumn.CellEditEvent<Bid, Boolean> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setIsFinished(t.getNewValue());
        });
    }
    
    @FXML
    private void handleButtonSubscribe(ActionEvent event) {
    	subscribedEncheres = treeTableViewBuyer.getSelectionModel().getSelectedItems();
    	
    	treeTableViewBuyer.getSelectionModel().getSelectedItems().clear();
    	// vBox.getChildren().remove(subscribe);
    	
    	treeTableViewBuyer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	treeTableViewBuyer.getSelectionModel().getSelectedItems().addAll(subscribedEncheres);
        System.out.println("You clicked me!");
    }
    
    @FXML
    private void handleButtonCancel(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    protected void setupNonEditableObjects() {
    	treeTableViewBuyer.setEditable(false);
    }
    
    public void setLabelTitleSection(String label) {
    	labelTitleSection.setText(label);
    }
}